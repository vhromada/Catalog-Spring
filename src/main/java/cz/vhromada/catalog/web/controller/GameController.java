package cz.vhromada.catalog.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.facade.GameFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.catalog.web.fo.GameFO;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A class represents controller for games.
 *
 * @author Vladimir Hromada
 */
@Controller("gameController")
@RequestMapping("/games")
public class GameController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/games/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Game doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Facade for games
     */
    private final GameFacade gameFacade;

    /**
     * Converter
     */
    private final Converter converter;

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
            final Converter converter) {
        Assert.notNull(gameFacade, "Facade for games mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.gameFacade = gameFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of games
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(gameFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of games.
     *
     * @param model model
     * @return view for page with list of games
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Game>> gamesResult = gameFacade.getAll();
        final Result<Integer> mediaCountResult = gameFacade.getTotalMediaCount();
        processResults(gamesResult, mediaCountResult);

        model.addAttribute("games", gamesResult.getData());
        model.addAttribute("mediaCount", mediaCountResult.getData());
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
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

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
     *                                                               or ID isn't null
     */
    @PostMapping("/add")
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("game") @Valid final GameFO game, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(game, "FO for game mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(game.getId(), "ID must be null.");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, game, "Add game", "gamesAdd");
            }
            processResults(gameFacade.add(converter.convert(game, Game.class)));
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
     * @throws IllegalRequestException  if game doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Game> result = gameFacade.get(id);
        processResults(result);

        final Game game = result.getData();
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
     * @param game       FO for game
     * @param errors       errors
     * @return view for redirect to page with list of games (no errors) or view for page for editing game (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for game is null
     *                                                               or errors are null
     *                                                               or ID is null
     * @throws IllegalRequestException                               if game doesn't exist
     */
    @PostMapping("/edit")
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("game") @Valid final GameFO game, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(game, "FO for game mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(game.getId(), NULL_ID_MESSAGE);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, game, "Edit game", "gamesEdit");
            }
            processResults(gameFacade.update(processGame(converter.convert(game, Game.class))));
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating game.
     *
     * @param id ID of duplicating game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if game doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(gameFacade.duplicate(getGame(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing game.
     *
     * @param id ID of removing game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if game doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(gameFacade.remove(getGame(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving game up.
     *
     * @param id ID of moving game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if game doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(gameFacade.moveUp(getGame(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving game down.
     *
     * @param id ID of moving game
     * @return view for redirect to page with list of games
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if game doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(gameFacade.moveDown(getGame(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of games
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(gameFacade.updatePositions());

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

    /**
     * Returns game with ID.
     *
     * @param id ID
     * @return game with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if game doesn't exist
     */
    private Game getGame(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Game game = new Game();
        game.setId(id);

        return processGame(game);
    }

    /**
     * Returns processed game.
     *
     * @param game for processing
     * @return processed game
     * @throws IllegalRequestException if game doesn't exist
     */
    private Game processGame(final Game game) {
        final Result<Game> gameResult = gameFacade.get(game.getId());
        processResults(gameResult);

        if (gameResult.getData() != null) {
            return game;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
