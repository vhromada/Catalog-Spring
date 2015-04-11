package cz.vhromada.catalog.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A class represents controller for errors.
 *
 * @author Vladimir Hromada
 */
@Controller("errorController")
@RequestMapping("/error")
public class ErrorController {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Shows page for known exception (e.g. exception in working with DB).
     *
     * @param model   model
     * @param request HTTP request
     * @return view for page for known exception
     */
    @RequestMapping("/known")
    public String processKnownException(final Model model, final HttpServletRequest request) {
        return processException(model, request, "There was error in working with data.");
    }

    /**
     * Shows page for exception in request.
     *
     * @param model   model
     * @param request HTTP request
     * @return view for exception in request
     */
    @RequestMapping("/request")
    public String processRequestException(final Model model, final HttpServletRequest request) {
        return processException(model, request, "There was illegal changes in pages or call on non existing data.");
    }

    /**
     * Shows page for unknown exception.
     *
     * @param model   model
     * @param request HTTP request
     * @return view for page for unknown exception
     */
    @RequestMapping("/unknown")
    public String processUnknownException(final Model model, final HttpServletRequest request) {
        return processException(model, request, "There was unknown error.");
    }

    /**
     * Shows page for exception in web (e.g. URL to not existing page).
     *
     * @param model model
     * @return view for page for exception in web
     */
    @RequestMapping("/page")
    public String processPageError(final Model model) {
        logger.error("Page error");

        return process(model, "Selected page doesn't exist.");
    }

    /**
     * Process exception.
     *
     * @param model        model
     * @param request      HTTP request
     * @param errorMessage error message
     * @return view for page for exception
     */
    private static String processException(final Model model, final HttpServletRequest request, final String errorMessage) {
        logger.error("Web exception", (Throwable) request.getAttribute("javax.servlet.error.exception"));

        return process(model, errorMessage);
    }

    /**
     * Adds data to model and returns view for page for exception.
     *
     * @param model        model
     * @param errorMessage error message
     * @return view for page for exception
     */
    private static String process(final Model model, final String errorMessage) {
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("title", "Error");

        return "error";
    }

}
