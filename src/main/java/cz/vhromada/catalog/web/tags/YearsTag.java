package cz.vhromada.catalog.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * A class represents tag for year.
 *
 * @author Vladimir Hromada
 */
public class YearsTag extends SimpleTagSupport {

    /**
     * Starting year
     */
    private int startYear;

    /**
     * Ending year
     */
    private int endYear;

    /**
     * Sets a new value to starting year.
     *
     * @param startYear new value
     */
    public void setStartYear(final int startYear) {
        this.startYear = startYear;
    }

    /**
     * Sets a new value to ending year.
     *
     * @param endYear new value
     */
    public void setEndYear(final int endYear) {
        this.endYear = endYear;
    }

    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.write(getYearsAsString());
    }

    /**
     * Returns genres as string.
     *
     * @return genres as string
     */
    private String getYearsAsString() {
        return startYear == endYear ? Integer.toString(startYear) : startYear + " - " + endYear;
    }

}
