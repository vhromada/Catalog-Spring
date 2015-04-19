package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import cz.vhromada.catalog.facade.GameFacade;
import cz.vhromada.catalog.facade.to.GameTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.GameFO;
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
 * A class represents controller for games.
 *
 * @author Vladimir Hromada
 */
@Controller("gameController")
@RequestMapping("/games")
public class GameController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/games/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for game doesn't exist.";

    /**
     * Model argument
     */
    private static final String MODEL_ARGUMENT = "Model";

    /**
     * ID argument
     */
    private static final String ID_ARGUMENT = "ID";

    /**
     * Facade for games
     */
    private GameFacade gameFacade;

    /**
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of GameController.
     *
     * @param gameFacade facade for games
     * @param converter  converter
     * @throws IllegalArgumentException if facade for games is null
     *                                  or converter is null
     */
    @Autowired
    public GameController(final GameFacade gameFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(gameFacade, "Facade for games");
        Validators.validateArgumentNotNull(converter, "converter");

        this.gameFacade = gameFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of games
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String processNew() {
        gameFacade.newData();

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of games.
     *
     * @param model model
     * @return view for page with list of games
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        model.addAttribute("games", new ArrayList<>(gameFacade.getGames()));
        model.addAttribute("mediaCount", gameFacade.getTotalMediaCount());
        model.addAttribute("title", "Games");

        return "gamesList";
    }

    /**
     * Shows page for adding game.
     *
     * @param model model
     * @return view for page for adding game
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);

        return createFormView(model, new GameFO(), "Add game", "gamesAdd");
    }

    /**
     * Process adding game.
     *
     * @param model        model
     * @param createButton button create
     * @param game         FO for game
     * @param errors       errors
     * @return view for redirect to page with list of games (no errors) or view for page for adding game (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for game is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("game") @Valid final GameFO game, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(game, "FO for game");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(game.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, game, "Add game", "gamesAdd");
            }
            gameFacade.add(converter.convert(game, GameTO.class));
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing game.
     *
     * @param model model
     * @param id    ID of editing game
     * @return view for page for editing game
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for game doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GameTO game = gameFacade.getGame(id);

        if (game != null) {
            return createFormView(model, converter.convert(game, GameFO.class), "Edit game", "gamesEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing game.
     *
     * @param model        model
     * @param createButton button create
     * @param game         FO for game
     * @param errors       errors
     * @return view for redirect to page with list of games (no errors) or view for page for editing game (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for game is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for game doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("game") @Valid final GameFO game, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(game, "FO for game");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(game.getId(), ID_ARGUMENT);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, game, "Edit game", "gamesEdit");
            }

            final GameTO gameTO = converter.convert(game, GameTO.class);
            if (gameFacade.exists(gameTO)) {
                gameFacade.update(gameTO);
            } else {
                throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
            }
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating game.
     *
     * @param id ID of duplicating game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for game doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GameTO game = new GameTO();
        game.setId(id);
        if (gameFacade.exists(game)) {
            gameFacade.duplicate(game);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing game.
     *
     * @param id ID of removing game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for game doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GameTO game = new GameTO();
        game.setId(id);
        if (gameFacade.exists(game)) {
            gameFacade.remove(game);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving game up.
     *
     * @param id ID of moving game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for game doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GameTO game = new GameTO();
        game.setId(id);
        if (gameFacade.exists(game)) {
            gameFacade.moveUp(game);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving game down.
     *
     * @param id ID of moving game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for game doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);

        final GameTO game = new GameTO();
        game.setId(id);
        if (gameFacade.exists(game)) {
            gameFacade.moveDown(game);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of games
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        gameFacade.updatePositions();

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model model
     * @param game  FO for game
     * @param title page's title
     * @param view  returning view
     * @return page's view with form
     */
    private static String createFormView(final Model model, final GameFO game, final String title, final String view) {
        model.addAttribute("game", game);
        model.addAttribute("title", title);

        return view;
    }

}
