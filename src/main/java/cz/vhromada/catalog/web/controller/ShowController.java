package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.web.domain.ShowData;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.ShowFO;
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
 * A class represents controller for shows.
 *
 * @author Vladimir Hromada
 */
@Controller("showController")
@RequestMapping("/shows")
public class ShowController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/shows/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Show doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

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
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Converter
     */
    private final Converter converter;

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
            final Converter converter) {
        Assert.notNull(showFacade, "Facade for shows mustn't be null.");
        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

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
    @GetMapping("/new")
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
    @RequestMapping({ "", "/", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Show>> showsResult = showFacade.getAll();
        final Result<Integer> seasonsCountResult = showFacade.getSeasonsCount();
        final Result<Integer> episodesCountResult = showFacade.getEpisodesCount();
        final Result<Time> totalLengthResult = showFacade.getTotalLength();
        processResults(showsResult, seasonsCountResult, episodesCountResult, totalLengthResult);

        model.addAttribute("shows", getShowData(showsResult.getData()));
        model.addAttribute("seasonsCount", seasonsCountResult.getData());
        model.addAttribute("episodesCount", episodesCountResult.getData());
        model.addAttribute("totalLength", totalLengthResult.getData());
        model.addAttribute("title", "Shows");

        return "show/index";
    }

    /**
     * Shows page for adding show.
     *
     * @param model model
     * @return view for page for adding show
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new ShowFO(), "Add show", "add");
    }

    /**
     * Process adding show.
     *
     * @param model  model
     * @param showFO FO for show
     * @param errors errors
     * @return view for redirect to page with list of shows (no errors) or view for page for adding show (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for show is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("show") final @Valid ShowFO showFO, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showFO, "FO for show mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(showFO.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, showFO, "Add show", "add");
        }
        final Show show = converter.convert(showFO, Show.class);
        show.setGenres(getGenres(show.getGenres()));
        processResults(showFacade.add(show));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding show.
     *
     * @return view for redirect to page with list of shows
     */
    @PostMapping(value = "/add", params = "cancel")
    public String processAdd() {
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
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Show> result = showFacade.get(id);
        processResults(result);

        final Show show = result.getData();
        if (show != null) {
            return createFormView(model, converter.convert(show, ShowFO.class), "Edit show", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing show.
     *
     * @param model  model
     * @param showFO FO for show
     * @param errors errors
     * @return view for redirect to page with list of shows (no errors) or view for page for editing show (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for show is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("show") final @Valid ShowFO showFO, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showFO, "FO for show mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(showFO.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, showFO, "Edit show", "edit");
        }
        final Show show = processShow(converter.convert(showFO, Show.class));
        show.setGenres(getGenres(show.getGenres()));
        processResults(showFacade.update(show));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel editing show.
     *
     * @return view for redirect to page with list of shows
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String processEdit() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating show.
     *
     * @param id ID of duplicating show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(showFacade.duplicate(getShow(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing show.
     *
     * @param id ID of removing show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(showFacade.remove(getShow(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving show up.
     *
     * @param id ID of moving show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(showFacade.moveUp(getShow(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving show down.
     *
     * @param id ID of moving show
     * @return view for redirect to page with list of shows
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(showFacade.moveDown(getShow(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of shows
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        showFacade.updatePositions();

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param show   FO for show
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private String createFormView(final Model model, final ShowFO show, final String title, final String action) {
        final Result<List<Genre>> result = genreFacade.getAll();
        processResults(result);

        model.addAttribute("show", show);
        model.addAttribute("title", title);
        model.addAttribute("genres", result.getData());
        model.addAttribute("action", action);

        return "show/form";
    }

    /**
     * Returns show with ID.
     *
     * @param id ID
     * @return show with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    private Show getShow(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Show show = new Show();
        show.setId(id);

        return processShow(show);
    }

    /**
     * Returns processed show.
     *
     * @param show for processing
     * @return processed show
     * @throws IllegalRequestException if show doesn't exist
     */
    private Show processShow(final Show show) {
        final Result<Show> showResult = showFacade.get(show.getId());
        processResults(showResult);

        if (showResult.getData() != null) {
            return show;
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

    /**
     * Returns list of show data.
     *
     * @param shows list fo shows
     * @return list of show data
     */
    private List<ShowData> getShowData(final List<Show> shows) {
        final List<ShowData> result = new ArrayList<>();
        for (final Show show : shows) {
            final ShowData showData = new ShowData();
            showData.setShow(show);
            int seasonsCount = 0;
            int episodesCount = 0;
            int length = 0;
            final Result<List<Season>> seasonsResult = seasonFacade.find(show);
            processResults(seasonsResult);
            for (final Season season : seasonsResult.getData()) {
                seasonsCount++;
                final Result<List<Episode>> episodesResult = episodeFacade.find(season);
                processResults(episodesResult);
                for (final Episode episode : episodesResult.getData()) {
                    episodesCount++;
                    length += episode.getLength();
                }
            }
            showData.setSeasonsCount(seasonsCount);
            showData.setEpisodesCount(episodesCount);
            showData.setTotalLength(new Time(length));
            result.add(showData);
        }
        return result;
    }

}
