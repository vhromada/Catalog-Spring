package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.web.domain.SeasonData;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.SeasonFO;
import cz.vhromada.catalog.web.mapper.SeasonMapper;
import cz.vhromada.common.Language;
import cz.vhromada.common.Time;
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
 * A class represents controller for seasons.
 *
 * @author Vladimir Hromada
 */
@Controller("seasonController")
@RequestMapping("/shows/{showId}/seasons")
public class SeasonController extends AbstractResultController {

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Season doesn't exist.";

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
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

    /**
     * Show model attribute
     */
    private static final String SHOW_ATTRIBUTE = "show";

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
     * Mapper for seasons
     */
    private final SeasonMapper seasonMapper;

    /**
     * Creates a new instance of SeasonController.
     *
     * @param showFacade    facade for shows
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @throws IllegalArgumentException if facade for shows is null
     *                                  or facade for seasons is null
     *                                  or facade for episodes is null
     */
    @Autowired
    public SeasonController(final ShowFacade showFacade, final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade) {
        Assert.notNull(showFacade, "Facade for shows mustn't be null.");
        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");

        this.showFacade = showFacade;
        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.seasonMapper = Mappers.getMapper(SeasonMapper.class);
    }

    /**
     * Shows page with list of seasons.
     *
     * @param model  model
     * @param showId show ID
     * @return view for page with list of seasons
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping({ "", "/list" })
    public String showList(final Model model, @PathVariable("showId") final Integer showId) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);

        final Show show = getShow(showId);

        final Result<List<Season>> seasonsResult = seasonFacade.find(show);
        processResults(seasonsResult);

        model.addAttribute("seasons", seasonsResult.getData());
        model.addAttribute(SHOW_ATTRIBUTE, showId);
        model.addAttribute(TITLE_ATTRIBUTE, "Seasons");

        return "season/index";
    }

    /**
     * Shows page with detail of season.
     *
     * @param model  model
     * @param showId show ID
     * @param id     ID of editing season
     * @return view for page with detail of season
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("/{id}/detail")
    public String showDetail(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);

        final Result<Season> result = seasonFacade.get(id);
        processResults(result);

        final Season season = result.getData();
        if (season != null) {
            final SeasonData seasonData = new SeasonData();
            seasonData.setSeason(season);
            int count = 0;
            int length = 0;
            final Result<List<Episode>> episodesResult = episodeFacade.find(season);
            processResults(episodesResult);
            for (final Episode episode : episodesResult.getData()) {
                count++;
                length += episode.getLength();
            }
            seasonData.setEpisodesCount(count);
            seasonData.setTotalLength(new Time(length));

            model.addAttribute("season", seasonData);
            model.addAttribute(SHOW_ATTRIBUTE, showId);
            model.addAttribute(TITLE_ATTRIBUTE, "Season detail");

            return "season/detail";
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Shows page for adding season.
     *
     * @param model  model
     * @param showId show ID
     * @return view for page for adding season
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/add")
    public String showAdd(final Model model, @PathVariable("showId") final Integer showId) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        getShow(showId);

        return createFormView(model, new SeasonFO(), showId, "Add season", "add");
    }

    /**
     * Process adding season.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonFO FO for season
     * @param errors   errors
     * @return view for redirect to page with list of seasons (no errors) or view for page for adding season (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or FO for season is null
     *                                  or errors are null
     *                                  or ID isn't null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @PathVariable("showId") final Integer showId, @ModelAttribute("season") final @Valid SeasonFO seasonFO,
        final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonFO, "FO for season mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(seasonFO.getId(), "ID must be null.");

        final Show show = getShow(showId);

        if (errors.hasErrors()) {
            return createFormView(model, seasonFO, showId, "Add season", "add");
        }

        final Season season = seasonMapper.mapBack(seasonFO);
        if (season.getSubtitles() == null) {
            season.setSubtitles(new ArrayList<>());
        }
        processResults(seasonFacade.add(show, season));

        return getListRedirectUrl(showId);
    }

    /**
     * Cancel adding season.
     *
     * @param showId show ID
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd(@PathVariable("showId") final Integer showId) {
        return cancel(showId);
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
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);

        final Result<Season> result = seasonFacade.get(id);
        processResults(result);

        final Season season = result.getData();
        if (season != null) {
            return createFormView(model, seasonMapper.map(season), showId, "Edit season", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing season.
     *
     * @param model    model
     * @param showId   show ID
     * @param seasonFO FO for season
     * @param errors   errors
     * @return view for redirect to page with list of seasons (no errors) or view for page for editing season (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or show ID is null
     *                                  or FO for season is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @PathVariable("showId") final Integer showId, @ModelAttribute("season") final @Valid SeasonFO seasonFO,
        final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(seasonFO, "FO for season mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(seasonFO.getId(), NULL_ID_MESSAGE);
        getShow(showId);

        if (errors.hasErrors()) {
            return createFormView(model, seasonFO, showId, "Edit season", "edit");
        }
        final Season season = processSeason(seasonMapper.mapBack(seasonFO));
        if (season.getSubtitles() == null) {
            season.setSubtitles(new ArrayList<>());
        }
        processResults(seasonFacade.update(season));

        return getListRedirectUrl(showId);
    }

    /**
     * Cancel editing season.
     *
     * @param showId show ID
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if how ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String cancelEdit(@PathVariable("showId") final Integer showId) {
        return cancel(showId);
    }

    /**
     * Process duplicating season.
     *
     * @param showId show ID
     * @param id     ID of duplicating season
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        processResults(seasonFacade.duplicate(getSeason(showId, id)));

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
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        processResults(seasonFacade.remove(getSeason(showId, id)));

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
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        processResults(seasonFacade.moveUp(getSeason(showId, id)));

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
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("showId") final Integer showId, @PathVariable("id") final Integer id) {
        processResults(seasonFacade.moveDown(getSeason(showId, id)));

        return getListRedirectUrl(showId);
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param season FO for season
     * @param showId show ID
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final SeasonFO season, final Integer showId, final String title, final String action) {
        model.addAttribute("season", season);
        model.addAttribute(SHOW_ATTRIBUTE, showId);
        model.addAttribute("languages", Language.values());
        model.addAttribute("subtitles", new Language[]{ Language.CZ, Language.EN });
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("action", action);

        return "season/form";
    }

    /**
     * Cancel processing season.
     *
     * @param showId show ID
     * @return view for redirect to page with list of seasons
     * @throws IllegalArgumentException if show ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    private String cancel(final Integer showId) {
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        getShow(showId);

        return getListRedirectUrl(showId);
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

    /**
     * Returns show.
     *
     * @param id show ID
     * @return show
     * @throws IllegalRequestException if show doesn't exist
     */
    @SuppressWarnings("Duplicates")
    private Show getShow(final int id) {
        final Result<Show> showResult = showFacade.get(id);
        processResults(showResult);

        final Show show = showResult.getData();
        if (show == null) {
            throw new IllegalRequestException("Show doesn't exist.");
        }

        return show;
    }

    /**
     * Returns season with ID.
     *
     * @param showId show ID
     * @param id     ID
     * @return season with ID
     * @throws IllegalArgumentException if show ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     *                                  or season doesn't exist
     */
    private Season getSeason(final Integer showId, final Integer id) {
        Assert.notNull(showId, NULL_SHOW_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getShow(showId);

        final Season season = new Season();
        season.setId(id);

        return processSeason(season);
    }

    /**
     * Returns processed season.
     *
     * @param season for processing
     * @return processed season
     * @throws IllegalRequestException if season doesn't exist
     */
    private Season processSeason(final Season season) {
        final Result<Season> seasonResult = seasonFacade.get(season.getId());
        processResults(seasonResult);

        if (seasonResult.getData() != null) {
            return season;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
