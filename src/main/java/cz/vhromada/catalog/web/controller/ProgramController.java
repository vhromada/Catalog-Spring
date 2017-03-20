package cz.vhromada.catalog.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.facade.ProgramFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.ProgramFO;
import cz.vhromada.converter.Converter;
import cz.vhromada.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A class represents controller for programs.
 *
 * @author Vladimir Hromada
 */
@Controller("programController")
@RequestMapping("/programs")
public class ProgramController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/programs/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Program doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Facade for programs
     */
    private final ProgramFacade programFacade;

    /**
     * Converter
     */
    private final Converter converter;

    /**
     * Creates a new instance of ProgramController.
     *
     * @param programFacade facade for programs
     * @param converter  converter
     * @throws IllegalArgumentException if facade for programs is null
     *                                  or converter is null
     */
    @Autowired
    public ProgramController(final ProgramFacade programFacade,
            final Converter converter) {
        Assert.notNull(programFacade, "Facade for programs mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.programFacade = programFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of programs
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(programFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of programs.
     *
     * @param model model
     * @return view for page with list of programs
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping(value = { "", "/", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Program>> programsResult = programFacade.getAll();
        final Result<Integer> mediaCountResult = programFacade.getTotalMediaCount();
        processResults(programsResult, mediaCountResult);

        model.addAttribute("programs", programsResult.getData());
        model.addAttribute("mediaCount", mediaCountResult.getData());
        model.addAttribute("title", "Programs");

        return "programsList";
    }

    /**
     * Shows page for adding program.
     *
     * @param model model
     * @return view for page for adding program
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new ProgramFO(), "Add program", "programsAdd");
    }

    /**
     * Process adding program.
     *
     * @param model        model
     * @param createButton button create
     * @param program         FO for program
     * @param errors       errors
     * @return view for redirect to page with list of programs (no errors) or view for page for adding program (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for program is null
     *                                                               or errors are null
     *                                                               or ID isn't null
     */
    @PostMapping("/add")
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("program") @Valid final ProgramFO program, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(program, "FO for program mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(program.getId(), "ID must be null.");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, program, "Add program", "programsAdd");
            }
            processResults(programFacade.add(converter.convert(program, Program.class)));
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing program.
     *
     * @param model model
     * @param id    ID of editing program
     * @return view for page for editing program
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Program> result = programFacade.get(id);
        processResults(result);

        final Program program = result.getData();
        if (program != null) {
            return createFormView(model, converter.convert(program, ProgramFO.class), "Edit program", "programsEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing program.
     *
     * @param model        model
     * @param createButton button create
     * @param program      FO for program
     * @param errors       errors
     * @return view for redirect to page with list of programs (no errors) or view for page for editing program (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for program is null
     *                                                               or errors are null
     *                                                               or ID is null
     * @throws IllegalRequestException                               if program doesn't exist
     */
    @PostMapping("/edit")
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("program") @Valid final ProgramFO program, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(program, "FO for program mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(program.getId(), NULL_ID_MESSAGE);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, program, "Edit program", "programsEdit");
            }
            processResults(programFacade.update(processProgram(converter.convert(program, Program.class))));
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating program.
     *
     * @param id ID of duplicating program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(programFacade.duplicate(getProgram(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing program.
     *
     * @param id ID of removing program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(programFacade.remove(getProgram(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving program up.
     *
     * @param id ID of moving program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(programFacade.moveUp(getProgram(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving program down.
     *
     * @param id ID of moving program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(programFacade.moveDown(getProgram(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of programs
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(programFacade.updatePositions());

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model model
     * @param program  FO for program
     * @param title page's title
     * @param view  returning view
     * @return page's view with form
     */
    private static String createFormView(final Model model, final ProgramFO program, final String title, final String view) {
        model.addAttribute("program", program);
        model.addAttribute("title", title);

        return view;
    }

    /**
     * Returns program with ID.
     *
     * @param id ID
     * @return program with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    private Program getProgram(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Program program = new Program();
        program.setId(id);

        return processProgram(program);
    }

    /**
     * Returns processed program.
     *
     * @param program for processing
     * @return processed program
     * @throws IllegalRequestException if program doesn't exist
     */
    private Program processProgram(final Program program) {
        final Result<Program> programResult = programFacade.get(program.getId());
        processResults(programResult);

        if (programResult.getData() != null) {
            return program;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
