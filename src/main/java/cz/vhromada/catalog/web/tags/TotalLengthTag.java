package cz.vhromada.catalog.web.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.MediumTO;

/**
 * A class represents tag for total length.
 *
 * @author Vladimir Hromada
 */
public class TotalLengthTag extends SimpleTagSupport {

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
        writer.write(getTotalLength().toString());
    }

    /**
     * Returns total length.
     *
     * @return total length
     */
    private Time getTotalLength() {
        int totalLength = 0;
        for (final MediumTO medium : media) {
            totalLength += medium.getLength();
        }

        return new Time(totalLength);
    }

}
