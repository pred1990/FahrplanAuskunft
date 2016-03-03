package de.fa.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.fa.model.ClockTime;

@FacesValidator(value="TimeValidator")
public class TimeValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		ClockTime time = (ClockTime) value;
//		System.out.println(new ClockTime(24, 0).getMinutes());

		// 1440 = 24 * 60 (Tag * Minuten) 
		if(time.getMinutes() >= ClockTime.MAX_VALUE){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error"));
		}
	}


}
