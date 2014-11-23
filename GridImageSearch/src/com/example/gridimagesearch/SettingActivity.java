package com.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingActivity extends Activity {
	
	private RadioGroup colorGroup;
	private RadioGroup sizeGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		colorGroup = (RadioGroup) findViewById(R.id.colorGroup);
		sizeGroup = (RadioGroup) findViewById(R.id.sizeGroup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveSetting(View v) {
		Intent intent = new Intent(this, SearchActivity.class);
		int sizeGroupCheckedId = sizeGroup.getCheckedRadioButtonId();
		RadioButton sizeBt = (RadioButton) findViewById(sizeGroupCheckedId);
		
		int colorGroupCheckedId = colorGroup.getCheckedRadioButtonId();
		RadioButton colorBt = (RadioButton) findViewById(colorGroupCheckedId);
		
		intent.putExtra("size", sizeBt.getText().toString());
		intent.putExtra("color", colorBt.getText().toString());
		
		startActivity(intent);
	}
}
