package cz.vhromada.catalog.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.SongFO;
import cz.vhromada.catalog.web.mapper.SongMapper;
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
 * A class represents controller for songs.
 *
 * @author Vladimir Hromada
 */
@Controller("songController")
@RequestMapping("/music/{musicId}/songs")
public class SongController extends AbstractResultController {

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "song doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Message for null music ID
     */
    private static final String NULL_MUSIC_ID_MESSAGE = "Music ID mustn't be null.";

    /**
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

    /**
     * Music model attribute
     */
    private static final String MUSIC_ATTRIBUTE = "music";

    /**
     * Facade for music
     */
    private final MusicFacade musicFacade;

    /**
     * Facade for songs
     */
    private final SongFacade songFacade;

    /**
     * Mapper for songs
     */
    private final SongMapper songMapper;

    /**
     * Creates a new instance of SongController.
     *
     * @param musicFacade facade for music
     * @param songFacade  facade for songs
     * @throws IllegalArgumentException if facade for music is null
     *                                  or facade for songs is null
     */
    @Autowired
    public SongController(final MusicFacade musicFacade, final SongFacade songFacade) {
        Assert.notNull(musicFacade, "Facade for music mustn't be null.");
        Assert.notNull(songFacade, "Facade for songs mustn't be null.");

        this.musicFacade = musicFacade;
        this.songFacade = songFacade;
        this.songMapper = Mappers.getMapper(SongMapper.class);
    }

