package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.GenreFO;
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
 * A class represents controller for genres.
 *
 * @author Vladimir Hromada
 */
@Controller("genreController")
@RequestMapping("/genres")
public class GenreController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/genres/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for genre doesn't exist.";

    /**
     * Model argument
     */
    private static final String MODEL_ARGUMENT = "Model";

    /**
     * ID argument
     */
    private static final String ID_ARGUMENT = "ID";

    /**
     * Facade for genres
     */
    private GenreFacade genreFacade;

    /**
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of GenreController.
     *
     * @param genreFacade facade for genres
     * @param converter   converter
     * @throws IllegalArgumentException if facade for genres is null
     *                                  or converter is null
     */
    @Autowired
    public GenreController(final GenreFacade genreFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(genreFacade, "Facade for genres");
        Validators.validateArgumentNotNull(converter, "converter");

        this.genreFacade = genreFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of genres
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String processNew() {
        genreFacade.newData();

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of genres.
     *
     * @param model model
     * @return view for page with list of genres
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        model.addAttribute("genres", new ArrayList<>(genreFacade.getGenres()));
        model.addAttribute("title", "Genres");

        return "genresList";
    }

    /**
     * Shows page for adding genre.
     *
     * @param model model
     * @return view for page for adding genre
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        return createFormView(model, new GenreFO(), "Add genre", "genresAdd");
    }

    /**
     * Process adding genre.
     *
     * @param model        model
     * @param createButton button create
     * @param genre        FO for genre
     * @param errors       errors
     * @return view for redirect to page with list of genres (no errors) or view for page for adding genre (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for genre is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("genre") @Valid final GenreFO genre, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(genre, "FO for genre");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(genre.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, genre, "Add genre", "genresAdd");
            }
            genreFacade.add(converter.convert(genre, GenreTO.class));
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing genre.
     *
     * @param model model
     * @param id    ID of editing genre
     * @return view for page for editing genre
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for genre doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GenreTO genre = genreFacade.getGenre(id);

        if (genre != null) {
            return createFormView(model, converter.convert(genre, GenreFO.class), "Edit genre", "genresEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing genre.
     *
     * @param model        model
     * @param createButton button create
     * @param genre        FO for genre
     * @param errors       errors
     * @return view for redirect to page with list of genres (no errors) or view for page for editing genre (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for genre is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for genre doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("genre") @Valid final GenreFO genre, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(genre, "FO for genre");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(genre.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, genre, "Edit genre", "genresEdit");
            }

            final GenreTO genreTO = converter.convert(genre, GenreTO.class);
            if (genreFacade.exists(genreTO)) {
                genreFacade.update(genreTO);
            } else {
                throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
            }
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating genre.
     *
     * @param id ID of duplicating genre
     * @return view for redirect to page with list of genres
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for genre doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GenreTO genre = new GenreTO();
        genre.setId(id);
        if (genreFacade.exists(genre)) {
            genreFacade.duplicate(genre);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing genre.
     *
     * @param id ID of removing genre
     * @return view for redirect to page with list of genres
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for genre doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GenreTO genre = new GenreTO();
        genre.setId(id);
        if (genreFacade.exists(genre)) {
            genreFacade.remove(genre);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model model
     * @param genre FO for genre
     * @param title page's title
     * @param view  returning view
     * @return page's view with form
     */
    private static String createFormView(final Model model, final GenreFO genre, final String title, final String view) {
        model.addAttribute("genre", genre);
        model.addAttribute("title", title);

        return view;
    }

}
