package cz.vhromada.catalog.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.web.domain.ShowData;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.common.Time;
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
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

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
     * Facade for pictures
     */
    private final PictureFacade pictureFacade;

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
     * @param pictureFacade facade for pictures
     * @param genreFacade   facade for genres
     * @param converter     converter
     * @throws IllegalArgumentException if facade for shows is null
     *                                  or facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or facade for pictures is null
     *                                  or facade for genres is null
     *                                  or converter is null
     */
    @Autowired
    public ShowController(final ShowFacade showFacade, final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade, final PictureFacade pictureFacade,
        final GenreFacade genreFacade, final Converter converter) {
        Assert.notNull(showFacade, "Facade for shows mustn't be null.");
        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");
        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.showFacade = showFacade;
        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.pictureFacade = pictureFacade;
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
    @RequestMapping({ "", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Show>> showsResult = showFacade.getAll();
        final Result<Integer> seasonsCountResult = showFacade.getSeasonsCount();
        final Result<Integer> episodesCountResult = showFacade.getEpisodesCount();
        final Result<Time> totalLengthResult = showFacade.getTotalLength();
        processResults(showsResult, seasonsCountResult, episodesCountResult, totalLengthResult);

        model.addAttribute("shows", showsResult.getData());
        model.addAttribute("seasonsCount", seasonsCountResult.getData());
        model.addAttribute("episodesCount", episodesCountResult.getData());
        model.addAttribute("totalLength", totalLengthResult.getData());
        model.addAttribute(TITLE_ATTRIBUTE, "Shows");

        return "show/index";
    }

    /**
     * Shows page with detail of show.
     *
     * @param model model
     * @param id    ID of editing show
     * @return view for page with detail of show
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @GetMapping("/{id}/detail")
    public String showDetail(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Show> result = showFacade.get(id);
        processResults(result);

        final Show show = result.getData();
        if (show != null) {
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

            model.addAttribute("show", showData);
            model.addAttribute(TITLE_ATTRIBUTE, "Show detail");

            return "show/detail";
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
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

        return createAddFormView(model, new ShowFO());
    }

    /**
     * Process adding show.
     *
     * @param model   model
     * @param showFO  FO for show
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of shows (no errors) or view for page for adding show (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for show is null
     *                                  or errors are null
     *                                  or HTTP request is null
     *                                  or ID isn't null
     */
    @PostMapping("/add")
    public String processAdd(final Model model, @ModelAttribute("show") final @Valid ShowFO showFO, final Errors errors, final HttpServletRequest request) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showFO, "FO for show mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(request, "Request mustn't be null.");
        Assert.isNull(showFO.getId(), "ID must be null.");

        if (request.getParameter("create") != null) {
            if (errors.hasErrors()) {
                return createAddFormView(model, showFO);
            }
            final Show show = converter.convert(showFO, Show.class);
            show.setGenres(getGenres(show.getGenres()));
            processResults(showFacade.add(show));
        }

        if (request.getParameter("choosePicture") != null) {
            return createAddFormView(model, showFO);
        }

        if (request.getParameter("removePicture") != null) {
            showFO.setPicture(null);
            return createAddFormView(model, showFO);
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
            return createEditFormView(model, converter.convert(show, ShowFO.class));
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing show.
     *
     * @param model   model
     * @param showFO  FO for show
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of shows (no errors) or view for page for editing show (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for show is null
     *                                  or errors are null
     *                                  or HTTP request is null
     *                                  or ID is null
     * @throws IllegalRequestException  if show doesn't exist
     */
    @PostMapping("/edit")
    public String processEdit(final Model model, @ModelAttribute("show") final @Valid ShowFO showFO, final Errors errors, final HttpServletRequest request) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(showFO, "FO for show mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(request, "Request mustn't be null.");
        Assert.notNull(showFO.getId(), NULL_ID_MESSAGE);

        if (request.getParameter("update") != null) {
            if (errors.hasErrors()) {
                return createEditFormView(model, showFO);
            }
            final Show show = processShow(converter.convert(showFO, Show.class));
            show.setGenres(getGenres(show.getGenres()));
            processResults(showFacade.update(show));
        }

        if (request.getParameter("choosePicture") != null) {
            return createEditFormView(model, showFO);
        }

        if (request.getParameter("removePicture") != null) {
            showFO.setPicture(null);
            return createEditFormView(model, showFO);
        }

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
        final Result<List<Picture>> pictures = pictureFacade.getAll();
        processResults(pictures);
        final Result<List<Genre>> genres = genreFacade.getAll();
        processResults(genres);

        model.addAttribute("show", show);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("pictures", pictures.getData().stream().map(Picture::getId).collect(Collectors.toList()));
        model.addAttribute("genres", genres.getData());
        model.addAttribute("action", action);

        return "show/form";
    }

    /**
     * Returns page's view with form for adding show.
     *
     * @param model model
     * @param show  FO for show
     * @return page's view with form for adding show
     */
    private String createAddFormView(final Model model, final ShowFO show) {
        return createFormView(model, show, "Add show", "add");
    }

    /**
     * Returns page's view with form for editing show.
     *
     * @param model model
     * @param show  FO for show
     * @return page's view with form for editing show
     */
    private String createEditFormView(final Model model, final ShowFO show) {
        return createFormView(model, show, "Edit show", "edit");
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

}
