package com.hyunsdk;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;


public class emailsend extends TabActivity {

	Button btn1, btn2, btn3, btn4;
	TextView tv1;
	EditText et1, et2;
	FrameLayout FL;
	LinearLayout l1, l2;
	TabWidget tabw1;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explain2);

		l1 = (LinearLayout) findViewById(R.id.tab1);
		l2 = (LinearLayout) findViewById(R.id.tab2);
		btn1 = (Button) findViewById(R.id.exp2close_btn);
		btn2 = (Button) findViewById(R.id.ema_bt1);
		btn3 = (Button) findViewById(R.id.exp2kakao);
		btn4 = (Button) findViewById(R.id.exp3close_btn);
		tv1 = (TextView) findViewById(R.id.ema_tv2);
		et1 = (EditText) findViewById(R.id.ema_et1);
		et2 = (EditText) findViewById(R.id.ema_et2);
		FL = (FrameLayout) findViewById(android.R.id.tabcontent);
		tabw1 = (TabWidget) findViewById(android.R.id.tabs);
		mSudoku_data4 = new sudoku_data(this);

		TabHost tabHost = getTabHost();

		TabSpec first = tabHost.newTabSpec("first").setIndicator("친구초대");
		first.setContent(R.id.tab1);
		tabHost.addTab(first);

		TabSpec two = tabHost.newTabSpec("two").setIndicator("문의하기");
		two.setContent(R.id.tab2);
		tabHost.addTab(two);

		tabHost.setCurrentTab(0);

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String to = tv1.getText().toString();
				String subject = et1.getText().toString();
				String message = et2.getText().toString();

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);

				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email, "이메일을 선택해주세요 !! "));
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = "http://blog.naver.com/cbfhry/140193016045";

				KakaoLink kakao = KakaoLink.getLink(getApplicationContext());

				if (!kakao.isAvailableIntent())
					return;

				try {
					kakao.openKakaoLink(
							emailsend.this,
							str,
							"스도쿠한판하실래요",
							getPackageName().toString(),
							getPackageManager().getPackageInfo(
									getPackageName(), 0).versionName.toString(),
							"Hyun Sudouku", "UTF-8");
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (isUserAvailable(1, 0) == 1) {

					sql4 = mSudoku_data4.getReadableDatabase();
					Cursor c1 = sql4.rawQuery("SELECT * FROM sudokudb0", null);
					c1.moveToFirst();
					point = c1.getInt(1);

					c1.close();
					sql4.close();
				}
				point = point + 1;
				sql4 = mSudoku_data4.getWritableDatabase();
				sql4.execSQL("DROP TABLE IF EXISTS sudokudb0");
				sql4.execSQL("CREATE TABLE sudokudb0 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT);");
				sql4.execSQL("INSERT INTO sudokudb0(sudokunumber1) VALUES('"
						+ point + "');");

				sql4.close();
				
			}

		});
		btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
