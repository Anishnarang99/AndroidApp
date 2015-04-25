package uk.ac.lboro.android.apps.Loughborough.Other;

// Features data access object
public class Features {
	
	public String featureName;
	public String websiteLink;
	
	// Constructor
	public Features(String fn, String wl)
	{
		this.featureName = fn;
		this.websiteLink = wl;
	}
	
	// Return's the feature's name
	public String getFeatureName()
	{
		return this.featureName;
	}
	
	// Return's the feature's website link
	public String getWebsiteLink()
	{
		return this.websiteLink;
	}
}
