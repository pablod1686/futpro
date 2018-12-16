package utils.com.futpro;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import circle.CircularImageView;
import circle.ResizeImage;
import profile.Challenge;
import profile.MatchUp;
import profile.services;

/**
 * Created by jpablo09 on 12/14/2017.
 */

public class MatchActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private Handler handler = new Handler();

    private ResizeImage resizeImage = new ResizeImage();

    private ArrayList<Opponent> copy =  new ArrayList<>();

    private String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "em";
    private static final String TAG_GAMER = "tag";
    private static final String TAG_TEAM = "team";
    private static final String TAG_DT ="dt";
    private static final String TAG_MODE ="mode";
    private static final String TAG_AMT ="amt";
    private static final String TAG_LEVEL ="lvl";
    private static final String TAG_CONSOLE ="con";
    private static final String TAG_ELO ="elo";
    private static final String TAG_SEARCH ="search";

    private  String id;
    private  String email;
    private  String tag;
    private  String gElo;
    private  String gMode;
    private  String gAmt;
    private  String searchType;
    private  String team;

    Handler mhandler = new Handler();


    public ArrayList<Challenge> pairUp =  new ArrayList<>();

    private static final String TAG_KEY = "key";
    private static final String TAG_ID_1 = "id_1";
    private static final String TAG_EMAIL_1 = "em_1";
    private static final String TAG_GAMER_1 = "tag_1";
    private static final String TAG_ELO_1 = "ELO_1";
    private static final String TAG_ID_2 = "id_2";
    private static final String TAG_EMAIL_2 = "em_2";
    private static final String TAG_GAMER_2 = "tag_2";
    private static final String TAG_TEAM_2 = "team";
    private static final String TAG_ELO_2 = "elo_2";
    private static final String TAG_STK ="stk";
    private static final String TAG_PTAG ="tag";
    private static final String TAG_PSTA ="pSta";
    private CircularImageView imageView;
    private CircularImageView imageViewTwo;
    TextView readyUp;
    public static Runnable runnable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        //startService(new Intent(this, services.class));



        getDataII();



        readyUp = findViewById(R.id.confirm_match);
        //readyUp.setVisibility(View.GONE);



        id = this.getIntent().getStringExtra("ID");
        email = this.getIntent().getStringExtra("Email");
        tag = this.getIntent().getStringExtra("Tag");
        gElo = this.getIntent().getStringExtra("Elo");
        gMode = this.getIntent().getStringExtra("Mode");
        gAmt = this.getIntent().getStringExtra("Stk");
        searchType =  this.getIntent().getStringExtra("Search");
        team =  this.getIntent().getStringExtra("Team");

        Log.d("User", "Data: " + tag + "-" + gMode + "-" + gAmt + "-" + searchType);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getData();



        imageView = findViewById(R.id.imgage_one);
        imageView.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(this.getResources(), R.drawable.gamer, 50,50));



        imageViewTwo = findViewById(R.id.imgage_two);
        imageViewTwo.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(this.getResources(), R.drawable.gamer, 50,50));




    }


    private void matchOpponent(final ArrayList<Challenge> data){

        if(data.size()>0) {



            new Thread(new Runnable() {
                public void run() {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String u1, u2;


                                u1 = data.get(0).getGamerTag();
                                u2 = data.get(0).getOpponentTag();

                                TextView userOne = findViewById(R.id.user_one);
                                userOne.setText(u1);

                                TextView userTwo = findViewById(R.id.user_two);
                                userTwo.setText(u2);
                                //progress.dismiss();
                                for (int i = 0; i < data.size(); i++) {

                                    if (tag.equals(u1)) {


                                        TextView tagOne = findViewById(R.id.match_team_one);
                                        tagOne.setText(team);


                                    }

                                    if (data.get(i).getOpponentTag().equals(u2)) {


                                        TextView tagTwo = findViewById(R.id.match_team_two);
                                        tagTwo.setText(data.get(i).getOpponentTeam());

                                    }


                                }


                            }
                        });



                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

            readyUp.setVisibility(View.VISIBLE);

        }

    }


    private void syncedMatchList() throws ParseException {


        String gamerEmail;
        String gamerID;
        String gamerTag;
        String team;
        String mode;
        String amount;
        String lobbyDt;
        String lvl;
        String console;
        String elo;
        String search;
        JSONArray gamer;


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            gamer = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i< gamer.length();i++){

                JSONObject c = gamer.getJSONObject(i);
                gamerID = c.getString(TAG_ID);
                gamerEmail = c.getString(TAG_EMAIL);
                gamerTag = c.getString(TAG_GAMER);
                team = c.getString(TAG_TEAM);
                lvl = c.getString(TAG_LEVEL);
                lobbyDt = c.getString(TAG_DT);
                mode = c.getString(TAG_MODE);
                amount = c.getString(TAG_AMT);
                console = c.getString(TAG_CONSOLE);
                elo = c.getString(TAG_ELO);
                search = c.getString(TAG_SEARCH);


                //Log.d("Users", "Data: " + tag + "-" + gMode + "-" + gAmt + "-" + searchType);

                if(!gamerTag.equals(tag) && mode.equals(gMode) && amount.equals(gAmt) && search.equals(searchType) ) {


                    copy.add(new Opponent(gamerID, gamerEmail, gamerTag, lobbyDt, mode, amount, team, lvl, console, elo, search));


                }


            }

            TreeSet<Opponent> tree = new TreeSet<>(new oComparator());

            for(int i = 0; i < copy.size(); i++){

                tree.add(new Opponent(copy.get(i).getID(), copy.get(i).getEmail(), copy.get(i).getTag(), copy.get(i).getDate(),
                        copy.get(i).getModeType(), copy.get(i).getStake(), copy.get(i).getTeamName(), copy.get(i).getGamerLvl(),copy.get(i).getConsole(),
                        copy.get(i).getEloRating(), copy.get(i).getSearch()));
                //Log.d("Opponents", "Data: " + copy.get(i).getGamerTag());

            }

            ArrayList<Opponent> lobby = new ArrayList<>();


            for (Opponent aTree : tree) {



                if(Integer.parseInt(aTree.getEloRating()) >= Integer.parseInt(gElo) - 10 && Integer.parseInt(aTree.getEloRating()) <= Integer.parseInt(gElo) + 10
                        && !aTree.getTag().equals(tag)){

                    lobby.add(new Opponent(aTree.getID() , aTree.getEmail(), aTree.getTag(), aTree.getTeamName(),
                            aTree.getEloRating(), aTree.getStake(), aTree.getModeType()));
                    //Log.d("Lobby", "Data: " + aTree.getElo() + " - " + gElo);


                }

            }
            //Log.d("Lobby", "Data: " + lobby.get(0).getGamerTag());

            ArrayList<Opponent> pair = new ArrayList<>();

            if(lobby.size() > 0) {

                List<String> matchUp = new ArrayList<>();
                Random rand = new Random();
                int pick = rand.nextInt(lobby.size());
                matchUp.add(tag);
                matchUp.add(lobby.get(pick).getTag());
                pair.add(new Opponent(lobby.get(pick).getID(), lobby.get(pick).getEmail(), lobby.get(pick).getTag(), lobby.get(pick).getTeamName(),
                        lobby.get(pick).getEloRating(),lobby.get(pick).getStake(),lobby.get(pick).getModeType()));
                //matchOpponent(matchUp, copy);
                insertPair(pair);



            }else{

                if(!pairExists(pairUp)) {

                    progress = new ProgressDialog(MatchActivity.this);
                    progress.setMessage("Searching Opponent");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //progress.setProgress(0);
                    progress.show();

                    this.mhandler = new Handler();
                    this.mhandler.postDelayed(m_Runnable, 10000);
                }

               // progress.dismiss();

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_lobby_data.php");


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


                //Log.d("MYSQL", "Data :" + myJSON);

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


    private void syncedMatchListII() throws ParseException {

        String finalKey = null;
        String key;
        String gamerEmail1;
        String gamerID1;
        String gamerTag1;
        String elo1;
        String mode;
        String amount;
        String gamerTag2;
        String gamerID2;
        String gamerTeam;
        String elo2;
        String gamerEmail2;
        String pTag;
        String pStatus;
        JSONArray gamer;


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            gamer = jsonObj.getJSONArray(TAG_RESULTS);
            //Log.d("MYSQL", "gamer Data :" + gamer);
            for(int i=0;i< gamer.length();i++){

                JSONObject c = gamer.getJSONObject(i);
                key = c.getString(TAG_KEY);
                gamerID1 = c.getString(TAG_ID_1);
                gamerEmail1 = c.getString(TAG_EMAIL_1);
                gamerTag1 = c.getString(TAG_GAMER_1);
                //elo1 = c.getString(TAG_ELO_1);
                gamerID2 = c.getString(TAG_ID_2);
                gamerTag2 = c.getString(TAG_GAMER_2);
                gamerTeam = c.getString(TAG_TEAM_2);
                //elo2 = c.getString(TAG_ELO_2);
                gamerEmail2 = c.getString(TAG_EMAIL_2);
                mode = c.getString(TAG_MODE);
                amount = c.getString(TAG_STK);
                pTag = c.getString(TAG_PTAG);
                pStatus = c.getString(TAG_PSTA);


                Log.d("pairs", "Data: " + key +  pTag + pStatus);

                if(gamerTag1.equals(tag) || gamerTag2.equals(tag)) {

                    pairUp.add(new Challenge(key, gamerID1, gamerEmail1, gamerTag1, gamerID2, gamerEmail2, gamerTag2, gamerTeam, mode, amount, pTag, pStatus));
                    finalKey = key;

                }

                matchOpponent(pairUp);


            }


            final TextView readyUp = findViewById(R.id.confirm_match);
            final String pairKey = finalKey;
            readyUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AsyncHttpClient client = new AsyncHttpClient();
                    final RequestParams params = new RequestParams();
                    final ArrayList<HashMap<String, String>> json = new ArrayList<>();
                    final HashMap<String, String> map = new HashMap<>();
                    Gson gson;
                    String list;



                    map.put("pair", pairKey);
                    map.put("tag", tag);
                    map.put("status","Ready");


                    json.add(map);
                    gson = new GsonBuilder().create();
                    list = gson.toJson(json);



                    params.put("readyData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                    client.post("http://betlogic.co/FUTPRO/insert_readyup_data.php", params, new AsyncHttpResponseHandler());

                    recreate();




                }
            });

            for(int i = 0; i < pairUp.size(); i++){

                //Log.d("pairs", "Data: " + pairUp.get(i).getPairKey() + pairUp.get(i).getpTag().equals(tag) + pairUp.get(i).getpStatus());

                if(pairUp.get(i).getGamerTag().equals(tag) && pairUp.get(i).getpTag().equals(tag) && pairUp.get(i).getpStatus().equals("Ready")){


                    imageView.setBorderColor(Color.GREEN);

                    //readyUp.setText(pairUp.get(i).getModeType());


                }else if(pairUp.get(i).getOpponentTag().equals(tag) && pairUp.get(i).getpTag().equals(tag) && pairUp.get(i).getpStatus().equals("Ready")) {


                    imageViewTwo.setBorderColor(Color.GREEN);

                    //readyUp.setText(pairUp.get(i).getModeType());


                }

                if(!pairUp.get(i).getGamerTag().equals(tag) && !pairUp.get(i).getpTag().equals(tag) && pairUp.get(i).getpStatus().equals("Ready")){


                    imageView.setBorderColor(Color.GREEN);



                }else if(!pairUp.get(i).getOpponentTag().equals(tag) && !pairUp.get(i).getpTag().equals(tag) && pairUp.get(i).getpStatus().equals("Ready")) {


                    imageViewTwo.setBorderColor(Color.GREEN);



                }




            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getDataII(){
        @SuppressLint("StaticFieldLeak")
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_challenge_data.php");


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
                    syncedMatchListII();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();


    }

    private boolean pairExists(final ArrayList<Challenge> data){

        for(int i = 0; i < data.size(); i++) {

            if (data.get(i).getGamerTag().equals(tag) || data.get(i).getOpponentTag().equals(tag)) {

                return true;
            }
        }

        return false;
    }



    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            //Toast.makeText(getApplicationContext(), "Searching Opponent", Toast.LENGTH_LONG).show();
            recreate();

        }
    };



    public Boolean deleteUser(int userId){
        // URL for getting all customers

        String url = "http://betlogic.co/FUTPRO/delete_active_lobby_gamer.php?userId="+userId;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity = httpResponse.getEntity();

            return true;
        } catch (ClientProtocolException e) {
            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void insertPair(ArrayList<Opponent> opponentData){

        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        final ArrayList<HashMap<String, String>> json = new ArrayList<>();
        final HashMap<String, String> map = new HashMap<>();
        Gson gson;
        String list;

        for(final Opponent data: opponentData){



                //Log.d("Mode", "Data: " + userID + "-" + userEmail + "-" + userTag + "-" + data.getGamerTag() + "-" + data.getMode());

                map.put("id_1", id);
                map.put("em_1", email);
                map.put("tag_1", tag);
                //map.put("elo_1", userElo);
                map.put("id_2", data.getID());
                map.put("em_2", data.getEmail());
                map.put("tag_2", data.getTag());
                map.put("team", data.getTeamName());
                //map.put("elo2", data.getElo());
                map.put("mode", data.getModeType());
                map.put("stk", data.getStake());
                map.put("status","Pending");

                json.add(map);
                gson = new GsonBuilder().create();
                list = gson.toJson(json);



                params.put("challengeData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                client.post("http://betlogic.co/FUTPRO/insert_challenge_data.php", params, new AsyncHttpResponseHandler());


            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        deleteUser(Integer.valueOf(id));
                        deleteUser(Integer.valueOf(data.getID()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();



        }

    }


}




class Opponent{

    private String ID;
    private String email;
    private String tag;
    private String date;
    private String teamName;
    private String eloRating;
    private String stake;
    private String modeType;
    private String search;
    private String gamerLvl;
    private String console;

    Opponent(String id, String email, String tag, String teamName, String eloRating, String stake, String modeType)
    {
        this.tag = tag;
        this.email = email;
        this.ID = id;
        this.teamName = teamName;
        this.eloRating = eloRating;
        this.modeType = modeType;
        this.stake = stake;
    }


    Opponent(String id ,String email, String gamerTag, String date, String modeType, String modeStk, String team, String level, String console, String elo, String search) {

        this.ID = id;
        this.email = email;
        this.tag = gamerTag;
        this.date = date;
        this.modeType = modeType;
        this.stake = modeStk;
        this.teamName = team;
        this.gamerLvl = level;
        this.console = console;
        this.eloRating = elo;
        this.search = search;

    }


    public String getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getTag() {
        return tag;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEloRating() {
        return eloRating;
    }

    public String getStake() {
        return stake;
    }

    public String getModeType() {
        return modeType;
    }

    public String getDate() {
        return date;
    }

    public String getSearch() {
        return search;
    }

    public String getGamerLvl() {
        return gamerLvl;
    }

    public String getConsole() {
        return console;
    }
}

class oComparator implements Comparator<Opponent> {
    public int compare(Opponent a, Opponent b) {
        Integer aLvl, bLvl;

        aLvl = Integer.parseInt(a.getEloRating());
        bLvl = Integer.parseInt(b.getEloRating());

        return bLvl.compareTo(aLvl);
    }
    // No need to override equals.
}
