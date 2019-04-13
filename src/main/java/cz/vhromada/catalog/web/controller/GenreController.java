package cz.vhromada.catalog.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.GenreFO;
import cz.vhromada.catalog.web.mapper.GenreMapper;
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
 * A class represents controller for genres.
 *
 * @author Vladimir Hromada
 */
@Controller("genreController")
@RequestMapping("/genres")
public class GenreController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/genres/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Genre doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Mapper for genres
     */
    private final GenreMapper genreMapper;

    /**
     * Creates a new instance of GenreController.
     *
     * @param genreFacade facade for genres
     * @throws IllegalArgumentException if facade for genres is null
     */
    @Autowired
    public GenreController(final GenreFacade genreFacade) {
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");

        this.genreFacade = genreFacade;
        this.genreMapper = Mappers.getMapper(GenreMapper.class);
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of genres
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(genreFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of genres.
     *
     * @param model model
     * @return view for page with list of genres
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Genre>> result = genreFacade.getAll();
        processResults(result);

        model.addAttribute("genres", result.getData());
        model.addAttribute("title", "Genres");

        return "genre/index";
    }

    /**
     * Shows page for adding genre.
     *
     * @param model model
     * @return view for page for adding genre
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new GenreFO(), "Add genre", "add");
    }

    /**
     * Process adding genre.
     *
     * @param model  model
     * @param genre  FO for genre
     * @param errors errors
     * @return view for redirect to page with list of genres (no errors) or view for page for adding genre (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for genre is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("genre") final @Valid GenreFO genre, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(genre, "FO for genre mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(genre.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, genre, "Add genre", "add");
        }
        processResults(genreFacade.add(genreMapper.mapBack(genre)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding genre.
     *
     * @return view for redirect to page with list of genres
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
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
     * @throws IllegalRequestException  if genre doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Genre> result = genreFacade.get(id);
        processResults(result);

        final Genre genre = result.getData();
        if (genre != null) {
            return createFormView(model, genreMapper.map(genre), "Edit genre", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing genre.
     *
     * @param model  model
     * @param genre  FO for genre
     * @param errors errors
     * @return view for redirect to page with list of genres (no errors) or view for page for editing genre (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for genre is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if genre doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("genre") final @Valid GenreFO genre, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(genre, "FO for genre mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(genre.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, genre, "Edit genre", "edit");
        }
        processResults(genreFacade.update(processGenre(genreMapper.mapBack(genre))));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel editing genre.
     *
     * @return view for redirect to page with list of genres
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String cancelEdit() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating genre.
     *
     * @param id ID of duplicating genre
     * @return view for redirect to page with list of genres
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if genre doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(genreFacade.duplicate(getGenre(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing genre.
     *
     * @param id ID of removing genre
     * @return view for redirect to page with list of genres
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if genre doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(genreFacade.remove(getGenre(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving genre up.
     *
     * @param id ID of moving genre
     * @return view for redirect to page with list of genres
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if genre doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(genreFacade.moveUp(getGenre(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving genre down.
     *
     * @param id ID of moving genre
     * @return view for redirect to page with list of genres
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if genre doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(genreFacade.moveDown(getGenre(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of genres
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(genreFacade.updatePositions());

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param genre  FO for genre
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final GenreFO genre, final String title, final String action) {
        model.addAttribute("genre", genre);
        model.addAttribute("title", title);
        model.addAttribute("action", action);

        return "genre/form";
    }

    /**
     * Returns genre with ID.
     *
     * @param id ID
     * @return genre with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if genre doesn't exist
     */
    private Genre getGenre(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Genre genre = new Genre();
        genre.setId(id);

        return processGenre(genre);
    }

    /**
     * Returns processed genre.
     *
     * @param genre for processing
     * @return processed genre
     * @throws IllegalRequestException if genre doesn't exist
     */
    private Genre processGenre(final Genre genre) {
        final Result<Genre> genreResult = genreFacade.get(genre.getId());
        processResults(genreResult);

        if (genreResult.getData() != null) {
            return genre;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
