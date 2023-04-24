package com.nagulov.data;

import java.util.HashMap;

import com.nagulov.controllers.ManagerController;
import com.nagulov.treatments.Treatment;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class Validator {
	
	
	private static ManagerController managerController = ManagerController.getInstance();
	
	public static void scheduleTreatment(Treatment t) {
		return;
	}
	
//	public static ErrorMessage updateUser(User user, HashMap<String, String> updateMap) {
//		
//	}
	
	
	public static ErrorMessage changePassword(String oldPassword, String newPassword) {
		
		if(DataBase.loggedUser.getUsername().isBlank()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(oldPassword.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(newPassword.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(!DataBase.users.containsKey(DataBase.loggedUser.getUsername())) {
			return ErrorMessage.INVALID_USERNAME;
		}
		if(!DataBase.users.get(DataBase.loggedUser.getUsername()).getPassword().equals(oldPassword)) {
			return ErrorMessage.INVALID_PASSWORD;
		}
		HashMap<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("Password", newPassword);
		
		managerController.updateUser(DataBase.users.get(DataBase.loggedUser.getUsername()), updateMap);
		return ErrorMessage.SUCCESS;
	}
	
	
	public static ErrorMessage loginUser(String username, String password) {
		if(username.isBlank()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(!DataBase.users.containsKey(username)) {
			return ErrorMessage.INVALID_USERNAME;
		}
		if(!DataBase.users.get(username).getPassword().equals(password)) {
			return ErrorMessage.INVALID_PASSWORD;
		}

		DataBase.loggedUser = managerController.getUser(username);
		return ErrorMessage.SUCCESS;
	}
	
	public static ErrorMessage registerUser(String name, String surname, String gender, String phoneNumber, String address, String username, String password) {
		if(username.isBlank()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(DataBase.users.containsKey(username)) {
			return ErrorMessage.USERNAME_ALREADY_EXISTS;
		}
		managerController.createUser(name, surname, gender, phoneNumber, address, username, password);
		
		return ErrorMessage.SUCCESS;
	}
	
	public static ErrorMessage registerUser(String role, String name, String surname, String gender, String phoneNumber, String address, String username, String password) {
		if(username.isBlank()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(DataBase.users.containsKey(username)) {
			return ErrorMessage.USERNAME_ALREADY_EXISTS;
		}
		
		switch(role) {
			case "Receptionist":
				Receptionist receptionist = new UserBuilder(username, password)
				.setName(name)
				.setSurname(surname)
				.setGender(gender)
				.setPhoneNumber(phoneNumber)
				.setAddress(address)
				.buildReceptionist();
				DataBase.users.put(receptionist.getUsername(), receptionist);
				break;
			case "Manager":
				Manager manager = new UserBuilder(username, password)
				.setName(name)
				.setSurname(surname)
				.setGender(gender)
				.setPhoneNumber(phoneNumber)
				.setAddress(address)
				.buildManager();
				DataBase.users.put(manager.getUsername(), manager);
				break;
			case "Client":
				Client client = new UserBuilder(username, password)
				.setName(name)
				.setSurname(surname)
				.setGender(gender)
				.setPhoneNumber(phoneNumber)
				.setAddress(address)
				.buildClient();
				DataBase.users.put(client.getUsername(), client);
				break;
			case "Beautician":
				Beautician beautician = new UserBuilder(username, password)
				.setName(name)
				.setSurname(surname)
				.setGender(gender)
				.setPhoneNumber(phoneNumber)
				.setAddress(address)
				.buildBeautician();
//				if(data.length == 9) {
//					for(String t : data[8].split(";")) {
//	//					cosmetologist.addTreatment(null, t);;
//					}
//				}
				DataBase.users.put(beautician.getUsername(), beautician);
				break;
		}
		DataBase.saveUsers();
		
		return ErrorMessage.SUCCESS;
	}
	

}
