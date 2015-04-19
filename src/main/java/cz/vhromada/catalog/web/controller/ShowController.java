package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.facade.to.EpisodeTO;
import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.domain.Show;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.ShowFO;
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
 * A class represents controller for shows.
 *
 * @author Vladimir Hromada
 */
@Controller("showController")
@RequestMapping("/shows")
public class ShowController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/shows/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for show doesn't exist.";

    /**
     * Model argument
     */
    private static final String MODEL_ARGUMENT = "Model";

    /**
     * ID argument
     */
    private static final String ID_ARGUMENT = "ID";

    /**
     * Facade for shows
     */
    private ShowFacade showFacade;

    /**
     * Facade for seasons
     */
    private SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private EpisodeFacade episodeFacade;

    /**
     * Facade for genres
     */
    private GenreFacade genreFacade;

    /**
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of ShowController.
     *
     * @param showFacade    facade for shows
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @param genreFacade   facade for genres
     * @param converter     converter
     * @throws IllegalArgumentException if facade for shows is null
     *                                  or facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or facade for genres is null
     *                                  or converter is null
     */
    @Autowired
    public ShowController(final ShowFacade showFacade,
            final SeasonFacade seasonFacade,
            final EpisodeFacade episodeFacade,
            final GenreFacade genreFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(showFacade, "Facade for shows");
        Validators.validateArgumentNotNull(seasonFacade, "Facade for seasons");
        Validators.validateArgumentNotNull(episodeFacade, "Facade for episodes");
        Validators.validateArgumentNotNull(genreFacade, "Facade for genres");
        Validators.validateArgumentNotNull(converter, "converter");

        this.showFacade = showFacade;
        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.genreFacade = genreFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of shows
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String processNew() {
        showFacade.newData();

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of shows.
     *
     * @param model model
     * @return view for page with list of shows
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        final List<Show> shows = new ArrayList<>();
        for (final ShowTO showTO : showFacade.getShows()) {
            final Show show = converter.convert(showTO, Show.class);
            int seasonsCount = 0;
            int episodesCount = 0;
            int length = 0;
            for (final SeasonTO season : seasonFacade.findSeasonsByShow(showTO)) {
                seasonsCount++;
                for (final EpisodeTO episode : episodeFacade.findEpisodesBySeason(season)) {
                    episodesCount++;
                    length += episode.getLength();
                }
            }
            show.setSeasonsCount(seasonsCount);
            show.setEpisodesCount(episodesCount);
            show.setTotalLength(new Time(length));
            shows.add(show);
        }

        model.addAttribute("shows", shows);
        model.addAttribute("seasonsCount", showFacade.getSeasonsCount());
        model.addAttribute("episodesCount", showFacade.getEpisodesCount());
        model.addAttribute("totalLength", showFacade.getTotalLength());
        model.addAttribute("title", "Shows");

        return "showsList";
    }

    /**
     * Shows page for adding show.
     *
     * @param model model
     * @return view for page for adding show
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        return createFormView(model, new ShowFO(), "Add show", "showsAdd");
    }

    /**
     * Process adding show.
     *
     * @param model        model
     * @param createButton button create
     * @param show         FO for show
     * @param errors       errors
     * @return view for redirect to page with list of shows (no errors) or view for page for adding show (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for show is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("show") @Valid final ShowFO show, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(show, "FO for show");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(show.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, show, "Add show", "showsAdd");
            }
            final ShowTO showTO = converter.convert(show, ShowTO.class);
            showTO.setGenres(getGenres(showTO.getGenres()));
            showFacade.add(showTO);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing show.
     *
     * @param model model
     * @param id    ID of editing show
     * @return view for page for editing show
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final ShowTO show = showFacade.getShow(id);
        if (show != null) {
            return createFormView(model, converter.convert(show, ShowFO.class), "Edit show", "showsEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing show.
     *
     * @param model        model
     * @param createButton button create
     * @param show         FO for show
     * @param errors       errors
     * @return view for redirect to page with list of shows (no errors) or view for page for editing show (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for show is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for show doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("show") @Valid final ShowFO show, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(show, "FO for show");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(show.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, show, "Edit show", "showsEdit");
            }

            final ShowTO showTO = converter.convert(show, ShowTO.class);
            if (showFacade.exists(showTO)) {
                showTO.setGenres(getGenres(showTO.getGenres()));
                showFacade.update(showTO);
            } else {
                throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
            }
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating show.
     *
     * @param id ID of duplicating show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final ShowTO show = new ShowTO();
        show.setId(id);
        if (showFacade.exists(show)) {
            showFacade.duplicate(show);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing show.
     *
     * @param id ID of removing show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final ShowTO show = new ShowTO();
        show.setId(id);
        if (showFacade.exists(show)) {
            showFacade.remove(show);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving show up.
     *
     * @param id ID of moving show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final ShowTO show = new ShowTO();
        show.setId(id);
        if (showFacade.exists(show)) {
            showFacade.moveUp(show);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving show down.
     *
     * @param id ID of moving show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final ShowTO show = new ShowTO();
        show.setId(id);
        if (showFacade.exists(show)) {
            showFacade.moveDown(show);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of shows
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        showFacade.updatePositions();

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model model
     * @param show  FO for show
     * @param title page's title
     * @param view  returning view
     * @return page's view with form
     */
    private String createFormView(final Model model, final ShowFO show, final String title, final String view) {
        model.addAttribute("show", show);
        model.addAttribute("title", title);
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
