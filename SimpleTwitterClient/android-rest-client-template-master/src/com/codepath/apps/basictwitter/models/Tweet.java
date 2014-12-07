package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private String uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJson(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.uid = json.getString("id");
			tweet.createdAt = json.getString("created_at");
			tweet.user = User.fomrJson(json.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public Tweet() {
		
	}
	
	public Tweet(String body, String uid, String createdAt, User user) {
		this.body = body;
		this.uid = uid;
		this.createdAt = createdAt;
		this.user = user;
		
	}

	public String getBody() {
		return body;
	}

	public String getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		
		for (int i = 0; i < json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	
	public String toString() {
		return getBody() + " - "+ getUser().getScreenName();
	}
	
}
