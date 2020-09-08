package com.revature.daos;

import org.hibernate.Session;
import com.revature.models.ReimbursementType;
import com.revature.utils.HibernateUtil;

public class ReimbursementTypeDAO implements IReimbursementTypeDAO {

	@Override
	public ReimbursementType getRTypeById(int id) {
		Session sesh = HibernateUtil.getSession();
		
		ReimbursementType rt = sesh.get(ReimbursementType.class, id);
		
		return rt;
	}	
}
