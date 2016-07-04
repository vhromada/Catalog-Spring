package cz.vhromada.catalog.web.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.MediumTO;

/**
 * A class represents tag for media.
 *
 * @author Vladimir Hromada
 */
public class MediaTag extends SimpleTagSupport {

    /**
     * Media
     */
    private List<MediumTO> media;

    /**
     * Sets a new value to media.
     *
     * @param media new value
     */
    public void setMedia(final List<MediumTO> media) {
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
        for (final MediumTO medium : media) {
            result.append(new Time(medium.getLength()));
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

}
