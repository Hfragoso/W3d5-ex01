package com.example.heber.w3d5_ex01;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;
import com.example.heber.w3d5_ex01.FeedReaderContract.FeedEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;

/**
 * Created by heber on 8/5/2017.
 */

public class SaveInfo {
    public static boolean saveUser(Result Result, DBHelper helper, SQLiteDatabase database, Context appContext) {

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_GENDER, Result.getGender());
        Name name = Result.getName();
        values.put(FeedEntry.COLUMN_NAME, String.format("%1$s %2$s %3$s", name.getTitle(), name.getFirst(), name.getLast()));
        Location location = Result.getLocation();
        values.put(FeedEntry.COLUMN_ADDRESS, String.format("%1$s, %2$s, %3$s, CP: %4$s",location.getStreet(), location.getCity(), location.getState(), location.getPostcode().toString()));
        values.put(FeedEntry.COLUMN_EMAIL, Result.getEmail());
        values.put(FeedEntry.COLUMN_DOB, Result.getDob());
        values.put(FeedEntry.COLUMN_PHONE, Result.getPhone());
        values.put(FeedEntry.COLUMN_CELL, Result.getCell());
        //Save picture to internal storage
        Bitmap pictureBM = Result.getPicture().getBmImage();
        String picrureName = name.getLast() + name.getFirst();
        String picturePath = saveToInternalStorage(pictureBM, picrureName, appContext);
        values.put(FeedEntry.COLUMN_PICTURE_PATH, picturePath);
        values.put(FeedEntry.COLUMN_NATIONALITY, Result.getNat());

        long recordId = database.insert(
                FeedEntry.TABLE_NAME,
                null,
                values
        );
        if(recordId > 0){
            return true;
        }else{
            return false;
        }
    }


    private static String saveToInternalStorage(Bitmap bitmapImage,String pictureName, Context appContext){
        ContextWrapper cw = new ContextWrapper(appContext);
        // path to /data/data/yourapp/app_data/imageRepo
        File directory = cw.getDir("imageRepo", Context.MODE_PRIVATE);
        // Create imageRepo
        File mypath=new File(directory, pictureName + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }
}
