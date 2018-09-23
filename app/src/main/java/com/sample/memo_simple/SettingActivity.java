package com.sample.memo_simple;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.sample.memo_simple.MainActivity.sharedPreferences;
import static com.sample.memo_simple.MainActivity.size;


/**
 * Created by tanaka on 2017/03/26.
 */

public class SettingActivity extends AppCompatActivity {
    final CharSequence[] items = { "特大","大","中","小","極小" };
    int checking;
    int checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        size = sharedPreferences.getInt("CharacterSize", 0);
        switch (size){
            case 30:
                checked=0;
                break;
            case 25:
                checked=1;
                break;
            case 20:
                checked=2;
                break;
            case 15:
                checked = 3;
                break;
            case 10:
                checked=4;
                break;
            default:
                break;
        }
        ((Button) findViewById(R.id.button_size)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle("文字の大きさ")
                                .setSingleChoiceItems(
                                        items,
                                        checked,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case 0:
                                                        MainActivity.editor.putInt("CharacterSize",30);
                                                        checked=0;
                                                        break;
                                                    case 1:
                                                        MainActivity.editor.putInt("CharacterSize",25);
                                                        checked=1;
                                                        break;
                                                    case 2:
                                                        MainActivity.editor.putInt("CharacterSize",20);
                                                        checked=2;
                                                        break;
                                                    case 3:
                                                        MainActivity.editor.putInt("CharacterSize",15);
                                                        checked=3;
                                                        break;
                                                    case 4:
                                                        MainActivity.editor.putInt("CharacterSize",10);
                                                        checked=4;
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                MainActivity.editor.apply();
                                                size = sharedPreferences.getInt("CharacterSize", 0);
                                                MainActivity.changeSize(size);
                                                dialog.dismiss();
                                            }
                                        }
                                )
                                .setNegativeButton("キャンセル", null)
                                .show();


                    }
                }
        );


    }
}
