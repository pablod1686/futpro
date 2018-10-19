package utils.com.futpro;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import circle.ResizeImage;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private Integer [] images = {R.drawable.jr13,R.drawable.mbappe,R.drawable.messi};
    private String [] txt = {"$5 Entry", "$10 Entry", "$20 Entry"};
    private String [] mode = {"Single Match", "Best of Three", "League Mode"};
    private ResizeImage resizeImage = new ResizeImage();
    private ArrayList<PlayerCard> userLobbyData;


    public ViewPagerAdapter(Context context, ArrayList<PlayerCard> data) {
        this.context = context;
        this.userLobbyData = data;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {



        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView =  view.findViewById(R.id.imageView);
        imageView.setImageBitmap(resizeImage.decodeSampledBitmapFromResource(context.getResources(),images[position], imageView.getMaxWidth(),imageView.getMaxHeight()));

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(txt[position]);
        TextView textViewII = view.findViewById(R.id.textViewTwo);
        textViewII.setText(mode[position]);


        TextView custom = view.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                final AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();
                final ArrayList<HashMap<String, String>> json = new ArrayList<>();
                final HashMap<String, String> map = new HashMap<>();
                Gson gson;
                String list;

                for(PlayerCard data: userLobbyData) {

                    switch (position) {

                        case 0:

                            map.put("id", data.getGamerID());
                            map.put("email", data.getGamerEmail());
                            map.put("tag", data.getGamerTag());
                            map.put("date", formatter.format(now));
                            map.put("mode", "Single Match");
                            map.put("stake", "5");
                            map.put("team", data.getTeamName());
                            map.put("rating", data.getGamerLvl());
                            map.put("console", data.getConsole());
                            map.put("elo", data.getElo());


                            json.add(map);
                            gson = new GsonBuilder().create();
                            list = gson.toJson(json);


                            params.put("lobbyData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                            client.post("http://betlogic.co/FUTPRO/insert_lobby_data.php", params, new AsyncHttpResponseHandler());

                            String id;
                            String email;
                            String tag;
                            String elo;
                            String mode = "Single Match";
                            String stk = "5";
                            Intent queue =  new Intent(v.getContext(), QueueActivity.class);

                            id = data.getGamerID();
                            email = data.getGamerEmail();
                            tag = data.getGamerTag();
                            elo = data.getElo();

                            queue.putExtra("ID", id);
                            queue.putExtra("Email", email);
                            queue.putExtra("Tag", tag);
                            queue.putExtra("Elo", elo);
                            queue.putExtra("Mode", mode);
                            queue.putExtra("Stk", stk);

                            v.getContext().startActivity(queue);

                            break;

                        case 1:

                            map.put("id", data.getGamerID());
                            map.put("email", data.getGamerEmail());
                            map.put("tag", data.getGamerTag());
                            map.put("date", formatter.format(now));
                            map.put("mode", "Best of Three");
                            map.put("stake", "10");
                            map.put("team", data.getTeamName());
                            map.put("rating", data.getGamerLvl());
                            map.put("console", data.getConsole());
                            map.put("elo", data.getElo());


                            json.add(map);
                            gson = new GsonBuilder().create();
                            list = gson.toJson(json);


                            params.put("lobbyData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                            client.post("http://betlogic.co/FUTPRO/insert_lobby_data.php", params, new AsyncHttpResponseHandler());

                            break;

                        case 2:

                            map.put("id", data.getGamerID());
                            map.put("email", data.getGamerEmail());
                            map.put("tag", data.getGamerTag());
                            map.put("date", formatter.format(now));
                            map.put("mode", "League Mode");
                            map.put("stake", "20");
                            map.put("team", data.getTeamName());
                            map.put("rating", data.getGamerLvl());
                            map.put("console", data.getConsole());
                            map.put("elo", data.getElo());


                            json.add(map);
                            gson = new GsonBuilder().create();
                            list = gson.toJson(json);


                            params.put("lobbyData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                            client.post("http://betlogic.co/FUTPRO/insert_lobby_data.php", params, new AsyncHttpResponseHandler());

                            break;


                    }

                }


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