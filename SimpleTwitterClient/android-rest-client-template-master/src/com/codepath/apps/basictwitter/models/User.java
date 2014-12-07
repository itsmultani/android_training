package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	public User(String name, long uid, String screenName, String profileImageUrl) {
		this.name = name;
		this.uid = uid;
		this.screenName = screenName;
		this.profileImageUrl = profileImageUrl;
	}
	
	public User() {
		
	}
	
	public static User fomrJson(JSONObject json) {
		User u  = new User();
		try {
			u.name = json.getString("name");;
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
}
