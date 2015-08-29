package com.calisation.controller;

import com.calisation.controller.validator.UploadedFileValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.ClipTO;
import com.komar.domain.resource.transfer.Edit;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.service.application.ClipService;
import com.komar.service.application.UploadService;
import com.komar.service.application.exception.MaximalClipsNumberExceeded;
import com.komar.service.cloudstorage.delete.DeleteService;
import com.komar.service.cloudstorage.put.PutEditService;
import com.komar.service.cloudstorage.put.PutException;
import com.komar.service.cloudstorage.put.PutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private PutService putService;

    @Autowired
    private PutEditService putEditService;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private ClipService clipService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UploadedFileValidator uploadedFileValidator;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String myResourcesView = "myResources";
    private static final String infoMessageModel = "infoMessage";

    private static final String resourceModel = "resource";
    private static final String audioClipsModel = "audioClips";
    private static final String videoClipsModel = "videoClips";

    private static final String uploadErrorMessageKey = "upload.error.message";
    private static final String uploadSuccessfulMessageKey = "upload.ok.message";

    private final static Logger logger = Logger.getLogger(UploadController.class.getName());

    @RequestMapping("/myResources")
    public String sandbox(Map<String, Object> map) {
        map.put(resourceModel, new UploadedFile());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ClipTO> clips = clipService.getClips(login);
        try {
            map.put(audioClipsModel, objectMapper.writeValueAsString(clips.stream().filter(clip -> ResourceType.AUDIO.equals(clip.getResourceType())).collect(Collectors.toList())));
            map.put(videoClipsModel, objectMapper.writeValueAsString(clips.stream().filter(clip -> ResourceType.VIDEO.equals(clip.getResourceType())).collect(Collectors.toList())));
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Unable to parse to json objects", e);
        }
        return myResourcesView;
    }

    @RequestMapping(value = "/myResources/addClip", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> addClip(@RequestParam(required = false) Float start, @RequestParam(required = false) Float end,
                                                        @RequestParam(required = false, defaultValue = "true") Boolean withAudio, @RequestParam String name, @RequestParam("file") MultipartFile clip) {
        Edit edit = new Edit();
        edit.setWithAudio(withAudio);
        edit.setStart(start);
        edit.setEnd(end);
        Optional<UploadedFile> file = uploadedFileValidator.validateClip(edit, clip);
        if(!file.isPresent() || !uploadedFileValidator.validateFileName(name)){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        UploadedFile uploadedFile = file.get();
        uploadedFile.setName(name);
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            clipService.isAddingClipPossible(login);
            PutResultTO putResultTO = putEditService.putEdit(uploadedFile, edit);
            List<ClipTO> clipTOs = clipService.saveClip(putResultTO, login, name, uploadedFile.getResourceType(), withAudio);
            return new ResponseEntity<String>(objectMapper.writeValueAsString(clipTOs), HttpStatus.CREATED);
        } catch (PutException e) {
            logger.log(Level.WARNING, "Unable to complete add_clip request", e);
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MaximalClipsNumberExceeded maximalClipsNumberExceeded) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        } catch (AccountNotFound accountNotFound) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Unable to parse to json objects", e);
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/myResources/deleteClip", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<String> deleteClip(@RequestParam String url) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        clipService.deleteClip(login, url);
        String[] splittedUrl = url.split("/");
        deleteService.delete(splittedUrl[splittedUrl.length - 1], null);
        try {
            return new ResponseEntity<String>(objectMapper.writeValueAsString(clipService.getClips(login)), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Unable to parse to json objects", e);
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //watch this http://stackoverflow.com/questions/25145940/sprign-mvc-jsr-303-validation-leads-to-error-400
    @RequestMapping(value = "/uploadResource", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(@ModelAttribute(resourceModel) @Valid UploadedFile uploadedFile,
                                          BindingResult bindingResult,
                                          final RedirectAttributes redirectAttributes) {

        ModelAndView myResourcesModelAndView = new ModelAndView();
        myResourcesModelAndView.setViewName(myResourcesView);

        if (bindingResult.hasErrors()) {
            myResourcesModelAndView.addObject(resourceModel, uploadedFile);
            return myResourcesModelAndView;
        }

        try {
            PutResultTO putResultTO = putService.put(uploadedFile);
            uploadService.saveResource(putResultTO, SecurityContextHolder.getContext().getAuthentication().getName(), uploadedFile);
            redirectAttributes.addFlashAttribute(infoMessageModel, resolveMessage(uploadSuccessfulMessageKey));
        } catch (PutException e) {
            logger.log(Level.WARNING, "Unsuccessful put on cloud storage provider");
            redirectAttributes.addFlashAttribute(infoMessageModel, resolveMessage(uploadErrorMessageKey));
        } catch (AccountNotFound accountNotFound) {
            logger.log(Level.WARNING, "No association between account and saved resource");
            redirectAttributes.addFlashAttribute(infoMessageModel, resolveMessage(uploadErrorMessageKey));
        }

        return myResourcesModelAndView;
    }

    private String resolveMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public void setPutService(PutService putService) {
        this.putService = putService;
    }

    public void setClipService(ClipService clipService) {
        this.clipService = clipService;
    }

    public void setDeleteService(DeleteService deleteService) {
        this.deleteService = deleteService;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        System.out.println("BAD REQUEST");
    }

    @InitBinder("resource")
    protected void initAccountBinder(WebDataBinder binder) {
        binder.setValidator(uploadedFileValidator);
    }
}
