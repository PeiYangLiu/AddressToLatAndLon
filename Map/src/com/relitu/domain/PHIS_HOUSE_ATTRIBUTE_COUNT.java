package com.relitu.domain;

import java.util.Date;

/**
 * @author : PeiYangLiu
 * @version: 1.0
 * @date: 2017/6/30
 */
public class PHIS_HOUSE_ATTRIBUTE_COUNT {
	private String ID;
	private String lng;
	private String lat;
	private Date date;
	private String HS_SIT;

	public String getHS_SIT() {
		return HS_SIT;
	}

	public void setHS_SIT(String hS_SIT) {
		HS_SIT = hS_SIT;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
