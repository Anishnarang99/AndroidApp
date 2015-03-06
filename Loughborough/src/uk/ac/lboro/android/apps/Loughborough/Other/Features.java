package uk.ac.lboro.android.apps.Loughborough.Other;

public class Features {
	
	public String featureName;
	public String websiteLink;
	
	public Features(String fn, String wl)
	{
		this.featureName = fn;
		this.websiteLink = wl;
	}

	public String getFeatureName()
	{
		return this.featureName;
	}
	
	public String getWebsiteLink()
	{
		return this.websiteLink;
	}
}
