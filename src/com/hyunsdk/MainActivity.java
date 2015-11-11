package com.hyunsdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
	Button bt1, bt2, bt3, bt4, bt5, bt6;
	sudoku_data mSudoku_data4;
	SQLiteDatabase sql4;
	int point = 0;

	public int isUserAvailable(int userId, int b) {
		int number = 0;
		Cursor c = null;
		try {
			sql4 = mSudoku_data4.getReadableDatabase();
			c = sql4.rawQuery(
					"SELECT _id FROM sudokudb" + b + " WHERE _id = ?",
					new String[] { String.valueOf(userId) });

			if (c.getCount() != 0)
				number = c.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null)
				c.close();
			sql4.close();
		}
		return number;
	}
	private ProgressDialog progressDialog = null;
	private final Handler dataHandle = new Handler(){
	      @Override
	      public void handleMessage(Message msg) {
	       // TODO Auto-generated method stub
	     progressDialog.dismiss();      
	     }
	     };
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);
		
	    progressDialog = new ProgressDialog(this);
		mSudoku_data4 = new sudoku_data(this);
		bt1 = (Button) findViewById(R.id.startbtn);
		bt2 = (Button) findViewById(R.id.endbtn);
		bt3 = (Button) findViewById(R.id.userbtn);
		bt4 = (Button) findViewById(R.id.contbtn);
		bt5 = (Button) findViewById(R.id.pointbtn);
		bt6 = (Button) findViewById(R.id.load_bt1);
		startActivity(new Intent(this,loading.class)); 

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final LayoutInflater inflater = this.getLayoutInflater();
		bt1.setOnClickListener(new View.OnClickListener() {

			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				   progressDialog.setMessage(Html.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
				   progressDialog.setIcon(0);
				   progressDialog.setCancelable(false);
				   progressDialog.show();
					Intent intent = new Intent(MainActivity.this, board.class);
					startActivity(intent);

				dataHandle.obtainMessage(0, -1, -1, "").sendToTarget(); 


			}
		});

		bt2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		bt3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				builder.setView(inflater.inflate(R.layout.maindialog, null));
				builder.setCancelable(true);
				builder.setPositiveButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}

						});
				builder.show();
			}
		});

		bt4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, loadsudoku.class);
				startActivity(intent);
			}
		});

		bt5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, pointcheck.class);
				if (isUserAvailable(1, 0) == 1) {

					sql4 = mSudoku_data4.getReadableDatabase();
					Cursor c1 = sql4.rawQuery("SELECT * FROM sudokudb0", null);
					c1.moveToFirst();
					point = c1.getInt(1);

					c1.close();
					sql4.close();

					intent.putExtra("pnt", point);

				} else {

					intent.putExtra("pnt", point);

				}
				startActivity(intent);
			}
		});
	}
}
