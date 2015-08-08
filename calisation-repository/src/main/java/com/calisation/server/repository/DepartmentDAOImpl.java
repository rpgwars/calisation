package com.calisation.server.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {

	@PersistenceContext(unitName="komar")
	private EntityManager em; 
	
	@Override
	public void doDBOperations() {
	
		Query query = em.createQuery("select e from Department e");
		List resultList = query.getResultList();
		if(resultList == null)
			System.out.println("null");
		else 
			System.out.println("not null " + resultList.size());
	}

}
