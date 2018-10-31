package utils.com.futpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class PagerRow{

    String starters;


    public PagerRow(String starters){

        this.starters = starters;


    }


}



public class PositionViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<PagerRow> lineup;




    public PositionViewPagerAdapter(Context context, String formation) {
        this.context = context;
        lineup = new ArrayList<>();

        int SIZE = 11;
        for (int i = 0; i < SIZE; i++) {

            if(formation.equals("3-1-4-2")){

                String[] form1 = {"ST", "ST", "LM", "CM", "CDM", "CM", "RM", "CB", "CB", "CB", "GK"};
                lineup.add(new PagerRow(form1[i]));
            }

            else if(formation.equals("3-4-1-2")){

                String[] form2 = {"ST", "ST", "CAM", "LM", "CM", "CM", "RM", "CB", "CB", "CB", "GK"};
                lineup.add(new PagerRow(form2[i]));
            }

            else if(formation.equals("3-4-2-1")){

                String[] form3 = {"ST", "LF", "RF", "LM", "CM", "CM", "RM", "CB", "CB", "CB", "GK"};
                lineup.add(new PagerRow(form3[i]));
            }

            else if(formation.equals("3-4-3")){

                String[] form4 = {"ST", "LW", "RW", "LM", "CM", "CM", "RM", "CB", "CB", "CB", "GK"};
                lineup.add(new PagerRow(form4[i]));
            }

            else if(formation.equals("3-5-2")){

                String[] form5 = {"ST", "ST", "CAM", "LM", "CDM", "CDM", "RM", "CB", "CB", "CB", "GK"};
                lineup.add(new PagerRow(form5[i]));
            }

            else if(formation.equals("4-1-2-1-2")){

                String[] form6 = {"ST", "ST", "CAM", "LM", "RM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form6[i]));
            }

            else if(formation.equals("4-1-2-1-2(2)")){

                String[] form7 = {"ST", "ST", "CAM", "CM", "CM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form7[i]));
            }

            else if(formation.equals("4-1-3-2")){

                String[] form8 = {"ST", "ST", "LM", "CM", "RM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form8[i]));
            }

            else if(formation.equals("4-1-4-1")){

                String[] form9 = {"ST", "LM", "CM", "CM", "RM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form9[i]));
            }

            else if(formation.equals("4-2-3-1")){

                String[] form10 = {"ST", "CAM", "CAM", "CAM", "CDM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form10[i]));
            }

            else if(formation.equals("4-2-3-1(2)")){

                String[] form11 = {"ST", "LM", "CAM", "RM", "CDM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form11[i]));
            }

            else if(formation.equals("4-2-2-2")){

                String[] form12 = {"ST", "ST", "CAM", "CAM", "CDM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form12[i]));
            }

            else if(formation.equals("4-2-4")){

                String[] form13 = {"ST", "ST", "LW", "RW", "CM", "CM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form13[i]));
            }

            else if(formation.equals("4-3-1-2")){

                String[] form14 = {"ST", "ST", "CAM", "CM", "CM", "CM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form14[i]));
            }

            else if(formation.equals("4-3-2-1")){

                String[] form15 = {"ST", "LF", "RF", "CM", "CM", "CM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form15[i]));
            }

            else if(formation.equals("4-3-3")) {


                String[] form16 = {"ST", "LW", "RW", "CAM", "LM", "RM", "CB", "CB", "LB", "RB", "GK"};
                lineup.add(new PagerRow(form16[i]));

            }

            else if(formation.equals("4-3-3(2)")) {


                String[] form17 = {"ST", "LW", "RW", "CM", "CDM", "CM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form17[i]));

            }

            else if(formation.equals("4-3-3(3)")) {


                String[] form17 = {"ST", "LW", "RW", "CDM", "CM", "CDM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form17[i]));

            }

            else if(formation.equals("4-3-3(4)")) {


                String[] form18 = {"ST", "LW", "RW", "CM", "CAM", "CM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form18[i]));

            }

            else if(formation.equals("4-3-3(5)")) {


                String[] form19 = {"LW", "CF", "RW", "CM", "CDM", "CM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form19[i]));

            }

            else if(formation.equals("4-4-1-1")) {


                String[] form20 = {"ST", "CF", "LM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form20[i]));

            }

            else if(formation.equals("4-4-1-1(2)")) {


                String[] form21 = {"ST", "CAM", "LM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form21[i]));

            }

            else if(formation.equals("4-4-2")) {


                String[] form22 = {"ST", "ST", "LM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form22[i]));

            }

            else if(formation.equals("4-4-2(2)")) {


                String[] form23 = {"ST", "ST", "LM", "CDM", "CDM", "RM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form23[i]));

            }

            else if(formation.equals("4-5-1")) {


                String[] form24 = {"ST", "CAM", "CAM", "LM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form24[i]));

            }

            else if(formation.equals("4-5-1(2)")) {


                String[] form25 = {"ST", "LM", "CM", "CM", "CM", "RM", "LB", "CB", "CB", "RB", "GK"};
                lineup.add(new PagerRow(form25[i]));

            }

            else if(formation.equals("5-2-1-2")) {


                String[] form26 = {"ST", "ST", "CAM", "CM", "CM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                lineup.add(new PagerRow(form26[i]));

            }

            else if(formation.equals("5-2-2-1")) {


                String[] form27 = {"ST", "LW", "RW", "CM", "CM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                lineup.add(new PagerRow(form27[i]));

            }

            else if(formation.equals("5-3-2")) {


                String[] form28 = {"ST", "ST", "CM", "CM", "CM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                lineup.add(new PagerRow(form28[i]));

            }

            else if(formation.equals("5-4-1")) {


                String[] form29 = {"ST", "LM", "CM", "CM", "RM", "LWB", "CB", "CB", "CB", "RWB", "GK"};
                lineup.add(new PagerRow(form29[i]));

            }

        }


    }

    @Override
    public int getCount() {
        return lineup.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        final PagerRow temp = lineup.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.position_pager_layout, null);

        final TextView textView = view.findViewById(R.id.position_text);
        textView.setText(temp.starters);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setTextColor(Color.GREEN);

                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("formationPosition", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("formPosition", temp.starters);
                editor.putInt("positionNumber", position);
                editor.apply();

                SharedPreferences atrributePreferences = v.getContext().getSharedPreferences("attributes", Context.MODE_PRIVATE);
                atrributePreferences.edit().clear().apply();



                Intent intent = new Intent(v.getContext(), TeamEditActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                v.getContext().startActivity(intent);




            }
        });





        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}