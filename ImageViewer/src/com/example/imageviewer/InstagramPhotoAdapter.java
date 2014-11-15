package com.example.imageviewer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

	public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photos, photos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// take data source
		// get the date item
		InstagramPhoto photo = getItem(position);
		// check if we are using a cycled view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_photos, parent, false);
		}
		// lookup the subview within the template
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption1);
		TextView tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		ImageView userProfileImg = (ImageView) convertView.findViewById(R.id.userProfileImg);
		// populate the subview
		tvLikeCount.setText("Likes: " + photo.likeCounts);
		tvCaption.setText(photo.caption);
		tvUserName.setText("author: " + photo.username);
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		imgPhoto.setImageResource(0);
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		Picasso.with(getContext()).load(photo.proileImage).into(userProfileImg);
		// return the view for that date item
		return convertView;
	}

}
