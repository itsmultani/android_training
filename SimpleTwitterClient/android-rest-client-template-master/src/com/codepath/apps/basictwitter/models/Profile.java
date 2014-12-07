package com.codepath.apps.basictwitter.models;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {
	private String profileImageUrl;
	private String numberOfFollowers;
	private String numberOfFollowing;
	private String screenName;
	private List<Tweet> tweets;
	
	public static Profile fromJson(JSONObject json) {
		Profile profile = new Profile();
		try {
			profile.profileImageUrl = json.getString("profile_image_url");
			profile.numberOfFollowers= json.getString("followers_count");
			profile.numberOfFollowing = json.getString("friends_count");
			profile.screenName = json.getString("screen_name");
			profile.tweets = Arrays.asList(Tweet.fromJsonWithoutUser(json.getJSONObject("status")));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return profile;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getNumberOfFollowers() {
		return numberOfFollowers;
	}
	
	public String getNumberOfFollowing() {
		return numberOfFollowing;
	}
	
	public List<Tweet> getTweets() {
		return tweets;
	}

}
