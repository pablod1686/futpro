package custom.adapters;

/**
 * Created by jpablo09 on 3/24/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.AbstractList;
import java.util.ArrayList;

import circle.ResizeImage;
import custom.menu.lists.Modes;

import utils.com.futpro.R;


class ModeRow{

    String leagues;
    Integer logo;

    ModeRow(String leagues, Integer logo)
    {
        this.leagues = leagues;
        this.logo = logo;

    }
}

public class ModeAdapter extends BaseAdapter {

    private ResizeImage resizeImage = new ResizeImage();
    private ArrayList<ModeRow> list;
    private Context context;

    public ModeAdapter(Context c) {

        context = c;
        list = new ArrayList<>();
        Modes modes = new Modes();

        for (int i =0 ; i < modes.leagues.size(); i ++){

            list.add(new ModeRow(modes.leagues.get(i), modes.images.get(i)));
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        ViewHolder holder;
        if (convertView == null) {

            row = inflater.inflate(R.layout.modes_layout, parent, false);
            holder = new ViewHolder();
            holder.league = row.findViewById(R.id.card_text);
            holder.imageView =  row.findViewById(R.id.img);
            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }


        ModeRow temp = list.get(position);
        holder.league.setText(temp.leagues);
        holder.imageView.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(context.getResources(),temp.logo, 50, 50));


        return row;

    }

        public class ViewHolder{

            public TextView league;
            public ImageView imageView;



        }


}










