package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelneFragment;
import com.codepath.apps.basictwitter.fragments.ProfileFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		Log.d("DEBUG", "timeline activity on Create");
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.timeline_menu, menu);
		return true;
	}
	
	public void composeTweet(MenuItem menu) {
		Intent intent = new Intent();
		intent.setClass(this, ComposeTweetActivity.class);
		startActivity(intent);
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tab0 = actionBar
				.newTab()
				.setText("Profile")
				.setIcon(R.drawable.ic_profile)
				.setTabListener(
				    new FragmentTabListener<ProfileFragment>(R.id.flContainer, this, "profile",
									ProfileFragment.class));

			actionBar.addTab(tab0);
			
		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTabListener(
			    new FragmentTabListener<MentionsTimelneFragment>(R.id.flContainer, this, "mentions",
								MentionsTimelneFragment.class));

		actionBar.addTab(tab2);
		

		actionBar.selectTab(tab0);
	}
}
