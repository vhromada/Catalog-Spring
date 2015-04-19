package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.facade.to.MusicTO;
import cz.vhromada.catalog.facade.to.SongTO;
import cz.vhromada.catalog.web.domain.Music;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.MusicFO;
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
 * A class represents controller for music.
 *
 * @author Vladimir Hromada
 */
@Controller("musicController")
@RequestMapping("/music")
public class MusicController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/music/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for music doesn't exist.";

    /**
     * Model argument
     */
    private static final String MODEL_ARGUMENT = "Model";

    /**
     * ID argument
     */
    private static final String ID_ARGUMENT = "ID";

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
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(musicFacade, "Facade for music");
        Validators.validateArgumentNotNull(songFacade, "Facade for songs");
        Validators.validateArgumentNotNull(converter, "converter");

        this.musicFacade = musicFacade;
        this.songFacade = songFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of music
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String processNew() {
        musicFacade.newData();

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of music.
     *
     * @param model model
     * @return view for page with list of music
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        final List<Music> musicList = new ArrayList<>();
        for (final MusicTO musicTO : musicFacade.getMusic()) {
            final Music music = converter.convert(musicTO, Music.class);
            int count = 0;
            int length = 0;
            for (final SongTO song : songFacade.findSongsByMusic(musicTO)) {
                count++;
                length += song.getLength();
            }
            music.setSongsCount(count);
            music.setTotalLength(new Time(length));
            musicList.add(music);
        }

        model.addAttribute("music", musicList);
        model.addAttribute("mediaCount", musicFacade.getTotalMediaCount());
        model.addAttribute("songsCount", musicFacade.getSongsCount());
        model.addAttribute("totalLength", musicFacade.getTotalLength());
        model.addAttribute("title", "Music");

        return "musicList";
    }

    /**
     * Shows page for adding music.
     *
     * @param model model
     * @return view for page for adding music
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        return createFormView(model, new MusicFO(), "Add music", "musicAdd");
    }

    /**
     * Process adding music.
     *
     * @param model        model
     * @param createButton button create
     * @param music        FO for music
     * @param errors       errors
     * @return view for redirect to page with list of music (no errors) or view for page for adding music (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for music is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("music") @Valid final MusicFO music, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(music, "FO for music");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(music.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, music, "Add music", "musicAdd");
            }
            musicFacade.add(converter.convert(music, MusicTO.class));
        }

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
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MusicTO music = musicFacade.getMusic(id);

        if (music != null) {
            return createFormView(model, converter.convert(music, MusicFO.class), "Edit music", "musicEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing music.
     *
     * @param model        model
     * @param createButton button create
     * @param music        FO for music
     * @param errors       errors
     * @return view for redirect to page with list of music (no errors) or view for page for editing music (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for music is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for music doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("music") @Valid final MusicFO music, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(music, "FO for music");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(music.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, music, "Edit music", "musicEdit");
            }

            final MusicTO musicTO = converter.convert(music, MusicTO.class);
            if (musicFacade.exists(musicTO)) {
                musicFacade.update(musicTO);
            } else {
                throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
            }
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating music.
     *
     * @param id ID of duplicating music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MusicTO music = new MusicTO();
        music.setId(id);
        if (musicFacade.exists(music)) {
            musicFacade.duplicate(music);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing music.
     *
     * @param id ID of removing music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MusicTO music = new MusicTO();
        music.setId(id);
        if (musicFacade.exists(music)) {
            musicFacade.remove(music);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving music up.
     *
     * @param id ID of moving music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MusicTO music = new MusicTO();
        music.setId(id);
        if (musicFacade.exists(music)) {
            musicFacade.moveUp(music);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving music down.
     *
     * @param id ID of moving music
     * @return view for redirect to page with list of music
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for music doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final MusicTO music = new MusicTO();
        music.setId(id);
        if (musicFacade.exists(music)) {
            musicFacade.moveDown(music);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of music
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        musicFacade.updatePositions();

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model model
     * @param music FO for music
     * @param title page's title
     * @param view  returning view
     * @return page's view with form
     */
    private static String createFormView(final Model model, final MusicFO music, final String title, final String view) {
        model.addAttribute("music", music);
        model.addAttribute("title", title);

        return view;
    }

}
