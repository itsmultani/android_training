package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	private HomeTimelineFragment fragmentHomeTimeline;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		fragmentHomeTimeline = new HomeTimelineFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				fragmentHomeTimeline.populateTimeline();
			}
		});
		
		return v;
	}
	
	public TweetArrayAdapter getAapter() {
		return aTweets;
	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	

}
