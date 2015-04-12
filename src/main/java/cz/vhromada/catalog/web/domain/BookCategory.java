package cz.vhromada.catalog.web.domain;

import java.io.Serializable;

/**
 * A class represents book category.
 *
 * @author Vladimir Hromada
 */
public class BookCategory implements Serializable {

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
    private String name;

    /**
     * Note
     */
    private String note;

    /**
     * Position
     */
    private int position;

    /**
     * Count of books
     */
    private int booksCount;

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

    /**
     * Returns note.
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets a new value to note.
     *
     * @param note new value
     */
    public void setNote(final String note) {
        this.note = note;
    }

    /**
     * Returns position.
     *
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets a new value to position.
     *
     * @param position new value
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    /**
     * Returns count of books.
     *
     * @return count of books
     */
    public int getBooksCount() {
        return booksCount;
    }

    /**
     * Sets a new value to count of books.
     *
     * @param booksCount new value
     */
    public void setBooksCount(final int booksCount) {
        this.booksCount = booksCount;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BookCategory) || id == null) {
            return false;
        }
        final BookCategory bookCategory = (BookCategory) obj;
        return id.equals(bookCategory.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("BookCategory [id=%d, name=%s, note=%s, position=%d, booksCount=%d]", id, name, note, position, booksCount);
    }

}
