package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

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

import Databases.DatabaseHelper;
import FUT.FUTPlayerAttributes;

/**
 * Created by jpablo09 on 10/12/2018.
 */

public class FUTSearchActivty extends AppCompatActivity{

    Toolbar toolbar;
    LinearLayout layout;
    TextView midFilter;
    TextView atkFilter;
    TextView defFilter;
    EditText searchFilter;

    private DatabaseHelper database;

    public ArrayList<FUTPlayerAttributes> attributes =  new ArrayList<>();

    private String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_KEY = "key";
    private static final String TAG_NAME = "name";
    private static final String TAG_RTG = "rtg";
    private static final String TAG_POS = "pos";
    private static final String TAG_CLUB = "club";
    private static final String TAG_LEAGUE ="league";
    private static final String TAG_NATION ="nation";
    private static final String TAG_PACE ="pace";
    private static final String TAG_SHOT ="shot";
    private static final String TAG_PASS ="pass";
    private static final String TAG_DRIBBLE ="dribble";
    private static final String TAG_DEFENSE ="defense";
    private static final String TAG_PHYSICAL ="physical";
    private static final String TAG_WEAKFOOT ="weakfoot";
    private static final String TAG_SKILL ="skill";
    private static final String TAG_URL ="url";
    private static final String TAG_MINI ="mini";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fut_player_layout_bar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        searchFilter = findViewById(R.id.search_player);

        getData();



        database = new DatabaseHelper(this);

        TextView player_count = findViewById(R.id.text_count);
        player_count.setText(String.valueOf(database.getFUTPlayers().size()));


