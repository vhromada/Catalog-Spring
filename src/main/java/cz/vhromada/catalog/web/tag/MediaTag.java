package cz.vhromada.catalog.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Medium;

/**
 * A class represents tag for media.
 *
 * @author Vladimir Hromada
 */
public class MediaTag extends SimpleTagSupport {

    /**
     * Media
     */
    private List<Medium> media;

    /**
     * Sets a new value to media.
     *
     * @param media new value
     */
    @SuppressWarnings("unused")
    public void setMedia(final List<Medium> media) {
        this.media = media;
    }

    @Override
    @SuppressWarnings("resource")
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.write(getMediaAsString());
    }

    /**
     * Returns media as string.
     *
     * @return media as string
     */
    private String getMediaAsString() {
        final StringBuilder result = new StringBuilder();
        for (final Medium medium : media) {
            result.append(new Time(medium.getLength()));
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

}
