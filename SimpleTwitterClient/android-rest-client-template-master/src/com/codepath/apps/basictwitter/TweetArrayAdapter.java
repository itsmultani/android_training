package com.codepath.apps.basictwitter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;


public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName1);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvCreateTime = (TextView) v.findViewById(R.id.tvCreateTime);
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		String createTime = tweet.getCreatedAt();
		tvUserName.setText(tweet.getUser().getScreenName() + " - " + getTimeDiffStringFromNow(createTime));
		tvBody.setText(tweet.getBody());
		tvCreateTime.setText(String.valueOf(getTimeStampFromString(createTime)));
		return v;
	}
	
	private long getTimeStampFromString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		try {
			return sdf.parse(time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0L;
		}
		
	}
	
	private String getTimeDiffStringFromNow(String time) {
		Date date = new Date();
		long pastTime = getTimeStampFromString(time);
		long diff = date.getTime() - pastTime;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		StringBuilder returnString = new StringBuilder();
		if (diffDays != 0) {
			returnString.append(diffDays + " days ");
		}
		if (diffHours != 0) {
			returnString.append(diffHours + " hours ");
		}
		returnString.append(diffMinutes + " mins ago");
		return returnString.toString();
	}
}