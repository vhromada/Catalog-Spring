package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import cz.vhromada.catalog.facade.ProgramFacade;
import cz.vhromada.catalog.facade.to.ProgramTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.ProgramFO;
import cz.vhromada.converters.Converter;
import cz.vhromada.validators.Validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A class represents controller for programs.
 *
 * @author Vladimir Hromada
 */
@Controller("programController")
@RequestMapping("/programs")
public class ProgramController {

    /**
     * Facade for programs
     */
    private ProgramFacade programFacade;

    /**
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of ProgramController.
     *
     * @param programFacade facade for programs
     * @param converter     converter
     * @throws IllegalArgumentException if facade for programs is null
     *                                  or converter is null
     */
    @Autowired
    public ProgramController(final ProgramFacade programFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(programFacade, "Facade for programs");
        Validators.validateArgumentNotNull(converter, "converter");

        this.programFacade = programFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of programs
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String processNew() {
        programFacade.newData();

        return "redirect:/programs/list";
    }

    /**
     * Shows page with list of programs.
     *
     * @param model model
     * @return view for page with list of programs
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, "Model");

        model.addAttribute("programs", new ArrayList<>(programFacade.getPrograms()));
        model.addAttribute("mediaCount", programFacade.getTotalMediaCount());
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
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, "Model");

        return createFormView(model, new ProgramFO(), "Add program", "programsAdd");
    }

    /**
     * Process adding program.
     *
     * @param model        model
     * @param createButton button create
     * @param program      FO for program
     * @param errors       errors
     * @return view for redirect to page with list of programs (no errors) or view for page for adding program (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for program is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("program") @Valid final ProgramFO program, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(program, "FO for program");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(program.getId(), "ID");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, program, "Add program", "programsAdd");
            }
            programFacade.add(converter.convert(program, ProgramTO.class));
        }

        return "redirect:/programs/list";
    }

    /**
     * Shows page for editing program.
     *
     * @param model model
     * @param id    ID of editing program
     * @return view for page for editing program
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for program doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(id, "ID");

        final ProgramTO program = programFacade.getProgram(id);

        if (program != null) {
            return createFormView(model, converter.convert(program, ProgramFO.class), "Edit program", "programsEdit");
        } else {
            throw new IllegalRequestException("TO for program doesn't exist.");
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
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for program doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("program") @Valid final ProgramFO program, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(program, "FO for program");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(program.getId(), "ID");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, program, "Edit program", "programsEdit");
            }

            final ProgramTO programTO = converter.convert(program, ProgramTO.class);
            if (programFacade.exists(programTO)) {
                programFacade.update(programTO);
            } else {
                throw new IllegalRequestException("TO for program doesn't exist.");
            }
        }

        return "redirect:/programs/list";
    }

    /**
     * Process duplicating program.
     *
     * @param id ID of duplicating program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for program doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final ProgramTO program = new ProgramTO();
        program.setId(id);
        if (programFacade.exists(program)) {
            programFacade.duplicate(program);
        } else {
            throw new IllegalRequestException("TO for program doesn't exist.");
        }

        return "redirect:/programs/list";
    }

    /**
     * Process removing program.
     *
     * @param id ID of removing program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for program doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final ProgramTO program = new ProgramTO();
        program.setId(id);
        if (programFacade.exists(program)) {
            programFacade.remove(program);
        } else {
            throw new IllegalRequestException("TO for program doesn't exist.");
        }

        return "redirect:/programs/list";
    }

    /**
     * Process moving program up.
     *
     * @param id ID of moving program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for program doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final ProgramTO program = new ProgramTO();
        program.setId(id);
        if (programFacade.exists(program)) {
            programFacade.moveUp(program);
        } else {
            throw new IllegalRequestException("TO for program doesn't exist.");
        }

        return "redirect:/programs/list";
    }

    /**
     * Process moving program down.
     *
     * @param id ID of moving program
     * @return view for redirect to page with list of programs
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for program doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final ProgramTO program = new ProgramTO();
        program.setId(id);
        if (programFacade.exists(program)) {
            programFacade.moveDown(program);
        } else {
            throw new IllegalRequestException("TO for program doesn't exist.");
        }

        return "redirect:/programs/list";
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of programs
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        programFacade.updatePositions();

        return "redirect:/programs/list";
    }

    /**
     * Returns page's view with form.
     *
     * @param model   model
     * @param program FO for program
     * @param title   page's title
     * @param view    returning view
     * @return page's view with form
     */
    private static String createFormView(final Model model, final ProgramFO program, final String title, final String view) {
        model.addAttribute("program", program);
        model.addAttribute("title", title);

        return view;
    }

}
