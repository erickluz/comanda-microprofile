package com.github.erickluz.repository;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ApplicationScoped
public class BaseDao {
	
	@Inject
    private EntityManager em;
	
	public Object find(String query, Map<String, Object> parameters) {
		Query q = em.createQuery(query);
		parameters.forEach((s, o) -> {
			q.setParameter(s, o);
		});
		return q.getFirstResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> list(String query, Map<String, Object> parameters) {
		Query q = em.createQuery(query);
		parameters.forEach((s, o) -> {
			q.setParameter(s, o);
		});
		return (List<Object>) q.getResultList();
	}
	
	public Object save(Object o) {
		Object entity = em.merge(o);
		em.flush();
		return entity;
	}
}
