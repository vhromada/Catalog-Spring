package cz.vhromada.catalog.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.entity.Game;

/**
 * A class represents tag for game's additional data.
 *
 * @author Vladimir Hromada
 */
public class GameAdditionalDataTag extends SimpleTagSupport {

    /**
     * Game
     */
    private Game game;

    /**
     * Sets a new value to game
     *
     * @param game new value
     */
    public void setGame(final Game game) {
        this.game = game;
    }

    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.write(getAdditionalData());
    }

    /**
     * Returns additional data.
     *
     * @return additional data
     */
    private String getAdditionalData() {
        final StringBuilder result = new StringBuilder();
        if (game.getCrack()) {
            result.append("Crack");
        }
        addToResult(result, game.getSerialKey(), "serial key");
        addToResult(result, game.getPatch(), "patch");
        addToResult(result, game.getTrainer(), "trainer");
        addToResult(result, game.getTrainerData(), "data for trainer");
        addToResult(result, game.getEditor(), "editor");
        addToResult(result, game.getSaves(), "saves");
        if (game.getOtherData() != null && !game.getOtherData().isEmpty()) {
            if (result.length() != 0) {
                result.append(", ");
            }
            result.append(game.getOtherData());
        }

        return result.toString();
    }

    /**
     * Adds data to result.
     *
     * @param result result
     * @param value  value
     * @param data   data
     */
    private static void addToResult(final StringBuilder result, final boolean value, final String data) {
        if (value) {
            if (result.length() == 0) {
                result.append(data.substring(0, 1).toUpperCase());
                result.append(data.substring(1));
            } else {
                result.append(", ");
                result.append(data);
            }
        }
    }

}
