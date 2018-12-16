package utils.com.futpro;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.pixelcan.inkpageindicator.InkPageIndicator;

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
import java.util.HashMap;
import java.util.Map;

import circle.ResizeImage;
import profile.ProfileData;

public class ProfileActivity extends AppCompatActivity {

    private static final String DEFAULT = "N/A";

    public ArrayList<PlayerCard> copy =  new ArrayList<>();

    private String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_GAMER = "tag";
    private static final String TAG_TEAM = "team";
    private static final String TAG_WIN ="win";
    private static final String TAG_DRAW ="draw";
    private static final String TAG_LOSS ="loss";
    private static final String TAG_RATING ="rating";
    private static final String TAG_AI ="computer_lvl";
    private static final String TAG_CONSOLE ="console";
    private static final String TAG_ELO ="elo";


    private ResizeImage resizeImage = new ResizeImage();

    ViewPagerAdapter mAdapter;
    ViewPager mPager;
    InkPageIndicator mIndicator;

    String gamerID;
    String teamName;
    String gamerEmail;
    String gameLvl ;
    String gamerTag;
    String console;
    String eloRating;

    Toolbar toolbar;

    AHBottomNavigation bottomNavigation;
    boolean notificationVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_bar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ImageView futEmblem = findViewById(R.id.fut_emblem);
        futEmblem.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(ProfileActivity.this.getResources(), R.drawable.fut_emblem, futEmblem.getMaxWidth(), futEmblem.getMaxHeight()));
        futEmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent FUTSearch = new Intent(ProfileActivity.this, FUTSearchActivty.class);
                startActivity(FUTSearch);

            }
        });


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
        bottomNavigation.setCurrentItem(0);
        createFakeNotification();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position){

                    case 0:

                        break;

                    case 1:

                        break;

                    case 2:

                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        final String loadUser = sharedPreferences.getString("userEmail", DEFAULT);

                        Intent myLobby = new Intent(ProfileActivity.this, ChallengeActivity.class);
                        myLobby.putExtra("userEmail", loadUser);
                        startActivity(myLobby);

                        break;

                    case 3:


                        break;


                }

                //TODO: update Fragment here
                // remove notification badge
                int lastItemPos = bottomNavigation.getItemsCount() - 1;
                if (notificationVisible && position == lastItemPos)
                    bottomNavigation.setNotification(new AHNotification(), lastItemPos);
                return true;


            }
        });






        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        getData();

        //Log.d("MYSQL", "gamer Data :" + copy);


    }

    private void createFakeNotification() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText("1")
                        .setBackgroundColor(Color.YELLOW)
                        .setTextColor(Color.BLACK)
                        .build();
                // Adding notification to last item.
                bottomNavigation.setNotification(notification, bottomNavigation.getItemsCount() - 1);
                notificationVisible = true;
            }
        }, 1000);
    }







    @SuppressLint("SetTextI18n")
    private void getUser(HashMap<String, ArrayList<ProfileData>> set){



        for (Map.Entry<String, ArrayList<ProfileData>> entry : set.entrySet()){

            //if(entry.getKey().equals("1")){

                for (int i = 0; i < entry.getValue().size(); i++) {

                    gamerID = entry.getValue().get(i).getID();
                    gamerEmail = entry.getValue().get(i).getEmail();
                    gamerTag = entry.getValue().get(i).getGamerTag();
                    teamName = entry.getValue().get(i).getTeamName();
                    gameLvl = entry.getValue().get(i).getGamerLvl();
                    console = entry.getValue().get(i).getConsole();
                    eloRating = entry.getValue().get(i).getEloRating();


                    Log.d("KEY", "gamer Data :" + teamName + " - " + gamerTag);



                    TextView futTeam = findViewById(R.id.fut_team_name);
                    futTeam.setText(teamName);


                    TextView gamerName =  findViewById(R.id.gamerTag);
                    gamerName.setText(gamerTag);

                    SharedPreferences tagPref = getSharedPreferences("GamerTagID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = tagPref.edit();
                    editor.putString("userTag", gamerTag);
                    editor.apply();

                    TextView eRating = findViewById(R.id.gamer_rank);
                    eRating.setText(eloRating);



                    final TextView gamerLvl = findViewById(R.id.gamer_level);
                    gamerLvl.setText("Fifa Level: " + gameLvl);

                    ImageView consoleType = findViewById(R.id.consoleType);

                    if(console.equals("PS4")) {

                        consoleType.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(this.getResources(), R.drawable.ps4, 100, 100));

                    }else{

                        consoleType.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(this.getResources(), R.drawable.xbox, 100, 100));

                    }

                    final TextView searchMatch = findViewById(R.id.mode_selection);
                    searchMatch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            searchMatch.setText("");


                            RelativeLayout layout = findViewById(R.id.transitions_container);
                            layout.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT ;


                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();


                            params.topMargin = -500;
                            params.leftMargin = 0;
                            params.rightMargin = 0;



                            layout.setLayoutParams(params);

                            RelativeLayout background = findViewById(R.id.background);
                            //background.setBackgroundResource(R.drawable.fifa_mode);
                            //int image = R.drawable.fifa_mode;
                            background.setBackgroundColor(Color.parseColor("#263238")) ;
                            background.setElevation(10f);



                            //BounceInterpolator bounceInterpolator = new BounceInterpolator();
                            AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
                            ObjectAnimator animY = ObjectAnimator.ofFloat(layout, "translationY", 0f, 500 );
                            animY.setInterpolator(interpolator);
                            animY.setDuration(1500).start();
                            //ObjectAnimator animX = ObjectAnimator.ofFloat(layout, "translationX", 0f, -50 );
                            //animX.setInterpolator(interpolator);
                            //animX.setDuration(1500).start();


                           // params.rightMargin = -50;
                            params.bottomMargin = 500;

                            LinearLayout layoutII = findViewById(R.id.container_two);
                            //layoutII.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT ;

                            RelativeLayout.LayoutParams paramsII = (RelativeLayout.LayoutParams) layoutII.getLayoutParams();

                            paramsII.topMargin = 500;
                            paramsII.leftMargin = 0;
                            paramsII.rightMargin = 0;
                            layoutII.setLayoutParams(paramsII);

                            ObjectAnimator animYII = ObjectAnimator.ofFloat(layoutII, "translationY", 0f,-500 );
                            animYII.setInterpolator(interpolator);
                            animYII.setDuration(1500).start();

                            paramsII.bottomMargin = -500;

                            //startActivity(intent);


                            ArrayList<PlayerCard> data = new ArrayList<>();
                            data.add(new PlayerCard(gamerID, gamerEmail,teamName, gamerTag, gameLvl, console, eloRating));


                            mAdapter =  new ViewPagerAdapter(view.getContext(), data);
                            mPager = findViewById(R.id.pager);
                            mPager.setAdapter(mAdapter);
                            mIndicator = findViewById(R.id.indicator);
                            mIndicator.setViewPager(mPager);

                            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent refresh = new Intent(v.getContext(), ProfileActivity.class);
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(refresh);
                                    overridePendingTransition(0, 0);


                                    Thread thread = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try  {
                                                DeleteUser(Integer.valueOf(gamerID));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();


                                }
                            });



                        }
                    });

                }

            //}

        }

    }




    private void syncedMatchList() throws ParseException {

        String gamerEmail;
        String gamerID;
        String gamerTag;
        String team; String fifaWins;
        String fifaDraws; String fifaLoss;
        String teamRating; String computerLvl;
        String console;
        String elo;
        JSONArray gamer;

        String userTeam = null;

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
                fifaWins = c.getString(TAG_WIN);
                fifaDraws = c.getString(TAG_DRAW);
                fifaLoss = c.getString(TAG_LOSS);
                teamRating = c.getString(TAG_RATING);
                computerLvl = c.getString(TAG_AI);
                console = c.getString(TAG_CONSOLE);
                elo = c.getString(TAG_ELO);


                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                final String loadUser = sharedPreferences.getString("userEmail", DEFAULT);

                if(gamerEmail.equals(loadUser)) {

                    copy.add(new PlayerCard(gamerID, gamerEmail, gamerTag, team, fifaWins, fifaDraws, fifaLoss, teamRating, computerLvl, console, elo));
                    SearchMatch searchMatch = new SearchMatch();
                    getUser(searchMatch.calculateTeamRating(copy));
                    userTeam = team;
                    //searchMatch.rankGroups();
                    //searchMatch.pickNRandom();



                }



                //Log.d("MYSQL", "gamer Data :" + copy.get(i).getTeamName());

            }

            SharedPreferences sharedPreferences = getSharedPreferences("GamerTeam", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("gamerTeam", userTeam);
            editor.apply();

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
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_gamer_data.php");


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



    public Boolean DeleteUser(int userId){
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


    private void openRegistrationMenu(){

        TextView register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
