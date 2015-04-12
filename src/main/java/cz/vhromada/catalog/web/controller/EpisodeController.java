package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.facade.to.EpisodeTO;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.EpisodeFO;
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
 * A class represents controller for episodes.
 *
 * @author Vladimir Hromada
 */
@Controller("episodeController")
@RequestMapping("/shows/{showId}/seasons/{seasonId}/episodes")
public class EpisodeController {

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
     * Creates a new instance of EpisodeController.
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
    public EpisodeController(final ShowFacade showFacade,
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
     * Shows page with list of episodes.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season's ID
     * @return view for page with list of episodes
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season's ID is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        validateShow(showId);

        final SeasonTO seasonTO = seasonFacade.getSeason(seasonId);
        if (seasonTO == null) {
            throw new IllegalRequestException("TO for season doesn't exist.");
        }

        model.addAttribute("episodes", new ArrayList<>(episodeFacade.findEpisodesBySeason(seasonTO)));
        model.addAttribute("show", showId);
        model.addAttribute("season", seasonId);
        model.addAttribute("title", "Episodes");

        return "episodesList";
    }

    /**
     * Shows page for adding episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season's ID
     * @return view for page for adding episode
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season's ID is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        validateShow(showId);
        validateSeason(seasonId);

        return createFormView(model, new EpisodeFO(), showId, seasonId, "Add episode", "episodesAdd");
    }

    /**
     * Process adding episode.
     *
     * @param model        model
     * @param createButton button create
     * @param showId       show ID
     * @param seasonId     season's ID
     * @param episodeFO    FO for episode
     * @param errors       errors
     * @return view for redirect to page with list of episodes (no errors) or view for page for adding episode (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or show ID is null
     *                                                               or season's ID is null
     *                                                               or FO for episode is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     * @throws IllegalRequestException                               if TO for show with show doesn't exist
     *                                                               or TO for season with show doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @ModelAttribute("episode") @Valid final EpisodeFO episodeFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(episodeFO, "FO for episode");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(episodeFO.getId(), "ID");
        validateShow(showId);
        validateSeason(seasonId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, episodeFO, showId, seasonId, "Add episode", "episodesAdd");
            }

            final EpisodeTO episodeTO = converter.convert(episodeFO, EpisodeTO.class);
            episodeTO.setSeason(seasonFacade.getSeason(seasonId));
            episodeFacade.add(episodeTO);
        }

        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Shows page for editing episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season's ID
     * @param id       ID of editing episode
     * @return view for page for editing episode
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season's ID is null
     *                                  or FO for episode is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     *                                  or TO for episode with show doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateShow(showId);
        validateSeason(seasonId);

        final EpisodeTO episode = episodeFacade.getEpisode(id);
        if (episode != null) {
            return createFormView(model, converter.convert(episode, EpisodeFO.class), showId, seasonId, "Edit episode", "episodesEdit");
        } else {
            throw new IllegalRequestException("TO for episode doesn't exist.");
        }
    }

    /**
     * Process editing episode.
     *
     * @param model        model
     * @param createButton button create
     * @param showId       show ID
     * @param seasonId     season's ID
     * @param episodeFO    FO for episode
     * @param errors       errors
     * @return view for redirect to page with list of episodes (no errors) or view for page for editing episode (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or show ID is null
     *                                                               or season's ID is null
     *                                                               or FO for episode is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for show with show doesn't exist
     *                                                               or TO for season with show doesn't exist
     *                                                               or TO for episode with show doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @ModelAttribute("episode") @Valid final EpisodeFO episodeFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(episodeFO, "FO for episode");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(episodeFO.getId(), "ID");
        validateShow(showId);
        validateSeason(seasonId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, episodeFO, showId, seasonId, "Edit episode", "episodesEdit");
            }

            final EpisodeTO episodeTO = converter.convert(episodeFO, EpisodeTO.class);
            if (episodeFacade.exists(episodeTO)) {
                episodeTO.setSeason(seasonFacade.getSeason(seasonId));
                episodeFacade.update(episodeTO);
            } else {
                throw new IllegalRequestException("TO for episode doesn't exist.");
            }
        }

        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Process duplicating episode.
     *
     * @param showId   show ID
     * @param seasonId season's ID
     * @param id       ID of duplicating episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season's ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     *                                  or TO for episode with show doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateShow(showId);
        validateSeason(seasonId);

        final EpisodeTO episode = new EpisodeTO();
        episode.setId(id);
        if (episodeFacade.exists(episode)) {
            episodeFacade.duplicate(episode);
        } else {
            throw new IllegalRequestException("TO for episode doesn't exist.");
        }

        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Process removing episode.
     *
     * @param showId   show ID
     * @param seasonId season's ID
     * @param id       ID of removing episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season's ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     *                                  or TO for episode with show doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateShow(showId);
        validateSeason(seasonId);

        final EpisodeTO episode = new EpisodeTO();
        episode.setId(id);
        if (episodeFacade.exists(episode)) {
            episodeFacade.remove(episode);
        } else {
            throw new IllegalRequestException("TO for episode doesn't exist.");
        }

        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Process moving episode up.
     *
     * @param showId   show ID
     * @param seasonId season's ID
     * @param id       ID of moving episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season's ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     *                                  or TO for episode with show doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateShow(showId);
        validateSeason(seasonId);

        final EpisodeTO episode = new EpisodeTO();
        episode.setId(id);
        if (episodeFacade.exists(episode)) {
            episodeFacade.moveUp(episode);
        } else {
            throw new IllegalRequestException("TO for episode doesn't exist.");
        }

        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Process moving episode down.
     *
     * @param showId   show ID
     * @param seasonId season's ID
     * @param id       ID of moving episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season's ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for show with show doesn't exist
     *                                  or TO for season with show doesn't exist
     *                                  or TO for episode with show doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
            @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(showId, "Show ID");
        Validators.validateArgumentNotNull(seasonId, "Season's ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateShow(showId);
        validateSeason(seasonId);

        final EpisodeTO episode = new EpisodeTO();
        episode.setId(id);
        if (episodeFacade.exists(episode)) {
            episodeFacade.moveDown(episode);
        } else {
            throw new IllegalRequestException("TO for episode doesn't exist.");
        }

        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Validates TO for show.
     *
     * @param id show ID
     * @throws IllegalRequestException if TO for show with show doesn't exist
     */
    private void validateShow(final int id) {
        final ShowTO showTO = new ShowTO();
        showTO.setId(id);
        if (!showFacade.exists(showTO)) {
            throw new IllegalRequestException("TO for show doesn't exist.");
        }
    }

    /**
     * Validates TO for season.
     *
     * @param id season's ID
     * @throws IllegalRequestException if TO for season with show doesn't exist
     */
    private void validateSeason(final int id) {
        final SeasonTO seasonTO = new SeasonTO();
        seasonTO.setId(id);
        if (!seasonFacade.exists(seasonTO)) {
            throw new IllegalRequestException("TO for season doesn't exist.");
        }
    }

    /**
     * Returns page's view with form.
     *
     * @param model    model
     * @param episode  FO for episode
     * @param showId   show ID
     * @param seasonId season's ID
     * @param title    page's title
     * @param view     returning view
     * @return page's view  with form
     */
    private static String createFormView(final Model model, final EpisodeFO episode, final Integer showId, final Integer seasonId, final String title,
            final String view) {
        model.addAttribute("episode", episode);
        model.addAttribute("show", showId);
        model.addAttribute("season", seasonId);
        model.addAttribute("title", title);

        return view;
    }

}
