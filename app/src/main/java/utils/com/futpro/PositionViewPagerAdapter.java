package utils.com.futpro;

import android.content.Context;
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

            else if(formation.equals("4-3-3")) {


                String[] form16 = {"ST", "LW", "RW", "CAM", "LM", "RM", "CB", "CB", "LB", "RB", "GK"};
                lineup.add(new PagerRow(form16[i]));

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



        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.position_pager_layout, null);

        TextView textView = view.findViewById(R.id.position_text);
        textView.setText(lineup.get(position).starters);



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