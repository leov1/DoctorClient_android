package com.hxqydyl.app.ys.push.bean;

public class PushSetBean {
	public PushSetBean(String activitys, String listener) {
		this.activitys = activitys;
		this.listener = listener;
	}

	private String activitys;
	private String listener;

	public String getActivitys() {
		return activitys;
	}

	public void setActivitys(String activitys) {
		this.activitys = activitys;
	}


	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}
}
