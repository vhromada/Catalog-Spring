package cz.vhromada.catalog.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.web.exception.IllegalRequestException;
import cz.vhromada.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * A class represents controller for pictures.
 *
 * @author Vladimir Hromada
 */
@Controller("pictureController")
@RequestMapping("/pictures")
public class PictureController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/pictures/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Picture doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

    /**
     * Facade for pictures
     */
    private final PictureFacade pictureFacade;

    /**
     * Creates a new instance of PictureController.
     *
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if facade for pictures is null
     */
    @Autowired
    public PictureController(final PictureFacade pictureFacade) {
        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");

        this.pictureFacade = pictureFacade;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of pictures
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(pictureFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of pictures.
     *
     * @param model model
     * @return view for page with list of pictures
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Picture>> picturesResult = pictureFacade.getAll();
        processResults(picturesResult);

        model.addAttribute("pictures", picturesResult.getData().stream().map(Picture::getId).collect(Collectors.toList()));
        model.addAttribute(TITLE_ATTRIBUTE, "Pictures");

        return "picture/index";
    }

    /**
     * Get picture content.
     *
     * @param id ID of picture
     * @return picture content
     * @throws IllegalArgumentException if ID is null
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resource> get(@PathVariable("id") final Integer id) {
        Assert.notNull(id, "ID mustn't be null.");

        final Result<Picture> picture = pictureFacade.get(id);
        processResults(picture);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"picture.jpg\"")
            .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
            .body(new ByteArrayResource(picture.getData().getContent()));
    }

    /**
     * Shows page for adding picture.
     *
     * @param model model
     * @return view for page for adding picture
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        model.addAttribute(TITLE_ATTRIBUTE, "Add picture");

        return "picture/form";
    }

    /**
     * Process adding picture.
     *
     * @param file picture
     * @return view for redirect to home page
     * @throws IllegalArgumentException if picture is null
     * @throws IOException              if getting picture content fails
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(@RequestParam("file") final MultipartFile file) throws IOException {
        Assert.notNull(file, "File mustn't be null.");

        if (!file.isEmpty()) {
            final Picture picture = new Picture();
            picture.setContent(file.getBytes());
            processResults(pictureFacade.add(picture));
        }

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding picture.
     *
     * @return view for redirect to home page
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing picture.
     *
     * @param id ID of removing picture
     * @return view for redirect to page with list of pictures
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if picture doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(pictureFacade.remove(getPicture(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving picture up.
     *
     * @param id ID of moving picture
     * @return view for redirect to page with list of pictures
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if picture doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(pictureFacade.moveUp(getPicture(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving picture down.
     *
     * @param id ID of moving picture
     * @return view for redirect to page with list of pictures
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if picture doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(pictureFacade.moveDown(getPicture(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of pictures
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(pictureFacade.updatePositions());

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns picture with ID.
     *
     * @param id ID
     * @return picture with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if picture doesn't exist
     */
    private Picture getPicture(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Picture picture = new Picture();
        picture.setId(id);

        final Result<Picture> pictureResult = pictureFacade.get(picture.getId());
        processResults(pictureResult);

        if (pictureResult.getData() != null) {
            return picture;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
