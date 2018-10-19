package custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Databases.DatabaseHelper;
import FUT.FUTPlayerAttributes;
import circle.CircularImageView;
import utils.com.futpro.R;

/**
 * Created by jpablo09 on 10/17/2018.
 */

class TeamRow{

    String name;
    String rtg;
    String pos;



    TeamRow(String name, String rtg, String pos)
    {
        this.name = name;
        this.rtg = rtg;
        this.pos = pos;


    }


}


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {


    ArrayList<TeamRow> playerList = new ArrayList<>();

    Context context;




    public TeamAdapter(ArrayList<FUTPlayerAttributes> players, Context context) {


        this.context = context;



        for (int i = 0; i < players.size(); i++) {


            playerList.add(new TeamRow(players.get(i).getPlauerName(), players.get(i).getPlayerRtg(), players.get(i).getPlayerPOS()));


        }

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String pName = playerList.get(position).name;
        holder.txtview.setText(pName);


    }

    @Override
    public int getItemCount()
    {
        return playerList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircularImageView imageView;
        TextView txtview;


        public MyViewHolder(View view) {
            super(view);

            imageView =  view.findViewById(R.id.imageview);
            txtview =  view.findViewById(R.id.txtview);

        }
    }
}


