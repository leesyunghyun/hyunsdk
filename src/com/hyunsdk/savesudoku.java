package com.hyunsdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class savesudoku extends Activity {
	Context context;
	int sapoint = 0;
	int hintpoint = 0;
	String satvbl[][] = new String[9][9];
	String satvgr[][] = new String[9][9];
	String answ[][] = new String[9][9];
	Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;
	sudoku_data mSudoku_data;
	SQLiteDatabase sql;
	
	public void dele(final int b) {
		View view = this.getLayoutInflater().inflate(R.layout.delete, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		builder.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				sql = mSudoku_data.getReadableDatabase();

				sql.execSQL("DROP TABLE IF EXISTS sudokudb"+b+"");

				sql.close();
				
				Toast.makeText(getApplicationContext(), "Storage"+b+"의 데이터가\n성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				Toast.makeText(getApplicationContext(), "Storage"+b+"의 삭제가 취소되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();

		return;
	}
	public int isUserAvailable(int userId, int b) {
		int number = 0;
		Cursor c = null;
		try {
			sql = mSudoku_data.getReadableDatabase();
			c = sql.rawQuery(
					"SELECT _id FROM sudokudb" + b + " WHERE _id = ?",
					new String[] { String.valueOf(userId) });

			if (c.getCount() != 0)
				number = c.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null)
				c.close();
			sql.close();
		}
		return number;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save);

		Intent intent = getIntent();
		sapoint = intent.getIntExtra("pt", 0);
		btn1 = (Button) findViewById(R.id.save_bt1);
		btn2 = (Button) findViewById(R.id.save_bt2);
		btn3 = (Button) findViewById(R.id.save_bt3);
		btn4 = (Button) findViewById(R.id.save_bt4);
		btn5 = (Button) findViewById(R.id.save_bt5);
		btn6 = (Button) findViewById(R.id.sadelete_bt1);
		btn7 = (Button) findViewById(R.id.sadelete_bt2);
		btn8 = (Button) findViewById(R.id.sadelete_bt3);
		btn9 = (Button) findViewById(R.id.sadelete_bt4);
		btn10 = (Button) findViewById(R.id.sadelete_bt5);
		mSudoku_data = new sudoku_data(this);

		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				satvbl[i][j] = intent.getStringExtra("blue" + i + j);
				satvgr[i][j] = intent.getStringExtra("gray" + i + j);
				answ[i][j] = intent.getStringExtra("answer" + i + j);
			}
		}
		hintpoint = intent.getIntExtra("hintpt", 0);
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				if (satvgr[i][j] == null) {
					satvgr[i][j] = "   ";
				}

				if (satvbl[i][j] == null) {
					satvbl[i][j] = "   ";
				}

			}
		}

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
		
				   
				sql = mSudoku_data.getWritableDatabase();
				sql.execSQL("DROP TABLE IF EXISTS sudokudb1");
				sql.execSQL("CREATE TABLE sudokudb1 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT DEFAULT 0, sudokunumber2 TEXT DEFAULT 0, sudokunumber3 TEXT, sudokunumber4 TEXT);");
				
				for (int i = 0; i <= 8; i++) {
					for (int j = 0; j <= 8; j++) {
						 
						sql.execSQL("INSERT INTO sudokudb1(sudokunumber1, sudokunumber2, sudokunumber3, sudokunumber4) VALUES('"
								+ satvbl[i][j]
								+ "', '"
								+ satvgr[i][j]
								+ "', '"
								+ answ[i][j] + "', '" + hintpoint + "');");
						
					}
				}
				
				Toast.makeText(getApplicationContext(), "Storage1에 데이터를\n성공적으로 저장되었습니다.",
						Toast.LENGTH_SHORT).show();
				sql.close();
				finish();
				
			}
		});

		/* Cursor c1 = sql.rawQuery("SELECT * FROM sudokudb1", null); */
		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
				sql = mSudoku_data.getWritableDatabase();

				sql.execSQL("DROP TABLE IF EXISTS sudokudb2");
				sql.execSQL("CREATE TABLE sudokudb2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT DEFAULT 0, sudokunumber2 TEXT DEFAULT 0, sudokunumber3 TEXT, sudokunumber4 TEXT);");
				for (int i = 0; i <= 8; i++) {
					for (int j = 0; j <= 8; j++) {

						sql.execSQL("INSERT INTO sudokudb2(sudokunumber1, sudokunumber2, sudokunumber3, sudokunumber4) VALUES('"
								+ satvbl[i][j]
								+ "', '"
								+ satvgr[i][j]
								+ "', '"
								+ answ[i][j] + "', '" + hintpoint + "');");

					}
				}
	
				Toast.makeText(getApplicationContext(), "Storage2에 데이터를\n성공적으로 저장되었습니다.",
						Toast.LENGTH_SHORT).show();
				sql.close();
				finish();

			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   
				sql = mSudoku_data.getWritableDatabase();

				sql.execSQL("DROP TABLE IF EXISTS sudokudb3");
				sql.execSQL("CREATE TABLE sudokudb3 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT DEFAULT 0, sudokunumber2 TEXT DEFAULT 0, sudokunumber3 TEXT, sudokunumber4 TEXT);");
				for (int i = 0; i <= 8; i++) {
					for (int j = 0; j <= 8; j++) {

						sql.execSQL("INSERT INTO sudokudb3(sudokunumber1, sudokunumber2, sudokunumber3, sudokunumber4) VALUES('"
								+ satvbl[i][j]
								+ "', '"
								+ satvgr[i][j]
								+ "', '"
								+ answ[i][j] + "', '" + hintpoint + "');");

					}
				}
				
				Toast.makeText(getApplicationContext(), "Storage3에 데이터를\n성공적으로 저장되었습니다.",
						Toast.LENGTH_SHORT).show();
				sql.close();
				finish();

			}
		});

		btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  
				sql = mSudoku_data.getWritableDatabase();

				sql.execSQL("DROP TABLE IF EXISTS sudokudb4");
				sql.execSQL("CREATE TABLE sudokudb4 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT DEFAULT 0, sudokunumber2 TEXT DEFAULT 0, sudokunumber3 TEXT, sudokunumber4 TEXT);");
				for (int i = 0; i <= 8; i++) {
					for (int j = 0; j <= 8; j++) {

						sql.execSQL("INSERT INTO sudokudb4(sudokunumber1, sudokunumber2, sudokunumber3, sudokunumber4) VALUES('"
								+ satvbl[i][j]
								+ "', '"
								+ satvgr[i][j]
								+ "', '"
								+ answ[i][j] + "', '" + hintpoint + "');");

					}
				}
				
				Toast.makeText(getApplicationContext(), "Storage4에 데이터를\n성공적으로 저장되었습니다.",
						Toast.LENGTH_SHORT).show();
				sql.close();
				finish();

			}
		});

		btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  
				sql = mSudoku_data.getWritableDatabase();

				sql.execSQL("DROP TABLE IF EXISTS sudokudb5");
				sql.execSQL("CREATE TABLE sudokudb5 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT DEFAULT 0, sudokunumber2 TEXT DEFAULT 0, sudokunumber3 TEXT, sudokunumber4 TEXT);");
				for (int i = 0; i <= 8; i++) {
					for (int j = 0; j <= 8; j++) {

						sql.execSQL("INSERT INTO sudokudb5(sudokunumber1, sudokunumber2, sudokunumber3, sudokunumber4) VALUES('"
								+ satvbl[i][j]
								+ "', '"
								+ satvgr[i][j]
								+ "', '"
								+ answ[i][j] + "', '" + hintpoint +  "');");

					}
				}
				
				Toast.makeText(getApplicationContext(), "Storage5에 데이터를\n성공적으로 저장되었습니다.",
						Toast.LENGTH_SHORT).show();
				
				sql.close();
				finish();

			}
		});

		btn6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 1) == 1) {

					dele(1);
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage1의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isUserAvailable(1, 2) == 1) {

					dele(2);
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage2의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isUserAvailable(1, 3) == 1) {

					dele(3);
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage3의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isUserAvailable(1, 4) == 1) {

					dele(4);
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage4의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 5) == 1) {

					dele(5);
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage5의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
