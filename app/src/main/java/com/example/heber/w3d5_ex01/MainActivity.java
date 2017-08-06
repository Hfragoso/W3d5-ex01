package com.example.heber.w3d5_ex01;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String BASE_URL = "https://randomuser.me/api";
    public static final String MAIN_ACTIVITY_EXTRA = "com.example.heber.w3d5_ex01.MAIN_ACTIVITY_EXTRA";

    private DBHelper helper;
    private SQLiteDatabase database;

    private OkHttpClient client;

    private TextView nameTV;
    private TextView addressTV;
    private TextView emailTV;
    private ImageView pictureIV;
    private EditText searchingNameET;
    private Button randomBTN;
    private Button searchBTN;
    private Button saveBTN;
    private Result currentResult;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            displayInfo((Result) msg.obj);
            currentResult = (Result) msg.obj;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();

        nameTV = (TextView) findViewById(R.id.tv_name);
        addressTV = (TextView) findViewById(R.id.tv_address);
        emailTV = (TextView) findViewById(R.id.tv_email);
        pictureIV = (ImageView) findViewById(R.id.iv_picture);
        searchingNameET = (EditText) findViewById(R.id.et_searching_name);
        randomBTN = (Button) findViewById(R.id.btn_random);
        randomBTN.setOnClickListener(this);
        searchBTN = (Button) findViewById(R.id.btn_search);
        searchBTN.setOnClickListener(this);
        saveBTN = (Button) findViewById(R.id.btn_save);
        saveBTN.setOnClickListener(this);

        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
    }

    private void displayInfo(Result result) {
        Name name = result.getName();
        nameTV.setText(String.format("Name: %1$s %2$s %3$s", name.getTitle(), name.getFirst(), name.getLast()));

        Location location = result.getLocation();
        addressTV.setText(String.format("Address: %1$s, %2$s, %3$s, CP: %4$s",location.getStreet(), location.getCity(), location.getState(), location.getPostcode().toString()));

        emailTV.setText(String.format("email: %1$s", result.getEmail()));

        Picture picture = result.getPicture();
        pictureIV.setImageBitmap(picture.getBmImage());

    }

    private Result restCall() {
//        final UserRandom myUser = new UserRandom();
        final Result myResult = new Result();
        Request req = new Request.Builder().url(BASE_URL).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                try{
                    JSONObject result = new JSONObject(resp);
                    JSONArray results = result.getJSONArray("results");


                    myResult.setGender(results.getJSONObject(0).getString("gender"));

                    Name name = new Name();
                    name.setTitle(results.getJSONObject(0).getJSONObject("name").getString("title"));
                    name.setFirst(results.getJSONObject(0).getJSONObject("name").getString("first"));
                    name.setLast(results.getJSONObject(0).getJSONObject("name").getString("last"));
                    myResult.setName(name);

                    Location location = new Location();
                    location.setStreet(results.getJSONObject(0).getJSONObject("location").getString("street"));
                    location.setCity(results.getJSONObject(0).getJSONObject("location").getString("city"));
                    location.setState(results.getJSONObject(0).getJSONObject("location").getString("state"));
                    location.setPostcode(results.getJSONObject(0).getJSONObject("location").getInt("postcode"));
                    myResult.setLocation(location);

                    myResult.setEmail(results.getJSONObject(0).getString("email"));

                    Login login = new Login();
                    login.setUsername(results.getJSONObject(0).getJSONObject("login").getString("username"));
                    login.setPassword(results.getJSONObject(0).getJSONObject("login").getString("password"));
                    login.setSalt(results.getJSONObject(0).getJSONObject("login").getString("salt"));
                    login.setMd5(results.getJSONObject(0).getJSONObject("login").getString("md5"));
                    login.setSha1(results.getJSONObject(0).getJSONObject("login").getString("sha1"));
                    login.setSha256(results.getJSONObject(0).getJSONObject("login").getString("sha256"));
                    myResult.setLogin(login);

                    myResult.setDob(results.getJSONObject(0).getString("dob"));
                    myResult.setRegistered(results.getJSONObject(0).getString("registered"));
                    myResult.setPhone(results.getJSONObject(0).getString("phone"));
                    myResult.setCell(results.getJSONObject(0).getString("cell"));

                    Id id = new Id();
                    id.setName(results.getJSONObject(0).getJSONObject("id").getString("name"));
                    id.setValue(results.getJSONObject(0).getJSONObject("id").getString("value"));
                    myResult.setId(id);

                    Picture picture = new Picture();
                    picture.setLarge(results.getJSONObject(0).getJSONObject("picture").getString("large"));
                    picture.setMedium(results.getJSONObject(0).getJSONObject("picture").getString("medium"));
                    picture.setThumbnail(results.getJSONObject(0).getJSONObject("picture").getString("thumbnail"));
                    try {
                        InputStream in = new java.net.URL(picture.getLarge()).openStream();
                        picture.setBmImage(BitmapFactory.decodeStream(in));
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                    myResult.setPicture(picture);

                    myResult.setNat(results.getJSONObject(0).getString("nat"));

                    Message msg = handler.obtainMessage();
                    msg.obj = myResult;
                    handler.sendMessage(msg);
//                    myUser.setResults((List<Result>) myResult);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        return myResult;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_random:
                restCall();
                break;
            case R.id.btn_save:
                boolean saved = SaveInfo.saveUser(currentResult, helper, database, getApplicationContext());
                if(saved)
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Couldn't save", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_search:
                String searchingName = searchingNameET.getText().toString();
                if(searchingName.isEmpty()){
                    Toast.makeText(MainActivity.this, "Searching Name box is empty", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, DisplaySearch.class);
                    intent.putExtra(MAIN_ACTIVITY_EXTRA, searchingName);
                    startActivity(intent);
                }
                break;
        }
    }
}
