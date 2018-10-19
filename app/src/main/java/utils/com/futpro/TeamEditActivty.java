package utils.com.futpro;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;

import Databases.DatabaseHelper;
import FUT.FUTPlayerAttributes;
import custom.adapters.LineupAdapter;
import custom.adapters.TeamAdapter;

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


    PositionViewPagerAdapter mAdapter;
    ViewPager mPager;
    InkPageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_team_layout);

        SharedPreferences sharedPreferences = getSharedPreferences("formation", Context.MODE_PRIVATE);
        final String loadFormation = sharedPreferences.getString("formationSet", DEFAULT);
        final int formationPos = sharedPreferences.getInt("position", DEFAULTPOS);

        TextView formation_name = findViewById(R.id.formation_name);

        formation_name.setText(loadFormation);

        player_recycler_view=  findViewById(R.id.team_list);

        ArrayList<FUTPlayerAttributes> selectedPlayers = new ArrayList<>();
        database = new DatabaseHelper(this);
        for(int i = 0; i < database.getFUTPlayers().size(); i++) {
            selectedPlayers.add(new FUTPlayerAttributes(database.getFUTPlayers().get(i).getPlauerName(), database.getFUTPlayers().get(i).getPlayerRtg(), database.getFUTPlayers().get(i).getPlayerPOS()));
        }

        teamAdapter =new TeamAdapter(selectedPlayers,TeamEditActivty.this);

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
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);




    }


    @Override
    public void onResume() {
        super.onResume();

        lineupAdapter.notifyDataSetChanged();
    }


}
