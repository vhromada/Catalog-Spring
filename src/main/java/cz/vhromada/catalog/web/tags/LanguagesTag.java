package cz.vhromada.catalog.web.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.common.Language;

/**
 * A class represents tag for languages.
 *
 * @author Vladimir Hromada
 */
public class LanguagesTag extends SimpleTagSupport {

    /**
     * Languages
     */
    private List<Language> languages;

    /**
     * Sets a new value to languages.
     *
     * @param languages new value
     */
    public void setLanguages(final List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.write(getLanguagesAsString());
    }

    /**
     * Returns languages as string.
     *
     * @return languages as string
     */
    private String getLanguagesAsString() {
        if (languages.isEmpty()) {
            return "";
        }

        final StringBuilder result = new StringBuilder();
        for (final Language language : languages) {
            result.append(language);
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

}
