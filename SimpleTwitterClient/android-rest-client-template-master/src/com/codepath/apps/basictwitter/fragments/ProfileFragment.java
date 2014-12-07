package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Profile;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileFragment extends Fragment {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvProfileTweets;
	private TextView numberOfFollowers;
	private TextView numberOfFollowing;
	private ImageView profileImage;
	private Profile profile;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
		if (getArguments().getString("uid") != null && getArguments().getString("screenName") != null) {
			getProfile(getArguments().getString("uid"), getArguments().getString("screenName"));
		} else {
			getProfile("13555812", "multani");
		}
	}
	
	private void getProfile(String uid, String screenName) {
		Log.d("DEBUG", "================= getProfile");
		client.getProfile(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("DEBUG", "json: " + json.toString());
				profile = Profile.fromJson(json);
				openProfileContent(profile);
				Log.d("DEBUG", "===================== get porfile done");
				
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", "================" + e.toString());
				Log.d("debug", s.toString());
			}
		}, uid, screenName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragement_profile, container, false);
		lvProfileTweets = (ListView) v.findViewById(R.id.lvProfileTweets);
		lvProfileTweets.setAdapter(aTweets);
		numberOfFollowing = (TextView) v.findViewById(R.id.tvNumberOfFollowing);
		numberOfFollowers = (TextView) v.findViewById(R.id.tvNumberOfFollower);
		profileImage = (ImageView) v.findViewById(R.id.ivProfileFragmentImage);
		return v;
	}
	
	public void openProfileContent(Profile profile) {
		if (profile == null) {
			return;
		}
		//aTweets.addAll(profile.getTweets());
		numberOfFollowing.setText("number of following: " + profile.getNumberOfFollowing());
		numberOfFollowers.setText("number of followers: " + profile.getNumberOfFollowers());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(profile.getProfileImageUrl(), profileImage);
		aTweets.clear();
		aTweets.addAll(profile.getTweets());
	}

	@Override
	public void onResume() {
		openProfileContent(profile);
		super.onResume();
	}
}