        FloatingActionButton teamEdit = findViewById(R.id.view_formation);
        teamEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamEditActivity = new Intent(FUTSearchActivty.this, TeamEditActivty.class);
                startActivity(teamEditActivity);

            }
        });




    }




    private void filterATKPostions(final ArrayList<FUTPlayerAttributes> playerFilter) {

        final String[] attackPos = new String[] {"ST", "CF", "LF", "RF", "LW", "RW"};

        final ArrayList<FUTPlayerAttributes> playerList = new ArrayList<>();

        atkFilter = findViewById(R.id.atk_filter);
        atkFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                midFilter.setBackgroundResource(R.drawable.border2);
                midFilter.setElevation(0);

                defFilter.setBackgroundResource(R.drawable.border2);
                defFilter.setElevation(0);


                atkFilter.setBackgroundResource(R.drawable.border3);
                atkFilter.setElevation(5f);
                int size =  attackPos.length;

                layout =  findViewById(R.id.position_filters);
                layout.removeAllViewsInLayout();

                for(int i=0;i<size;i++)
                {
                    final ToggleButton text = new ToggleButton(FUTSearchActivty.this);
                    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80,80, 1.0f);
                    params.setMargins(20,0,20,0);
                    text.setText(attackPos[i]);
                    text.setTextColor(Color.WHITE);
                    text.setGravity(Gravity.CENTER);
                    text.setTextSize(7f);
                    text.setBackgroundResource(R.drawable.border2);
                    text.setElevation(10);
                    text.setLayoutParams(params);
                    layout.addView(text);
                    final int textPos = i;
                    text.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {



                            for(int x = 0; x < playerFilter.size(); x++){

                                switch (attackPos[textPos]) {

                                    case "ST":

                                        if (text.isChecked()) {

                                            text.setText("ST");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("ST")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("ST");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("ST")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }

                                        break;

                                    case "CF":

                                        if (text.isChecked()) {

                                            text.setText("CF");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("CF")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("CF");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("CF")){

                                                   playerList.remove(j);

                                                }

                                            }

                                        }


                                        break;

                                    case "LF":

                                        if (text.isChecked()) {

                                            text.setText("LF");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("LF")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("LF");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("LF")){

                                                    playerList.remove(j);

                                                }

                                            }

                                        }


                                        break;

                                    case "RF":

                                        if (text.isChecked()) {

                                            text.setText("RF");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("RF")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("RF");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("RF")){

                                                    playerList.remove(j);

                                                }

                                            }

                                        }


                                        break;

                                    case "LW":

                                        if (text.isChecked()) {

                                            text.setText("LW");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("LW")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("LW");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("LW")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                    case "RW":

                                        if (text.isChecked()) {

                                            text.setText("RW");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("RW")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("RW");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("RW")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                }



                            }

                            PlayerAttributesAdapter adapter = new PlayerAttributesAdapter(FUTSearchActivty.this, playerList);
                            ListView playerListView = findViewById(R.id.fut_player_list);
                            playerListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();




                        }
                    });

                }


            }
        });


    }

    private void filterMIDPostions(final ArrayList<FUTPlayerAttributes> playerFilter) {

        final String[] midPos = new String[] {"CAM", "LM", "RM", "CM", "CDM"};

        final ArrayList<FUTPlayerAttributes> playerList = new ArrayList<>();

        midFilter = findViewById(R.id.mid_filter);
        midFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               atkFilter.setBackgroundResource(R.drawable.border2);
               atkFilter.setElevation(0);

                defFilter.setBackgroundResource(R.drawable.border2);
                defFilter.setElevation(0);


                midFilter.setBackgroundResource(R.drawable.border3);
                midFilter.setElevation(5f);
                int size =  midPos.length;

                layout =  findViewById(R.id.position_filters);
                layout.removeAllViewsInLayout();


                for(int i=0;i<size;i++)
                {

                    final ToggleButton text = new ToggleButton(FUTSearchActivty.this);
                    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80,80, 1.0f);
                    params.setMargins(20,0,20,0);
                    text.setText(midPos[i]);
                    text.setTextColor(Color.WHITE);
                    text.setGravity(Gravity.CENTER);
                    text.setTextSize(9f);
                    text.setBackgroundResource(R.drawable.border2);
                    text.setElevation(10);
                    text.setLayoutParams(params);
                    layout.addView(text);
                    final int textPos = i;
                    text.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {



                            for(int x = 0; x < playerFilter.size(); x++){

                                switch (midPos[textPos]) {

                                    case "CAM":

                                        if (text.isChecked()) {

                                            text.setText("CAM");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("CAM")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("CAM");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("CAM")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }

                                        break;

                                    case "LM":

                                        if (text.isChecked()) {

                                            text.setText("LM");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("LM")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("LM");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("LM")){

                                                    playerList.remove(j);

                                                }

                                            }

                                        }


                                        break;

                                    case "RM":

                                        if (text.isChecked()) {

                                            text.setText("RM");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("RM")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("RM");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("RM")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                    case "CM":

                                        if (text.isChecked()) {

                                            text.setText("CM");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("CM")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("CM");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("CM")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                    case "CDM":

                                        if (text.isChecked()) {

                                            text.setText("CDM");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("CDM")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(),playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("CDM");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("CDM")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                }



                            }

                            PlayerAttributesAdapter adapter = new PlayerAttributesAdapter(FUTSearchActivty.this, playerList);
                            ListView playerListView = findViewById(R.id.fut_player_list);
                            playerListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();




                        }
                    });

                }





            }
        });



    }

    private void filterDEFPostions(final ArrayList<FUTPlayerAttributes> playerFilter) {

        final String[] defPos = new String[] {"CB", "LB", "RB", "LWB", "RWB", "GK"};

        final ArrayList<FUTPlayerAttributes> playerList = new ArrayList<>();

        defFilter = findViewById(R.id.def_filter);
        defFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                atkFilter.setBackgroundResource(R.drawable.border2);
                atkFilter.setElevation(0);

                midFilter.setBackgroundResource(R.drawable.border2);
                midFilter.setElevation(0);


                defFilter.setBackgroundResource(R.drawable.border3);
                defFilter.setElevation(5f);
                int size =  defPos.length;

                layout =  findViewById(R.id.position_filters);
                layout.removeAllViewsInLayout();


                for(int i=0;i<size;i++)
                {

                    final ToggleButton text = new ToggleButton(FUTSearchActivty.this);
                    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80,80, 1.0f);
                    params.setMargins(20,0,20,0);
                    text.setText(defPos[i]);
                    text.setTextColor(Color.WHITE);
                    text.setGravity(Gravity.CENTER);
                    text.setTextSize(9f);
                    text.setBackgroundResource(R.drawable.border2);
                    text.setElevation(10);
                    text.setLayoutParams(params);
                    layout.addView(text);
                    final int textPos = i;
                    text.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {



                            for(int x = 0; x < playerFilter.size(); x++){

                                switch (defPos[textPos]) {

                                    case "CB":

                                        if (text.isChecked()) {

                                            text.setText("CB");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("CB")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("CB");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("CB")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }

                                        break;

                                    case "LB":

                                        if (text.isChecked()) {

                                            text.setText("LB");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("LB")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("LB");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("LB")){

                                                    playerList.remove(j);

                                                }

                                            }

                                        }


                                        break;

                                    case "RB":

                                        if (text.isChecked()) {

                                            text.setText("RB");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("RB")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("RB");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("RB")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                    case "LWB":

                                        if (text.isChecked()) {

                                            text.setText("LWB");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("LWB")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("LWB");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("LWB")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                    case "RWB":

                                        if (text.isChecked()) {

                                            text.setText("RWB");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("RWB")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(),playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("RWB");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("RWB")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;

                                    case "GK":

                                        if (text.isChecked()) {

                                            text.setText("GK");
                                            text.setBackgroundResource(R.drawable.border3);

                                            if(playerFilter.get(x).getPlayerPOS().equals("GK")){

                                                playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(),
                                                        playerFilter.get(x).getPlayerClub(), playerFilter.get(x).getPlayerLeague(), playerFilter.get(x).getPlayerNation(),playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                                                        playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                                                        playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));



                                            }


                                        } else {

                                            text.setText("GK");
                                            text.setBackgroundResource(R.drawable.border2);

                                            for(int j = 0 ; j < playerList.size(); j++){

                                                if(playerList.get(j).getPlayerPOS().equals("GK")){

                                                    playerList.remove(j);

                                                }

                                            }


                                        }
                                        break;


                                }



                            }

                            PlayerAttributesAdapter adapter = new PlayerAttributesAdapter(FUTSearchActivty.this, playerList);
                            ListView playerListView = findViewById(R.id.fut_player_list);
                            playerListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();




                        }
                    });

                }





            }
        });



    }


    private void filterBySearch(final ArrayList<FUTPlayerAttributes> playerFilter){

        final ArrayList<FUTPlayerAttributes> playerList = new ArrayList<>();
        String setPlayerName = searchFilter.getText().toString();




            for (int x = 0; x < playerFilter.size(); x++) {

                if (playerFilter.get(x).getPlauerName().contains(setPlayerName) && setPlayerName.length() > 3) {

                    playerList.add(new FUTPlayerAttributes(playerFilter.get(x).getKey(), playerFilter.get(x).getPlauerName(), playerFilter.get(x).getPlayerRtg(), playerFilter.get(x).getPlayerPOS(), playerFilter.get(x).getPlayerClub(),
                            playerFilter.get(x).getPlayerLeague(),  playerFilter.get(x).getPlayerNation(), playerFilter.get(x).getPace(), playerFilter.get(x).getShot(), playerFilter.get(x).getPace(),
                            playerFilter.get(x).getDribble(), playerFilter.get(x).getDefense(), playerFilter.get(x).getPhysical(), playerFilter.get(x).getWeakFoot(), playerFilter.get(x).getSkill(),
                            playerFilter.get(x).getUrl(), playerFilter.get(x).getUrl_mini()));


                }


            }



        PlayerAttributesAdapter adapter = new PlayerAttributesAdapter(FUTSearchActivty.this, playerList);
        ListView playerListView = findViewById(R.id.fut_player_list);
        playerListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }



    private void syncedFUTPlayerData() throws ParseException {

        String key;
        String name;
        String rtg;
        String pos;
        String club; String league;
        String nation; String pace;
        String shot; String pass;
        String dribble;
        String defense;
        String physical;
        String weakFoot;
        String skill;
        String url;
        String mini;
        JSONArray gamer;


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            gamer = jsonObj.getJSONArray(TAG_RESULTS);
            //Log.d("MYSQL", "gamer Data :" + gamer);
            for(int i=0;i< gamer.length();i++){

                JSONObject c = gamer.getJSONObject(i);

                key = c.getString(TAG_KEY);
                name = c.getString(TAG_NAME);
                rtg = c.getString(TAG_RTG);
                pos = c.getString(TAG_POS);
                club = c.getString(TAG_CLUB);
                league = c.getString(TAG_LEAGUE);
                nation = c.getString(TAG_NATION);
                pace = c.getString(TAG_PACE);
                shot = c.getString(TAG_SHOT);
                pass = c.getString(TAG_PASS);
                dribble = c.getString(TAG_DRIBBLE);
                defense = c.getString(TAG_DEFENSE);
                physical = c.getString(TAG_PHYSICAL);
                weakFoot = c.getString(TAG_WEAKFOOT);
                url = c.getString(TAG_URL);
                mini = c.getString(TAG_MINI);
                skill = c.getString(TAG_SKILL);

                //Log.d("URL", "mini: " + name + " - " + mini);

                //SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                //final String loadUser = sharedPreferences.getString("userEmail", DEFAULT);

                attributes.add(new FUTPlayerAttributes(key, name, rtg, pos, club, league, nation, pace, shot, pass, dribble, defense, physical, weakFoot, skill, url, mini));
                filterATKPostions(attributes);
                filterMIDPostions(attributes);
                filterDEFPostions(attributes);
                //filterBySearch(attributes);


                TextWatcher textWatcher = new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {



                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                        filterBySearch(attributes);





                    }
                };


                //searchFilter.setSelectAllOnFocus(true);
                searchFilter.addTextChangedListener(textWatcher);



                        //Log.d("MYSQL", "gamer Data :" + copy.get(i).getTeamName());

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

                try {

                    syncedFUTPlayerData();





                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }




}
