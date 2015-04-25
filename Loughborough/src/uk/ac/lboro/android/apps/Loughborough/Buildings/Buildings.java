package uk.ac.lboro.android.apps.Loughborough.Buildings;

// Buildings data access object
public class Buildings {
	
	String buildingName;
	String roomCodes;
	String latitude;
	String longitude;
	
	// Constructor
	public Buildings(String bn, String rc, String lat, String lng)
	{
		this.buildingName = bn;
		this.roomCodes = rc;
		this.latitude = lat;
		this.longitude = lng;	
	}
	
	// Returns the building's name
	public String getBuildingName()
	{
		return this.buildingName;
	}
	
	// Returns the building's room code
	public String[] getRoomCodes()
	{	
		// If there is more than one room code for one building, split them up
		String[] splitRoomCodes = roomCodes.split(",");
		return splitRoomCodes;
	}
	
	// Returns the building's latitude co-ordinates
	public String getLatitude()
	{
		return this.latitude;
	}
	
	// Returns the building's longitude co-ordinates
	public String getLongitude()
	{
		return this.longitude;
	}
	
}
