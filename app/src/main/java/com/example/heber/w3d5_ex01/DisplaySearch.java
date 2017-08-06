package com.example.heber.w3d5_ex01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.heber.w3d5_ex01.FeedReaderContract.FeedEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DisplaySearch extends AppCompatActivity {

    private DBHelper helper;
    private SQLiteDatabase database;

    private TextView idTV;
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

        idTV = (TextView) findViewById(R.id.tv_id);
        genderTV = (TextView) findViewById(R.id.tv_gender);
        nameTV = (TextView) findViewById(R.id.tv_name);
        addressTV = (TextView) findViewById(R.id.tv_address);
        emailTV = (TextView) findViewById(R.id.tv_email);
        dobTV = (TextView) findViewById(R.id.tv_dob);
        phoneTV = (TextView) findViewById(R.id.tv_phone);
        cellTV = (TextView) findViewById(R.id.tv_cell);
        pictureIV = (ImageView) findViewById(R.id.iv_picture);
        nationalityTV = (TextView) findViewById(R.id.tv_nationality);

        helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        displayInfoFromDatabase();

    }

    private void displayInfoFromDatabase() {
        String currentName = new String();
        Intent intent = getIntent();
        if(intent != null){
            currentName = intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        }

        String[] projection = {
            FeedEntry.COLUMN_GENDER,
            FeedEntry.COLUMN_NAME,
            FeedEntry.COLUMN_ADDRESS,
            FeedEntry.COLUMN_EMAIL,
            FeedEntry.COLUMN_DOB,
            FeedEntry.COLUMN_PHONE,
            FeedEntry.COLUMN_CELL,
            FeedEntry.COLUMN_PICTURE_PATH,
            FeedEntry.COLUMN_NATIONALITY
        };

        String selection = FeedEntry.COLUMN_NAME + " = ?";
        String[] selectionArg = {
                currentName
        };

        Cursor cursor = database.query(
                FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArg,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
//            long entryId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry._ID));
            String entryGender = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_GENDER));
            String entryName = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
            String entryAddress = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_ADDRESS));
            String entryEmail = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_EMAIL));
            String entryDOB = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DOB));
            String entryPhone = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_PHONE));
            String entryCell = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_CELL));
            String entryPicturePath = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_PICTURE_PATH));
            String entryNationality = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NATIONALITY));

//            idTV.setText(String.format("Id: %1$s", Long.toString(entryId)));
            genderTV.setText(entryGender);
            nameTV.setText(entryName);
            addressTV.setText(entryAddress);
            emailTV.setText(entryEmail);
            dobTV.setText(entryDOB);
            phoneTV.setText(entryPhone);
            cellTV.setText(entryCell);
            obtenerImagen(entryPicturePath);
            nationalityTV.setText(entryNationality);

        }
    }

    private void obtenerImagen(String entryPicturePath) {
        try {
            File f=new File(entryPicturePath);
            Bitmap bitMapImage = BitmapFactory.decodeStream(new FileInputStream(f));
            pictureIV.setImageBitmap(bitMapImage);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


}
