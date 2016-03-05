package de.fa.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.fa.model.ClockTime;

@FacesConverter(value="TimeConverter")
public class TimeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		String[] values = value.split(":");
		if(values.length == 2){
			try{
				return new ClockTime(Integer.valueOf(values[0]), Integer.valueOf(values[1]));
			} catch(NumberFormatException e) {
				writeMessage("search:"+component.getId(), "Fehler");
				return null;
			}
		} else {

			// do not handle empty Strings!
			if(value.length() != 0)
				writeMessage("search:"+component.getId(), "Fehler");
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((ClockTime)value).toString();
	}

	private void writeMessage(String component, String message){
		FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(message));
		
	}
	
}
