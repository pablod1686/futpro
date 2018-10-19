package utils.com.futpro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import profile.Challenge;

/**
 * Created by jpablo09 on 10/9/2018.
 */

class PendingRow{

    String tag;
    String email;
    String ID;



    PendingRow(String tag, String email, String ID)
    {
        this.tag = tag;
        this.email = email;
        this.ID = ID;


    }


}

public class PendingChallengeAdapter extends BaseAdapter {


    private ArrayList<PendingRow> list;

    private String userID, userEmail, userTag, userElo;
    private Context context;



    public PendingChallengeAdapter(Context c, ArrayList<Challenge> gamers)  {

        context = c;
        list = new ArrayList<>();

        for (int i = 0; i < gamers.size(); i++) {


            list.add(new PendingRow(gamers.get(i).getGamerTag(), gamers.get(i).getGamerEmail(), gamers.get(i).getGamerID()));


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
            row = inflater.inflate(R.layout.pending_challenge_view, parent, false);
            holder = new ViewHolder();
            holder.gamerTag =  row.findViewById(R.id.gamerTag);
            holder.accept =  row.findViewById(R.id.accept_challenge);
            holder.deny = row.findViewById(R.id.deny_challenge);



            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }


        final PendingRow temp = list.get(position);

        holder.gamerTag.setText(temp.tag);






        return row;

    }





    public class ViewHolder{


        public TextView gamerTag;
        public ImageView accept;
        public ImageView deny;


    }






}
