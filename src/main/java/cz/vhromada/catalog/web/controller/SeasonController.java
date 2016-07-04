package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.facade.to.EpisodeTO;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.domain.Season;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.SeasonFO;
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
 * A class represents controller for seasons.
 *
 * @author Vladimir Hromada
 */
@Controller("seasonController")
@RequestMapping("/shows/{showId}/seasons")
public class SeasonController {

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for season doesn't exist.";

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
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of SeasonController.
     *
     * @param showFacade    facade for shows
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @param converter     converter
     * @throws IllegalArgumentException if facade for shows is null
     *                                  or facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or converter is null
     */
    @Autowired
    public SeasonController(final ShowFacade showFacade,
            final SeasonFacade seasonFacade,
            final EpisodeFacade episodeFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(showFacade, "Facade for shows");
        Validators.validateArgumentNotNull(seasonFacade, "Facade for seasons");
        Validators.validateArgumentNotNull(episodeFacade, "Facade for episodes");
        Validators.validateArgumentNotNull(converter, "converter");

        this.showFacade = showFacade;
        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.converter = converter;
    }

    /**
     * Shows page with list of seasons.
     *
     * @param model  model
     * @param showId show ID
     * @return view for page with list of seasons
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model, @PathVariable("showId") final Integer showId) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);

        final ShowTO show = showFacade.getShow(showId);
        if (show == null) {
            throw new IllegalRequestException("TO for show doesn't exist.");
        }

        final List<Season> seasons = new ArrayList<>();
        for (final SeasonTO seasonTO : seasonFacade.findSeasonsByShow(show)) {
            final Season season = new Season();
            season.setSeason(seasonTO);
            int count = 0;
            int length = 0;
            for (final EpisodeTO episode : episodeFacade.findEpisodesBySeason(seasonTO)) {
                count++;
                length += episode.getLength();
            }
            season.setEpisodesCount(count);
            season.setTotalLength(new Time(length));
            seasons.add(season);
        }

        model.addAttribute("seasons", seasons);
        model.addAttribute("show", showId);
        model.addAttribute("title", "Seasons");

        return "seasonsList";
    }

    /**
     * Shows page for adding season.
     *
     * @param model  model
     * @param showId show ID
     * @return view for page for adding season
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model, @PathVariable("showId") final Integer showId) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        validateShow(showId);

        return createFormView(model, new SeasonFO(), showId, "Add season", "seasonsAdd");
    }

    /**
     * Process adding season.
     *
     * @param model        model
     * @param createButton button create
     * @param showId       show ID
     * @param seasonFO     FO for season
     * @param errors       errors
     * @return view for redirect to page with list of seasons (no errors) or view for page for adding season (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or show ID is null
     *                                                               or FO for season is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     * @throws IllegalRequestException                               if TO for show doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("showId") final Integer showId, @ModelAttribute("season") @Valid final SeasonFO seasonFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(seasonFO, "FO for season");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(seasonFO.getId(), ID_ARGUMENT);
        validateShow(showId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, seasonFO, showId, "Add season", "seasonsAdd");
            }

            final SeasonTO seasonTO = converter.convert(seasonFO, SeasonTO.class);
            if (seasonTO.getSubtitles() == null) {
                seasonTO.setSubtitles(new ArrayList<>());
            }
            seasonFacade.add(showFacade.getShow(showId), seasonTO);
        }

        return getListRedirectUrl(showId);
    }

    /**
     * Shows page for editing season.
     *
     * @param model  model
     * @param showId show ID
     * @param id     ID of editing season
     * @return view for page for editing season
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or FO for season is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     *                                  or TO for season doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateShow(showId);

        final SeasonTO season = seasonFacade.getSeason(id);
        if (season != null) {
            return createFormView(model, converter.convert(season, SeasonFO.class), showId, "Edit season", "seasonsEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing season.
     *
     * @param model        model
     * @param createButton button create
     * @param showId       show ID
     * @param seasonFO     FO for season
     * @param errors       errors
     * @return view for redirect to page with list of seasons (no errors) or view for page for editing season (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or show ID is null
     *                                                               or FO for season is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for show doesn't exist
     *                                                               or TO for season doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("showId") final Integer showId, @ModelAttribute("season") @Valid final SeasonFO seasonFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(seasonFO, "FO for season");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(seasonFO.getId(), ID_ARGUMENT);
        validateShow(showId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, seasonFO, showId, "Edit season", "seasonsEdit");
            }

            final SeasonTO seasonTO = converter.convert(seasonFO, SeasonTO.class);
            if (seasonFacade.getSeason(seasonTO.getId()) != null) {
                if (seasonTO.getSubtitles() == null) {
                    seasonTO.setSubtitles(new ArrayList<>());
                }
                seasonFacade.update(seasonTO);
            } else {
                throw new IllegalRequestException("TO for episode doesn't exist.");
            }
        }

        return getListRedirectUrl(showId);
    }

    /**
     * Process duplicating season.
     *
     * @param showId show ID
     * @param id     ID of duplicating season
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     *                                  or TO for season doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateShow(showId);

        final SeasonTO season = new SeasonTO();
        season.setId(id);
        if (seasonFacade.getSeason(id) != null) {
            seasonFacade.duplicate(season);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(showId);
    }

    /**
     * Process removing season.
     *
     * @param showId show ID
     * @param id     ID of removing season
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     *                                  or TO for season doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateShow(showId);

        final SeasonTO season = new SeasonTO();
        season.setId(id);
        if (seasonFacade.getSeason(id) != null) {
            seasonFacade.remove(season);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(showId);
    }

    /**
     * Process moving season up.
     *
     * @param showId show ID
     * @param id     ID of moving season
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     *                                  or TO for season doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateShow(showId);

        final SeasonTO season = new SeasonTO();
        season.setId(id);
        if (seasonFacade.getSeason(id) != null) {
            seasonFacade.moveUp(season);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(showId);
    }

    /**
     * Process moving season down.
     *
     * @param showId show ID
     * @param id     ID of moving season
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show doesn't exist
     *                                  or TO for season doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateShow(showId);

        final SeasonTO season = new SeasonTO();
        season.setId(id);
        if (seasonFacade.getSeason(id) != null) {
            seasonFacade.moveDown(season);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(showId);
    }

    /**
     * Validates TO for show.
     *
     * @param id show ID
     * @throws IllegalRequestException if TO for show doesn't exist
     */
    private void validateShow(final int id) {
        final ShowTO showTO = new ShowTO();
        showTO.setId(id);
        if (showFacade.getShow(id) == null) {
            throw new IllegalRequestException("TO for show doesn't exist.");
        }
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param season FO for season
     * @param showId show ID
     * @param title  page's title
     * @param view   returning view
     * @return page's view  with form
     */
    private static String createFormView(final Model model, final SeasonFO season, final Integer showId, final String title, final String view) {
        model.addAttribute("season", season);
        model.addAttribute("show", showId);
        model.addAttribute("languages", Language.values());
        model.addAttribute("subtitles", new Language[]{ Language.CZ, Language.EN });
        model.addAttribute("title", title);

        return view;
    }

    /**
     * Returns redirect URL to list.
     *
     * @param showId show ID
     * @return redirect URL to list
     */
    private static String getListRedirectUrl(final Integer showId) {
        return "redirect:/shows/" + showId + "/seasons/list";
    }

}
