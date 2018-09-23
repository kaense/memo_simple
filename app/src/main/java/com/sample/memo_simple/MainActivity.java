package com.sample.memo_simple;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    static protected EditText memo;
    String fileName;
    static protected SharedPreferences sharedPreferences;
    static protected SharedPreferences.Editor editor;
    static protected int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memo = (EditText) findViewById(R.id.memo);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        size = sharedPreferences.getInt("CharacterSize", 0);
        changeSize(size);

        fileName = getResources().getString(R.string.file_name);
        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] readBytes = new byte[fis.available()];
            fis.read(readBytes);
            memo.setText(new String(readBytes));
            fis.close();
        } catch (Exception e) {
        }
        String str = memo.getText().toString();
        memo.setSelection(str.length());

        memo.addTextChangedListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(memo.getText().toString().getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(memo, InputMethodManager.SHOW_IMPLICIT);
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(memo.getText().toString().getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.c_size:
                sizeDialog();
                break;
            //case R.id.backup:
            //    break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    static protected void changeSize(int size) {
        switch (size) {
            case 30:
                memo.setTextSize(30);
                break;
            case 25:
                memo.setTextSize(25);
                break;
            case 20:
                memo.setTextSize(20);
                break;
            case 15:
                memo.setTextSize(15);
                break;
            case 10:
                memo.setTextSize(10);
                break;
            default:
                memo.setTextSize(20);
                break;
        }
    }

    public void sizeDialog(){
        Intent i = new Intent(getApplicationContext(),com.sample.memo_simple.SettingActivity.class);
        startActivity(i);
    }
}
