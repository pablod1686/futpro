package custom.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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


    public LineupAdapter( Context context, String formation) {


        this.context = context;

        lineup = new ArrayList<>();



        int SIZE = 11;
        for (int i = 0; i < SIZE; i++) {

            if(formation.equals("3-1-4-2")){

                String[] form1 = {"ST", "ST", "LM", "CM", "CDM", "CM", "RM", "CB", "CB", "CB", "GK"};
                lineup.add(new LineupRow(form1[i]));
            }

            else if(formation.equals("4-3-3")) {


                String[] form16 = {"ST", "LW", "RW", "CAM", "LM", "RM", "CB", "CB", "LB", "RB", "GK"};
                lineup.add(new LineupRow(form16[i]));

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

        String pName = lineup.get(position).starters;
        holder.txtview.setText(pName);


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
