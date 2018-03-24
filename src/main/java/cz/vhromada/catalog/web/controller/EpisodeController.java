package cz.vhromada.catalog.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.EpisodeFO;
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
 * A class represents controller for episodes.
 *
 * @author Vladimir Hromada
 */
@Controller("episodeController")
@RequestMapping("/shows/{showId}/seasons/{seasonId}/episodes")
public class EpisodeController extends AbstractResultController {

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Episode doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Message for null show ID
     */
    private static final String NULL_SHOW_ID_MESSAGE = "Show ID mustn't be null.";

    /**
     * Message for null season ID
     */
    private static final String NULL_SEASON_ID_MESSAGE = "Season ID mustn't be null.";

    /**
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

    /**
     * Show model attribute
     */
    private static final String SHOW_ATTRIBUTE = "show";

    /**
     * Season model attribute
     */
    private static final String SEASON_ATTRIBUTE = "season";

    /**
     * Facade for shows
     */
    private final ShowFacade showFacade;

    /**
     * Facade for seasons
     */
    private final SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private final EpisodeFacade episodeFacade;

    /**
     * Converter
     */
    private final Converter converter;

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
    public EpisodeController(final ShowFacade showFacade, final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade, final Converter converter) {
        Assert.notNull(showFacade, "Facade for shows mustn't be null.");
        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

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
     * @param seasonId season ID
     * @return view for page with list of episodes
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping({ "", "/", "/list" })
    public String showList(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        getShow(showId);

        final Season season = getSeason(seasonId);

        final Result<List<Episode>> result = episodeFacade.find(season);
        processResults(result);

        model.addAttribute("episodes", result.getData());
        model.addAttribute(SHOW_ATTRIBUTE, showId);
        model.addAttribute(SEASON_ATTRIBUTE, seasonId);
        model.addAttribute(TITLE_ATTRIBUTE, "Episodes");

        return "episode/index";
    }

    /**
     * Shows page with detail of episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID of editing episode
     * @return view for page with detail of episode
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @GetMapping("/{id}/detail")
    public String showDetail(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        final Result<Episode> result = episodeFacade.get(id);
        processResults(result);

        final Episode episode = result.getData();
        if (episode != null) {
            model.addAttribute("episode", episode);
            model.addAttribute(SHOW_ATTRIBUTE, showId);
            model.addAttribute(SEASON_ATTRIBUTE, seasonId);
            model.addAttribute(TITLE_ATTRIBUTE, "Episode detail");

            return "episode/detail";
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Shows page for adding episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season ID
     * @return view for page for adding episode
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("add")
    public String showAdd(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        return createFormView(model, new EpisodeFO(), showId, seasonId, "Add episode", "add");
    }

    /**
     * Process adding episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season ID
     * @param episode  FO for episode
     * @param errors   errors
     * @return view for redirect to page with list of episodes (no errors) or view for page for adding episode (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season ID is null
     *                                  or FO for episode is null
     *                                  or errors are null
     *                                  or ID isn't null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @ModelAttribute("episode") final @Valid EpisodeFO episode, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        Assert.notNull(episode, "FO for episode mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(episode.getId(), "ID must be null.");
        getShow(showId);

        final Season season = getSeason(seasonId);

        if (errors.hasErrors()) {
            return createFormView(model, episode, showId, seasonId, "Add episode", "add");
        }
        processResults(episodeFacade.add(season, converter.convert(episode, Episode.class)));

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Cancel adding episode.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId) {
        return cancel(showId, seasonId);
    }

    /**
     * Shows page for editing episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID of editing episode
     * @return view for page for editing episode
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season ID is null
     *                                  or FO for episode is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @GetMapping("edit/{id}")
    public String showEdit(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        final Result<Episode> result = episodeFacade.get(id);
        processResults(result);

        final Episode episode = result.getData();
        if (episode != null) {
            return createFormView(model, converter.convert(episode, EpisodeFO.class), showId, seasonId, "Edit episode", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing episode.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonId season ID
     * @param episode  FO for episode
     * @param errors   errors
     * @return view for redirect to page with list of episodes (no errors) or view for page for editing episode (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or season ID is null
     *                                  or FO for episode is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @ModelAttribute("episode") final @Valid EpisodeFO episode, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        Assert.notNull(episode, "FO for episode mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(episode.getId(), NULL_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        if (errors.hasErrors()) {
            return createFormView(model, episode, showId, seasonId, "Edit episode", "edit");
        }
        processResults(episodeFacade.update(processEpisode(converter.convert(episode, Episode.class))));

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Cancel editing episode.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String cancelEdit(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId) {
        return cancel(showId, seasonId);
    }

    /**
     * Process duplicating episode.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID of duplicating episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @PathVariable("id") final Integer id) {
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        processResults(episodeFacade.duplicate(getEpisode(showId, seasonId, id)));

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Process removing episode.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID of removing episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @PathVariable("id") final Integer id) {
        processResults(episodeFacade.remove(getEpisode(showId, seasonId, id)));

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Process moving episode up.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID of moving episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @PathVariable("id") final Integer id) {
        processResults(episodeFacade.moveUp(getEpisode(showId, seasonId, id)));

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Process moving episode down.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID of moving episode
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("showId") final Integer showId, @PathVariable("seasonId") final Integer seasonId,
        @PathVariable("id") final Integer id) {
        processResults(episodeFacade.moveDown(getEpisode(showId, seasonId, id)));

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Returns page's view with form.
     *
     * @param model    model
     * @param episode  FO for episode
     * @param showId   show ID
     * @param seasonId season ID
     * @param title    page's title
     * @param action   action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final EpisodeFO episode, final Integer showId, final Integer seasonId, final String title,
        final String action) {
        model.addAttribute("episode", episode);
        model.addAttribute(SHOW_ATTRIBUTE, showId);
        model.addAttribute(SEASON_ATTRIBUTE, seasonId);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("action", action);

        return "episode/form";
    }

    /**
     * Cancel processing episode.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @return view for redirect to page with list of episodes
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    private String cancel(final Integer showId, final Integer seasonId) {
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        return getListRedirectUrl(showId, seasonId);
    }

    /**
     * Returns redirect URL to list.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @return redirect URL to list
     */
    private static String getListRedirectUrl(final Integer showId, final Integer seasonId) {
        return "redirect:/shows/" + showId + "/seasons/" + seasonId + "/episodes/list";
    }

    /**
     * Returns show.
     *
     * @param id show ID
     * @throws IllegalRequestException if show doesn't exist
     */
    @SuppressWarnings("Duplicates")
    private void getShow(final int id) {
        final Result<Show> showResult = showFacade.get(id);
        processResults(showResult);

        if (showResult.getData() == null) {
            throw new IllegalRequestException("Show doesn't exist.");
        }
    }

    /**
     * Returns season.
     *
     * @param id season ID
     * @return season
     * @throws IllegalRequestException if season doesn't exist
     */
    private Season getSeason(final int id) {
        final Result<Season> seasonResult = seasonFacade.get(id);
        processResults(seasonResult);

        final Season season = seasonResult.getData();
        if (season == null) {
            throw new IllegalRequestException("Season doesn't exist.");
        }

        return season;
    }

    /**
     * Returns episode with ID.
     *
     * @param showId   show ID
     * @param seasonId season ID
     * @param id       ID
     * @return episode with ID
     * @throws IllegalArgumentException if show ID is null
     *                                  or season ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     *                                  or episode doesn't exist
     */
    private Episode getEpisode(final Integer showId, final Integer seasonId, final Integer id) {
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonId, NULL_SEASON_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);
        getSeason(seasonId);

        final Episode episode = new Episode();
        episode.setId(id);

        return processEpisode(episode);
    }

    /**
     * Returns processed episode.
     *
     * @param episode for processing
     * @return processed episode
     * @throws IllegalRequestException if episode doesn't exist
     */
    private Episode processEpisode(final Episode episode) {
        final Result<Episode> episodeResult = episodeFacade.get(episode.getId());
        processResults(episodeResult);

        if (episodeResult.getData() != null) {
            return episode;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
