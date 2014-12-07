package com.codepath.apps.basictwitter.models;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {
	private String name;
	private String profileImageUrl;
	private String numberOfFollowers;
	private String numberOfFollowing;
	private String screenName;
	private long userId;
	private List<Tweet> tweets;
	
	public static Profile fromJson(JSONObject json) {
		Profile profile = new Profile();
		try {
			profile.name = json.getString("name");
			profile.profileImageUrl = json.getString("profile_image_url");
			profile.numberOfFollowers= json.getString("followers_count");
			profile.numberOfFollowing = json.getString("friends_count");
			profile.screenName = json.getString("screen_name");
			profile.userId = json.getLong("id");
			
			JSONObject status = json.getJSONObject("status");
			User user = new User(
					profile.getName(),
					profile.getUserId(),
					profile.getScreenName(),
					profile.getProfileImageUrl()
					);
			Tweet tweet = new Tweet(
					status.getString("text"),
					String.valueOf(profile.getUserId()),
					status.getString("created_at"),
					user
					);
			profile.tweets = Arrays.asList(tweet);
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return profile;
	}
	
	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public long getUserId() {
		return userId;
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
