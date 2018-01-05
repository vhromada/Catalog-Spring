package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.vhromada.catalog.common.Language;
import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.MovieFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.fo.TimeFO;
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

/**
 * A class represents controller for movies.
 *
 * @author Vladimir Hromada
 */
@Controller("movieController")
@RequestMapping("/movies")
public class MovieController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/movies/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Movie doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Facade for movies
     */
    private final MovieFacade movieFacade;

    /**
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Converter
     */
    private final Converter converter;

    /**
     * Creates a new instance of MovieController.
     *
     * @param movieFacade facade for movies
     * @param genreFacade facade for genres
     * @param converter   converter
     * @throws IllegalArgumentException if facade for movies is null
     *                                  or facade for genres is null
     *                                  or converter is null
     */
    @Autowired
    public MovieController(final MovieFacade movieFacade,
            final GenreFacade genreFacade,
            final Converter converter) {
        Assert.notNull(movieFacade, "Facade for movies mustn't be null.");
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.movieFacade = movieFacade;
        this.genreFacade = genreFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of movies
     */
    @GetMapping("/new")
    public String processNew() {
        movieFacade.newData();

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of movies.
     *
     * @param model model
     * @return view for page with list of movies
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping({ "", "/", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Movie>> moviesResult = movieFacade.getAll();
        final Result<Integer> mediaCountResult = movieFacade.getTotalMediaCount();
        final Result<Time> totalLengthResult = movieFacade.getTotalLength();
        processResults(moviesResult, mediaCountResult, totalLengthResult);

        model.addAttribute("movies", moviesResult.getData());
        model.addAttribute("mediaCount", mediaCountResult.getData());
        model.addAttribute("totalLength", totalLengthResult.getData());
        model.addAttribute("title", "Movies");

        return "movie/index";
    }

    /**
     * Shows page for adding movie.
     *
     * @param model model
     * @return view for page for adding movie
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createAddFormView(model, new MovieFO());
    }

    /**
     * Process adding movie.
     *
     * @param model   model
     * @param movieFO FO for movie
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for adding movie (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for movie is null
     *                                  or errors are null
     *                                  or HTTP request is null
     *                                  or ID isn't null
     */
    @PostMapping("/add")
    public String processAdd(final Model model, @ModelAttribute("movie") final @Valid MovieFO movieFO, final Errors errors, final HttpServletRequest request) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(movieFO, "FO for movie mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(request, "Request mustn't be null.");
        Assert.isNull(movieFO.getId(), "ID must be null.");

        if (request.getParameter("create") != null) {
            if (errors.hasErrors()) {
                return createAddFormView(model, movieFO);
            }
            final Movie movie = converter.convert(movieFO, Movie.class);
            if (movie.getSubtitles() == null) {
                movie.setSubtitles(new ArrayList<>());
            }
            movie.setGenres(getGenres(movie.getGenres()));
            processResults(movieFacade.add(movie));
        }

        if (request.getParameter("addMedium") != null) {
            movieFO.getMedia().add(new TimeFO());

            return createAddFormView(model, movieFO);
        }

        final Integer index = getRemoveIndex(request);
        if (index != null) {
            movieFO.getMedia().remove(index.intValue());

            return createAddFormView(model, movieFO);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing movie.
     *
     * @param model model
     * @param id    ID of editing movie
     * @return view for page for editing movie
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Movie> result = movieFacade.get(id);
        processResults(result);

        final Movie movie = result.getData();
        if (movie != null) {
            return createEditFormView(model, converter.convert(movie, MovieFO.class));
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing movie.
     *
     * @param model   model
     * @param movieFO FO for movie
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for editing movie (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for movie is null
     *                                  or errors are null
     *                                  or HTTP request is null
     *                                  or ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    @PostMapping("/edit")
    public String processEdit(final Model model, @ModelAttribute("movie") final @Valid MovieFO movieFO, final Errors errors, final HttpServletRequest request) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(movieFO, "FO for movie mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(request, "Request mustn't be null.");
        Assert.notNull(movieFO.getId(), NULL_ID_MESSAGE);

        if (request.getParameter("update") != null) {
            if (errors.hasErrors()) {
                return createEditFormView(model, movieFO);
            }

            final Movie movie = processMovie(converter.convert(movieFO, Movie.class));
            if (movie.getSubtitles() == null) {
                movie.setSubtitles(new ArrayList<>());
            }
            movie.setGenres(getGenres(movie.getGenres()));
            processResults(movieFacade.update(movie));
        }

        if (request.getParameter("add") != null) {
            movieFO.getMedia().add(new TimeFO());

            return createEditFormView(model, movieFO);
        }

        final Integer index = getRemoveIndex(request);
        if (index != null) {
            movieFO.getMedia().remove(index.intValue());

            return createEditFormView(model, movieFO);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating movie.
     *
     * @param id ID of duplicating movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(movieFacade.duplicate(getMovie(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing movie.
     *
     * @param id ID of removing movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(movieFacade.remove(getMovie(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving movie up.
     *
     * @param id ID of moving movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(movieFacade.moveUp(getMovie(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving movie down.
     *
     * @param id ID of moving movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(movieFacade.moveDown(getMovie(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of movies
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(movieFacade.updatePositions());

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns index of removing media.
     *
     * @param request HTTP request
     * @return index of removing media
     */
    private static Integer getRemoveIndex(final HttpServletRequest request) {
        Integer index = null;
        for (final Enumeration<String> names = request.getParameterNames(); names.hasMoreElements() && index == null; ) {
            final String name = names.nextElement();
            if (name.startsWith("removeMedium")) {
                index = Integer.parseInt(name.substring(12));
            }
        }

        return index;
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param movie  FO for movie
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private String createFormView(final Model model, final MovieFO movie, final String title, final String action) {
        final Result<List<Genre>> result = genreFacade.getAll();
        processResults(result);

        model.addAttribute("movie", movie);
        model.addAttribute("title", title);
        model.addAttribute("languages", Language.values());
        model.addAttribute("subtitles", new Language[]{ Language.CZ, Language.EN });
        model.addAttribute("genres", result.getData());
        model.addAttribute("action", action);

        return "movie/form";
    }

    /**
     * Returns page's view with form for adding movie.
     *
     * @param model model
     * @param movie FO for movie
     * @return page's view with form for adding movie
     */
    private String createAddFormView(final Model model, final MovieFO movie) {
        return createFormView(model, movie, "Add movie", "add");
    }

    /**
     * Returns page's view with form for editing movie.
     *
     * @param model model
     * @param movie FO for movie
     * @return page's view with form for editing movie
     */
    private String createEditFormView(final Model model, final MovieFO movie) {
        return createFormView(model, movie, "Edit movie", "edit");
    }

    /**
     * Returns movie with ID.
     *
     * @param id ID
     * @return movie with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if movie doesn't exist
     */
    private Movie getMovie(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Movie movie = new Movie();
        movie.setId(id);

        return processMovie(movie);
    }

    /**
     * Returns processed movie.
     *
     * @param movie for processing
     * @return processed movie
     * @throws IllegalRequestException if movie doesn't exist
     */
    private Movie processMovie(final Movie movie) {
        final Result<Movie> movieResult = movieFacade.get(movie.getId());
        processResults(movieResult);

        if (movieResult.getData() != null) {
            return movie;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

    /**
     * Returns genres.
     *
     * @param source list of genres
     * @return genres
     */
    private List<Genre> getGenres(final List<Genre> source) {
        return source.stream().map(genre -> {
            final Result<Genre> result = genreFacade.get(genre.getId());
            processResults(result);

            return result.getData();
        }).collect(Collectors.toList());
    }

}
