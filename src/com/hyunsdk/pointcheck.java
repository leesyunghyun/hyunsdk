package com.hyunsdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class pointcheck extends Activity{

	int point = 0;
	TextView tv1;
	Button btn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pointcheck);
		tv1 = (TextView) findViewById(R.id.pointtv2);
		btn1 = (Button) findViewById(R.id.pointbtn);
		
		Intent intent = getIntent();
		
		point = intent.getIntExtra("pnt", 0);
		
		tv1.setText(Integer.toString(point) + " ");
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	
	
}
