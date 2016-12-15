/*
	Class to represent a Student at a school and associated data
	Written by Team 7 for CS2212-w2014
*/

package cs2212.team7;

import java.io.Serializable;

public class Student implements Serializable {

	/* Attributes */

	private String fName, lName, email;	//Attributes for student name and email
	private long studentNum;		//Attribute for student number

	/* Constructor */
	
        /**
         * Constructor for student object
         * @param first String for first name
         * @param last String for last name
         * @param mail String for email address
         * @param num Long for student number
         */
	public Student(String first, String last, String mail, long num){
		fName = first;	//Set all fields to passed parameters
		lName = last;
		email = mail;
		studentNum=num;
	}

	/* Public Methods */
	
	/**
         * Get for first name
         * @return String of student first name
         */
	public String getFName (){
		return fName;
	}

	/**
         * Get for last name
         * @return String of student last name
         */
	public String getLName(){
		return lName;
	}

	/**
         * Get for student email
         * @return String of student's email
         */
	public String getEmail(){
		return email;
	}

	/**
         * Get for student number
         * @return long of student number
         */
	public long getStudentNum(){
		return studentNum;
	}

	/**
         * Set for first name
         * @param name String to be new first name
         */
	public void setFName(String name){
		this.fName=name;
	}

	/**
         * Set for last name
         * @param name String to be new last name
         */
	public void setLName (String name){
		this.lName=name;
	}

	/**
         * Set for email
         * @param email String to be new email
         */
	public void setEmail (String email){
		this.email=email;	//Will need to test for duplicate emails
	}
	
	/**
         * Set for student number
         * @param num Long to be new number
         */
	public void setStudentNum(long num){
		this.studentNum=num;	//Will need to test for duplicate/valid student number
	}

}
