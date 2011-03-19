package com.lizhenghome.android.bujicheck;

import java.util.Date;

public class BujiInfoItem {
	
	private Long id;
	
	private String phoneNumber;

	private String bujiStatus;
	
	private String position;
	
	private Date sendDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBujiStatus() {
		return bujiStatus;
	}

	public void setBujiStatus(String bujiStatus) {
		this.bujiStatus = bujiStatus;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}
