package de.fa.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.fa.model.Station;

@FacesConverter(value="StationConverter")
public class StationConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value.length() != 0){
			
			//check for alphaNumeric ?
			SearchHandler search = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(context, "#{SearchHandler}", SearchHandler.class);
			
			List<Station> stations = search.searchStation(value);
			System.out.println("result = " + stations.size());
			
			if(stations.size() > 0){
				return (Station) stations.get(0);
			}
			throw new ConverterException(new FacesMessage("Unbekannte Station"));
		} 
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Station)value).getName();
	}

}
