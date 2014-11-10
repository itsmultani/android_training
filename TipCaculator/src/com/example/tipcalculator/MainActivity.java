package com.example.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText edTotalAmount;
    TextView result;
    Button currentPercent = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edTotalAmount = (EditText) findViewById(R.id.etTotalAmount);
        
        edTotalAmount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				float amount = getAmountFromEdiText(edTotalAmount);
				if (amount != 0 && currentPercent != null) {
					calculateTips(currentPercent);
				}
			}
		});
        
        result = (TextView) findViewById(R.id.tvResult);
    }
    
    public void calculateTips(View v) {
    	currentPercent = (Button) v;
    	float tip = 0;
    	float amount = getAmountFromEdiText(edTotalAmount);
    	if (amount != 0) {
    		Button b = (Button) v;
            String buttonText = b.getText().toString();
            float p = Float.valueOf(buttonText.substring(0, 2));
            tip = amount * p / 100;
    	}
		result.setText("$ " + String.format("%.2f", tip));
    }
    
    private float getAmountFromEdiText(EditText t) {
    	String totalAmount = edTotalAmount.getText().toString();
    	float amount = 0;
    	try {
    		amount = Float.valueOf(totalAmount);
    	} catch (Exception e) {
    	}
		return amount;
    }
}
