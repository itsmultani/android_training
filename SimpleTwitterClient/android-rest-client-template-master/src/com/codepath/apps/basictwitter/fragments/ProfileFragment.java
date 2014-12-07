package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Profile;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ProfileFragment extends Fragment {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvProfileTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
		getProfile();
	}
	
	private void getProfile() {
		client.getProfile(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("DEBUG", "json: " + json.toString());
				Profile profile = Profile.fromJson(json);
				aTweets.addAll(profile.getTweets());
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", "================" + e.toString());
				Log.d("debug", s.toString());
			}
		}, "13555812", "multani");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragement_profile, container, false);
		lvProfileTweets = (ListView) v.findViewById(R.id.lvProfileTweets);
		lvProfileTweets.setAdapter(aTweets);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
