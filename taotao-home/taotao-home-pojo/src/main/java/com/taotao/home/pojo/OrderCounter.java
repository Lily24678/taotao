package com.taotao.home.pojo;

import java.io.Serializable;

public class OrderCounter implements Serializable{

	private int noPayCount;
	private int noConfirm;
	private int noRateCount;
	public int getNoPayCount() {
		return noPayCount;
	}
	public void setNoPayCount(int noPayCount) {
		this.noPayCount = noPayCount;
	}
	public int getNoConfirm() {
		return noConfirm;
	}
	public void setNoConfirm(int noConfirm) {
		this.noConfirm = noConfirm;
	}
	public int getNoRateCount() {
		return noRateCount;
	}
	public void setNoRateCount(int noRateCount) {
		this.noRateCount = noRateCount;
	}
}
