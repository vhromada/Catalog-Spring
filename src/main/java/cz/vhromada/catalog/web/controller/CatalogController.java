package cz.vhromada.catalog.web.controller;

import cz.vhromada.validators.Validators;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A class represents controller for index.
 *
 * @author Vladimir Hromada
 */
@Controller("catalogController")
@RequestMapping({ "", "/" })
public class CatalogController {

    /**
     * Show index page.
     *
     * @return view for index page
     * @param model    model
     * @throws IllegalArgumentException if model is null
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showIndex(final Model model) {
        Validators.validateArgumentNotNull(model, "Model");

        model.addAttribute("title", "Catalog");

        return "index";
    }

    /**
     * Forwards to fav icon.
     *
     * @return view for forwarding to fav icon
     */
    @RequestMapping("**/favicon.ico")
    @SuppressWarnings("SpringMVCViewInspection")
    public String favIconForward() {
        return "forward:/resources/fav.ico";
    }

}
