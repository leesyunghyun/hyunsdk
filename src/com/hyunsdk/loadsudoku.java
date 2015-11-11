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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class loadsudoku extends Activity {

	Button btn1, btn2, btn3, btn4, btn5;
	Button btn6, btn7, btn8, btn9, btn10;
	int hintpoint = 0;
	String lobl[][] = new String[9][9];
	String logr[][] = new String[9][9];
	String loansw[][] = new String[9][9];
	sudoku_data mSudoku_data1;
	SQLiteDatabase sql2;
	int loadsudoku = 1;

	public int isUserAvailable(int userId, int b) {
		int number = 0;
		Cursor c = null;
		try {
			sql2 = mSudoku_data1.getReadableDatabase();
			c = sql2.rawQuery(
					"SELECT _id FROM sudokudb" + b + " WHERE _id = ?",
					new String[] { String.valueOf(userId) });

			if (c.getCount() != 0)
				number = c.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null)
				c.close();
			sql2.close();
		}
		return number;
	}

	public void dele(final int b) {
		View view = this.getLayoutInflater().inflate(R.layout.delete, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		builder.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				sql2 = mSudoku_data1.getReadableDatabase();

				sql2.execSQL("DROP TABLE IF EXISTS sudokudb" + b + "");

				sql2.close();
				Toast.makeText(getApplicationContext(),
						"Storage" + b + "의 데이터가\n성공적으로 삭제되었습니다.",
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				Toast.makeText(getApplicationContext(),
						"Storage" + b + "의 삭제가 취소되었습니다.", Toast.LENGTH_SHORT)
						.show();
			}
		});
		builder.show();

		return;
	}

	private ProgressDialog progressDialog = null;
	private final Handler dataHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			progressDialog.dismiss(); // Handler 에서 프로그레스바 종료
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		progressDialog = new ProgressDialog(this);
		btn1 = (Button) findViewById(R.id.load_bt1);
		btn2 = (Button) findViewById(R.id.load_bt2);
		btn3 = (Button) findViewById(R.id.load_bt3);
		btn4 = (Button) findViewById(R.id.load_bt4);
		btn5 = (Button) findViewById(R.id.load_bt5);
		btn6 = (Button) findViewById(R.id.lodelete_bt1);
		btn7 = (Button) findViewById(R.id.lodelete_bt2);
		btn8 = (Button) findViewById(R.id.lodelete_bt3);
		btn9 = (Button) findViewById(R.id.lodelete_bt4);
		btn10 = (Button) findViewById(R.id.lodelete_bt5);

		mSudoku_data1 = new sudoku_data(this);

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 1) == 1) {
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setMessage(Html
							.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
					progressDialog.setIcon(0);
					progressDialog.setCancelable(false);
					progressDialog.show();
					Intent intent = new Intent(loadsudoku.this, board.class);
					sql2 = mSudoku_data1.getReadableDatabase();

					Cursor c1 = sql2.rawQuery("SELECT * FROM sudokudb1", null);
					c1.moveToFirst();
					hintpoint = c1.getInt(4);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							lobl[i][j] = c1.getString(1);
							logr[i][j] = c1.getString(2);
							loansw[i][j] = c1.getString(3);
							
							c1.moveToNext();
						}
					}

					intent.putExtra("loa", loadsudoku);
					intent.putExtra("hintcount2", hintpoint);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							intent.putExtra("abc" + i + j, lobl[i][j]);
							intent.putExtra("def" + i + j, logr[i][j]);
							intent.putExtra("ghi" + i + j, loansw[i][j]);
						}
					}
					sql2.close();
					c1.close();
					dataHandle.obtainMessage(0, -1, -1, "").sendToTarget();
					Toast.makeText(getApplicationContext(),
							"Storage1의 데이터를\n성공적으로 불러왔습니다.", Toast.LENGTH_SHORT)
							.show();
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage1의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 2) == 1) {
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setMessage(Html
							.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
					progressDialog.setIcon(0);
					progressDialog.setCancelable(false);
					progressDialog.show();
					Intent intent = new Intent(loadsudoku.this, board.class);
					sql2 = mSudoku_data1.getReadableDatabase();

					Cursor c1 = sql2.rawQuery("SELECT * FROM sudokudb2", null);
					c1.moveToFirst();
					hintpoint = c1.getInt(4);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							lobl[i][j] = c1.getString(1);
							logr[i][j] = c1.getString(2);
							loansw[i][j] = c1.getString(3);
							
							c1.moveToNext();
						}
					}
					intent.putExtra("loa", loadsudoku);
					intent.putExtra("hintcount2", hintpoint);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							intent.putExtra("abc" + i + j, lobl[i][j]);
							intent.putExtra("def" + i + j, logr[i][j]);
							intent.putExtra("ghi" + i + j, loansw[i][j]);
						}
					}
					sql2.close();
					c1.close();
					dataHandle.obtainMessage(0, -1, -1, "").sendToTarget();
					Toast.makeText(getApplicationContext(),
							"Storage2의 데이터를\n성공적으로 불러왔습니다.", Toast.LENGTH_SHORT)
							.show();
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage2의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 3) == 1) {
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setMessage(Html
							.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
					progressDialog.setIcon(0);
					progressDialog.setCancelable(false);
					progressDialog.show();
					Intent intent = new Intent(loadsudoku.this, board.class);
					sql2 = mSudoku_data1.getReadableDatabase();

					Cursor c1 = sql2.rawQuery("SELECT * FROM sudokudb3", null);
					c1.moveToFirst();
					hintpoint = c1.getInt(4);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							lobl[i][j] = c1.getString(1);
							logr[i][j] = c1.getString(2);
							loansw[i][j] = c1.getString(3);
							c1.moveToNext();
						}
					}
					intent.putExtra("loa", loadsudoku);
					intent.putExtra("hintcount2", hintpoint);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							intent.putExtra("abc" + i + j, lobl[i][j]);
							intent.putExtra("def" + i + j, logr[i][j]);
							intent.putExtra("ghi" + i + j, loansw[i][j]);
						}
					}
					sql2.close();
					c1.close();
					dataHandle.obtainMessage(0, -1, -1, "").sendToTarget();
					Toast.makeText(getApplicationContext(),
							"Storage3의 데이터를\n성공적으로 불러왔습니다.", Toast.LENGTH_SHORT)
							.show();
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage3의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 4) == 1) {
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setMessage(Html
							.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
					progressDialog.setIcon(0);
					progressDialog.setCancelable(false);
					progressDialog.show();
					Intent intent = new Intent(loadsudoku.this, board.class);
					sql2 = mSudoku_data1.getReadableDatabase();

					Cursor c1;
					c1 = sql2.rawQuery("SELECT * FROM sudokudb4", null);
					c1.moveToFirst();
					hintpoint = c1.getInt(4);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							lobl[i][j] = c1.getString(1);
							logr[i][j] = c1.getString(2);
							loansw[i][j] = c1.getString(3);
							c1.moveToNext();
						}
					}
					intent.putExtra("loa", loadsudoku);
					intent.putExtra("hintcount2", hintpoint);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							intent.putExtra("abc" + i + j, lobl[i][j]);
							intent.putExtra("def" + i + j, logr[i][j]);
							intent.putExtra("ghi" + i + j, loansw[i][j]);
						}
					}
					sql2.close();
					c1.close();
					dataHandle.obtainMessage(0, -1, -1, "").sendToTarget();
					Toast.makeText(getApplicationContext(),
							"Storage4의 데이터를\n성공적으로 불러왔습니다.", Toast.LENGTH_SHORT)
							.show();
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage4의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isUserAvailable(1, 5) == 1) {
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setMessage(Html
							.fromHtml("<FONT Color='Black'>Data Loading...</FONT>"));
					progressDialog.setIcon(0);
					progressDialog.setCancelable(false);
					progressDialog.show();
					Intent intent = new Intent(loadsudoku.this, board.class);
					sql2 = mSudoku_data1.getReadableDatabase();

					Cursor c1 = sql2.rawQuery("SELECT * FROM sudokudb5", null);
					c1.moveToFirst();
					hintpoint = c1.getInt(4);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							lobl[i][j] = c1.getString(1);
							logr[i][j] = c1.getString(2);
							loansw[i][j] = c1.getString(3);
							c1.moveToNext();
						}
					}
					intent.putExtra("loa", loadsudoku);
					intent.putExtra("hintcount2", hintpoint);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							intent.putExtra("abc" + i + j, lobl[i][j]);
							intent.putExtra("def" + i + j, logr[i][j]);
							intent.putExtra("ghi" + i + j, loansw[i][j]);
						}
					}
					sql2.close();
					c1.close();
					dataHandle.obtainMessage(0, -1, -1, "").sendToTarget();
					Toast.makeText(getApplicationContext(),
							"Storage5의 데이터를\n성공적으로 불러왔습니다.", Toast.LENGTH_SHORT)
							.show();
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Storage5의 데이터가 없습니다.\n다시 확인해주세요",
							Toast.LENGTH_SHORT).show();
				}
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
				}			}
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
