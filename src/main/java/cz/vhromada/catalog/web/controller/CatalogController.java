package cz.vhromada.catalog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param model model
     * @return view for index page
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping
    public String showIndex(final Model model) {
        Assert.notNull(model, "Model mustn't be null.");

        model.addAttribute("title", "Catalog");
        model.addAttribute("inner", false);

        return "index";
    }

}
