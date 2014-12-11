package com.wifi.server.model;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author rayjun
 * 存放位置信息以及这个位置的wifi信息
 */

public class LocationDetails {

	private long locationId;
	
	private int X;
	
	private int Y;
	
	private int floor;
	
	private List<WifiDetail> wifiDetails = new ArrayList<WifiDetail>();

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public List<WifiDetail> getWifiDetails() {
		return wifiDetails;
	}

	public void setWifiDetails(List<WifiDetail> wifiDetails) {
		this.wifiDetails = wifiDetails;
	}
	
	
}
