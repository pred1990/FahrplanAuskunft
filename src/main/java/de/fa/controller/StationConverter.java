package de.fa.controller;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;


@FacesConverter(value="StationConverter")
public class StationConverter implements Converter {

	@PersistenceContext
	private EntityManager entityManager;

	@Resource
	private UserTransaction transaction;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		List<?> result = entityManager.createQuery("select s from Station s where s.name = :name").getResultList();
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		return null;
	}

}
