package com.example.gmaps;

public class Buildings {
	
	String buildingName;
	String roomCodes;
	String latitude;
	String longitude;
	
	public Buildings(String bn, String rc, String lat, String lng)
	{
		this.buildingName = bn;
		this.roomCodes = rc;
		this.latitude = lat;
		this.longitude = lng;	
	}

	public String getBuildingName()
	{
		return this.buildingName;
	}
	
	public String getRoomCodes()
	{
		return this.roomCodes;
	}
	
	public String getLatitude()
	{
		return this.latitude;
	}

	public String getLongitude()
	{
		return this.longitude;
	}
	
}
