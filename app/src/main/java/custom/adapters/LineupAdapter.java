package custom.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Databases.DatabaseHelperII;
import FUT.FUTPlayerAttributes;
import circle.CircularImageView;
import utils.com.futpro.R;

/**
 * Created by jpablo09 on 10/18/2018.
 */
class LineupRow{

    String starters;


    public LineupRow(String starters){

        this.starters = starters;


    }


}



public class LineupAdapter extends RecyclerView.Adapter<LineupAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<LineupRow> lineup;
    private DatabaseHelperII getTeam;
    private String formation;




    public LineupAdapter( Context context, String formation) {


        this.context = context;
        this.formation = formation;


        lineup = new ArrayList<>();




        int SIZE = 11;
        for (int i = 0; i < SIZE; i++) {

            switch (formation) {
                case "3-1-4-2":

                    String[] form1 = {"ST", "ST", "LM", "CM", "CDM", "CM", "RM", "CB", "CB", "CB", "GK"};
                    lineup.add(new LineupRow(form1[i]));
                    break;
                case "3-4-1-2":

                    String[] form2 = {"ST", "ST", "CAM", "LM", "CM", "CM", "RM", "CB", "CB", "CB", "GK"};
                    lineup.add(new LineupRow(form2[i]));
                    break;
                case "3-4-2-1":

                    String[] form3 = {"ST", "LF", "RF", "LM", "CM", "CM", "RM", "CB", "CB", "CB", "GK"};
                    lineup.add(new LineupRow(form3[i]));

                    break;
                case "3-4-3":

                    String[] form4 = {"ST", "LW", "RW", "LM", "CM", "CM", "RM", "CB", "CB", "CB", "GK"};
                    lineup.add(new LineupRow(form4[i]));
                    break;
                case "3-5-2":

                    String[] form5 = {"ST", "ST", "CAM", "LM", "CDM", "CDM", "RM", "CB", "CB", "CB", "GK"};
                    lineup.add(new LineupRow(form5[i]));
                    break;
                case "4-1-2-1-2":

                    String[] form6 = {"ST", "ST", "CAM", "LM", "RM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form6[i]));
                    break;
                case "4-1-2-1-2(2)":

                    String[] form7 = {"ST", "ST", "CAM", "CM", "CM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form7[i]));
                    break;
                case "4-1-3-2":

                    String[] form8 = {"ST", "ST", "LM", "CM", "RM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form8[i]));
                    break;
                case "4-1-4-1":

                    String[] form9 = {"ST", "LM", "CM", "CM", "RM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form9[i]));
                    break;
                case "4-2-3-1":

                    String[] form10 = {"ST", "CAM", "CAM", "CAM", "CDM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form10[i]));
                    break;
                case "4-2-3-1(2)":

                    String[] form11 = {"ST", "LM", "CAM", "RM", "CDM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form11[i]));
                    break;
                case "4-2-2-2":

                    String[] form12 = {"ST", "ST", "CAM", "CAM", "CDM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form12[i]));
                    break;
                case "4-2-4":

                    String[] form13 = {"ST", "ST", "LW", "RW", "CM", "CM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form13[i]));
                    break;
                case "4-3-1-2":

                    String[] form14 = {"ST", "ST", "CAM", "CM", "CM", "CM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form14[i]));
                    break;
                case "4-3-2-1":

                    String[] form15 = {"ST", "LF", "RF", "CM", "CM", "CM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form15[i]));
                    break;
                case "4-3-3":


                    String[] form16 = {"ST", "LW", "RW", "CAM", "LM", "RM", "CB", "CB", "LB", "RB", "GK"};
                    lineup.add(new LineupRow(form16[i]));

                    break;
                case "4-3-3(2)": {


                    String[] form17 = {"ST", "LW", "RW", "CM", "CDM", "CM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form17[i]));

                    break;
                }
                case "4-3-3(3)": {


                    String[] form17 = {"ST", "LW", "RW", "CDM", "CM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form17[i]));

                    break;
                }
                case "4-3-3(4)":


                    String[] form18 = {"ST", "LW", "RW", "CM", "CAM", "CM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form18[i]));

                    break;
                case "4-3-3(5)":


                    String[] form19 = {"LW", "CF", "RW", "CM", "CDM", "CM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form19[i]));

                    break;
                case "4-4-1-1":


                    String[] form20 = {"ST", "CF", "LM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form20[i]));

                    break;
                case "4-4-1-1(2)":


                    String[] form21 = {"ST", "CAM", "LM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form21[i]));

                    break;
                case "4-4-2":


                    String[] form22 = {"ST", "ST", "LM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form22[i]));

                    break;
                case "4-4-2(2)":


                    String[] form23 = {"ST", "ST", "LM", "CDM", "CDM", "RM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form23[i]));

                    break;
                case "4-5-1":


                    String[] form24 = {"ST", "CAM", "CAM", "LM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form24[i]));

                    break;
                case "4-5-1(2)":


                    String[] form25 = {"ST", "LM", "CM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                    lineup.add(new LineupRow(form25[i]));

                    break;
                case "5-2-1-2":


                    String[] form26 = {"ST", "ST", "CAM", "CM", "CM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                    lineup.add(new LineupRow(form26[i]));

                    break;
                case "5-2-2-1":


                    String[] form27 = {"ST", "LW", "RW", "CM", "CM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                    lineup.add(new LineupRow(form27[i]));

                    break;
                case "5-3-2":


                    String[] form28 = {"ST", "ST", "CM", "CM", "CM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                    lineup.add(new LineupRow(form28[i]));

                    break;
                case "5-4-1":


                    String[] form29 = {"ST", "LM", "CM", "CM", "RM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                    lineup.add(new LineupRow(form29[i]));

                    break;
            }

        }


    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineup_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String pPos = lineup.get(position).starters;
        holder.txtview.setText(pPos);

        getTeam = new DatabaseHelperII(context);

        if (getTeam.getLineUpPlayers().size() > 0) {

            for(int j = 0; j < getTeam.getLineUpPlayers().size(); j++) {

                FUTPlayerAttributes row = getTeam.getLineUpPlayers().get(j);

                if (row.getFormation().equals(formation) && row.getFormPos().equals(pPos) && row.getPosNum() == position) {

                    holder.imageView.setColorFilter(Color.GREEN);

                }

            }


        }

    }

    @Override
    public int getItemCount()
    {
        return lineup.size();

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtview;


        public MyViewHolder(View view) {
            super(view);

            imageView =  view.findViewById(R.id.imageview);
            txtview = view.findViewById(R.id.txtview);


        }
    }
}
