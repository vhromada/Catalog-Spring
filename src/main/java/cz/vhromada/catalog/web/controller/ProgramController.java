package cz.vhromada.catalog.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.facade.ProgramFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.ProgramFO;
import cz.vhromada.catalog.web.mapper.ProgramMapper;
import cz.vhromada.validation.result.Result;

import org.mapstruct.factory.Mappers;
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
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

    /**
     * Facade for programs
     */
    private final ProgramFacade programFacade;

    /**
     * Mapper for programs
     */
    private final ProgramMapper programMapper;

    /**
     * Creates a new instance of ProgramController.
     *
     * @param programFacade facade for programs
     * @throws IllegalArgumentException if facade for programs is null
     */
    @Autowired
    public ProgramController(final ProgramFacade programFacade) {
        Assert.notNull(programFacade, "Facade for programs mustn't be null.");

        this.programFacade = programFacade;
        this.programMapper = Mappers.getMapper(ProgramMapper.class);
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
    @GetMapping({ "", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Program>> programsResult = programFacade.getAll();
        final Result<Integer> mediaCountResult = programFacade.getTotalMediaCount();
        processResults(programsResult, mediaCountResult);

        model.addAttribute("programs", programsResult.getData());
        model.addAttribute("mediaCount", mediaCountResult.getData());
        model.addAttribute(TITLE_ATTRIBUTE, "Programs");

        return "program/index";
    }

    /**
     * Shows page with detail of program.
     *
     * @param model model
     * @param id    ID of editing program
     * @return view for page with detail of program
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @GetMapping("/{id}/detail")
    public String showDetail(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Program> result = programFacade.get(id);
        processResults(result);

        final Program program = result.getData();
        if (program != null) {
            model.addAttribute("program", program);
            model.addAttribute(TITLE_ATTRIBUTE, "Program detail");

            return "program/detail";
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
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

        return createFormView(model, new ProgramFO(), "Add program", "add");
    }

    /**
     * Process adding program.
     *
     * @param model   model
     * @param program FO for program
     * @param errors  errors
     * @return view for redirect to page with list of programs (no errors) or view for page for adding program (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for program is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("program") final @Valid ProgramFO program, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(program, "FO for program mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(program.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, program, "Add program", "add");
        }
        processResults(programFacade.add(programMapper.mapBack(program)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process adding program.
     *
     * @return view for redirect to page with list of programs
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
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
            return createFormView(model, programMapper.map(program), "Edit program", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing program.
     *
     * @param model   model
     * @param program FO for program
     * @param errors  errors
     * @return view for redirect to page with list of programs (no errors) or view for page for editing program (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for program is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if program doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("program") final @Valid ProgramFO program, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(program, "FO for program mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(program.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, program, "Edit program", "edit");
        }
        processResults(programFacade.update(processProgram(programMapper.mapBack(program))));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel editing program.
     *
     * @return view for redirect to page with list of programs
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String cancelEdit() {
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
     * @param model   model
     * @param program FO for program
     * @param title   page's title
     * @param action  action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final ProgramFO program, final String title, final String action) {
        model.addAttribute("program", program);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("action", action);

        return "program/form";
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