    /**
     * Shows page with list of songs.
     *
     * @param model   model
     * @param musicId music ID
     * @return view for page with list of songs
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping({ "", "/list" })
    public String showList(final Model model, @PathVariable("musicId") final Integer musicId) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);

        final Music music = getMusic(musicId);

        final Result<List<Song>> result = songFacade.find(music);
        processResults(result);

        model.addAttribute("songs", result.getData());
        model.addAttribute(MUSIC_ATTRIBUTE, musicId);
        model.addAttribute(TITLE_ATTRIBUTE, "Songs");

        return "song/index";
    }

    /**
     * Shows page with detail of song.
     *
     * @param model   model
     * @param musicId music ID
     * @param id      ID of editing song
     * @return view for page with detail of song
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @GetMapping("/{id}/detail")
    public String showDetail(final Model model, @PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getMusic(musicId);

        final Result<Song> result = songFacade.get(id);
        processResults(result);

        final Song song = result.getData();
        if (song != null) {
            model.addAttribute("song", song);
            model.addAttribute(MUSIC_ATTRIBUTE, musicId);
            model.addAttribute(TITLE_ATTRIBUTE, "Song detail");

            return "song/detail";
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Shows page for adding song.
     *
     * @param model   model
     * @param musicId music ID
     * @return view for page for adding song
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @GetMapping("/add")
    public String showAdd(final Model model, @PathVariable("musicId") final Integer musicId) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        getMusic(musicId);

        return createFormView(model, new SongFO(), musicId, "Add song", "add");
    }

    /**
     * Process adding song.
     *
     * @param model   model
     * @param musicId music ID
     * @param song    FO for song
     * @param errors  errors
     * @return view for redirect to page with list of songs (no errors) or view for page for adding song (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     *                                  or FO for song is null
     *                                  or errors are null
     *                                  or ID isn't null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @PathVariable("musicId") final Integer musicId, @ModelAttribute("song") final @Valid SongFO song,
        final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        Assert.notNull(song, "FO for song mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(song.getId(), "ID must be null.");

        final Music music = getMusic(musicId);

        if (errors.hasErrors()) {
            return createFormView(model, song, musicId, "Add song", "add");
        }
        processResults(songFacade.add(music, songMapper.mapBack(song)));

        return getListRedirectUrl(musicId);
    }

    /**
     * Cancel adding song.
     *
     * @param musicId music ID
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd(@PathVariable("musicId") final Integer musicId) {
        return cancel(musicId);
    }

    /**
     * Shows page for editing song.
     *
     * @param model   model
     * @param musicId music ID
     * @param id      ID of editing song
     * @return view for page for editing song
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getMusic(musicId);

        final Result<Song> result = songFacade.get(id);
        processResults(result);

        final Song song = result.getData();
        if (song != null) {
            return createFormView(model, songMapper.map(song), musicId, "Edit song", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing song.
     *
     * @param model   model
     * @param musicId music ID
     * @param song    FO for song
     * @param errors  errors
     * @return view for redirect to page with list of songs (no errors) or view for page for editing song (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     *                                  or FO for song is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @PathVariable("musicId") final Integer musicId, @ModelAttribute("song") final @Valid SongFO song,
        final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        Assert.notNull(song, "FO for song mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(song.getId(), NULL_ID_MESSAGE);
        getMusic(musicId);

        if (errors.hasErrors()) {
            return createFormView(model, song, musicId, "Edit song", "edit");
        }
        processResults(songFacade.update(processSong(songMapper.mapBack(song))));

        return getListRedirectUrl(musicId);
    }

    /**
     * Cancel editing song.
     *
     * @param musicId music ID
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    @PostMapping("/edit")
    public String cancelEdit(@PathVariable("musicId") final Integer musicId) {
        return cancel(musicId);
    }

    /**
     * Process duplicating song.
     *
     * @param musicId music ID
     * @param id      ID of duplicating song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        processResults(songFacade.duplicate(getSong(musicId, id)));

        return getListRedirectUrl(musicId);
    }

    /**
     * Process removing song.
     *
     * @param musicId music ID
     * @param id      ID of removing song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        processResults(songFacade.remove(getSong(musicId, id)));

        return getListRedirectUrl(musicId);
    }

    /**
     * Process moving song up.
     *
     * @param musicId music ID
     * @param id      ID of moving song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        processResults(songFacade.moveUp(getSong(musicId, id)));

        return getListRedirectUrl(musicId);
    }

    /**
     * Process moving song down.
     *
     * @param musicId music ID
     * @param id      ID of moving song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        processResults(songFacade.moveDown(getSong(musicId, id)));

        return getListRedirectUrl(musicId);
    }

    /**
     * Returns page's view with form.
     *
     * @param model   model
     * @param song    FO for song
     * @param musicId music ID
     * @param title   page's title
     * @param action  action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final SongFO song, final Integer musicId, final String title, final String action) {
        model.addAttribute("song", song);
        model.addAttribute(MUSIC_ATTRIBUTE, musicId);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("action", action);

        return "song/form";
    }

    /**
     * Cancel processing song.
     *
     * @param musicId music ID
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     * @throws IllegalRequestException  if music doesn't exist
     */
    private String cancel(final Integer musicId) {
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        getMusic(musicId);

        return getListRedirectUrl(musicId);
    }

    /**
     * Returns redirect URL to list.
     *
     * @param musicId music ID
     * @return redirect URL to list
     */
    private static String getListRedirectUrl(final Integer musicId) {
        return "redirect:/music/" + musicId + "/songs/list";
    }

    /**
     * Returns music.
     *
     * @param id music ID
     * @return music
     * @throws IllegalRequestException if music doesn't exist
     */
    private Music getMusic(final int id) {
        final Result<Music> musicResult = musicFacade.get(id);
        processResults(musicResult);

        final Music music = musicResult.getData();
        if (music == null) {
            throw new IllegalRequestException("Music doesn't exist.");
        }

        return music;
    }

    /**
     * Returns song with ID.
     *
     * @param musicId music ID
     * @param id      ID
     * @return song with ID
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if music doesn't exist
     *                                  or song doesn't exist
     */
    private Song getSong(final Integer musicId, final Integer id) {
        Assert.notNull(musicId, NULL_MUSIC_ID_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);
        getMusic(musicId);

        final Song song = new Song();
        song.setId(id);

        return processSong(song);
    }

    /**
     * Returns processed song.
     *
     * @param song for processing
     * @return processed song
     * @throws IllegalRequestException if song doesn't exist
     */
    private Song processSong(final Song song) {
        final Result<Song> songResult = songFacade.get(song.getId());
        processResults(songResult);

        if (songResult.getData() != null) {
            return song;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
