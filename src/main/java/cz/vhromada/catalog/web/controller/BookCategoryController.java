package cz.vhromada.catalog.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.vhromada.catalog.facade.BookCategoryFacade;
import cz.vhromada.catalog.facade.BookFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.web.domain.BookCategory;
import cz.vhromada.catalog.web.exceptions.IllegalRequestException;
import cz.vhromada.catalog.web.fo.BookCategoryFO;
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
 * A class represents controller for book categories.
 *
 * @author Vladimir Hromada
 */
@Controller("bookCategoryController")
@RequestMapping("/bookCategories")
public class BookCategoryController {

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
     * Creates a new instance of BookCategoryController.
     *
     * @param bookCategoryFacade facade for book categories
     * @param bookFacade         facade for books
     * @param converter          converter
     * @throws IllegalArgumentException if facade for book categories is null
     *                                  or facade for books is null
     *                                  or converter is null
     */
    @Autowired
    public BookCategoryController(final BookCategoryFacade bookCategoryFacade,
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
     * Process new data.
     *
     * @return view for redirect to page with list of book categories
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String processNew() {
        bookCategoryFacade.newData();

        return "redirect:/bookCategories/list";
    }

    /**
     * Shows page with list of book categories.
     *
     * @param model model
     * @return view for page with list of book categories
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = { "", "/", "list" }, method = RequestMethod.GET)
    public String showList(final Model model) {
        Validators.validateArgumentNotNull(model, "Model");

        final List<BookCategory> bookCategories = new ArrayList<>();
        for (final BookCategoryTO bookCategoryTO : bookCategoryFacade.getBookCategories()) {
            final BookCategory bookCategory = converter.convert(bookCategoryTO, BookCategory.class);
            bookCategory.setBooksCount(bookFacade.findBooksByBookCategory(bookCategoryTO).size());
            bookCategories.add(bookCategory);
        }

        model.addAttribute("bookCategories", bookCategories);
        model.addAttribute("booksCount", bookCategoryFacade.getBooksCount());
        model.addAttribute("title", "Books");

        return "bookCategoriesList";
    }

    /**
     * Shows page for adding book category.
     *
     * @param model model
     * @return view for page for adding book category
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String showAdd(final Model model) {
        Validators.validateArgumentNotNull(model, "Model");

        return createFormView(model, new BookCategoryFO(), "Add book category", "bookCategoriesAdd");
    }

    /**
     * Process adding book category.
     *
     * @param model        model
     * @param createButton button create
     * @param bookCategory FO for book category
     * @param errors       errors
     * @return view for redirect to page with list of book categories (no errors) or view for page for adding book category (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for book category is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID isn't null
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdd(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("bookCategory") @Valid final BookCategoryFO bookCategory, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(bookCategory, "FO for book category");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNull(bookCategory.getId(), "ID");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, bookCategory, "Add book category", "bookCategoriesAdd");
            }
            bookCategoryFacade.add(converter.convert(bookCategory, BookCategoryTO.class));
        }

        return "redirect:/bookCategories/list";
    }

    /**
     * Shows page for editing book category.
     *
     * @param model model
     * @param id    ID of editing book category
     * @return view for page for editing book category
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if TO for book category doesn't exist
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(id, "ID");

        final BookCategoryTO bookCategory = bookCategoryFacade.getBookCategory(id);

        if (bookCategory != null) {
            return createFormView(model, converter.convert(bookCategory, BookCategoryFO.class), "EditBookCategory", "bookCategoriesEdit");
        } else {
            throw new IllegalRequestException("TO for book category doesn't exist.");
        }
    }

    /**
     * Process editing bookCategory.
     *
     * @param model        model
     * @param createButton button create
     * @param bookCategory FO for book category
     * @param errors       errors
     * @return view for redirect to page with list of book categories (no errors) or view for page for editing book category (errors)
     * @throws IllegalArgumentException                              if model is null
     *                                                               or FO for book category is null
     *                                                               or errors are null
     * @throws cz.vhromada.validators.exceptions.ValidationException if ID is null
     * @throws IllegalRequestException                               if TO for book category doesn't exist
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEdit(final Model model, @RequestParam(value = "create", required = false) final String createButton,
            @ModelAttribute("bookCategory") @Valid final BookCategoryFO bookCategory, final Errors errors) {
        Validators.validateArgumentNotNull(model, "Model");
        Validators.validateArgumentNotNull(bookCategory, "FO for book category");
        Validators.validateArgumentNotNull(errors, "Errors");
        Validators.validateNotNull(bookCategory.getId(), "ID");

        if ("Submit".equals(createButton)) {
            if (errors.hasErrors()) {
                return createFormView(model, bookCategory, "EditBookCategory", "bookCategoriesEdit");
            }

            final BookCategoryTO bookCategoryTO = converter.convert(bookCategory, BookCategoryTO.class);
            if (bookCategoryFacade.exists(bookCategoryTO)) {
                bookCategoryFacade.update(bookCategoryTO);
            } else {
                throw new IllegalRequestException("TO for book category doesn't exist.");
            }
        }

        return "redirect:/bookCategories/list";
    }

    /**
     * Process duplicating book category.
     *
     * @param id ID of duplicating book category
     * @return view for redirect to page with list of book categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for book category doesn't exist
     */
    @RequestMapping(value = "duplicate/{id}", method = RequestMethod.GET)
    public String processDuplicate(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final BookCategoryTO bookCategory = new BookCategoryTO();
        bookCategory.setId(id);
        if (bookCategoryFacade.exists(bookCategory)) {
            bookCategoryFacade.duplicate(bookCategory);
        } else {
            throw new IllegalRequestException("TO for book category doesn't exist.");
        }

        return "redirect:/bookCategories/list";
    }

    /**
     * Process removing book category.
     *
     * @param id ID of removing book category
     * @return view for redirect to page with list of book categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for book category doesn't exist
     */
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String processRemove(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final BookCategoryTO bookCategory = new BookCategoryTO();
        bookCategory.setId(id);
        if (bookCategoryFacade.exists(bookCategory)) {
            bookCategoryFacade.remove(bookCategory);
        } else {
            throw new IllegalRequestException("TO for book category doesn't exist.");
        }

        return "redirect:/bookCategories/list";
    }

    /**
     * Process moving book category up.
     *
     * @param id ID of moving book category
     * @return view for redirect to page with list of book categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for book category doesn't exist
     */
    @RequestMapping(value = "moveUp/{id}", method = RequestMethod.GET)
    public String processMoveUp(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final BookCategoryTO bookCategory = new BookCategoryTO();
        bookCategory.setId(id);
        if (bookCategoryFacade.exists(bookCategory)) {
            bookCategoryFacade.moveUp(bookCategory);
        } else {
            throw new IllegalRequestException("TO for book category doesn't exist.");
        }

        return "redirect:/bookCategories/list";
    }

    /**
     * Process moving book category down.
     *
     * @param id ID of moving book category
     * @return view for redirect to page with list of book categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if TO for book category doesn't exist
     */
    @RequestMapping(value = "moveDown/{id}", method = RequestMethod.GET)
    public String processMoveDown(@PathVariable("id") final Integer id) {
        Validators.validateArgumentNotNull(id, "ID");

        final BookCategoryTO bookCategory = new BookCategoryTO();
        bookCategory.setId(id);
        if (bookCategoryFacade.exists(bookCategory)) {
            bookCategoryFacade.moveDown(bookCategory);
        } else {
            throw new IllegalRequestException("TO for book category doesn't exist.");
        }

        return "redirect:/bookCategories/list";
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of book categories
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String processUpdatePositions() {
        bookCategoryFacade.updatePositions();

        return "redirect:/bookCategories/list";
    }

    /**
     * Returns page's view with form.
     *
     * @param model        model
     * @param bookCategory FO for book category
     * @param title        page's title
     * @param view         returning view
     * @return page's view with form
     */
    private static String createFormView(final Model model, final BookCategoryFO bookCategory, final String title, final String view) {
        model.addAttribute("bookCategory", bookCategory);
        model.addAttribute("title", title);

        return view;
    }

}
