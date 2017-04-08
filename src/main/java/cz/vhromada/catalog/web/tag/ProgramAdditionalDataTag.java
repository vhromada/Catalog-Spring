package cz.vhromada.catalog.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.entity.Program;

/**
 * A class represents tag for program's additional data.
 *
 * @author Vladimir Hromada
 */
public class ProgramAdditionalDataTag extends SimpleTagSupport {

    /**
     * Program
     */
    private Program program;

    /**
     * Sets a new value to program
     *
     * @param program new value
     */
    public void setProgram(final Program program) {
        this.program = program;
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
        if (program.getCrack()) {
            result.append("Crack");
        }
        addToResult(result, program.getSerialKey(), "serial key");
        if (program.getOtherData() != null && !program.getOtherData().isEmpty()) {
            if (result.length() != 0) {
                result.append(", ");
            }
            result.append(program.getOtherData());
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
    @SuppressWarnings({ "Duplicates", "SameParameterValue" })
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
