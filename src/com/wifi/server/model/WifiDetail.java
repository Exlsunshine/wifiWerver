package com.wifi.server.model;


/**
 * 
 * @author rayjun
 *隶属于某一特定位置的某一个wifi的信息
 */

public class WifiDetail {
	
	private long wifiId;
	
	private long locationId; 
	
	private String BSSID;
	
	private String SSID;
	
	private int RSS;

	public long getWifiId() {
		return wifiId;
	}

	public void setWifiId(long wifiId) {
		this.wifiId = wifiId;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public String getBSSID() {
		return BSSID;
	}

	public void setBSSID(String bSSID) {
		BSSID = bSSID;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public int getRSS() {
		return RSS;
	}

	public void setRSS(int rSS) {
		RSS = rSS;
	}
	
	
}
