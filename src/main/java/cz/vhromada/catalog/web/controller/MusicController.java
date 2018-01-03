package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.web.domain.MusicData;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.MusicFO;
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
 * A class represents controller for music.
 *
 * @author Vladimir Hromada
 */
@Controller("musicController")
@RequestMapping("/music")
public class MusicController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/music/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Music doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Facade for music
     */
    private final MusicFacade musicFacade;

    /**
     * Facade for songs
     */
    private final SongFacade songFacade;

    /**
     * Converter
     */
    private final Converter converter;

    /**
     * Creates a new instance of MusicController.
     *
     * @param musicFacade facade for music
     * @param songFacade  facade for songs
     * @param converter   converter
     * @throws IllegalArgumentException if facade for music is null
     *                                  or facade for songs is null
     *                                  or converter is null
     */
    @Autowired
    public MusicController(final MusicFacade musicFacade,
            final SongFacade songFacade,
            final Converter converter) {
        Assert.notNull(musicFacade, "Facade for music mustn't be null.");
        Assert.notNull(songFacade, "Facade for songs mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.musicFacade = musicFacade;
        this.songFacade = songFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of music
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(musicFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of music.
     *
     * @param model model
     * @return view for page with list of music
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Music>> musicResult = musicFacade.getAll();
        final Result<Integer> mediaCountResult = musicFacade.getTotalMediaCount();
        final Result<Integer> songsCountResult = musicFacade.getSongsCount();
        final Result<Time> totalLengthResult = musicFacade.getTotalLength();
        processResults(musicResult, mediaCountResult, songsCountResult, totalLengthResult);

        final List<MusicData> musicDataList = new ArrayList<>();
        for (final Music music : musicResult.getData()) {
            final MusicData musicData = new MusicData();
            musicData.setMusic(music);
            int count = 0;
            int length = 0;
            final Result<List<Song>> songsResult = songFacade.find(music);
            processResults(songsCountResult);
            for (final Song song : songsResult.getData()) {
                count++;
                length += song.getLength();
            }
            musicData.setSongsCount(count);
            musicData.setTotalLength(new Time(length));
            musicDataList.add(musicData);
        }

        model.addAttribute("music", musicDataList);
        model.addAttribute("mediaCount", mediaCountResult.getData());
        model.addAttribute("songsCount", songsCountResult.getData());
        model.addAttribute("totalLength", totalLengthResult.getData());
        model.addAttribute("title", "Music");

        return "music/index";
    }

    /**
     * Shows page for adding music.
     *
     * @param model model
     * @return view for page for adding music
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new MusicFO(), "Add music", "add");
    }

    /**
     * Process adding music.
     *
     * @param model  model
     * @param music  FO for music
     * @param errors errors
     * @return view for redirect to page with list of music (no errors) or view for page for adding music (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for music is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("music") @Valid final MusicFO music, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(music, "FO for music mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(music.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, music, "Add music", "add");
        }
        processResults(musicFacade.add(converter.convert(music, Music.class)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding music.
     *
     * @return view for redirect to page with list of music
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing music.
     *
     * @param model model
     * @param id    ID of editing music
     * @return view for page for editing music
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Music> result = musicFacade.get(id);
        processResults(result);

        final Music music = result.getData();
        if (music != null) {
            return createFormView(model, converter.convert(music, MusicFO.class), "Edit music", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing music.
     *
     * @param model  model
     * @param music  FO for music
     * @param errors errors
     * @return view for redirect to page with list of music (no errors) or view for page for editing music (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for music is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("music") @Valid final MusicFO music, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(music, "FO for music mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(music.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, music, "Edit music", "edit");
        }
        processResults(musicFacade.update(processMusic(converter.convert(music, Music.class))));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process editing music.
     *
     * @return view for redirect to page with list of music
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String cancelEdit() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating music.
     *
     * @param id ID of duplicating music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(musicFacade.duplicate(getMusic(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing music.
     *
     * @param id ID of removing music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(musicFacade.remove(getMusic(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving music up.
     *
     * @param id ID of moving music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(musicFacade.moveUp(getMusic(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving music down.
     *
     * @param id ID of moving music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(musicFacade.moveDown(getMusic(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of music
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(musicFacade.updatePositions());

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param music  FO for music
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final MusicFO music, final String title, final String action) {
        model.addAttribute("music", music);
        model.addAttribute("title", title);
        model.addAttribute("action", action);

        return "music/form";
    }

    /**
     * Returns music with ID.
     *
     * @param id ID
     * @return music with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    private Music getMusic(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Music music = new Music();
        music.setId(id);

        return processMusic(music);
    }

    /**
     * Returns processed music.
     *
     * @param music for processing
     * @return processed music
     * @throws IllegalRequestException if music doesn't exist
     */
    private Music processMusic(final Music music) {
        final Result<Music> musicResult = musicFacade.get(music.getId());
        processResults(musicResult);

        if (musicResult.getData() != null) {
            return music;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
