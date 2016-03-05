package de.fa.controller;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fa.model.Station;


@FacesConverter(value="StationConverter")
public class StationConverter implements Converter {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("converting Sring to Station");
		if(value.length() != 0){
			
			//check for alphaNumeric ?
			
			Query q = entityManager.createQuery("select s from Station s where s.name = :name");
			q.setParameter("name", value);
			List<?> stations = q.getResultList();
			
			if(stations.size() > 0){
				return (Station) stations.get(0);
			}
		} 
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Station)value).getName();
	}

}
