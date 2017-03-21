package cz.vhromada.catalog.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cz.vhromada.catalog.entity.Genre;

/**
 * A class represents tag for genres.
 *
 * @author Vladimir Hromada
 */
public class GenresTag extends SimpleTagSupport {

    /**
     * Genres
     */
    private List<Genre> genres;

    /**
     * Sets a new value to genres.
     *
     * @param genres new value
     */
    public void setGenres(final List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    @SuppressWarnings("resource")
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.write(getGenresAsString());
    }

    /**
     * Returns genres as string.
     *
     * @return genres as string
     */
    private String getGenresAsString() {
        final StringBuilder result = new StringBuilder();
        for (final Genre genre : genres) {
            result.append(genre.getName());
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

}
