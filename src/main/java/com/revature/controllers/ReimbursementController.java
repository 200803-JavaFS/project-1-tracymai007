package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ReimbursementStatusDAO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.services.ReimbursementServices;
import com.revature.services.UserServices;

public class ReimbursementController {

	private static ReimbursementStatusDAO rsdao = new ReimbursementStatusDAO();
	private static ReimbursementServices rs = new ReimbursementServices();
	private static UserServices us = new UserServices();
	private static ObjectMapper om = new ObjectMapper();
	
	//Sends back a single reimbursement as a response
	public void getReimbursement(HttpServletResponse res, int id) throws IOException {
		
		Reimbursement r = rs.getReimbursementById(id);
		
		if (r == null) {
			res.setStatus(204);
		} else {
			res.setStatus(200);
			String json = om.writeValueAsString(r);
			res.getWriter().println(json);
		}
		
	}
	
	//Sends back all of the reimbursements back as a response
	public void getAllReimbursements(HttpServletResponse res) throws IOException {
		
		List<Reimbursement> all = rs.getAllReimbursements();
		res.setStatus(200);
		String json = om.writeValueAsString(all);
		res.getWriter().println(json);
		
	}
	
	//Sends back all reimbursements by author
	public void getAllReimbursementsByAuthor(HttpServletResponse res, int aId) throws IOException {
		
		User u = us.getUserById(aId);
		List<Reimbursement> allReimbsByAuthor = rs.getAllReimbursementsByAuthor(u);
		if (allReimbsByAuthor != null && allReimbsByAuthor.isEmpty()) {
			res.setStatus(204);
		} else {
			res.setStatus(200);
			String json = om.writeValueAsString(allReimbsByAuthor);
			res.getWriter().println(json);
		}
		
		
	}
	
	//Gets all reimbursements by status
	public void getAllReimbursementsByStatus (HttpServletResponse res, int sId) throws IOException {
		
		ReimbursementStatus rStats = rsdao.getRStatusById(sId);
		List<Reimbursement> allReimbsByStatus = rs.getAllReimbursementsByStatus(rStats);
		
		if (allReimbsByStatus.isEmpty()) {
			res.setStatus(204);
		} else {
			res.setStatus(200);
			String json = om.writeValueAsString(allReimbsByStatus);
			res.getWriter().println(json);
		}
		
	}
	
	//Adds a new reimbursement
	public void addReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		BufferedReader reader = req.getReader();
		
		StringBuilder sb = new StringBuilder();
		
		String line = reader.readLine();
		
		while(line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		
		String body = new String(sb);
		
		System.out.println(body);
		
		ReimbursementDTO rdto = om.readValue(body, ReimbursementDTO.class);
		
		double rAmt = rdto.getAmt();
		String rDesc = rdto.getDescription();
		User rAuthor = us.getUserById(rdto.getrAuthorId());
		ReimbursementStatus nrs = new ReimbursementStatus(1, "PENDING");
		
		String type = rdto.getrType();
		
		ReimbursementType rtype = null;
		if (type.toLowerCase().equals("Food")) {
			rtype = new ReimbursementType(1, "Food");
		} else if (type.toLowerCase().equals("Lodging")) {
			rtype = new ReimbursementType(2, "Lodging");
		} else if (type.toLowerCase().equals("Travel")) {
			rtype = new ReimbursementType(3, "Travel");
		} else if (type.toLowerCase().equals("Other")) {
			rtype = new ReimbursementType(4, "Other");
		}
		
		Reimbursement r = new Reimbursement(rAmt, new Timestamp(System.currentTimeMillis()), null, rDesc, rAuthor, null, nrs, rtype);
		
		if (rs.addReimbursement(r)) {
			res.setStatus(201);
			res.getWriter().println("Reimbursement Created and Added to Database");
		} else {
			res.setStatus(403);
		}
		
	}
	
	//Updates a Reimbursement
	public void updateReimbStatus (HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		BufferedReader reader = req.getReader();
		
		StringBuilder sb = new StringBuilder();
		
		String line = reader.readLine();
		
		while(line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		
		String body = new String(sb);
		
		ReimbursementDTO rdto = om.readValue(body, ReimbursementDTO.class);
		
		int rId = rdto.getId();
		
		Reimbursement r = rs.getReimbursementById(rId);
		
		String status = rdto.getrStatus();
		
		ReimbursementStatus rStatus = null;
		if (status.equals("APPROVED")) {
			rStatus = new ReimbursementStatus(2, "APPROVED");
		} else if (status.equals("DENIED")) {
			rStatus = new ReimbursementStatus(3, "DENIED");
		}
		
		int resolverId = rdto.getrAuthorId();
		
		r.setReimbStatusId(rStatus);
		User resolver = us.getUserById(resolverId);
		r.setReimbResolverId(resolver);
		r.setResolved(new Timestamp(System.currentTimeMillis()));
		
		if (rs.updateReimbursement(r)) {
			res.setStatus(202);
			res.getWriter().println("Reimbursement Status Updated");
		} else {
			res.setStatus(403);
		}	
	}
}