package com.revature;

import com.revature.daos.IUserRoleDAO;
import com.revature.daos.UserRoleDAO;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserServices;

public class Driver {

	private static IUserRoleDAO urDao = new UserRoleDAO();
	private static UserServices us = new UserServices();
	
	public static void main(String[] args) {
		
		addUsers();
	}
	
	public static void addUsers() {
		String pw1 = "godisgood";
		StringBuilder sb1 = new StringBuilder();
		sb1.append(pw1.hashCode());
		String hpw1 = sb1.toString();
		
		String pw2 = "ilovegod";
		StringBuilder sb2 = new StringBuilder();
		sb2.append(pw2.hashCode());
		String hpw2 = sb2.toString();
		
		UserRole ur1 = urDao.getRoleById(1);
		UserRole ur2 = urDao.getRoleById(2);
		
		User u1 = new User("hello_world", hpw1, "Kayla", "Tran", "kaylatran@gmail.com", ur1);
		User u2 = new User("blessed", hpw2, "Tracy", "Mai", "tracymai@gmail.com", ur2);
		
		us.addUser(u1);
		us.addUser(u2);
	}
}
