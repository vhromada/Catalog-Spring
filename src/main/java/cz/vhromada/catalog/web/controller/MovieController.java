package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.MovieFacade;
import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.MovieTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.MovieFO;
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
 * A class represents controller for movies.
 *
 * @author Vladimir Hromada
 */
@Controller("movieController")
@RequestMapping("/movies")
public class MovieController {

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

        return "redirect:/movies/list";
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
        Validators.validateArgumentNotNull(model, "Model");

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
        Validators.validateArgumentNotNull(model, "Model");

        return createFormView(model, new MovieFO(), "Add movie", "moviesAdd");
    }

    /**
     * Process adding movie.
     *
     * @param model        model
     * @param createButton button create
     * @param movie        FO for movie
     * @param errors       errors
     * @return view for redirect to page with list of movies (no errors) or view for page for adding movie (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for movie is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("movie") @Valid final MovieFO movie, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(movie, "FO for movie");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(movie.getId(), "ID");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, movie, "Add movie", "moviesAdd");
            }
            final MovieTO movieTO = converter.convert(movie, MovieTO.class);
            if (movieTO.getSubtitles() == null) {
                movieTO.setSubtitles(new ArrayList<Language>());
            }
            movieTO.setGenres(getGenres(movieTO.getGenres()));
            movieFacade.add(movieTO);
        }

        return "redirect:/movies/list";
    }

    /**
     * Shows page for editing movie.
     *
     * @param model model
     * @param id    ID of editing movie
     * @return view for page for editing movie
     * @throws IllegalArgumentException                                   if model is null
     *                                                                    or ID is null
     * @throws cz.vhromada.catalog.web.exceptions.IllegalRequestException if TO for movie doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(id, "ID");

        final MovieTO movie = movieFacade.getMovie(id);

        if (movie != null) {
            return createFormView(model, converter.convert(movie, MovieFO.class), "Edit movie", "moviesEdit");
        } else {
            throw new IllegalRequestException("TO for movie doesn't exist.");
        }
    }

    /**
     * Process editing movie.
     *
     * @param model        model
     * @param createButton button create
     * @param movie        FO for movie
     * @param errors       errors
     * @return view for redirect to page with list of movies (no errors) or view for page for editing movie (errors)
     * @throws IllegalArgumentException                                   if model is null
     *                                                                    or FO for movie is null
     *                                                                    or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException      if ID is null
     * @throws cz.vhromada.catalog.web.exceptions.IllegalRequestException if TO for movie doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("movie") @Valid final MovieFO movie, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(movie, "FO for movie");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(movie.getId(), "ID");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, movie, "Edit movie", "moviesEdit");
            }

            final MovieTO movieTO = converter.convert(movie, MovieTO.class);
            if (movieFacade.exists(movieTO)) {
                if (movieTO.getSubtitles() == null) {
                    movieTO.setSubtitles(new ArrayList<Language>());
                }
                movieTO.setGenres(getGenres(movieTO.getGenres()));
                movieFacade.update(movieTO);
            } else {
                throw new IllegalRequestException("TO for movie doesn't exist.");
            }
        }

        return "redirect:/movies/list";
    }

    /**
     * Process duplicating movie.
     *
     * @param id ID of duplicating movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException                                   if ID is null
     * @throws cz.vhromada.catalog.web.exceptions.IllegalRequestException if TO for movie doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.exists(movie)) {
            movieFacade.duplicate(movie);
        } else {
            throw new IllegalRequestException("TO for movie doesn't exist.");
        }

        return "redirect:/movies/list";
    }

    /**
     * Process removing movie.
     *
     * @param id ID of removing movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException                                   if ID is null
     * @throws cz.vhromada.catalog.web.exceptions.IllegalRequestException if TO for movie doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.exists(movie)) {
            movieFacade.remove(movie);
        } else {
            throw new IllegalRequestException("TO for movie doesn't exist.");
        }

        return "redirect:/movies/list";
    }

    /**
     * Process moving movie up.
     *
     * @param id ID of moving movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException                                   if ID is null
     * @throws cz.vhromada.catalog.web.exceptions.IllegalRequestException if TO for movie doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.exists(movie)) {
            movieFacade.moveUp(movie);
        } else {
            throw new IllegalRequestException("TO for movie doesn't exist.");
        }

        return "redirect:/movies/list";
    }

    /**
     * Process moving movie down.
     *
     * @param id ID of moving movie
     * @return view for redirect to page with list of movies
     * @throws IllegalArgumentException                                   if ID is null
     * @throws cz.vhromada.catalog.web.exceptions.IllegalRequestException if TO for movie doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final MovieTO movie = new MovieTO();
        movie.setId(id);
        if (movieFacade.exists(movie)) {
            movieFacade.moveDown(movie);
        } else {
            throw new IllegalRequestException("TO for movie doesn't exist.");
        }

        return "redirect:/movies/list";
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of movies
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        movieFacade.updatePositions();

        return "redirect:/movies/list";
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
     * Returns genres.
     *
     * @param source list of genres
     * @return genres
     */
    private List<GenreTO> getGenres(final List<GenreTO> source) {
        final List<GenreTO> genres = new ArrayList<>();
        for (final GenreTO genre : source) {
            genres.add(genreFacade.getGenre(genre.getId()));
        }

        return genres;
    }

}
