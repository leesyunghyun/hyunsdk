package com.hyunsdk;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;


public class hintenable extends Activity {
	
	int hintcount = 3;
	sudoku_data mSudoku_data;
	SQLiteDatabase sql;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hintenable);
		TextView hintenabletv1 = (TextView) findViewById(R.id.hintentv1);
		mSudoku_data = new sudoku_data(this);
		sql = mSudoku_data.getReadableDatabase();
		Cursor c1 = sql.rawQuery("SELECT * FROM sudokudb6",null);
		c1.moveToFirst();
		 
		hintcount = c1.getInt(1);
		c1.close();
		sql.close();

		hintenabletv1.setText("남은 힌트는 " + hintcount + " 회 입니다. \n 힌트를 사용하시겠습니까 ?" );
	}

}
