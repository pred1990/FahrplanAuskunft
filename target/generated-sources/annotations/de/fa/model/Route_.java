package de.fa.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Route.class)
public abstract class Route_ {

	public static volatile SingularAttribute<Route, String> name;
	public static volatile SingularAttribute<Route, Integer> id;
	public static volatile ListAttribute<Route, Station> stations;
	public static volatile SingularAttribute<Route, String> direction;

}

