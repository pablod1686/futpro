package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Databases.DatabaseHelper;
import Databases.DatabaseHelperII;
import FUT.FUTPlayerAttributes;
import custom.adapters.LineupAdapter;
import custom.adapters.TeamAdapter;
import graphs.Displayable;
import graphs.Graph;
import graphs.GraphActivity;
import graphs.UnweightedGraph;

/**
 * Created by jpablo09 on 10/17/2018.
 */

public class TeamEditActivty extends AppCompatActivity {

    RecyclerView player_recycler_view;
    TeamAdapter teamAdapter;

    RecyclerView formation_recycler_view;
    LineupAdapter lineupAdapter;

    FormationAdapter formationAdapter;

    private DatabaseHelper database;
    private static final String DEFAULT = "N/A";
    private static final int DEFAULTPOS = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences formPosPrefrences;
    SharedPreferences atrributePreferences;


    PositionViewPagerAdapter mAdapter;
    ViewPager mPager;
    InkPageIndicator mIndicator;

    String loadFormation;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_team_layout);

        sharedPreferences = getSharedPreferences("formation", Context.MODE_PRIVATE);
        loadFormation = sharedPreferences.getString("formationSet", DEFAULT);
        final int formationPos = sharedPreferences.getInt("position", DEFAULTPOS);

        formPosPrefrences = getSharedPreferences("formationPosition", Context.MODE_PRIVATE);
        final String origPos = formPosPrefrences.getString("formPosition", DEFAULT);
        final int pagerPos = formPosPrefrences.getInt("positionNumber", DEFAULTPOS);



        atrributePreferences = getSharedPreferences("attributes", Context.MODE_PRIVATE);




        final String loadSelFormation = atrributePreferences.getString("formation", DEFAULT);

        /*

        final String loadFormPos = atrributePreferences.getString("formPos", DEFAULT);
        final String loadName = atrributePreferences.getString("playerName", DEFAULT);
        final String loadRtg = atrributePreferences.getString("playerRating", DEFAULT);
        final String loadPos = atrributePreferences.getString("playerPosition", DEFAULT);
        final String loadClub = atrributePreferences.getString("playerClub", DEFAULT);
        final String loadPace = atrributePreferences.getString("playerPace", DEFAULT);
        final String loadShot = atrributePreferences.getString("playerShot", DEFAULT);
        final String loadPass = atrributePreferences.getString("playerPass", DEFAULT);
        final String loadDribble = atrributePreferences.getString("playerDribble", DEFAULT);
        final String loadDefense = atrributePreferences.getString("playerDefense", DEFAULT);
        final String loadPhysical = atrributePreferences.getString("playerPhysical", DEFAULT);


        */


        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final String loadUser = sharedPreferences.getString("userEmail", DEFAULT);

        SharedPreferences tagPref = getSharedPreferences("GamerTagID", Context.MODE_PRIVATE);
        final String loadTag = tagPref.getString("userTag", DEFAULT);

        SharedPreferences teamPref = getSharedPreferences("GamerTeam", Context.MODE_PRIVATE);
        final String loadTeam = teamPref.getString("gamerTeam", DEFAULT);


        TextView formation_name = findViewById(R.id.formation_name);
        formation_name.setText(loadFormation);
        formation_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitTeam(loadUser,loadTag,loadTeam,loadFormation);
                Toast.makeText(TeamEditActivty.this, "Team data submitted", Toast.LENGTH_SHORT).show();

            }
        });

        /*

        TextView playerName = findViewById(R.id.playername);
        //playerName.setText(loadName);

        TextView playerRtg = findViewById(R.id.playerrating);
        //playerRtg.setText(loadRtg);

        TextView playerPos = findViewById(R.id.playerposition);
        //playerPos.setText(loadPos);


        TextView playerClub = findViewById(R.id.playerclub);
        //playerClub.setText(loadClub);




        TextView playerPace = findViewById(R.id.pace_stats);
        playerPace.setText(loadPace +  " PAC");

        TextView playerShot = findViewById(R.id.shot_stats);
        playerShot.setText(loadShot +  " SHO");

        TextView playerPass = findViewById(R.id.pass_stats);
        playerPass.setText(loadPass +  " PAS");


        TextView playerDribble = findViewById(R.id.dribble_stats);
        playerDribble.setText(loadDribble + " DRI");

        TextView playerDefense = findViewById(R.id.defense_stats);
        playerDefense.setText(loadDefense + " DEF");


        TextView playerPhysical = findViewById(R.id.physical_stats);
        playerPhysical.setText(loadPhysical +  " PHY");

        */

        ImageView card = findViewById(R.id.player_stats);

        if(isPosSelected(origPos, pagerPos) != null) {

            Glide.with(this).load(isPosSelected(origPos, pagerPos)).into(card);

        }


        //TextView playerSelFormation = findViewById(R.id.selectedFormation);
        //playerSelFormation.setText(loadSelFormation);

        //TextView playerFormPos = findViewById(R.id.formationPosition);
        //playerFormPos.setText(loadFormPos);



        player_recycler_view=  findViewById(R.id.team_list);

        ArrayList<FUTPlayerAttributes> selectedPlayers = new ArrayList<>();
        database = new DatabaseHelper(this);
        for(int i = 0; i < database.getFUTPlayers().size(); i++) {
            selectedPlayers.add(new FUTPlayerAttributes(database.getFUTPlayers().get(i).getKey(),database.getFUTPlayers().get(i).getPlauerName(), database.getFUTPlayers().get(i).getPlayerRtg(), database.getFUTPlayers().get(i).getPlayerPOS(),
                    database.getFUTPlayers().get(i).getPlayerClub(), database.getFUTPlayers().get(i).getPlayerLeague(), database.getFUTPlayers().get(i).getPlayerNation(),
                    database.getFUTPlayers().get(i).getPace(), database.getFUTPlayers().get(i).getShot(), database.getFUTPlayers().get(i).getPass(),
                    database.getFUTPlayers().get(i).getDribble(), database.getFUTPlayers().get(i).getDefense(), database.getFUTPlayers().get(i).getPhysical(),
                    database.getFUTPlayers().get(i).getWeakFoot(), database.getFUTPlayers().get(i).getSkill(), database.getFUTPlayers().get(i).getUrl(),
                    database.getFUTPlayers().get(i).getUrl_mini()));
        }

        teamAdapter = new TeamAdapter(selectedPlayers, loadFormation, origPos, pagerPos,TeamEditActivty.this);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(TeamEditActivty.this, LinearLayoutManager.HORIZONTAL, false);
        player_recycler_view.setLayoutManager(horizontalLayoutManager);
        player_recycler_view.setAdapter(teamAdapter);



        formationAdapter = new FormationAdapter(TeamEditActivty.this);

        ListView formationList = findViewById(R.id.formation_list);
        formationList.setAdapter(formationAdapter);
        formationList.setSelection(formationPos);
        formationAdapter.notifyDataSetChanged();



        LinearLayoutManager horizontalLayoutManagerII = new LinearLayoutManager(TeamEditActivty.this, LinearLayoutManager.HORIZONTAL, false);
        formation_recycler_view = findViewById(R.id.starters);
        lineupAdapter = new LineupAdapter(TeamEditActivty.this, loadFormation);
        formation_recycler_view.setLayoutManager(horizontalLayoutManagerII);
        formation_recycler_view.setAdapter(lineupAdapter);
        lineupAdapter.notifyDataSetChanged();


        mAdapter =  new PositionViewPagerAdapter(TeamEditActivty.this, loadFormation);
        mPager = findViewById(R.id.pager_positions);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(pagerPos);
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);








    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        viewFormation();
    }


    @Override
    public void onBackPressed() {
        super.onResume();

        sharedPreferences = getSharedPreferences("formation", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        formPosPrefrences = getSharedPreferences("formationPosition", Context.MODE_PRIVATE);
        formPosPrefrences.edit().clear().apply();

        atrributePreferences = getSharedPreferences("attributes", Context.MODE_PRIVATE);
        atrributePreferences.edit().clear().apply();

        Intent intent = new Intent(TeamEditActivty.this, FUTSearchActivty.class);
        startActivity(intent);

    }


    public void viewFormation(){

        RelativeLayout v =  findViewById(R.id.formation_view);
        v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        Log.d("Layout" , "Height: " + v.getHeight());
        GraphActivity.FomationView myView = new GraphActivity.FomationView(this);

        switch (loadFormation) {
            case "3-1-4-2":



                myView.setGraph(formation3142(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);


                break;
            case "3-4-1-2":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation3412(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "3-4-2-1":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation3421(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "3-4-3":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation343(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;

            case "3-5-2":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation352(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-1-2-1-2":


                // v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation41212(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-1-2-1-2(2)":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation41212_2(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-1-3-2":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4132(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;

            case "4-1-4-1":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4141(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-2-2-2":

                // v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4222(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-2-3-1":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4231(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-2-3-1(2)":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4231_2(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-2-4":
                // v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation424(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-3-1-2":
                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4312(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-3-2-1":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation4321(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
            case "4-3-3":

                //v.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                myView.setGraph(formation433(v.getWidth(),v.getHeight()));
                v.setBackgroundResource(R.drawable.campo_2);
                v.addView(myView);

                break;
        }


    }


    public Graph<TeamFormation> formation3142 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation("ST",(int)(parentWidth * 0.4f), (int) (parentHeight * 0.15f)), new TeamFormation("ST", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.15f)),
                new TeamFormation("LM", (int)(parentWidth * 0.25f), (int) (parentHeight * 0.3f)), new TeamFormation("CM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.3f)), new TeamFormation("CM,", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.3f)), new TeamFormation("RM", (int)(parentWidth * 0.75f), (int) (parentHeight * 0.3f)),
                new TeamFormation("CDM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.5f)),
                new TeamFormation("CB", (int)(parentWidth * 0.3f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.7f), (int) (parentHeight * 0.65f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation3412 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST",(int)(parentWidth * 0.4f), (int) (parentHeight * 0.15f)), new TeamFormation("ST", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.15f)),
                new TeamFormation("CAM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.35f)),
                new TeamFormation("LM", (int)(parentWidth * 0.25f), (int) (parentHeight * 0.5f)), new TeamFormation("CM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.5f)), new TeamFormation("CM,", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.5f)), new TeamFormation("RM", (int)(parentWidth * 0.75f), (int) (parentHeight * 0.5f)),
                new TeamFormation("CB", (int)(parentWidth * 0.3f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.7f), (int) (parentHeight * 0.65f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation3421 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.15f)),
                new TeamFormation("LF", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.25f)), new TeamFormation("RF", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.25f)),
                new TeamFormation("LM", (int)(parentWidth * 0.25f), (int) (parentHeight * 0.4f)), new TeamFormation("CM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.4f)), new TeamFormation("CM,", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.4f)), new TeamFormation("RM", (int)(parentWidth * 0.75f), (int) (parentHeight * 0.4f)),
                new TeamFormation("CB", (int)(parentWidth * 0.3f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.7f), (int) (parentHeight * 0.65f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation343 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.15f)),
                new TeamFormation("LW", (int)(parentWidth * 0.3f), (int) (parentHeight * 0.2f)), new TeamFormation("RW", (int)(parentWidth * 0.7f), (int) (parentHeight * 0.2f)),
                new TeamFormation("LM", (int)(parentWidth * 0.25f), (int) (parentHeight * 0.4f)), new TeamFormation("CM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.4f)), new TeamFormation("CM,", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.4f)), new TeamFormation("RM", (int)(parentWidth * 0.75f), (int) (parentHeight * 0.4f)),
                new TeamFormation("CB", (int)(parentWidth * 0.3f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.7f), (int) (parentHeight * 0.65f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation352 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST",(int)(parentWidth * 0.4f), (int) (parentHeight * 0.15f)), new TeamFormation("ST", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.15f)),
                new TeamFormation("CAM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.3f)),
                new TeamFormation("LM", (int)(parentWidth * 0.25f), (int) (parentHeight * 0.4f)), new TeamFormation("CM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.4f)), new TeamFormation("CM,", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.4f)), new TeamFormation("RM", (int)(parentWidth * 0.75f), (int) (parentHeight * 0.4f)),
                new TeamFormation("CB", (int)(parentWidth * 0.3f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)), new TeamFormation("CB", (int)(parentWidth * 0.7f), (int) (parentHeight * 0.65f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation41212( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST",(int)(parentWidth * 0.4f), (int) (parentHeight * 0.15f)), new TeamFormation("ST", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.15f)),
                new TeamFormation("CAM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.35f)),
                new TeamFormation("LM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.5f)),  new TeamFormation("RM", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.5f)),
                new TeamFormation("CDM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)),
                new TeamFormation("LB", (int)(parentWidth * 0.2f), (int) (parentHeight * 0.8f)), new TeamFormation("CB", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.8f)), new TeamFormation("CB", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.8f)), new TeamFormation("RB", (int)(parentWidth * 0.8f), (int) (parentHeight * 0.8f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation41212_2 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST",(int)(parentWidth * 0.4f), (int) (parentHeight * 0.15f)), new TeamFormation("ST", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.15f)),
                new TeamFormation("CAM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.35f)),
                new TeamFormation("LM", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.5f)),  new TeamFormation("RM", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.5f)),
                new TeamFormation("CDM", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.65f)),
                new TeamFormation("LB", (int)(parentWidth * 0.2f), (int) (parentHeight * 0.8f)), new TeamFormation("CB", (int)(parentWidth * 0.4f), (int) (parentHeight * 0.8f)), new TeamFormation("CB", (int)(parentWidth * 0.6f), (int) (parentHeight * 0.8f)), new TeamFormation("RB", (int)(parentWidth * 0.8f), (int) (parentHeight * 0.8f)),
                new TeamFormation("GK", (int)(parentWidth * 0.5f), (int) (parentHeight * 0.9f))
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4132( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 285, 50), new TeamFormation("ST", 350, 50),
                new TeamFormation("LM", 125, 110),  new TeamFormation("CM", 320, 100), new TeamFormation("RM", 510, 110),
                new TeamFormation("CDM", 320, 200),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4141( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 320, 50),
                new TeamFormation("LM", 125, 150), new TeamFormation("CM", 265, 150), new TeamFormation("CM,", 370, 150), new TeamFormation("RM", 510, 150),
                new TeamFormation("CDM", 320, 200),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4231( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 320, 50),
                new TeamFormation("CAM", 265, 125), new TeamFormation("CAM,", 370, 125),
                new TeamFormation("CAM", 320, 160),
                new TeamFormation("CDM", 245, 200), new TeamFormation("CDM,", 390, 200),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4231_2( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 320, 50),
                new TeamFormation("CAM", 100, 125), new TeamFormation("CAM,", 530, 125),
                new TeamFormation("CAM", 320, 140),
                new TeamFormation("CDM", 245, 200), new TeamFormation("CDM,", 390, 200),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4222( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 285, 50), new TeamFormation("ST", 350, 50),
                new TeamFormation("CAM", 125, 150), new TeamFormation("CM", 265, 175), new TeamFormation("CM,", 370, 175), new TeamFormation("CAM", 510, 150),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation424( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 285, 50), new TeamFormation("ST", 350, 50),
                new TeamFormation("LW", 125, 100),  new TeamFormation("RW", 510, 100),
                new TeamFormation("CM", 265, 200), new TeamFormation("CM,", 370, 200),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4312( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("ST", 285, 50), new TeamFormation("ST", 350, 50),
                new TeamFormation("CAM", 320, 100),
                new TeamFormation("CM", 200, 155),  new TeamFormation("CM", 320, 175), new TeamFormation("CM", 430, 155),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation4321( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {

                new TeamFormation("ST", 320, 50),
                new TeamFormation("LF", 250, 100), new TeamFormation("RF", 385, 100),
                new TeamFormation("CM", 200, 175),  new TeamFormation("CM", 320, 175), new TeamFormation("CM", 430, 175),
                new TeamFormation("LB", 75, 250), new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275), new TeamFormation("RB", 555, 250),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0},{0,0},
                {0,0},{0,0},
                {0,0},{0,0}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }

    public Graph<TeamFormation> formation433 ( int parentWidth,int parentHeight){

        TeamFormation[] vertices = {
                new TeamFormation("LW", 125, 50), new TeamFormation("ST", 320, 25), new TeamFormation("RW", 510, 50),
                new TeamFormation("LM,", 125, 150), new TeamFormation("CAM", 320, 125), new TeamFormation("RM", 510, 150),
                new TeamFormation("LB", 75, 250), new TeamFormation("RB", 555, 250),
                new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275),
                new TeamFormation("GK", 320, 325)
        };

        int[][] edges = {
                {0,1},{0,3},
                {1,0},{1,2},{1,4},
                {2,1},{2,5},
                {3,4},{3,6},
                {5,4},{5,7},
                {8,9},{8,6},{8,3},
                {9,7},{9,5},
                {10,8},{10,9}

        };

        Graph<TeamFormation> teamFormationGraph;
        teamFormationGraph = new UnweightedGraph<>(edges, vertices);

        return teamFormationGraph;
    }


    public String isPosSelected( String pos, int posNum){

        DatabaseHelperII getTeam = new DatabaseHelperII(TeamEditActivty.this);

        for(int i = 0; i < getTeam.getLineUpPlayers().size(); i++){

            FUTPlayerAttributes data = getTeam.getLineUpPlayers().get(i);


            if(data.getFormPos().equals(pos) && data.getPosNum() == posNum){

                return data.getUrl();

            }
        }

        return  null;
    }

    public void submitTeam(String emaiil, String tag, String team, String formation){

        DatabaseHelperII getTeam = new DatabaseHelperII(TeamEditActivty.this);


        StringBuilder chain = new StringBuilder();
        StringBuilder chain2 = new StringBuilder();
        StringBuilder chain3 = new StringBuilder();
        StringBuilder chain4 = new StringBuilder();
        for(int i = 0; i < getTeam.getLineUpPlayers().size(); i++){

            FUTPlayerAttributes data = getTeam.getLineUpPlayers().get(i);

            chain.append(data.getKey());
            chain.append("-");
            chain2.append(data.getFormPos());
            chain2.append("-");
            chain3.append(data.getPosNum());
            chain3.append("-");
            chain4.append(data.getPlayerPOS());
            chain4.append("-");

        }

        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        final ArrayList<HashMap<String, String>> json = new ArrayList<>();

        final HashMap<String, String> map = new HashMap<>();


            map.put("email", emaiil);
            map.put("tag", tag);
            map.put("team", team);
            map.put("formation",formation);
            map.put("chain1", chain.toString());
            map.put("chain2", chain2.toString());
            map.put("chain3",chain3.toString());
            map.put("chain4", chain4.toString());


            json.add(map);
            Gson gson = new GsonBuilder().create();
            String list = gson.toJson(json);


            params.put("teamData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
            client.post("http://betlogic.co/FUTPRO/insert_team_data.php", params, new AsyncHttpResponseHandler());




            //params.put("singleBet", list);
            //client.post("http://betlogic.co/FUTPRO/insert_multiple_gamer_data.php", params, new AsyncHttpResponseHandler());



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
