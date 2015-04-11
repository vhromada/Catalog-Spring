package cz.vhromada.catalog.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.facade.to.GameTO;

/**
 * A class represents tag for game's additional data.
 *
 * @author Vladimir Hromada
 */
public class GameAdditionalDataTag extends SimpleTagSupport {

    /**
     * TO for game
     */
    private GameTO game;

    /**
     * Sets a new value to TO for game
     *
     * @param game new value
     */
    public void setGame(final GameTO game) {
        this.game = game;
    }

    @Override
    @SuppressWarnings("resource")
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
        if (game.hasCrack()) {
            result.append("Crack");
        }
        addToResult(result, game.hasSerialKey(), "serial key");
        addToResult(result, game.hasPatch(), "patch");
        addToResult(result, game.hasTrainer(), "trainer");
        addToResult(result, game.hasTrainerData(), "data for trainer");
        addToResult(result, game.hasEditor(), "editor");
        addToResult(result, game.haveSaves(), "saves");
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
