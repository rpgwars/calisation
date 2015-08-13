package com.calisation.controller;

import com.calisation.controller.validator.ResourceTOValidator;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.service.application.UploadService;
import com.komar.service.cloudstorage.put.PutException;
import com.komar.service.cloudstorage.put.PutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private PutService putService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResourceTOValidator resourceTOValidator;

    private static final String myResourcesView = "myResources";
    private static final String infoMessageModel = "infoMessage";

    private static final String resourceModel = "resource";

    private static final String uploadErrorMessageKey = "upload.error.message";
    private static final String uploadSuccessfulMessageKey = "upload.ok.message";

    private final static Logger logger = Logger.getLogger(UploadController.class.getName());

    @RequestMapping("/myResources")
    public String sandbox(Map<String, Object> map) {
        map.put(resourceModel, new UploadedFile());
        return myResourcesView;
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
            PutResultTO putResultTO = putService.put(uploadedFile.getFile());
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

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @InitBinder("resource")
    protected void initAccountBinder(WebDataBinder binder) {
        binder.setValidator(resourceTOValidator);
    }
}
