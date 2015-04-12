package cz.vhromada.catalog.web.fo;

import java.io.Serializable;

import cz.vhromada.catalog.web.validator.constraints.Languages;

import org.hibernate.validator.constraints.NotBlank;

/**
 * A class represents FO for book.
 *
 * @author Vladimir Hromada
 */
@Languages
public class BookFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Author
     */
    @NotBlank
    private String author;

    /**
     * Title
     */
    @NotBlank
    private String title;

    /**
     * True if language is czech
     */
    private boolean czech;

    /**
     * True if language is Eenglish
     */
    private boolean english;

    /**
     * Note
     */
    private String note;

    /**
     * Position
     */
    private int position;

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
     * Returns author.
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets a new value to author.
     *
     * @param author new value
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * Returns title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new value to title.
     *
     * @param title new value
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Returns true if language is czech.
     *
     * @return true if language is czech
     */
    public boolean isCzech() {
        return czech;
    }

    /**
     * Sets a new value to if language is czech.
     *
     * @param czech new value
     */
    public void setCzech(final boolean czech) {
        this.czech = czech;
    }

    /**
     * Returns true if language is english.
     *
     * @return true if language is english
     */
    public boolean isEnglish() {
        return english;
    }

    /**
     * Sets a new value to if language is english.
     *
     * @param english new value
     */
    public void setEnglish(final boolean english) {
        this.english = english;
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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BookFO) || id == null) {
            return false;
        }
        final BookFO book = (BookFO) obj;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("BookFO [id=%d, author=%s, title=%s, czech=%b, english=%b, note=%s, position=%d]", id, author, title, czech, english, note,
                position);
    }

}
