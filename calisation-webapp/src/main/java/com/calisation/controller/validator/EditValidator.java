package com.calisation.controller.validator;

import com.komar.domain.resource.transfer.Edit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EditValidator implements Validator{

    public static final String editStartFiled = "edit.start";
    public static final String editEndFiled = "edit.end";

    private static final String editStartError = "validation.clip.edit.start.error";
    private static final String editEndError = "validation.clip.edit.end.error";

    @Override
    public boolean supports(Class<?> aClass) {
        return Edit.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if(object instanceof Edit){
            Edit edit = (Edit) object;
            if(edit.getStart() < 0)
                errors.rejectValue(editStartFiled, editStartError);
            if(edit.getStart() <= edit.getEnd())
                errors.rejectValue(editEndFiled, editEndError);
        }
        else
            throw new RuntimeException();
    }
}
