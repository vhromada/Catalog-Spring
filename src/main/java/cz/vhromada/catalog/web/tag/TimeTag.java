package cz.vhromada.catalog.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.common.Time;

/**
 * A class represents tag for time.
 *
 * @author Vladimir Hromada
 */
public class TimeTag extends SimpleTagSupport {

    /**
     * Time
     */
    private int time;

    /**
     * Sets a new value to time.
     *
     * @param time new value
     */
    public void setTime(final int time) {
        this.time = time;
    }

    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.write(new Time(time).toString());
    }

}
