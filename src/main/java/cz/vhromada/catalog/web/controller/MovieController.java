package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.MovieFacade;
import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.MovieTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.fo.TimeFO;
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

/**
 * A class represents controller for movies.
 *
 * @author Vladimir Hromada
 */
@Controller("movieController")
@RequestMapping("/movies")
public class MovieController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/movies/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for movie doesn't exist.";

    /**
     * Model argument
     */
    private static final String MODEL_ARGUMENT = "Model";

    /**
     * ID argument
     */
    private static final String ID_ARGUMENT = "ID";

    /**
     * Button parameter value
     */
    private static final String BUTTON_PARAMETER_VALUE = "Submit";

    /**
     * Facade for movies
     */
    private MovieFacade movieFacade;

    /**
     * Facade for genres
     */
    private GenreFacade genreFacade;

    /**
     * Converter
     */
    private Converter converter;

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
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(movieFacade, "Facade for movies");
        Validators.validateArgumentNotNull(genreFacade, "Facade for genres");
        Validators.validateArgumentNotNull(converter, "converter");

        this.movieFacade = movieFacade;
        this.genreFacade = genreFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of movies
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
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
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        model.addAttribute("movies", new ArrayList<>(movieFacade.getMovies()));
        model.addAttribute("mediaCount", movieFacade.getTotalMediaCount());
        model.addAttribute("totalLength", movieFacade.getTotalLength());
        model.addAttribute("title", "Movies");

        return "moviesList";
    }

    /**
     * Shows page for adding movie.
     *
     * @param model model
     * @return view for page for adding movie
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        return createAddFormView(model, new MovieFO());
    }

    /**
     * Process adding movie.
     *
     * @param model   model
     * @param movie   FO for movie
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for adding movie (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for movie is null
     *                                                               or errors are null
     *                                                               or HTTP request is null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @ModelAttribute("movie") @Valid final MovieFO movie, final Errors errors, final HttpServletRequest request) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(movie, "FO for movie");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateArgumentNotNull(request, "Request");
        Validators.validateNull(movie.getId(), ID_ARGUMENT);

        if (BUTTON_PARAMETER_VALUE.equals(request.getParameter("create"))) {
            if (errors.hasErrors()) {
                return createAddFormView(model, movie);
            }
            final MovieTO movieTO = converter.convert(movie, MovieTO.class);
            if (movieTO.getSubtitles() == null) {
                movieTO.setSubtitles(new ArrayList<>());
            }
            movieTO.setGenres(getGenres(movieTO.getGenres()));
            movieFacade.add(movieTO);
        }

        if (BUTTON_PARAMETER_VALUE.equals(request.getParameter("add"))) {
            movie.getMedia().add(new TimeFO());

            return createAddFormView(model, movie);
        }

        final Integer index = getRemoveIndex(request);
        if (index != null) {
            movie.getMedia().remove(index.intValue());

            return createAddFormView(model, movie);
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
     * @throws IllegalRequestException  if TO for movie doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MovieTO movie = movieFacade.getMovie(id);

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
     * @param movie   FO for movie
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for editing movie (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for movie is null
     *                                                               or errors are null
     *                                                               or HTTP request is null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for movie doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @ModelAttribute("movie") @Valid final MovieFO movie, final Errors errors, final HttpServletRequest request) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(movie, "FO for movie");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateArgumentNotNull(request, "Request");
        Validators.validateNotNull(movie.getId(), ID_ARGUMENT);

        if (BUTTON_PARAMETER_VALUE.equals(request.getParameter("create"))) {
            if (errors.hasErrors()) {
                return createEditFormView(model, movie);
            }

            final MovieTO movieTO = converter.convert(movie, MovieTO.class);
            if (movieFacade.getMovie(movieTO.getId()) != null) {
                if (movieTO.getSubtitles() == null) {
                    movieTO.setSubtitles(new ArrayList<>());
                }
                movieTO.setGenres(getGenres(movieTO.getGenres()));
                movieFacade.update(movieTO);
            } else {
                throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
            }
        }

        if (BUTTON_PARAMETER_VALUE.equals(request.getParameter("add"))) {
            movie.getMedia().add(new TimeFO());

            return createEditFormView(model, movie);
        }

        final Integer index = getRemoveIndex(request);
        if (index != null) {
            movie.getMedia().remove(index.intValue());

            return createEditFormView(model, movie);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating movie.
     *
     * @param id ID of duplicating movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for movie doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.getMovie(id) != null) {
            movieFacade.duplicate(movie);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing movie.
     *
     * @param id ID of removing movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for movie doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.getMovie(id) != null) {
            movieFacade.remove(movie);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving movie up.
     *
     * @param id ID of moving movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for movie doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.getMovie(id) != null) {
            movieFacade.moveUp(movie);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving movie down.
     *
     * @param id ID of moving movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for movie doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.getMovie(id) != null) {
            movieFacade.moveDown(movie);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of movies
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        movieFacade.updatePositions();

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model model
     * @param movie FO for movie
     * @param title page's title
     * @param view  returning view
     * @return page's view with form
     */
    private String createFormView(final Model model, final MovieFO movie, final String title, final String view) {
        model.addAttribute("movie", movie);
        model.addAttribute("title", title);
        model.addAttribute("languages", Language.values());
        model.addAttribute("subtitles", new Language[]{ Language.CZ, Language.EN });
        model.addAttribute("genres", genreFacade.getGenres());

        return view;
    }

    /**
     * Returns page's view with form for adding movie.
     *
     * @param model model
     * @param movie FO for movie
     * @return page's view with form for adding movie
     */
    private String createAddFormView(final Model model, final MovieFO movie) {
        return createFormView(model, movie, "Add movie", "moviesAdd");
    }

    /**
     * Returns page's view with form for editing movie.
     *
     * @param model model
     * @param movie FO for movie
     * @return page's view with form for editing movie
     */
    private String createEditFormView(final Model model, final MovieFO movie) {
        return createFormView(model, movie, "Edit movie", "moviesEdit");
    }

    /**
     * Returns genres.
     *
     * @param source list of genres
     * @return genres
     */
    private List<GenreTO> getGenres(final List<GenreTO> source) {
        return source.stream().map(genre -> genreFacade.getGenre(genre.getId())).collect(Collectors.toList());
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
            if (name.startsWith("remove")) {
                if (BUTTON_PARAMETER_VALUE.equals(request.getParameter(name))) {
                    index = Integer.parseInt(name.substring(6));
                }
            }
        }

        return index;
    }

}
