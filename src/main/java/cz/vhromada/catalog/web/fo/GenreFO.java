package cz.vhromada.catalog.web.fo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * A class represents FO for genre.
 *
 * @author Vladimir Hromada
 */
public class GenreFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Name
     */
    @NotBlank
    private String name;

    /**
     * Returns ID.
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets a new value to ID.
     *
     * @param id new value
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns name.
     *
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets a new value to name.
     *
     * @param name new value
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GenreFO) || id == null) {
            return false;
        }
        final GenreFO genre = (GenreFO) obj;
        return id.equals(genre.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("GenreFO [id=%d, name=%s]", id, name);
    }

}
