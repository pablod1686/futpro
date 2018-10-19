package utils.com.futpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jpablo09 on 10/18/2018.
 */

class formationRow{

    String formation;

    public formationRow(String formation){

        this.formation = formation;
    }

}

public class FormationAdapter extends BaseAdapter {

    Context context;
    String[] formations = {"3-1-4-2", "3-4-1-2", "3-4-2-1", "3-4-3", "3-5-2", "4-1-2-1-2", "4-1-2-1-2(2)", "4-1-3-2", "4-1-4-1", "4-2-3-1", "4-2-3-1(2)", "4-2-2-2", "4-2-4", "4-3-1-2",
    "4-3-2-1", "4-3-3", "4-3-3(2)", "4-3-3(3)", "4-3-3(4)", "4-3-3(5)", "4-4-1-1", "4-4-1-1(2)", "4-4-2","4-4-2(2)", "4-5-1", "4-5-1(2)", "5-2-1-2", "5-2-2-1", "5-3-2", "5-4-1"};


    ArrayList<formationRow> formationList;

    public FormationAdapter(Context context){


        this.context = context;

        formationList = new ArrayList<>();

        for(int i = 0; i < formations.length; i++){

            formationList.add(new formationRow(formations[i]));

        }



    }


    @Override
    public int getCount() {
        return formationList.size();
    }

    @Override
    public Object getItem(int position) {
        return formationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.formation_view, parent, false);
            holder = new ViewHolder();
            holder.form = row.findViewById(R.id.formation_set);



            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }

        final formationRow temp = formationList.get(position);

        holder.form.setText(temp.formation);

        holder.form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("formation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("formationSet", temp.formation);
                editor.putInt("position", position);
                editor.apply();


                Intent intent = new Intent(v.getContext(), TeamEditActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                v.getContext().startActivity(intent);



            }
        });

        return row;
    }

    public class ViewHolder{


        public TextView form;


    }
}
