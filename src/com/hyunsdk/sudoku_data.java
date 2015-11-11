package com.hyunsdk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class sudoku_data extends SQLiteOpenHelper {
	public sudoku_data(Context context) {
		super(context, Environment.getExternalStorageDirectory().getPath()
				+ "/sudokudata/sudoku", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE sudokudb1 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT, sudokunumber2 TEXT, sudokunumber3 TEXT, sudokunumber4 TEXT);");
		db.execSQL("CREATE TABLE sudokudb2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT, sudokunumber2 TEXT, sudokunumber3 TEXT, sudokunumber4 TEXT);");
		db.execSQL("CREATE TABLE sudokudb3 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT, sudokunumber2 TEXT, sudokunumber3 TEXT, sudokunumber4 TEXT);");
		db.execSQL("CREATE TABLE sudokudb4 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT, sudokunumber2 TEXT, sudokunumber3 TEXT, sudokunumber4 TEXT);");
		db.execSQL("CREATE TABLE sudokudb5 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT, sudokunumber2 TEXT, sudokunumber3 TEXT, sudokunumber4 TEXT);");
		db.execSQL("CREATE TABLE sudokudb0 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokunumber1 TEXT);");
		db.execSQL("CREATE TABLE sudokudb6 (_id INTEGER PRIMARY KEY AUTOINCREMENT, sudokuhint TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS sudokudb1");
		db.execSQL("DROP TABLE IF EXISTS sudokudb2");
		db.execSQL("DROP TABLE IF EXISTS sudokudb3");
		db.execSQL("DROP TABLE IF EXISTS sudokudb4");
		db.execSQL("DROP TABLE IF EXISTS sudokudb5");
		onCreate(db);
	}

}
