package utils.com.futpro;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

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

import profile.Challenge;

/**
 * Created by jpablo09 on 10/9/2018.
 */

public class ChallengeActivity extends AppCompatActivity {

    public ArrayList<Challenge> copy =  new ArrayList<>();

    private String myJSON;

    private static final String TAG_RESULTS ="result";
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
    private static final String TAG_MODE ="mode";
    private static final String TAG_AMT ="stk";

    AHBottomNavigation bottomNavigation;
    boolean notificationVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_challenge_layout_bar);

        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp);

        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem("Credits", R.drawable.reciept);

        AHBottomNavigationItem item4 =
                new AHBottomNavigationItem("Matches", R.drawable.field_vector);

        AHBottomNavigationItem item5 =
                new AHBottomNavigationItem("MyLobby", R.drawable.ic_people_black_24dp);



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
        JSONArray gamer;

        String userEmail =  getIntent().getStringExtra("userEmail");



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
                amount = c.getString(TAG_AMT);


                //Log.d("challenge", "Data: " + gamerTag2);

                if(!gamerEmail1.equals(userEmail) && gamerEmail2.equals(userEmail)) {

                    copy.add(new Challenge(key, gamerID1, gamerEmail1, gamerTag1, gamerID2, gamerEmail2, gamerTag2, gamerTeam, mode, amount));

                }

            }


            if(copy.size() > 0) {

                for (int i = 0; i < copy.size(); i++) {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int count = 0;
                            count = count + 1;
                            AHNotification notification = new AHNotification.Builder()
                                    .setText(String.valueOf(count))
                                    .setBackgroundColor(Color.YELLOW)
                                    .setTextColor(Color.BLACK)
                                    .build();
                            // Adding notification to last item.
                            bottomNavigation.setNotification(notification, 2);
                            notificationVisible = true;
                        }
                    }, 1000);


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

                    syncedMatchList();
                    ListView list = findViewById(R.id.list_pending);
                    list.setAdapter(new PendingChallengeAdapter(ChallengeActivity.this, copy));


                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }

}
