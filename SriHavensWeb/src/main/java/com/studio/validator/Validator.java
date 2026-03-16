package com.studio.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static boolean isEmailValid(String email) {
		String regex = "^(.+)@(.+)$"; 
		Pattern pattern = Pattern.compile(regex);  
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean isMobileNumberValid(String mobile) {
		String regex = "^\\d{10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}
	
	public static boolean isUserIDValid(String userID) {
		String regex = "^[A-Za-z]\\w{6,29}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userID);
		return matcher.matches();
	}
	
	public static boolean isPasswordValid(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
	//String with out any numbers, spaces or special numbers
	public static boolean isStringValidWithOutNumNSpaceNSpl(String input) {
		String regex = "^[a-z]{1,45}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	//String without spaces and special characters
	public static boolean isStringValidWithOutSpaceNSpl(String input) {
		String regex = "^[a-z0-9]{1,45}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	public static boolean isStringEmpty(String input){
		return (input==null || input.equals(""));
	}
	
	public static boolean isValidNumber(String input) {
		String regex = "[+-]?[0-9][0-9]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	public static boolean isValidFloat(String input) {
		String regex = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}
