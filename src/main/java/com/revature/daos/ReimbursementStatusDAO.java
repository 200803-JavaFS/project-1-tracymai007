package com.revature.daos;

import org.hibernate.Session;
import com.revature.models.ReimbursementStatus;
import com.revature.utils.HibernateUtil;

	public class ReimbursementStatusDAO implements IReimbursementStatusDAO {

	@Override
	public ReimbursementStatus getRStatusById(int id) {
		Session sesh = HibernateUtil.getSession();
		
		ReimbursementStatus rs = sesh.get(ReimbursementStatus.class, id);
		
		return rs;
	}
}