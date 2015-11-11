package com.hyunsdk;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class board extends Activity {
	Random rnd = new Random();
	int hint = 0;
	int hintcount = 3;
	int bl = 1;
	int gr = 2;
	int f = 0; // 난이도
	int a = 0, b = 0, c = 0;
	int ab;
	int bc;
	int load1 = 0;
	int flag = 0;
	int faultcount = 0;
	int point = 0;
	String list[] = { "하(권장연령 : 10세이하)", "중(권장연령 : 12~16세)", "상(권장연령 : 17세이상)" };
	String dbblue[][] = new String[9][9];
	String dbgray[][] = new String[9][9];
	String cpnum1[][] = new String[9][9];
	String temp[] = new String[9];
	TextView tv[][] = new TextView[9][9];
	ToggleButton t1, t2, t3, t4, t5, t6, t7, t8, t9;
	Button cle_bt, sav_bt, ans_bt, re_bt, exp_bt, ext_bt;
	sudoku_data mSudoku_data3;
	SQLiteDatabase sql3;

	public int isUserAvailable(int userId, int b) {
		int number = 0;
		Cursor c = null;
		try {
			sql3 = mSudoku_data3.getReadableDatabase();
			c = sql3.rawQuery(
					"SELECT _id FROM sudokudb" + b + " WHERE _id = ?",
					new String[] { String.valueOf(userId) });

			if (c.getCount() != 0)
				number = c.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null)
				c.close();
			sql3.close();
		}
		return number;
	}

	int tv1[][] = {
			{ R.id.ltv1_1, R.id.ltv1_2, R.id.ltv1_3, R.id.ltv1_4, R.id.ltv1_5,
					R.id.ltv1_6, R.id.ltv1_7, R.id.ltv1_8, R.id.ltv1_9 },
			{ R.id.ltv2_1, R.id.ltv2_2, R.id.ltv2_3, R.id.ltv2_4, R.id.ltv2_5,
					R.id.ltv2_6, R.id.ltv2_7, R.id.ltv2_8, R.id.ltv2_9 },
			{ R.id.ltv3_1, R.id.ltv3_2, R.id.ltv3_3, R.id.ltv3_4, R.id.ltv3_5,
					R.id.ltv3_6, R.id.ltv3_7, R.id.ltv3_8, R.id.ltv3_9 },
			{ R.id.ltv4_1, R.id.ltv4_2, R.id.ltv4_3, R.id.ltv4_4, R.id.ltv4_5,
					R.id.ltv4_6, R.id.ltv4_7, R.id.ltv4_8, R.id.ltv4_9 },
			{ R.id.ltv5_1, R.id.ltv5_2, R.id.ltv5_3, R.id.ltv5_4, R.id.ltv5_5,
					R.id.ltv5_6, R.id.ltv5_7, R.id.ltv5_8, R.id.ltv5_9 },
			{ R.id.ltv6_1, R.id.ltv6_2, R.id.ltv6_3, R.id.ltv6_4, R.id.ltv6_5,
					R.id.ltv6_6, R.id.ltv6_7, R.id.ltv6_8, R.id.ltv6_9 },
			{ R.id.ltv7_1, R.id.ltv7_2, R.id.ltv7_3, R.id.ltv7_4, R.id.ltv7_5,
					R.id.ltv7_6, R.id.ltv7_7, R.id.ltv7_8, R.id.ltv7_9 },
			{ R.id.ltv8_1, R.id.ltv8_2, R.id.ltv8_3, R.id.ltv8_4, R.id.ltv8_5,
					R.id.ltv8_6, R.id.ltv8_7, R.id.ltv8_8, R.id.ltv8_9 },
			{ R.id.ltv9_1, R.id.ltv9_2, R.id.ltv9_3, R.id.ltv9_4, R.id.ltv9_5,
					R.id.ltv9_6, R.id.ltv9_7, R.id.ltv9_8, R.id.ltv9_9 } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface myNewFace = Typeface.createFromAsset(getAssets(),
				"fonts/boardff.ttf");
		mSudoku_data3 = new sudoku_data(this);
		Intent intent = getIntent();
		load1 = intent.getIntExtra("loa", 0);
		hintcount = intent.getIntExtra("hintcount2", 0);
		t1 = (ToggleButton) findViewById(R.id.toggleButton1);
		t2 = (ToggleButton) findViewById(R.id.toggleButton2);
		t3 = (ToggleButton) findViewById(R.id.toggleButton3);
		t4 = (ToggleButton) findViewById(R.id.toggleButton4);
		t5 = (ToggleButton) findViewById(R.id.toggleButton5);
		t6 = (ToggleButton) findViewById(R.id.toggleButton6);
		t7 = (ToggleButton) findViewById(R.id.toggleButton7);
		t8 = (ToggleButton) findViewById(R.id.toggleButton8);
		t9 = (ToggleButton) findViewById(R.id.toggleButton9);
		cle_bt = (Button) findViewById(R.id.clr_btn); // 청소
		sav_bt = (Button) findViewById(R.id.sav_btn); // 저장
		ans_bt = (Button) findViewById(R.id.an_btn); // 정답
		re_bt = (Button) findViewById(R.id.re_btn); // 다시만들기
		exp_bt = (Button) findViewById(R.id.ex_btn); // 설명서
		ext_bt = (Button) findViewById(R.id.exit_btn);

		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				tv[i][j] = (TextView) findViewById(tv1[i][j]);
				tv[i][j].setTypeface(myNewFace);
			}
		}

		if (load1 == 1) {
			flag = 1;
			cle_bt.setEnabled(true);
			sav_bt.setEnabled(true);
			ans_bt.setEnabled(true);
			exp_bt.setEnabled(true);
			ext_bt.setEnabled(true);
			t1.setEnabled(true);
			t2.setEnabled(true);
			t3.setEnabled(true);
			t4.setEnabled(true);
			t5.setEnabled(true);
			t6.setEnabled(true);
			t7.setEnabled(true);
			t8.setEnabled(true);
			t9.setEnabled(true);

			for (int i = 0; i <= 8; i++) {
				for (int j = 0; j <= 8; j++) {
					dbblue[i][j] = intent.getStringExtra("abc" + i + j);
					dbgray[i][j] = intent.getStringExtra("def" + i + j);
					cpnum1[i][j] = intent.getStringExtra("ghi" + i + j);
					tv[i][j].setVisibility(android.view.View.VISIBLE);
				}
			}

			for (int i = 0; i <= 8; i++) {
				for (int j = 0; j <= 8; j++) {
					if (dbgray[i][j].equals((String) "   ")) {
						tv[i][j].setTextColor(Color.BLUE);
						tv[i][j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
						tv[i][j].setText(dbblue[i][j]);
					} else {
						tv[i][j].setTextColor(Color.GRAY);
						tv[i][j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
						tv[i][j].setText(dbgray[i][j]);
					}
				}
			}

		} else {
			cle_bt.setEnabled(false);
			sav_bt.setEnabled(false);
			ans_bt.setEnabled(false);
			exp_bt.setEnabled(false);
			ext_bt.setEnabled(false);
			t1.setEnabled(false);
			t2.setEnabled(false);
			t3.setEnabled(false);
			t4.setEnabled(false);
			t5.setEnabled(false);
			t6.setEnabled(false);
			t7.setEnabled(false);
			t8.setEnabled(false);
			t9.setEnabled(false);

		}
		if (flag == 0) {
			LayoutInflater inflater = this.getLayoutInflater();
			AlertDialog.Builder dia1 = new AlertDialog.Builder(board.this);
			
			
			dia1.setTitle("알려드립니다!!!");
			dia1.setView(inflater.inflate(R.layout.firstexp, null));
			dia1.setCancelable(false);
			dia1.setPositiveButton("닫기", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}

			});
			dia1.show();
		}

		sav_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (flag == 1) {
					Intent intent = new Intent(board.this, savesudoku.class);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							if (tv[i][j].getCurrentTextColor() == Color.BLUE) {

								intent.putExtra("blcol", bl);
								intent.putExtra("blue" + i + j, tv[i][j]
										.getText().toString());

							} else if (tv[i][j].getCurrentTextColor() == Color.GRAY) {
								intent.putExtra("grcol", gr);
								intent.putExtra("gray" + i + j, tv[i][j]
										.getText().toString());

							}
							intent.putExtra("answer" + i + j, cpnum1[i][j]);
						}
					}
					intent.putExtra("hintpt", hintcount);
					startActivityForResult(intent, 0);
				} else
					Toast.makeText(getApplicationContext(),
							"Error!! Make버튼을 눌러주세요!", Toast.LENGTH_SHORT)
							.show();
			}
		});
		exp_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(board.this, emailsend.class);
				startActivity(intent);
			}
		});
		ans_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (flag == 1) {
					AlertDialog.Builder dia1 = new AlertDialog.Builder(
							board.this);

					dia1.setMessage("정말 정답을 확인하시겠습니까 ? \n확인 후에는 되돌릴 수 없습니다.");
					dia1.setCancelable(false);
					dia1.setPositiveButton("정답확인",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									if (isUserAvailable(1, 0) == 1) {
										sql3 = mSudoku_data3
												.getReadableDatabase();
										Cursor c1 = sql3
												.rawQuery(
														"SELECT * FROM sudokudb0",
														null);
										c1.moveToFirst();
										point = c1.getInt(1);

										c1.close();
										sql3.close();
									}

									if (flag == 1) {

										for (int i = 0; i <= 8; i++) {

											for (int j = 0; j <= 8; j++) {
												if (!(tv[i][j].getText()
														.toString()
														.equals((String) cpnum1[i][j]))) {
													tv[i][j].setText(cpnum1[i][j]);
													tv[i][j].setTextColor(Color.RED);
													faultcount++;
												}

											}
										}
										if (faultcount == 0) {
											Toast.makeText(
													getApplicationContext(),
													"모두 맞추셨습니다. 축하드립니다.",
													Toast.LENGTH_SHORT).show();
											flag = 0;
											faultcount = 0;
										} else {
											Toast.makeText(
													getApplicationContext(),
													"틀린 개수 : " + faultcount
															+ " !!",
													Toast.LENGTH_SHORT).show();

										}
										switch (f) {
										case 25:
											if (faultcount >= 0
													&& faultcount <= 2) {
												point = point + 3;
												Toast.makeText(
														getApplicationContext(),
														"포인트 3점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();

											} else if (faultcount >= 3
													&& faultcount <= 8) {
												point = point + 2;
												Toast.makeText(
														getApplicationContext(),
														"포인트 2점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											} else if (faultcount >= 9
													&& faultcount <= 12) {
												point = point + 1;
												Toast.makeText(
														getApplicationContext(),
														"포인트 1점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												point = point + 0;
												Toast.makeText(
														getApplicationContext(),
														"아쉽군요. 포인트를 쵝득하지 못하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											}
											break;
										case 45:
											if (faultcount >= 0
													&& faultcount <= 4) {
												point = point + 7;
												Toast.makeText(
														getApplicationContext(),
														"포인트 7점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();

											} else if (faultcount >= 5
													&& faultcount <= 11) {
												point = point + 4;
												Toast.makeText(
														getApplicationContext(),
														"포인트 4점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											} else if (faultcount >= 12
													&& faultcount <= 23) {
												point = point + 2;
												Toast.makeText(
														getApplicationContext(),
														"포인트 2점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												point = point + 0;
												Toast.makeText(
														getApplicationContext(),
														"아쉽군요. 포인트를 쵝득하지 못하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											}
											break;
										case 73:
											if (faultcount >= 0
													&& faultcount <= 5) {
												point = point + 10;
												Toast.makeText(
														getApplicationContext(),
														"포인트 10점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();

											} else if (faultcount >= 6
													&& faultcount <= 15) {
												point = point + 5;
												Toast.makeText(
														getApplicationContext(),
														"포인트 5점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											} else if (faultcount >= 16
													&& faultcount <= 30) {
												point = point + 3;
												Toast.makeText(
														getApplicationContext(),
														"포인트 3점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												point = point + 0;
												Toast.makeText(
														getApplicationContext(),
														"아쉽군요. 포인트를 쵝득하지 못하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											}
											break;
										default:
											if (faultcount >= 0
													&& faultcount <= 2) {
												point = point + 1;
												Toast.makeText(
														getApplicationContext(),
														"포인트 1점을 획득하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();

											} else {
												point = point + 0;
												Toast.makeText(
														getApplicationContext(),
														"아쉽군요. 포인트를 쵝득하지 못하셨습니다!",
														Toast.LENGTH_SHORT)
														.show();
											}
											break;

										}

										sql3 = mSudoku_data3
												.getWritableDatabase();

										sql3.execSQL("DROP TABLE IF EXISTS sudokudb0");
										sql3.execSQL("CREATE TABLE sudokudb0 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT);");
										sql3.execSQL("INSERT INTO sudokudb0(sudokunumber1) VALUES('"
												+ point + "');");

										sql3.close();
										faultcount = 0;
										flag = 0;
									}
								}
							});
					dia1.setNegativeButton("닫기",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
					dia1.show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Error!! Make버튼을 눌러주세요!", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		cle_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {

					AlertDialog.Builder dia2 = new AlertDialog.Builder(
							board.this);
					dia2.setMessage("파란색 숫자가 전부 지워집니다.\n  계속 하시겠습니까?");
					dia2.setCancelable(false);
					dia2.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									t1.setChecked(false);
									t2.setChecked(false);
									t3.setChecked(false);
									t4.setChecked(false);
									t5.setChecked(false);
									t6.setChecked(false);
									t7.setChecked(false);
									t8.setChecked(false);
									t9.setChecked(false);

									for (int i = 0; i <= 8; i++) {
										for (int j = 0; j <= 8; j++) {
											if (tv[i][j].getCurrentTextColor() == Color.RED) {
												Toast.makeText(
														getApplicationContext(),
														"Error!! Make버튼을 눌러주세요!",
														Toast.LENGTH_SHORT)
														.show();
												return;
											}

										}
									}

									for (int i = 0; i <= 8; i++) {
										for (int j = 0; j <= 8; j++) {

											if (tv[i][j].getCurrentTextColor() == Color.BLUE) {
												tv[i][j].setText("   ");
											}

										}
									}
									if (flag == 1) {
										Toast.makeText(getApplicationContext(),
												"화면 청소완료! \n다시 끈기를 가지고 화이팅!!",
												Toast.LENGTH_SHORT).show();
									}
								}

							});

					dia2.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

					dia2.show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Error!! Make버튼을 눌러주세요!", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		re_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				t1.setEnabled(true);
				t2.setEnabled(true);
				t3.setEnabled(true);
				t4.setEnabled(true);
				t5.setEnabled(true);
				t6.setEnabled(true);
				t7.setEnabled(true);
				t8.setEnabled(true);
				t9.setEnabled(true);
				t1.setChecked(false);
				t2.setChecked(false);
				t3.setChecked(false);
				t4.setChecked(false);
				t5.setChecked(false);
				t6.setChecked(false);
				t7.setChecked(false);
				t8.setChecked(false);
				t9.setChecked(false);

				cle_bt.setEnabled(true);
				sav_bt.setEnabled(true);
				ans_bt.setEnabled(true);
				exp_bt.setEnabled(true);
				ext_bt.setEnabled(true);
				for (int i = 0; i <= 8; i++) {
					for (int j = 0; j <= 8; j++) {
						tv[i][j].setTextColor(Color.GRAY);
						tv[i][j].setVisibility(android.view.View.INVISIBLE);

					}
				}

				tv[0][0].setText(" 9");
				tv[0][1].setText(" 4");
				tv[0][2].setText(" 5");
				tv[0][3].setText(" 6");
				tv[0][4].setText(" 1");
				tv[0][5].setText(" 2");
				tv[0][6].setText(" 3");
				tv[0][7].setText(" 7");
				tv[0][8].setText(" 8");
				tv[1][0].setText(" 3");
				tv[1][1].setText(" 7");
				tv[1][2].setText(" 8");
				tv[1][3].setText(" 9");
				tv[1][4].setText(" 4");
				tv[1][5].setText(" 5");
				tv[1][6].setText(" 6");
				tv[1][7].setText(" 1");
				tv[1][8].setText(" 2");
				tv[2][0].setText(" 6");
				tv[2][1].setText(" 1");
				tv[2][2].setText(" 2");
				tv[2][3].setText(" 3");
				tv[2][4].setText(" 7");
				tv[2][5].setText(" 8");
				tv[2][6].setText(" 9");
				tv[2][7].setText(" 4");
				tv[2][8].setText(" 5");
				tv[3][0].setText(" 4");
				tv[3][1].setText(" 5");
				tv[3][2].setText(" 6");
				tv[3][3].setText(" 1");
				tv[3][4].setText(" 2");
				tv[3][5].setText(" 3");
				tv[3][6].setText(" 7");
				tv[3][7].setText(" 8");
				tv[3][8].setText(" 9");
				tv[4][0].setText(" 7");
				tv[4][1].setText(" 8");
				tv[4][2].setText(" 9");
				tv[4][3].setText(" 4");
				tv[4][4].setText(" 5");
				tv[4][5].setText(" 6");
				tv[4][6].setText(" 1");
				tv[4][7].setText(" 2");
				tv[4][8].setText(" 3");
				tv[5][0].setText(" 1");
				tv[5][1].setText(" 2");
				tv[5][2].setText(" 3");
				tv[5][3].setText(" 7");
				tv[5][4].setText(" 8");
				tv[5][5].setText(" 9");
				tv[5][6].setText(" 4");
				tv[5][7].setText(" 5");
				tv[5][8].setText(" 6");
				tv[6][0].setText(" 5");
				tv[6][1].setText(" 6");
				tv[6][2].setText(" 1");
				tv[6][3].setText(" 2");
				tv[6][4].setText(" 3");
				tv[6][5].setText(" 7");
				tv[6][6].setText(" 8");
				tv[6][7].setText(" 9");
				tv[6][8].setText(" 4");
				tv[7][0].setText(" 8");
				tv[7][1].setText(" 9");
				tv[7][2].setText(" 4");
				tv[7][3].setText(" 5");
				tv[7][4].setText(" 6");
				tv[7][5].setText(" 1");
				tv[7][6].setText(" 2");
				tv[7][7].setText(" 3");
				tv[7][8].setText(" 7");
				tv[8][0].setText(" 2");
				tv[8][1].setText(" 3");
				tv[8][2].setText(" 7");
				tv[8][3].setText(" 8");
				tv[8][4].setText(" 9");
				tv[8][5].setText(" 4");
				tv[8][6].setText(" 5");
				tv[8][7].setText(" 6");
				tv[8][8].setText(" 1");

				for (int i = 0; i <= 8; i++) {
					ab = rnd.nextInt(9);
					for (int j = 0; j <= 8; j++) {
						if (ab == 0) {
							break;
						} else if (ab == 3 || ab == 6) {
							temp[j] = tv[ab][j].getText().toString();
							tv[ab][j].setText(tv[ab + 1][j].getText()
									.toString());
							tv[ab + 1][j].setText(temp[j]);
						} else {
							temp[j] = tv[ab][j].getText().toString();
							tv[ab][j].setText(tv[ab - 1][j].getText()
									.toString());
							tv[ab - 1][j].setText(temp[j]);
						}

					}
				}

				for (int k = 0; k <= 8; k++) {
					ab = rnd.nextInt(9);
					for (int l = 0; l <= 8; l++) {
						if (ab == 0) {
							break;
						} else if (ab == 3 || ab == 6) {
							temp[l] = tv[l][ab].getText().toString();
							tv[l][ab].setText(tv[l][ab + 1].getText()
									.toString());
							tv[l][ab + 1].setText(temp[l]);
						} else {
							temp[l] = tv[l][ab].getText().toString();
							tv[l][ab].setText(tv[l][ab - 1].getText()
									.toString());
							tv[l][ab - 1].setText(temp[l]);
						}

					}
				}
				AlertDialog.Builder dia1 = new AlertDialog.Builder(board.this);
				dia1.setTitle("난이도를 선택해주세요");
				dia1.setIcon(R.drawable.icon4);
				dia1.setItems(list, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							flag = 1;
							f = 25;
							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									tv[i][j].setVisibility(android.view.View.VISIBLE);
									cpnum1[i][j] = tv[i][j].getText()
											.toString();
								}
							}

							for (int i = 0; i <= f; i++) {
								ab = rnd.nextInt(9);
								bc = rnd.nextInt(9);
								tv[ab][bc]
										.setVisibility(android.view.View.INVISIBLE);
							}

							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									if (tv[i][j].getVisibility() == View.INVISIBLE) {
										tv[i][j].setVisibility(android.view.View.VISIBLE);
										tv[i][j].setText("   ");
										if (tv[i][j].getText().toString() == "   ") {
											tv[i][j].setTextColor(Color.BLUE);
										} else {
											tv[i][j].setTextColor(Color.GRAY);
										}
									}
								}
							}

							break;
						case 1:
							flag = 1;
							f = 45;
							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									tv[i][j].setVisibility(android.view.View.VISIBLE);
									cpnum1[i][j] = tv[i][j].getText()
											.toString();
								}
							}

							for (int i = 0; i <= f; i++) {
								ab = rnd.nextInt(9);
								bc = rnd.nextInt(9);
								tv[ab][bc]
										.setVisibility(android.view.View.INVISIBLE);
							}

							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									if (tv[i][j].getVisibility() == View.INVISIBLE) {
										tv[i][j].setVisibility(android.view.View.VISIBLE);
										tv[i][j].setText("   ");
										if (tv[i][j].getText().toString() == "   ") {
											tv[i][j].setTextColor(Color.BLUE);
										} else {
											tv[i][j].setTextColor(Color.GRAY);
										}
									}
								}
							}

							break;
						case 2:
							flag = 1;
							f = 73;
							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									tv[i][j].setVisibility(android.view.View.VISIBLE);
									cpnum1[i][j] = tv[i][j].getText()
											.toString();
								}
							}

							for (int i = 0; i <= f; i++) {
								ab = rnd.nextInt(9);
								bc = rnd.nextInt(9);
								tv[ab][bc]
										.setVisibility(android.view.View.INVISIBLE);
							}

							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									if (tv[i][j].getVisibility() == View.INVISIBLE) {
										tv[i][j].setVisibility(android.view.View.VISIBLE);
										tv[i][j].setText("   ");
										if (tv[i][j].getText().toString() == "   ") {
											tv[i][j].setTextColor(Color.BLUE);
										} else {
											tv[i][j].setTextColor(Color.GRAY);
										}
									}
								}
							}

							break;
						default:
							flag = 0;
							f = 0;
							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									tv[i][j].setVisibility(android.view.View.VISIBLE);
									cpnum1[i][j] = tv[i][j].getText()
											.toString();
								}
							}

							for (int i = 0; i <= f; i++) {
								ab = rnd.nextInt(9);
								bc = rnd.nextInt(9);
								tv[ab][bc]
										.setVisibility(android.view.View.INVISIBLE);
							}

							for (int i = 0; i <= 8; i++) {
								for (int j = 0; j <= 8; j++) {
									if (tv[i][j].getVisibility() == View.INVISIBLE) {
										tv[i][j].setVisibility(android.view.View.VISIBLE);
										tv[i][j].setText("   ");
										if (tv[i][j].getText().toString() == "   ") {
											tv[i][j].setTextColor(Color.BLUE);
										} else {
											tv[i][j].setTextColor(Color.GRAY);
										}
									}
								}
							}
						}
					}
				});

				dia1.show();
				hintcount = 3;
			}
		});

		ext_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();

			}
		});
		t1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (t1.isChecked()) {
					t2.setChecked(false);
					t3.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);

					/*
					 * t2.setEnabled(false); t3.setEnabled(false);
					 * t4.setEnabled(false); t5.setEnabled(false);
					 * t6.setEnabled(false); t7.setEnabled(false);
					 * t8.setEnabled(false); t9.setEnabled(false);
					 */
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t1.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 1") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {

																tv[i1][j1]
																		.setText(" 1");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});

						}
					}

				}

				else {
					t2.setChecked(false);
					t3.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});
		t2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t2.isChecked()) {
					t1.setChecked(false);
					t3.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t2.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 2") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 2");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});

						}
					}
				}

				else {
					t1.setChecked(false);
					t3.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});
		t3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t3.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t3.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 3") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 3");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}

									});
						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});
		t4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t4.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t3.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t4.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 4") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 4");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}

											}
											return false;
										}
									});
						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t3.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);
				}
			}
		});
		t5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t5.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t3.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t5.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 5") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 5");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});

						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t3.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});
		t6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t6.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t3.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t6.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 6") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 6");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});
						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t3.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});
		t7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t7.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t3.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t7.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 7") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 7");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});
						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t3.setChecked(false);
					t8.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);
				}
			}
		});
		t8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t8.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t3.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t8.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 8") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 8");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});
						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t3.setChecked(false);
					t9.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});
		t9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t9.isChecked()) {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t3.setChecked(false);
					cle_bt.setEnabled(false);
					sav_bt.setEnabled(false);
					ans_bt.setEnabled(false);
					re_bt.setEnabled(false);
					exp_bt.setEnabled(false);
					ext_bt.setEnabled(false);
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							final int i1 = i;
							final int j1 = j;

							tv[i1][j1]
									.setOnTouchListener(new View.OnTouchListener() {

										@Override
										public boolean onTouch(View v,
												MotionEvent event) {
											// TODO Auto-generated method
											// stub
											if (event.getAction() == MotionEvent.ACTION_DOWN) {
												if (flag == 1) {
													if (tv[i1][j1]
															.getCurrentTextColor() == Color.BLUE) {
														if (t9.isChecked()) {
															if (tv[i1][j1]
																	.getText()
																	.toString() == " 9") {
																tv[i1][j1]
																		.setText("   ");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);

															} else {
																tv[i1][j1]
																		.setText(" 9");
																tv[i1][j1]
																		.setTextColor(Color.BLUE);
															}
														}
													}
												}
											}
											return false;
										}
									});

						}
					}
				}

				else {
					t1.setChecked(false);
					t2.setChecked(false);
					t4.setChecked(false);
					t5.setChecked(false);
					t6.setChecked(false);
					t7.setChecked(false);
					t8.setChecked(false);
					t3.setChecked(false);
					cle_bt.setEnabled(true);
					sav_bt.setEnabled(true);
					ans_bt.setEnabled(true);
					re_bt.setEnabled(true);
					exp_bt.setEnabled(true);
					ext_bt.setEnabled(true);

				}
			}
		});

	}

	public void diamake_1() {
		AlertDialog.Builder dia1 = new AlertDialog.Builder(board.this);
		/* dia1.setTitle("난이도를 선택해주세요"); */

		dia1.setIcon(R.drawable.ic_launcher);
		dia1.show();
		return;
	}

	@Override
	public void onBackPressed() {

		View view = this.getLayoutInflater().inflate(R.layout.dialo, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		builder.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		builder.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "힌트보기");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		sql3 = mSudoku_data3.getWritableDatabase();
		sql3.execSQL("DROP TABLE IF EXISTS sudokudb6");
		sql3.execSQL("CREATE TABLE sudokudb6 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokuhint TEXT);");
		sql3.execSQL("INSERT INTO sudokudb6(sudokuhint) VALUES('" + hintcount
				+ "');");
		sql3.close();

		if (flag == 1) {

			if (hintcount != 0) {

				switch (item.getItemId()) {

				case 1:
					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					LinearLayout abc = (LinearLayout) vi.inflate(
							R.layout.hintenable, null);
					final TextView enentv1 = (TextView) abc
							.findViewById(R.id.hintentv1);
					enentv1.setText("남은 힌트는 " + hintcount
							+ " 회 입니다. \n 힌트를 사용하시겠습니까 ?");

					AlertDialog.Builder dia1 = new AlertDialog.Builder(
							board.this);
					dia1.setTitle("Hint!");
					dia1.setView(abc);
					dia1.setCancelable(false);
					dia1.setPositiveButton("사용하기",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(),
											"힌트가 필요한 곳을 찍어주세요!!",
											Toast.LENGTH_SHORT).show();
									t1.setChecked(false);
									t2.setChecked(false);
									t3.setChecked(false);
									t4.setChecked(false);
									t5.setChecked(false);
									t6.setChecked(false);
									t7.setChecked(false);
									t8.setChecked(false);
									t9.setChecked(false);
									t1.setEnabled(false);
									t2.setEnabled(false);
									t3.setEnabled(false);
									t4.setEnabled(false);
									t5.setEnabled(false);
									t6.setEnabled(false);
									t7.setEnabled(false);
									t8.setEnabled(false);
									t9.setEnabled(false);
									cle_bt.setEnabled(false);
									sav_bt.setEnabled(false);
									ans_bt.setEnabled(false);
									re_bt.setEnabled(false);
									exp_bt.setEnabled(false);
									ext_bt.setEnabled(false);
									for (int i = 0; i <= 8; i++) {
										for (int j = 0; j <= 8; j++) {
											final int i1 = i;
											final int j1 = j;

											tv[i1][j1]
													.setOnTouchListener(new View.OnTouchListener() {
														@Override
														public boolean onTouch(
																View v,
																MotionEvent event) {
															// TODO
															// Auto-generated
															// method
															// stub
															if (hint == 0) {
																if (event
																		.getAction() == MotionEvent.ACTION_DOWN) {
																	if (flag == 1) {
																		if (tv[i1][j1]
																				.getCurrentTextColor() == Color.BLUE) {

																			tv[i1][j1]
																					.setText(cpnum1[i1][j1]);
																			tv[i1][j1]
																					.setTextColor(Color.BLUE);
																			hint = hint + 1;
																			hintcount = hintcount - 1;
																			t1.setEnabled(true);
																			t2.setEnabled(true);
																			t3.setEnabled(true);
																			t4.setEnabled(true);
																			t5.setEnabled(true);
																			t6.setEnabled(true);
																			t7.setEnabled(true);
																			t8.setEnabled(true);
																			t9.setEnabled(true);
																			cle_bt.setEnabled(true);
																			sav_bt.setEnabled(true);
																			ans_bt.setEnabled(true);
																			re_bt.setEnabled(true);
																			exp_bt.setEnabled(true);
																			ext_bt.setEnabled(true);

																		}

																	}
																}
															}
															return false;

														}
													});
										}
									}
								}

							});
					dia1.setNegativeButton("닫기",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					dia1.show();
					hint = 0;

					break;
				}

			} else {
				View view = this.getLayoutInflater().inflate(
						R.layout.hintunenable, null);
				AlertDialog.Builder dia1 = new AlertDialog.Builder(board.this);
				dia1.setTitle("Sorry!!");
				dia1.setView(view);
				dia1.setCancelable(true);
				dia1.setPositiveButton("사용하기",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								sql3 = mSudoku_data3.getReadableDatabase();
								Cursor c1 = sql3.rawQuery(
										"SELECT * FROM sudokudb0", null);
								c1.moveToFirst();
								point = c1.getInt(1);

								c1.close();
								sql3.close();

								if (point >= 0 && point < 10) {
									Toast.makeText(getApplicationContext(),
											"포인트가 부족합니다. 다시 확인해주세요!",
											Toast.LENGTH_SHORT).show();
									return;
								} else {
									Toast.makeText(getApplicationContext(),
											"힌트가 필요한 곳을 찍어주세요!!",
											Toast.LENGTH_SHORT).show();
									t1.setChecked(false);
									t2.setChecked(false);
									t3.setChecked(false);
									t4.setChecked(false);
									t5.setChecked(false);
									t6.setChecked(false);
									t7.setChecked(false);
									t8.setChecked(false);
									t9.setChecked(false);
									t1.setEnabled(false);
									t2.setEnabled(false);
									t3.setEnabled(false);
									t4.setEnabled(false);
									t5.setEnabled(false);
									t6.setEnabled(false);
									t7.setEnabled(false);
									t8.setEnabled(false);
									t9.setEnabled(false);
									cle_bt.setEnabled(false);
									sav_bt.setEnabled(false);
									ans_bt.setEnabled(false);
									re_bt.setEnabled(false);
									exp_bt.setEnabled(false);
									ext_bt.setEnabled(false);
									for (int i = 0; i <= 8; i++) {
										for (int j = 0; j <= 8; j++) {
											final int i1 = i;
											final int j1 = j;

											tv[i1][j1]
													.setOnTouchListener(new View.OnTouchListener() {
														@Override
														public boolean onTouch(
																View v,
																MotionEvent event) {
															// TODO
															// Auto-generated
															// method
															// stub
															if (hint == 0) {
																if (event
																		.getAction() == MotionEvent.ACTION_DOWN) {
																	if (flag == 1) {
																		if (tv[i1][j1]
																				.getCurrentTextColor() == Color.BLUE) {

																			tv[i1][j1]
																					.setText(cpnum1[i1][j1]);
																			tv[i1][j1]
																					.setTextColor(Color.BLUE);
																			hint = hint + 1;
																			point = point - 10;
																			sql3 = mSudoku_data3
																					.getWritableDatabase();
																			sql3.execSQL("DROP TABLE IF EXISTS sudokudb0");
																			sql3.execSQL("CREATE TABLE sudokudb0 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT);");
																			sql3.execSQL("INSERT INTO sudokudb0(sudokunumber1) VALUES('"
																					+ point
																					+ "');");

																			sql3.close();
																			t1.setEnabled(true);
																			t2.setEnabled(true);
																			t3.setEnabled(true);
																			t4.setEnabled(true);
																			t5.setEnabled(true);
																			t6.setEnabled(true);
																			t7.setEnabled(true);
																			t8.setEnabled(true);
																			t9.setEnabled(true);
																			cle_bt.setEnabled(true);
																			sav_bt.setEnabled(true);
																			ans_bt.setEnabled(true);
																			re_bt.setEnabled(true);
																			exp_bt.setEnabled(true);
																			ext_bt.setEnabled(true);

																		}

																	}
																}
															}
															return false;

														}
													});
										}
									}

									hint = 0;
								}
							}
						});
				dia1.setNegativeButton("닫기",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				dia1.show();

			}
		} else {
			Toast.makeText(getApplicationContext(), "Error!! make버튼을 눌러주세요!",
					Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);

	}

}
