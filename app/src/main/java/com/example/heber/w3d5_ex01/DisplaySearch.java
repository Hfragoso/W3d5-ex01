package com.example.heber.w3d5_ex01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplaySearch extends AppCompatActivity {

    private TextView genderTV;
    private TextView nameTV;
    private TextView addressTV;
    private TextView emailTV;
    private TextView dobTV;
    private TextView phoneTV;
    private TextView cellTV;
    private ImageView pictureIV;
    private TextView nationalityTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);

        genderTV = (TextView) findViewById(R.id.tv_gender);
        nameTV = (TextView) findViewById(R.id.tv_name);
        addressTV = (TextView) findViewById(R.id.tv_address);
        emailTV = (TextView) findViewById(R.id.tv_email);
        dobTV = (TextView) findViewById(R.id.tv_dob);
        phoneTV = (TextView) findViewById(R.id.tv_phone);
        cellTV = (TextView) findViewById(R.id.tv_cell);
        pictureIV = (ImageView) findViewById(R.id.iv_picture);
        nationalityTV = (TextView) findViewById(R.id.tv_nationality);

        displayInfoFromDatabase();
    }

    private void displayInfoFromDatabase() {
        String currentName = new String();
        Intent intent = getIntent();
        if(intent != null){
            currentName = intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        }
    }


}
