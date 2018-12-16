package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import graphs.Displayable;
import graphs.Graph;
import graphs.GraphActivity;
import graphs.UnweightedGraph;


/**
 * Created by jpablo09 on 10/31/2018.
 */

public class OpponentTeamActivity extends AppCompatActivity {

    private static final String DEFAULT = "N/A";

    public ArrayList<OpponentFormation> copy =  new ArrayList<>();

    public ArrayList<OpponentFormation> copyII =  new ArrayList<>();

    private String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_GAMER = "tag";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_TEAM = "team";
    private static final String TAG_FORMATION = "formation";
    private static final String TAG_PLAYER_ID = "key";
    private static final String TAG_POS_ID = "num";

    private static final String TAG_PLAYER_NAME = "name";
    private static final String TAG_MINI = "mini";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_layout_bar);

        //RelativeLayout v =  findViewById(R.id.team_opponent);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        ///StrictMode.setThreadPolicy(policy);

        getData();
        getData2();


    }



    private void opponentedTeamList() throws ParseException{



        String gamerTag;
        String gamerEmail;
        String gamerTeam;
        String formation;
        String playerKey;
        String posNum;
        JSONArray opponent;

        try{

            JSONObject jsonObj = new JSONObject(myJSON);
            opponent = jsonObj.getJSONArray(TAG_RESULTS);
            for(int i=0;i< opponent.length();i++){

                JSONObject c = opponent.getJSONObject(i);

                gamerEmail = c.getString(TAG_EMAIL);
                gamerTag = c.getString(TAG_GAMER);
                gamerTeam = c.getString(TAG_TEAM);
                formation = c.getString(TAG_FORMATION);
                playerKey = c.getString(TAG_PLAYER_ID);
                posNum = c.getString(TAG_POS_ID);


                //SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                //final String loadUser = sharedPreferences.getString("userEmail", DEFAULT);

                final String loadOpponent = this.getIntent().getStringExtra("rivalEmail");


                Log.d("Opponent Data", "Comapare" + gamerEmail + " - "+ loadOpponent);

                if(gamerEmail.equals(loadOpponent)) {

                    copy.add(new OpponentFormation(gamerEmail,gamerTag, gamerTeam, formation,playerKey,posNum));

                }




                //Log.d("FUT", "FUT player Data :" + copy.get(i).getTag() + " - " + copy.get(i).getFormation() + " - " + copy.get(i).getPlayer_keys());

            }


        }catch (JSONException e){

            e.printStackTrace();

        }



    }

    private void getData(){
        @SuppressLint("StaticFieldLeak")
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_opponent_team_data.php");


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
                    opponentedTeamList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }


    private void opponentedTeamListNames(){


        final int SIZE = 11;

        String playerKey;
        String name;
        String mini;
        JSONArray opponent;


        try{

            JSONObject jsonObj = new JSONObject(myJSON);
            opponent = jsonObj.getJSONArray(TAG_RESULTS);
            for(int i=0;i< opponent.length();i++){

                JSONObject c = opponent.getJSONObject(i);


                playerKey = c.getString(TAG_PLAYER_ID);
                name = c.getString(TAG_PLAYER_NAME);
                mini = c.getString(TAG_MINI);

                for(int j = 0; j < copy.size(); j++) {

                    String id = copy.get(j).getPlayer_keys();
                    String pos = copy.get(j).getPosition_ids();

                    String str[] = id.split("-");

                    List<String> keys;
                    keys = Arrays.asList(str);

                    String strII[] =pos.split("-");

                    List<String> posKeys;
                    posKeys = Arrays.asList(strII);


                    for(int x = 0; x < SIZE; x++){

                        // Log.d("FUT", "FUT player Data :" + ids );

                        if(playerKey.equals(keys.get(x))){

                            copyII.add(new OpponentFormation(copy.get(j).getEmail(), copy.get(j).getTag(), copy.get(j).getTeam(), copy.get(j).getFormation(),keys.get(x), name, posKeys.get(x), mini));
                            Collections.sort(copyII, new MyComparator());

                        }


                    }

                }

                //Log.d("FUT", "FUT player Data :" + copyII.get(i).player_keys + " - " + copyII.get(i).getName());
            }
            
            String loadFormation = null;
            String opponentTag = null;
            String opponentTeam = null;


            for(int i = 0 ; i < copyII.size(); i++){

                loadFormation = copyII.get(i).getFormation();
                opponentTag = copyII.get(i).getTag();
                opponentTeam = copyII.get(i).getTeam();
                Log.d("FUT", "FUT player Data :" + copyII.get(i).getTag() + " | " + copyII.get(i).getFormation() + " | " + copyII.get(i).getPlayer_keys() + " | " + copyII.get(i).getName() + " | " +copyII.get(i).getPosition_ids());

            }


            TextView formation = findViewById(R.id.formation);
            TextView teamName = findViewById(R.id.fut_team_name);


            formation.setText(loadFormation);
            teamName.setText(opponentTeam);

            RelativeLayout v =  findViewById(R.id.team_opponent);
            GraphActivity.FomationView graphView = new GraphActivity.FomationView(this);
            Graph<? extends  Displayable> graph;
            List<? extends Displayable> vertices;

            switch (loadFormation) {


                case "4-3-3":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation433(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(),v.getWidth(), v.getHeight()), opponentTag);


                    Log.d("Layout" , "Hieght: " + v.getHeight());
                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-3-2-1":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4321(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-3-1-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4312(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-2-2-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4222(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-2-3-1(2)":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4231_2(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-2-3-1":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4231(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-1-4-1":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4141(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-1-3-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation4132(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-1-2-1-2(2)":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation41212_2(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "4-1-2-1-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation41212(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "3-5-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation352(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "3-4-3":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation343(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "3-4-2-1":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation3421(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "3-4-1-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation3412(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;

                case "3-1-4-2":

                    v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    graphView.setGraphWithBitMap(formation3142(copyII.get(0).getMini(),copyII.get(1).getMini(), copyII.get(2).getMini(), copyII.get(3).getMini(), copyII.get(4).getMini()
                            , copyII.get(5).getMini(), copyII.get(6).getMini(), copyII.get(7).getMini(), copyII.get(8).getMini(), copyII.get(9).getMini(), copyII.get(10).getMini(), v.getWidth(), v.getHeight()), opponentTag);


                    v.setBackgroundResource(R.drawable.campo_6);
                    v.addView(graphView);

                    graph = graphView.getGraph();
                    vertices = graph.getVertices();

                    for (int i = 0; i < graph.getSize(); i++) {
                        final int x = vertices.get(i).getX();
                        final int y = vertices.get(i).getY();
                        //final String pos = vertices.get(i).getName();
                        ImageView imgView = new ImageView(this);
                        imgView.setX(x);
                        imgView.setY(y);
                        Glide.with(this)
                                .load(vertices.get(i).getName())
                                .override(175,200)
                                .into(imgView);
                        v.addView(imgView);
                    }


                    break;
            }








        }catch (JSONException e){

            e.printStackTrace();

        }



    }

    private void getData2(){
        @SuppressLint("StaticFieldLeak")
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_fut_player_data.php");


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



                    opponentedTeamListNames();



            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }


    public Graph<TeamFormation> formation3142 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                               String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.15f)), new TeamFormation(pos2, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.15f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.3f)), new TeamFormation(pos4, (int)(parentWidth * 0.35f), (int)(parentHeight * 0.35f)), new TeamFormation(pos5, (int)(parentWidth * 0.35f), (int)(parentHeight * 0.3f)), new TeamFormation(pos6, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.45f)),
                new TeamFormation(pos8, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.65f)), new TeamFormation(pos9, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.6f)), new TeamFormation(pos10, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f))

        };

        int[][] edges = {

                {0,1}, {1,2},{1,4},{1,5},
                {0,3},{2,6},
                {3,4},{4,5},{5,6},
                {3,7},{4,8},{5,8},{6,9},
                {7,8}, {8,9},
                {7,10},{8,10},{9,10}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation3412 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                               String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.35f)), new TeamFormation(pos2, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.45f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.5f)), new TeamFormation(pos5, (int)(parentWidth * 0.35f), (int)(parentHeight * 0.55f)), new TeamFormation(pos6, (int)(parentWidth * 0.55f), (int)(parentHeight * 0.65f)), new TeamFormation(pos7, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos8, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f)), new TeamFormation(pos10, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.75f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {

                {0,1}, {1,2},{1,4},{1,5},
                {0,3},{2,6},
                {3,4},{4,5},{5,6},
                {3,7},{4,8},{5,8},{6,9},
                {7,8}, {8,9},
                {7,10},{8,10},{9,10}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation3421 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                               String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos2, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.4f)), new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.35f)), new TeamFormation(pos3, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.4f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.5f)), new TeamFormation(pos5, (int)(parentWidth * 0.35f), (int)(parentHeight * 0.55f)), new TeamFormation(pos6, (int)(parentWidth * 0.55f), (int)(parentHeight * 0.65f)), new TeamFormation(pos7, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos8, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f)), new TeamFormation(pos10, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.75f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {

                {0,1}, {1,2},{1,4},{1,5},
                {0,3},{2,6},
                {3,4},{4,5},{5,6},
                {3,7},{4,8},{5,8},{6,9},
                {7,8}, {8,9},
                {7,10},{8,10},{9,10}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation343 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos2, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.4f)), new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.35f)), new TeamFormation(pos3, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.4f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.5f)), new TeamFormation(pos5, (int)(parentWidth * 0.35f), (int)(parentHeight * 0.55f)), new TeamFormation(pos6, (int)(parentWidth * 0.55f), (int)(parentHeight * 0.65f)), new TeamFormation(pos7, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos8, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f)), new TeamFormation(pos10, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.75f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {

                {0,1}, {1,2},{1,4},{1,5},
                {0,3},{2,6},
                {3,4},{4,5},{5,6},
                {3,7},{4,8},{5,8},{6,9},
                {7,8}, {8,9},
                {7,10},{8,10},{9,10}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation352 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){


        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.15f)), new TeamFormation(pos2, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.15f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.1f), (int)(parentHeight * 0.35f)), new TeamFormation(pos4, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.3f)), new TeamFormation(pos5, (int)(parentWidth * 0.8f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos6, (int)(parentWidth * 0.35f), (int)(parentHeight * 0.45f)), new TeamFormation(pos7, (int)(parentWidth * 0.55f), (int)(parentHeight * 0.45f)),
                new TeamFormation(pos8, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.65f)), new TeamFormation(pos9, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.6f)), new TeamFormation(pos10, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f))

        };

        int[][] edges = {
                {0,1},
                {0,2},{1,4},
                {0,3},{1,3},
                {3,2},{3,4},{3,5},{3,6}, {2,5},{5,6},
                {4,6},{5,8},{6,8},{9,8}, {5,7},
                {7,2},{6,9}, {7,8}, {4,9},
                {7,10},{8,10},{9,10}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation41212(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                               String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.35f)), new TeamFormation(pos2, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.45f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.1f), (int)(parentHeight * 0.55f)), new TeamFormation(pos5, (int)(parentWidth * 0.8f), (int)(parentHeight * 0.55f)),
                new TeamFormation(pos6, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {
                {0,1}, {0,2},{0,3},
                {2,1},{1,4},
                {2,3},{2,4},{2,5},
                {5,4},{2,5},{5,7},{5,8},{5,3},
                {3,6},{4,9},
                {6,7},{9,8},
                {7,10},{8,10},
                {7,8}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation41212_2 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                                  String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.35f)), new TeamFormation(pos2, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.45f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.1f), (int)(parentHeight * 0.55f)), new TeamFormation(pos5, (int)(parentWidth * 0.8f), (int)(parentHeight * 0.55f)),
                new TeamFormation(pos6, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {
                {0,1}, {0,2},{0,3},
                {2,1},{1,4},
                {2,3},{2,4},{2,5},{4,9},
                {5,4},{2,5},{5,7},{5,8},{5,3},
                {3,6},
                {6,7},{9,8},
                {7,10},{8,10},
                {7,8}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4132(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){
        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.35f)), new TeamFormation(pos2, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.5f)),  new TeamFormation(pos4, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.5f)), new TeamFormation(pos5, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.5f)),
                new TeamFormation(pos6, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {
                {0,1},
                {0,2},{1,4},
                {0,3},{1,3},{2,3},{4,3},
                {5,4},{2,5},{5,7},{5,8},{5,3},
                {2,6},{4,9},
                {7,6},{8,9},
                {7,10},{8,10},
                {7,8}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4141(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos2, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.55f)), new TeamFormation(pos3, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.6f)), new TeamFormation(pos4, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.6f)), new TeamFormation(pos5, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.55f)),
                new TeamFormation(pos6, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.7f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))
        };

        int[][] edges = {
                {0,1},{0,2},{0,3},{0,4},
                {5,2},{5,3},{5,7},{5,8},
                {2,1},{3,4},{3,2},
                {2,7},{3,8},
                {6,1},{6,7},
                {9,4},{9,8},
                {10,7},{10,8},
                {7,8}
        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4231(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos2, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.5f)), new TeamFormation(pos3, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.5f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.55f)),
                new TeamFormation(pos5, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.65f)), new TeamFormation(pos6, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))
        };

        int[][] edges = {
                {0,1},{0,2},{0,3},
                {1,3},{1,4},
                {2,5},{2,3},
                {4,3},{5,3},
                {4,6},{4,7},
                {5,8},{5,9},
                {6,7},{8,9},
                {7,10},{8,10},
                {8,7}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4231_2(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                                String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos2, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.5f)), new TeamFormation(pos3, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.5f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.55f)),
                new TeamFormation(pos5, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.65f)), new TeamFormation(pos6, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))
        };

        int[][] edges = {
                {0,1},{0,2},{0,3},
                {1,6},{1,3},{1,4},
                {2,5},{2,3},{2,9},
                {4,3},{5,3},
                {4,6},{4,7},
                {5,8},{5,9},
                {6,7},{8,9},
                {7,10},{8,10},
                {8,7}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4222(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.35f)), new TeamFormation(pos2, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.2f), (int)(parentHeight * 0.5f)), new TeamFormation(pos4, (int)(parentWidth * 0.7f), (int)(parentHeight * 0.5f)),
                new TeamFormation(pos5, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.65f)), new TeamFormation(pos6, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))
        };

        int[][] edges = {
                {0,1},
                {0,2},{0,4},{1,3},{1,5},
                {2,4},{3,5},
                {4,5},
                {2,6},{3,9},
                {7,4},{8,5},
                {6,7},{8,9},
                {7,8},
                {7,10},{8,10}
        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4312(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos1, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.35f)), new TeamFormation(pos2, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.35f)),
                new TeamFormation(pos3, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.5f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.6f)),  new TeamFormation(pos5, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.65f)), new TeamFormation(pos6, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.6f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.8f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.85f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.85f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.8f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.99f))

        };

        int[][] edges = {
                {0,1},
                {0,2},{1,2},
                {0,3},{2,4},{1,5},
                {3,4},{4,5},
                {3,6},{5,9},
                {7,4},{8,4},
                {6,7},{7,8},{8,9},
                {7,10},{8,10}


        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4321(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {


                new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.15f)),
                new TeamFormation(pos2, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.2f)), new TeamFormation(pos3, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.2f)),
                new TeamFormation(pos4, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.4f)),  new TeamFormation(pos5, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.4f)), new TeamFormation(pos6, (int)(parentWidth * 0.75f), (int)(parentHeight * 0.4f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.6f)), new TeamFormation(pos8, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.65f)), new TeamFormation(pos9, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.65f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.6f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f))
        };

        int[][] edges = {
                {0,1},{0,2},
                {1,3},{1,4},
                {2,4},{2,5},
                {4,3},{4,5},
                {3,6},{3,7},
                {5,8},{5,9},
                {6,7},{8,9},
                {7,8},
                {7,10},{8,10}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation433 (String pos1, String pos2, String pos3, String pos4, String pos5, String pos6,
                                              String pos7, String pos8, String pos9, String pos10, String pos11, int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation(pos2, (int)(parentWidth * 0.25f), (int)(parentHeight * 0.2f)), new TeamFormation(pos1, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.15f)), new TeamFormation(pos3, (int)(parentWidth * 0.65f), (int)(parentHeight * 0.2f)),
                new TeamFormation(pos5, (int)(parentWidth * 0.15f), (int)(parentHeight * 0.4f)), new TeamFormation(pos4, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.4f)), new TeamFormation(pos6,  (int)(parentWidth * 0.75f), (int)(parentHeight * 0.4f)),
                new TeamFormation(pos9, (int)(parentWidth * 0.05f), (int)(parentHeight * 0.6f)), new TeamFormation(pos10, (int)(parentWidth * 0.85f), (int)(parentHeight * 0.6f)),
                new TeamFormation(pos7, (int)(parentWidth * 0.3f), (int)(parentHeight * 0.65f)), new TeamFormation(pos8, (int)(parentWidth * 0.6f), (int)(parentHeight * 0.65f)),
                new TeamFormation(pos11, (int)(parentWidth * 0.45f), (int)(parentHeight * 0.8f))
        };

        int[][] edges = {
                {1,0},
                {1,2},{1,4},
                {5,2},
                {4,3},{6,3},{0,3},
                {5,4},{5,7},
                {8,9},{8,6},{8,3},
                {9,7},{9,5},
                {10,8},{10,9}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }


    public class OpponentFormation{

        private String email;
        private String tag;
        private String team;
        private String formation;
        private String player_keys;
        private String position_ids;
        private String name;
        private String mini;




        public OpponentFormation(String email, String tag, String teamName ,String formation, String player_keys, String position_ids){

            this.email = email;
            this.tag = tag;
            this.team = teamName;
            this.formation = formation;
            this.player_keys = player_keys;
            this.position_ids = position_ids;

        }

        public OpponentFormation(String email, String tag, String teamName ,String formation, String player_keys, String name, String position_ids){

            this.email = email;
            this.tag = tag;
            this.team = teamName;
            this.formation = formation;
            this.player_keys = player_keys;
            this.name = name;
            this.position_ids = position_ids;

        }

        public OpponentFormation(String email, String tag, String teamName, String formation, String player_keys, String name, String position_ids, String mini){

            this.email = email;
            this.tag = tag;
            this.team = teamName;
            this.formation = formation;
            this.player_keys = player_keys;
            this.name = name;
            this.position_ids = position_ids;
            this.mini = mini;

        }


        public String getEmail(){

            return email;
        }

        public String getTag(){

            return tag;

        }

        public String getFormation(){


            return formation;

        }

        public String getPlayer_keys(){

            return player_keys;
        }

        public String getPosition_ids(){

            return position_ids;
        }

        public String getName(){

            return name;
        }

        public String getMini() {
            return mini;
        }


        public String getTeam() {
            return team;
        }
    }

    class MyComparator implements Comparator<OpponentFormation> {
        public int compare(OpponentFormation a, OpponentFormation b) {
            Integer aLvl, bLvl;

            aLvl = Integer.parseInt(a.getPosition_ids());
            bLvl = Integer.parseInt(b.getPosition_ids());

            return aLvl.compareTo(bLvl);
        }
        // No need to override equals.
    }

    static  class TeamFormation implements Displayable {

        private int x, y;
        private String name;

        TeamFormation(String name, int x, int y){
            this.name = name;
            this.x = x;
            this.y = y;

        }

        @Override
        public int getX(){
            return x;
        }

        @Override
        public int getY(){
            return y;
        }

        @Override
        public String getName() {
            return name;
        }
    }




}
