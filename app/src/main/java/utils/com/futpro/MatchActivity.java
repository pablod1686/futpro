package utils.com.futpro;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

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
import java.util.List;

import circle.CircularImageView;
import circle.ResizeImage;

/**
 * Created by jpablo09 on 12/14/2017.
 */

public class MatchActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private Handler handler = new Handler();
    private int progressStatus = 0;

    private ResizeImage resizeImage = new ResizeImage();

    private ArrayList<PlayerCard> copy =  new ArrayList<>();

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Toolbar toolbar = findViewById(R.id.toolbar_match);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getData();

        CircularImageView imageView = findViewById(R.id.imgage_one);
        imageView.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(this.getResources(), R.drawable.gamer, 100,100));

        CircularImageView imageViewTwo = findViewById(R.id.imgage_two);
        imageViewTwo.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(this.getResources(), R.drawable.gamer, 100,100));


    }


    private void matchOpponent(final List<String> pairs,  final ArrayList<PlayerCard> data){

        progress=new ProgressDialog(this);
        progress.setMessage("Searching Opponent");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setProgress(0);
        progress.show();






        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progress.setProgress(progressStatus);
                                String u1, u2;

                                u1 = pairs.get(0);
                                u2 = pairs.get(1);

                                TextView userOne = findViewById(R.id.match_team_one);
                                userOne.setText(u1);

                                TextView userTwo = findViewById(R.id.match_team_two);
                                userTwo.setText(u2);
                                progress.setMessage("Complete");
                                //progress.dismiss();
                                for (int i = 0; i < data.size(); i ++){

                                    if(data.get(i).getTeamName().equals(u1)){


                                        TextView tagOne = findViewById(R.id.user_one);
                                        tagOne.setText(data.get(i).getGamerTag());



                                    }

                                    if(data.get(i).getTeamName().equals(u2)){


                                        TextView tagTwo = findViewById(R.id.user_two);
                                        tagTwo.setText(data.get(i).getGamerTag());

                                    }



                                }

                            }
                        });

                    } try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

         }).start();


    }


    private void syncedMatchList() throws ParseException {


        String gamerID;
        String gamerEmail;
        String gamerTag;
        String team; String win;
        String draw; String loss;
        String rating; String computerLvl;
        String console;
        String elo;
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
                win = c.getString(TAG_WIN);
                draw = c.getString(TAG_DRAW);
                loss = c.getString(TAG_LOSS);
                rating = c.getString(TAG_RATING);
                computerLvl = c.getString(TAG_AI);
                console = c.getString(TAG_CONSOLE);
                elo = c.getString(TAG_ELO);


                copy.add(new PlayerCard( gamerID, gamerEmail, gamerTag, team, win, draw, loss,rating, computerLvl, console, elo));




            }

            SearchMatch pairList = new SearchMatch();
            pairList.calculateTeamRating(copy);
            pairList.rankGroups();
            pairList.pickNRandom();
            matchOpponent(pairList.pair, copy);





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getData(){
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


                //Log.d("MYSQL", "Data :" + myJSON);

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


}
