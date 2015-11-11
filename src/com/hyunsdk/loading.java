package com.hyunsdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.Toast;

public class loading extends Activity {
	private ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(Html
				.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
		progressDialog.setIcon(0);
		progressDialog.setCancelable(false);

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				finish();
				progressDialog.dismiss();
			}
		};

		handler.sendEmptyMessageDelayed(0, 2000);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				progressDialog.show();
			}
		}, 800);

		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Hyun's Sudoku에 오신 것을 환영합니다!", Toast.LENGTH_SHORT)
						.show();

			}
		});

	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);

		builder.show();

	}
}
