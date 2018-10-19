package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import profile.UserData;


/**
 * Created by jpablo09 on 12/20/2017.
 */

public class PlayerCardRegistration extends AppCompatActivity {

    public ArrayList<UserData> copy =  new ArrayList<>();
    private String myJSON;

    private EditText tag, team, win, draw, loss, rating;
    private String setID;
    private String setTag;
    private String setTeam;
    private String setWin;
    private String setDraw;
    private String setLoss;
    private String setRating;

    private ArrayList<String> computerSpinner = new ArrayList<>();
    private ArrayList<String> consoleSpinner = new ArrayList<>();

    Boolean CheckEditTextEmpty;
    String level, console;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "em";

    private static final String EXTRA_EMAIL = "userEmail";

    private String getUserEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        tag =  findViewById(R.id.gamerTagEntry);
        team =  findViewById(R.id.teamNameEntry);
        win =  findViewById(R.id.editTextWins);
        draw =  findViewById(R.id.editTextDraws);
        loss =  findViewById(R.id.editTextLosses);
        rating = findViewById(R.id.editTextTeamRating);


        getUserEmail = PlayerCardRegistration.this.getIntent().getStringExtra(EXTRA_EMAIL);
        Log.d("user", "Email: " + getUserEmail);


        initSpinners();

        Spinner s1 = findViewById(R.id.AISelction);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_list, computerSpinner) {

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        s1.setAdapter(adapter1);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                level = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner s2 = findViewById(R.id.consoleSelection);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_list, consoleSpinner){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        s2.setAdapter(adapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                console = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getData();


        TextView insert = findViewById(R.id.insert_card_data);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    appendUserFifaData();


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });









    }


    private void syncedMatchList() throws ParseException {


        String id;
        String email;

        JSONArray user;


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            user = jsonObj.getJSONArray(TAG_RESULTS);
            //Log.d("MYSQL", "gamer Data :" + gamer);
            for(int i=0;i< user.length();i++){

                JSONObject c = user.getJSONObject(i);
                id = c.getString(TAG_ID);
                email = c.getString(TAG_EMAIL);


                copy.add(new UserData(id, email));
                if( Objects.equals(copy.get(i).getEmail(), getUserEmail))
                    setID = copy.get(i).getID();


                Log.d("PW", "USER Data :" +  copy.get(i).getID() + " - " + copy.get(i).getEmail());

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getData(){
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

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){


                myJSON=result;

                try {

                    syncedMatchList();


                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }


    public void appendUserFifaData() throws ParseException {

        SubmitData();


        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        final RequestParams paramStats = new RequestParams();
        final ArrayList<HashMap<String, String>> json = new ArrayList<>();
        final ArrayList<HashMap<String, String>> jsonStat = new ArrayList<>();

        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> statMap = new HashMap<>();

        if(isUser()) {


            String setEmail = getUserEmail;
            map.put("id", setID);
            map.put("email", setEmail);
            map.put("tag", setTag);
            map.put("team", setTeam);
            map.put("win", setWin);
            map.put("draw", setDraw);
            map.put("loss", setLoss);
            map.put("rating", setRating);
            map.put("computer_level", level);
            map.put("console", console);


            json.add(map);
            Gson gson = new GsonBuilder().create();
            String list = gson.toJson(json);


            params.put("gamerData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
            client.post("http://betlogic.co/FUTPRO/insert_futpro_data.php", params, new AsyncHttpResponseHandler());


            statMap.put("id", setID);
            statMap.put("email", setEmail);
            statMap.put("tag", setTag);
            statMap.put("team", setTeam);
            statMap.put("win", "0");
            statMap.put("draw", "0");
            statMap.put("loss", "0");
            statMap.put("elo", "1000");

            jsonStat.add(statMap);
            Gson gsonStat = new GsonBuilder().create();
            String listStats = gsonStat.toJson(jsonStat);

            paramStats.put("gamerStatData", listStats.replaceAll("\\[", "").replaceAll("\\]", ""));
            client.post("http://betlogic.co/FUTPRO/insert_stats_data.php", paramStats, new AsyncHttpResponseHandler());


            Intent intent = new Intent(PlayerCardRegistration.this, ProfileActivity.class);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EXTRA_EMAIL, setEmail);
            editor.apply();

            startActivity(intent);


        }

            //params.put("singleBet", list);
            //client.post("http://betlogic.co/FUTPRO/insert_multiple_gamer_data.php", params, new AsyncHttpResponseHandler());



        //Log.d("Tag", "JSON list: " + params);
    }

    public void SubmitData() throws ParseException {



        setTag = tag.getText().toString();
        setTeam = team.getText().toString();
        setWin = win.getText().toString();
        setDraw = draw.getText().toString();
        setLoss = loss.getText().toString();
        setRating = rating.getText().toString();




        CheckEditTextIsEmptyOrNot(setTag, setTeam, setWin, setDraw, setLoss, setRating);

        if(CheckEditTextEmpty)
        {



            Toast.makeText(PlayerCardRegistration.this,"Data Submit Successfully", Toast.LENGTH_LONG).show();

            ClearEditTextAfterDoneTask();

        }
        else {

            Toast.makeText(PlayerCardRegistration.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextIsEmptyOrNot(String tag,String team, String w, String d, String l, String rating ){

        if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(team) || TextUtils.isEmpty(w) || TextUtils.isEmpty(d) ||
                TextUtils.isEmpty(l) || TextUtils.isEmpty(rating)){

            CheckEditTextEmpty = false ;

        }
        else {

            CheckEditTextEmpty = true ;
        }
    }

    public void ClearEditTextAfterDoneTask(){

        tag.getText().clear();
        team.getText().clear();
        win.getText().clear();
        draw.getText().clear();
        loss.getText().clear();
        rating.getText().clear();




    }

    public void initSpinners(){

       computerSpinner.add("Select AI Level");
       computerSpinner.add("Ultimate");
       computerSpinner.add("Legendary");
       computerSpinner.add("World Class");
       computerSpinner.add("Professional");
       computerSpinner.add("Semi-Pro");
       computerSpinner.add("Amateur");
       computerSpinner.add("Beginner");

       consoleSpinner.add("Select Console");
       consoleSpinner.add("PS4");
       consoleSpinner.add("Xbox One");


    }

    public boolean isUser(){


        for(int i = 0; i < copy.size(); i++){


            if( Objects.equals(copy.get(i).getEmail(), getUserEmail))


                return true;
        }

        return false;

    }








}
