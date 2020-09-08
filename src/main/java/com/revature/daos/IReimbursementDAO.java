
package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;

public interface IReimbursementDAO {

	public List<Reimbursement> getAllReimbursements();
	public List<Reimbursement> getAllReimbursementsByAuthor(User author);
	public List<Reimbursement> getAllReimbursementsByStatus(ReimbursementStatus status);
	
	public Reimbursement getReimbursementById(int id);
	
	public boolean addReimbursement(Reimbursement r);
	public boolean updateReimbursement(Reimbursement r);
}