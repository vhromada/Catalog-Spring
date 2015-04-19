package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.BookCategoryFacade;
import cz.vhromada.catalog.facade.BookFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.BookFO;
import cz.vhromada.converters.Converter;
import cz.vhromada.validators.Validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@Controller("bookController")
@RequestMapping("/categories/{bookCategoryId}/books")
public class BookController {

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "TO for book doesn't exist.";

    /**
     * Model argument
     */
    private static final String MODEL_ARGUMENT = "Model";

    /**
     * ID argument
     */
    private static final String ID_ARGUMENT = "ID";

    /**
     * Book category ID argument
     */
    private static final String BOOK_CATEGORY_ID_ARGUMENT = "Book category ID";

    /**
     * Facade for book categories
     */
    private BookCategoryFacade bookCategoryFacade;

    /**
     * Facade for books
     */
    private BookFacade bookFacade;

    /**
     * Converter
     */
    private Converter converter;

    /**
     * Creates a new instance of BookController.
     *
     * @param bookCategoryFacade facade for book categories
     * @param bookFacade         facade for books
     * @param converter          converter
     * @throws IllegalArgumentException if facade for book categories is null
     *                                  or facade for books is null
     *                                  or converter is null
     */
    @Autowired
    public BookController(final BookCategoryFacade bookCategoryFacade,
            final BookFacade bookFacade,
            @Qualifier("webDozerConverter") final Converter converter) {
        Validators.validateArgumentNotNull(bookCategoryFacade, "Facade for book categories");
        Validators.validateArgumentNotNull(bookFacade, "Facade for books");
        Validators.validateArgumentNotNull(converter, "converter");

        this.bookCategoryFacade = bookCategoryFacade;
        this.bookFacade = bookFacade;
        this.converter = converter;
    }

