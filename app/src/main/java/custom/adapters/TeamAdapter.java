package custom.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Databases.DatabaseHelperII;
import FUT.FUTPlayerAttributes;
import utils.com.futpro.R;
import utils.com.futpro.TeamEditActivty;

/**
 * Created by jpablo09 on 10/17/2018.
 */

class TeamRow{

    String playerKey;
    String gamerTag;
    String formation;
    String formPos;
    int posNum;
    String name;
    String rtg;
    String pos;
    String club;
    String league;
    String nation;
    String pace;
    String shot;
    String pass;
    String dribble;
    String defense;
    String physical;
    String weakFoot;
    String skill;



    TeamRow( String gamerTag, String formation, String formPos, int posNum,String playerKey, String name, String rtg, String pos, String club, String league, String nation, String pace, String shot,
            String pass, String dribble, String defense, String physical, String weakFoot, String skill)
    {

        this.gamerTag = gamerTag;
        this.formation = formation;
        this.formPos = formPos;
        this.posNum = posNum;
        this.playerKey = playerKey;
        this.name = name;
        this.rtg = rtg;
        this.pos = pos;
        this.club = club;
        this.league = league;
        this.nation = nation;
        this.pace = pace;
        this.shot = shot;
        this.pass = pass;
        this.dribble = dribble;
        this.defense = defense;
        this.physical = physical;
        this.weakFoot = weakFoot;
        this.skill = skill;


    }


}


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {


    private ArrayList<TeamRow> playerList = new ArrayList<>();

    private Context context;

    private static final String DEFAULT = "N/A";




    public TeamAdapter(ArrayList<FUTPlayerAttributes> players, String formation, String formPos, int posNum, Context context) {


        this.context = context;

        SharedPreferences tagPref = context.getSharedPreferences("GamerTagID", Context.MODE_PRIVATE);
        final String loadTag = tagPref.getString("userTag", DEFAULT);


        //Log.d("Pager: ", "POSITION 1: " + posNum);

        for (int i = 0; i < players.size(); i++) {



            playerList.add(new TeamRow(loadTag,formation, formPos, posNum, players.get(i).getKey(), players.get(i).getPlauerName(), players.get(i).getPlayerRtg(), players.get(i).getPlayerPOS(), players.get(i).getPlayerClub(),
                                        players.get(i).getPlayerLeague(), players.get(i).getPlayerNation(), players.get(i).getPace(), players.get(i).getShot(), players.get(i).getPass(),
                                        players.get(i).getDribble(), players.get(i).getDefense(), players.get(i).getPhysical(), players.get(i).getWeakFoot(), players.get(i).getSkill()));


        }

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //String pName = playerList.get(position).name;



        final TeamRow temp = playerList.get(position);
        holder.txtview.setText(temp.name);

        switch (temp.name) {
            case "James Rodriguez":

                holder.imageView.setImageResource(R.drawable.james_rodriguez);


                break;
            case "Cristiano Ronaldo":

                holder.imageView.setImageResource(R.drawable.cristiano);

                break;
            case "Juan Cuadrado":

                holder.imageView.setImageResource(R.drawable.cuadrado);

                break;
            case "Hugo Lloris":

                holder.imageView.setImageResource(R.drawable.lloris);

                break;
            case "Davinson Sanchez":

                holder.imageView.setImageResource(R.drawable.sanchez);

                break;
            case "Leroy Sane":

                holder.imageView.setImageResource(R.drawable.sane);

                break;
            case "Serge Gnabry":

                holder.imageView.setImageResource(R.drawable.gnabry);

                break;
            case "Jan Vertonghen":

                holder.imageView.setImageResource(R.drawable.vertonghen);

                break;
            case "Kieran Trippier":

                holder.imageView.setImageResource(R.drawable.trippier);

                break;
            case "Benjamin Mendy":

                holder.imageView.setImageResource(R.drawable.mendy);

                break;
            case "Paulo Dybala":

                holder.imageView.setImageResource(R.drawable.dybala);

                break;
        }



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!temp.formation.equals("N/A") && !temp.formPos.equals("N/A")) {


                    if(isPlayerExist(temp.name, temp.posNum)){

                        Toast.makeText(v.getContext(),"Player or Position already selected", Toast.LENGTH_LONG).show();

                    }else {

                        SharedPreferences playerPreferences = v.getContext().getSharedPreferences("attributes", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = playerPreferences.edit();
                        editor.putString("formation", temp.formation);
                        editor.putString("formPos", temp.formPos);

                        editor.putString("playerName", temp.name);
                        editor.putString("playerRating", temp.rtg);
                        editor.putString("playerPosition", temp.pos);
                        editor.putString("playerClub", temp.club);

                        editor.putString("playerPace", temp.pace);
                        editor.putString("playerShot", temp.shot);
                        editor.putString("playerPass", temp.pass);
                        editor.putString("playerDribble", temp.dribble);
                        editor.putString("playerDefense", temp.defense);
                        editor.putString("playerPhysical", temp.physical);

                        editor.apply();



                        DatabaseHelperII createLineup = new DatabaseHelperII(v.getContext());

                        createLineup.addLineUpPlayers(new FUTPlayerAttributes(temp.playerKey, temp.gamerTag,temp.formation, temp.formPos, temp.posNum, temp.name, temp.rtg, temp.pos, temp.club, temp.league, temp.nation, temp.pace, temp.shot,
                                temp.pass, temp.dribble, temp.defense, temp.physical, temp.weakFoot, temp.skill));



                    }



                }else{

                    Toast.makeText(v.getContext(),"Please select a formation or position", Toast.LENGTH_LONG).show();

                }

                /*
                DatabaseHelperII createLineup = new DatabaseHelperII(v.getContext());
                createLineup.addLineUpPlayers(new FUTPlayerAttributes(temp.formation, temp.formPos,temp.name, temp.rtg, temp.pos, temp.club, temp.league, temp.nation, temp.pace, temp.shot,
                        temp.pass, temp.dribble, temp.defense, temp.physical, temp.weakFoot, temp.skill));
                */




                Intent intent = new Intent(v.getContext(), TeamEditActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                v.getContext().startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount()
    {
        return playerList.size();
    }

    public boolean isPlayerExist(String name, int posNum){

        DatabaseHelperII getTeam = new DatabaseHelperII(context);

        for(int i = 0; i < getTeam.getLineUpPlayers().size(); i++){

            FUTPlayerAttributes row = getTeam.getLineUpPlayers().get(i);

            Log.d("Pager: ", "POSITION: " + row.getPosNum());

            if(row.getPlauerName().equals(name) || row.getPosNum() == posNum){


                return true;

            }

        }

        return false;

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtview;


        public MyViewHolder(View view) {
            super(view);

            imageView =  view.findViewById(R.id.imageview);
            txtview =  view.findViewById(R.id.txtview);

        }
    }
}


