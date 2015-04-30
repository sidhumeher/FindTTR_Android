package com.example.findttr_android;

public class GetLatLong {
	
	private Double latitude;
	private Double longitude;
	
	
	public GetLatLong(Double lat, Double lng){
		this.latitude = lat;
		this.longitude = lng;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
