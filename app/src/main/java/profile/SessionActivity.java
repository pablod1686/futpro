package profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import JBCrypt.BCrypt;
import utils.com.futpro.ProfileActivity;
import utils.com.futpro.R;

/**
 * Created by jpablo09 on 9/27/2018.
 */

@SuppressLint("Registered")
public class SessionActivity extends AppCompatActivity {


    private EditText user;
    private EditText password;

    private String myJSON;
    private static final String TAG_RESULTS ="result";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "em";
    private static final String TAG_PW = "pw";

    ArrayList<UserData> allUsers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        getData();
        initSession();
        //saveUser();

    }


    public void syncedMatchList()  {


        String  id;
        String email;
        String pw;



        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray users = jsonObj.getJSONArray(TAG_RESULTS);

            Log.d("MYSQL", "Data :" + users);

            for(int i = 0; i< users.length(); i++){

                JSONObject c = users.getJSONObject(i);
                id = c.getString(TAG_ID);
                email = c.getString(TAG_EMAIL);
                pw = c.getString(TAG_PW);


                allUsers.add(new UserData(id, email, pw));


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(){

        @SuppressLint("StaticFieldLeak")
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_user_data.php");


                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line).append("\n");
                    }
                    result = sb.toString();
                } catch (Exception ignored) {

                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception ignored){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){

                myJSON=result;
                syncedMatchList();


            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    private void initSession(){


        user =  findViewById(R.id.emailEntry);
        password =  findViewById(R.id.passwordEntry);


        TextView logIn = findViewById(R.id.sign_in);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (contains(allUsers, user, password)) {

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userEmail", user.getText().toString());

                    editor.apply();

                    Intent intent = new Intent(SessionActivity.this, ProfileActivity.class);
                    startActivity(intent);



                } else {

                    Toast.makeText(SessionActivity.this, "***User ID and/or password is incorrect. Please try again!***", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }


            }


        });

    }




    @Override
    public void onBackPressed(){

        Intent refresh = new Intent(SessionActivity.this, SessionActivity.class);
        startActivity(refresh);
        this.finish(); //

    }


    boolean contains(List<UserData> list, EditText userEmail, EditText userPass) {

        String encryption;

        String users = userEmail.getText().toString();

        String pass = userPass.getText().toString();

        for (UserData userList : list) {


            if (userList.getEmail().equals(users)){

                encryption =  userList.getPassword();


                if(BCrypt.checkpw(pass, encryption)) {

                    Log.d("Decrypt", "Match: " + pass + " - " + encryption);

                    return true;

                }

            }
        }

        return false;

    }



}
