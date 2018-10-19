package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

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


/**
 * Created by jpablo09 on 10/1/2018.
 */

public class QueueActivity extends AppCompatActivity {

    public ArrayList<PlayerCard> copy =  new ArrayList<>();

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

    private  String id;
    private  String email;
    private  String tag;
    private  String gElo;
    private  String gMode;
    private  String gAmt;

    AHBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_layout_bar);

        id = this.getIntent().getStringExtra("ID");
        email = this.getIntent().getStringExtra("Email");
        tag = this.getIntent().getStringExtra("Tag");
        gElo = this.getIntent().getStringExtra("Elo");
        gMode = this.getIntent().getStringExtra("Mode");
        gAmt = this.getIntent().getStringExtra("Stk");


        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp);


        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem("Credits", R.drawable.reciept);

        AHBottomNavigationItem item4 =
                new AHBottomNavigationItem("Matches", R.drawable.field_vector);

        AHBottomNavigationItem item5 =
                new AHBottomNavigationItem("Lobby", R.drawable.ic_people_black_24dp);


        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#263238"));
        bottomNavigation.setUseElevation(true);
        bottomNavigation.setAccentColor(Color.parseColor("#FFFF00"));
        bottomNavigation.setInactiveColor(Color.WHITE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(2);


        getData();


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
        JSONArray gamer;




        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            gamer = jsonObj.getJSONArray(TAG_RESULTS);
            //Log.d("MYSQL", "gamer Data :" + gamer);
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

                //Log.d("Mode Data", "Data: " + tag + "-" + gMode + "-" + gAmt + "-" + gElo );



                if(!gamerTag.equals(tag) && mode.equals(gMode) && amount.equals(gAmt) && elo.equals(gElo) ) {


                        copy.add(new PlayerCard(gamerID, gamerEmail, gamerTag, lobbyDt, mode, amount, team, lvl, console, elo));


                }





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
                    ListView list = findViewById(R.id.list_queue);
                    list.setAdapter(new QueueAdapter(QueueActivity.this, copy, id, email, tag, gElo));


                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }





    }
