package uk.ac.lboro.android.apps.Loughborough.Staff;

public class Staff {
	
	public String name;
	public String dept;
	public String email;
	public String extension;
	
	public Staff(String stfn, String dept, String em, String ext)
	{
		this.name = stfn;
		this.dept = dept;
		this.email = em;
		this.extension = ext;
	}

	public String getStaffName()
	{
		return this.name;
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
