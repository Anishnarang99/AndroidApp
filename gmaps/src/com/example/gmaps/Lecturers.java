package com.example.gmaps;

public class Lecturers {
	
	String lecturerName;
	String dept;
	String email;
	String extension;
	
	public Lecturers(String ln, String dept, String em, String ext)
	{
		this.lecturerName = ln;
		this.dept = dept;
		this.email = em;
		this.extension = ext;
	}

	public String getLecturerName()
	{
		return this.lecturerName;
	}
	
	public String getDept()
	{
		return this.dept;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getExtension()
	{
		return this.extension;
	}
}
