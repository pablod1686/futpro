package utils.com.futpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Databases.DatabaseHelper;
import FUT.FUTPlayerAttributes;
import circle.CircularImageView;

/**
 * Created by jpablo09 on 10/15/2018.
 */


class AttributesRow{

        String name;
        String rtg;
        String pos;



    AttributesRow(String name, String rtg, String pos)
        {
        this.name = name;
        this.rtg = rtg;
        this.pos = pos;


        }


}

public class PlayerAttributesAdapter extends BaseAdapter {


    private ArrayList<AttributesRow> list;
    private Context context;



    public PlayerAttributesAdapter(Context c, ArrayList<FUTPlayerAttributes> players)  {

        context = c;
        list = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {


            list.add(new AttributesRow(players.get(i).getPlauerName(), players.get(i).getPlayerRtg(), players.get(i).getPlayerPOS()));


        }

    }


    @Override
    public int getCount() {

        return list.size();

    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        ViewHolder holder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.player_view, parent, false);
            holder = new ViewHolder();
            holder.name = row.findViewById(R.id.playername);
            holder.pos = row.findViewById(R.id.pos);
            holder.rtg = row.findViewById(R.id.player_rating);
            holder.select = row.findViewById(R.id.select_player);




            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }

        final AttributesRow temp = list.get(position);

        holder.name.setText(temp.name);
        holder.pos.setText(temp.pos);
        holder.rtg.setText(temp.rtg);

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper database = new DatabaseHelper(v.getContext());
                database.addFUTPlayers(new FUTPlayerAttributes(temp.name, temp.rtg, temp.pos));

            }
        });


        return row;
    }

    public class ViewHolder{


        public TextView name;
        public TextView rtg;
        public TextView pos;
        public TextView select;



    }







}
