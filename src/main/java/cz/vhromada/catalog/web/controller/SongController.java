package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.facade.to.MusicTO;
import cz.vhromada.catalog.facade.to.SongTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.SongFO;
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
 * A class represents controller for songs.
 *
 * @author Vladimir Hromada
 */
@Controller("songController")
@RequestMapping("/music/{musicId}/songs")
public class SongController {

    /**
     * Facade for music
     */
    private MusicFacade musicFacade;

    /**
     * Facade for songs
     */
    private SongFacade songFacade;

    /**
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of SongController.
     *
     * @param musicFacade facade for music
     * @param songFacade  facade for songs
     * @param converter   converter
     * @throws IllegalArgumentException if facade for music is null
     *                                  or facade for songs is null
     *                                  or converter is null
     */
    @Autowired
    public SongController(final MusicFacade musicFacade,
            final SongFacade songFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(musicFacade, "Facade for music");
        Validators.validateArgumentNotNull(songFacade, "Facade for songs");
        Validators.validateArgumentNotNull(converter, "converter");

        this.musicFacade = musicFacade;
        this.songFacade = songFacade;
        this.converter = converter;
    }

    /**
     * Shows page with list of songs.
     *
     * @param model   model
     * @param musicId music ID
     * @return view for page with list of songs
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model, @PathVariable("musicId") final Integer musicId) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(musicId, "Music ID");

        final MusicTO music = musicFacade.getMusic(musicId);
        if (music == null) {
            throw new IllegalRequestException("TO for music doesn't exist.");
        }

        model.addAttribute("songs", new ArrayList<>(songFacade.findSongsByMusic(music)));
        model.addAttribute("music", musicId);
        model.addAttribute("title", "Songs");

        return "songsList";
    }

    /**
     * Shows page for adding song.
     *
     * @param model   model
     * @param musicId music ID
     * @return view for page for adding song
     * @throws IllegalArgumentException if model is null
     *                                  or music ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model, @PathVariable("musicId") final Integer musicId) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(musicId, "Music ID");
        validateMusic(musicId);

        return createFormView(model, new SongFO(), musicId, "Add song", "songsAdd");
    }

    /**
     * Process adding song.
     *
     * @param model        model
     * @param createButton button create
     * @param musicId      music ID
     * @param songFO       FO for song
     * @param errors       errors
     * @return view for redirect to page with list of songs (no errors) or view for page for adding song (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or music ID is null
     *                                                               or FO for song is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     * @throws IllegalRequestException                               if TO for music doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("musicId") final Integer musicId, @ModelAttribute("song") @Valid final SongFO songFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(songFO, "FO for song");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(songFO.getId(), "ID");
        validateMusic(musicId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, songFO, musicId, "Add song", "songsAdd");
            }

            final SongTO songTO = converter.convert(songFO, SongTO.class);
            songTO.setMusic(musicFacade.getMusic(musicId));
            songFacade.add(songTO);
        }

        return "redirect:/music/" + musicId + "/songs/list";
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
     * @throws IllegalRequestException  if TO for music doesn't exist
     *                                  or TO for song doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateMusic(musicId);

        final SongTO song = songFacade.getSong(id);
        if (song != null) {
            return createFormView(model, converter.convert(song, SongFO.class), musicId, "Edit song", "songsEdit");
        } else {
            throw new IllegalRequestException("TO for song doesn't exist.");
        }
    }

    /**
     * Process editing song.
     *
     * @param model        model
     * @param createButton button create
     * @param musicId      music ID
     * @param songFO       FO for song
     * @param errors       errors
     * @return view for redirect to page with list of songs (no errors) or view for page for editing song (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or music ID is null
     *                                                               or FO for song is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for music doesn't exist
     *                                                               or TO for song doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("musicId") final Integer musicId, @ModelAttribute("song") @Valid final SongFO songFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(songFO, "FO for song");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(songFO.getId(), "ID");
        validateMusic(musicId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, songFO, musicId, "Edit song", "songsEdit");
            }

            final SongTO songTO = converter.convert(songFO, SongTO.class);
            if (songFacade.exists(songTO)) {
                songTO.setMusic(musicFacade.getMusic(musicId));
                songFacade.update(songTO);
            } else {
                throw new IllegalRequestException("TO for song doesn't exist.");
            }
        }

        return "redirect:/music/" + musicId + "/songs/list";
    }

    /**
     * Process duplicating song.
     *
     * @param musicId music ID
     * @param id      ID of duplicating song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     *                                  or TO for song doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateMusic(musicId);

        final SongTO song = new SongTO();
        song.setId(id);
        if (songFacade.exists(song)) {
            songFacade.duplicate(song);
        } else {
            throw new IllegalRequestException("TO for song doesn't exist.");
        }

        return "redirect:/music/" + musicId + "/songs/list";
    }

    /**
     * Process removing song.
     *
     * @param musicId music ID
     * @param id      ID of removing song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     *                                  or TO for song doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateMusic(musicId);

        final SongTO song = new SongTO();
        song.setId(id);
        if (songFacade.exists(song)) {
            songFacade.remove(song);
        } else {
            throw new IllegalRequestException("TO for song doesn't exist.");
        }

        return "redirect:/music/" + musicId + "/songs/list";
    }

    /**
     * Process moving song up.
     *
     * @param musicId music ID
     * @param id      ID of moving song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     *                                  or TO for song doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateMusic(musicId);

        final SongTO song = new SongTO();
        song.setId(id);
        if (songFacade.exists(song)) {
            songFacade.moveUp(song);
        } else {
            throw new IllegalRequestException("TO for song doesn't exist.");
        }

        return "redirect:/music/" + musicId + "/songs/list";
    }

    /**
     * Process moving song down.
     *
     * @param musicId music ID
     * @param id      ID of moving song
     * @return view for redirect to page with list of songs
     * @throws IllegalArgumentException if music ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     *                                  or TO for song doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("musicId") final Integer musicId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(musicId, "Music ID");
        Validators.validateArgumentNotNull(id, "ID");
        validateMusic(musicId);

        final SongTO song = new SongTO();
        song.setId(id);
        if (songFacade.exists(song)) {
            songFacade.moveDown(song);
        } else {
            throw new IllegalRequestException("TO for song doesn't exist.");
        }

        return "redirect:/music/" + musicId + "/songs/list";
    }

    /**
     * Validates TO for music.
     *
     * @param id music ID
     * @throws IllegalRequestException if TO for music doesn't exist
     */
    private void validateMusic(final int id) {
        final MusicTO music = new MusicTO();
        music.setId(id);
        if (!musicFacade.exists(music)) {
            throw new IllegalRequestException("TO for music doesn't exist.");
        }
    }

    /**
     * Returns page's view with form.
     *
     * @param model   model
     * @param song    FO for song
     * @param musicId music ID
     * @param title   page's title
     * @param view    returning view
     * @return page's view  with form
     */
    private static String createFormView(final Model model, final SongFO song, final Integer musicId, final String title, final String view) {
        model.addAttribute("song", song);
        model.addAttribute("music", musicId);
        model.addAttribute("title", title);

        return view;
    }

}
