package uk.ac.lboro.android.apps.Loughborough.Staff;

// Staff data access object
public class Staff {
	
	public String name;
	public String dept;
	public String email;
	public String extension;
	
	// Constructor
	public Staff(String stfn, String dept, String em, String ext)
	{
		this.name = stfn;
		this.dept = dept;
		this.email = em;
		this.extension = ext;
	}
	
	// Returns the staff's name
	public String getStaffName()
	{
		return this.name;
	}
	
	// Returns the staff's department
	public String getDept()
	{
		return this.dept;
	}
	
	// Returns the staff's email
	public String getEmail()
	{
		return this.email;
	}
	
	// Returns the staff's extension number
	public String getExtension()
	{
		return this.extension;
	}
}