    /**
     * Shows page with list of books.
     *
     * @param model          model
     * @param bookCategoryId book category ID
     * @return view for page with list of books
     * @throws IllegalArgumentException if model is null
     *                                  or book category ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model, @PathVariable("bookCategoryId") final Integer bookCategoryId) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);

        final BookCategoryTO bookCategory = bookCategoryFacade.getBookCategory(bookCategoryId);
        if (bookCategory == null) {
            throw new IllegalRequestException("TO for book categories doesn't exist.");
        }

        model.addAttribute("books", new ArrayList<>(bookFacade.findBooksByBookCategory(bookCategory)));
        model.addAttribute("bookCategory", bookCategoryId);
        model.addAttribute("title", "Books");

        return "booksList";
    }

    /**
     * Shows page for adding book.
     *
     * @param model          model
     * @param bookCategoryId book category ID
     * @return view for page for adding book
     * @throws IllegalArgumentException if model is null
     *                                  or book category ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model, @PathVariable("bookCategoryId") final Integer bookCategoryId) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        return createFormView(model, new BookFO(), bookCategoryId, "Add book", "booksAdd");
    }

    /**
     * Process adding book.
     *
     * @param model          model
     * @param createButton   button create
     * @param bookCategoryId book category ID
     * @param bookFO         FO for book
     * @param errors         errors
     * @return view for redirect to page with list of books (no errors) or view for page for adding book (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or book category ID is null
     *                                                               or FO for book is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     * @throws IllegalRequestException                               if TO for book categories doesn't exist
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("bookCategoryId") final Integer bookCategoryId, @ModelAttribute("book") @Valid final BookFO bookFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(bookFO, "FO for book");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(bookFO.getId(), ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, bookFO, bookCategoryId, "Add book", "booksAdd");
            }

            final BookTO bookTO = converter.convert(bookFO, BookTO.class);
            bookTO.setBookCategory(bookCategoryFacade.getBookCategory(bookCategoryId));
            bookFacade.add(bookTO);
        }

        return getListRedirectUrl(bookCategoryId);
    }

    /**
     * Shows page for editing book.
     *
     * @param model          model
     * @param bookCategoryId book category ID
     * @param id             ID of editing book
     * @return view for page for editing book
     * @throws IllegalArgumentException if model is null
     *                                  or book category ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     *                                  or TO for book doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("bookCategoryId") final Integer bookCategoryId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        final BookTO book = bookFacade.getBook(id);
        if (book != null) {
            return createFormView(model, converter.convert(book, BookFO.class), bookCategoryId, "Edit book", "booksEdit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing book.
     *
     * @param model          model
     * @param createButton   button create
     * @param bookCategoryId book category ID
     * @param bookFO         FO for book
     * @param errors         errors
     * @return view for redirect to page with list of books (no errors) or view for page for editing book (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or book category ID is null
     *                                                               or FO for book is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for book categories doesn't exist
     *                                                               or TO for book doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @PathVariable("bookCategoryId") final Integer bookCategoryId, @ModelAttribute("book") @Valid final BookFO bookFO, final Errors errors) {
        Validators.validateArgumentNotNull(model, MODEL_ARGUMENT);
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(bookFO, "FO for book");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(bookFO.getId(), ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, bookFO, bookCategoryId, "Edit book", "booksEdit");
            }

            final BookTO bookTO = converter.convert(bookFO, BookTO.class);
            if (bookFacade.exists(bookTO)) {
                bookTO.setBookCategory(bookCategoryFacade.getBookCategory(bookCategoryId));
                bookFacade.update(bookTO);
            } else {
                throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
            }
        }

        return getListRedirectUrl(bookCategoryId);
    }

    /**
     * Process duplicating book.
     *
     * @param bookCategoryId book category ID
     * @param id             ID of duplicating book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if book category ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     *                                  or TO for book doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("bookCategoryId") final Integer bookCategoryId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        final BookTO book = new BookTO();
        book.setId(id);
        if (bookFacade.exists(book)) {
            bookFacade.duplicate(book);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(bookCategoryId);
    }

    /**
     * Process removing book.
     *
     * @param bookCategoryId book category ID
     * @param id             ID of removing book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if book category ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     *                                  or TO for book doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("bookCategoryId") final Integer bookCategoryId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        final BookTO book = new BookTO();
        book.setId(id);
        if (bookFacade.exists(book)) {
            bookFacade.remove(book);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(bookCategoryId);
    }

    /**
     * Process moving book up.
     *
     * @param bookCategoryId book category ID
     * @param id             ID of moving book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if book category ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     *                                  or TO for book doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("bookCategoryId") final Integer bookCategoryId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        final BookTO book = new BookTO();
        book.setId(id);
        if (bookFacade.exists(book)) {
            bookFacade.moveUp(book);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(bookCategoryId);
    }

    /**
     * Process moving book down.
     *
     * @param bookCategoryId book category ID
     * @param id             ID of moving book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if book category ID is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for book categories doesn't exist
     *                                  or TO for book doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("bookCategoryId") final Integer bookCategoryId, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(bookCategoryId, BOOK_CATEGORY_ID_ARGUMENT);
        Validators.validateArgumentNotNull(id, ID_ARGUMENT);
        validateBookCategory(bookCategoryId);

        final BookTO book = new BookTO();
        book.setId(id);
        if (bookFacade.exists(book)) {
            bookFacade.moveDown(book);
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }

        return getListRedirectUrl(bookCategoryId);
    }

    /**
     * Validates TO for book categories.
     *
     * @param id book category ID
     * @throws IllegalRequestException if TO for book categories doesn't exist
     */
    private void validateBookCategory(final int id) {
        final BookCategoryTO bookCategory = new BookCategoryTO();
        bookCategory.setId(id);
        if (!bookCategoryFacade.exists(bookCategory)) {
            throw new IllegalRequestException("TO for book categories doesn't exist.");
        }
    }

    /**
     * Returns page's view with form.
     *
     * @param model          model
     * @param book           FO for book
     * @param bookCategoryId book category ID
     * @param title          page's title
     * @param view           returning view
     * @return page's view  with form
     */
    private static String createFormView(final Model model, final BookFO book, final Integer bookCategoryId, final String title, final String view) {
        model.addAttribute("book", book);
        model.addAttribute("bookCategory", bookCategoryId);
        model.addAttribute("title", title);
        model.addAttribute("languages", new Language[]{ Language.CZ, Language.EN });

        return view;
    }

    /**
     * Returns redirect URL to list.
     *
     * @param bookCategoryId book category ID
     * @return redirect URL to list
     */
    private static String getListRedirectUrl(final Integer bookCategoryId) {
        return "redirect:/categories/" + bookCategoryId + "/books/list";
    }

}
