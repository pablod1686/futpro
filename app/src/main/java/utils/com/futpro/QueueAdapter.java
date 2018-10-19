package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import profile.Challenge;
import profile.ProfileData;
import profile.UserData;

/**
 * Created by jpablo09 on 10/1/2018.
 */

class QueueRow{

    String tag;
    String email;
    String ID;



    QueueRow(String tag, String email, String ID)
    {
        this.tag = tag;
        this.email = email;
        this.ID = ID;


    }


}

public class QueueAdapter extends BaseAdapter{


    private ArrayList<QueueRow> list;
    private ArrayList<PlayerCard> opponentData;
    private String userID, userEmail, userTag, userElo;
    private Context context;



    public QueueAdapter(Context c, ArrayList<PlayerCard> gamers, String userID, String userEmail, String userTag, String userElo)  {

        context = c;
        list = new ArrayList<>();
        this.opponentData = gamers;
        this.userID = userID;
        this.userEmail = userEmail;
        this.userTag = userTag;
        this.userElo = userElo;

        for (int i = 0; i < gamers.size(); i++) {


            list.add(new QueueRow(gamers.get(i).getGamerTag(), gamers.get(i).getGamerEmail(), gamers.get(i).getGamerID()));


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
            row = inflater.inflate(R.layout.queue_view, parent, false);
            holder = new ViewHolder();
            holder.gamerTag =  row.findViewById(R.id.gamerTag);
            holder.challenge =  row.findViewById(R.id.challenge);



            row.setTag(holder);
        } else {

            holder = (ViewHolder) row.getTag();
        }


        final QueueRow temp = list.get(position);

        holder.gamerTag.setText(temp.tag);


        holder.challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();
                final ArrayList<HashMap<String, String>> json = new ArrayList<>();
                final HashMap<String, String> map = new HashMap<>();
                Gson gson;
                String list;

                for(PlayerCard data: opponentData){

                    if(temp.tag.equals(data.getGamerTag())) {

                    Log.d("Mode", "Data: " + userID + "-" + userEmail + "-" + userTag + "-" + data.getGamerTag() + "-" + data.getMode());

                        map.put("id_1", userID);
                        map.put("em_1", userEmail);
                        map.put("tag_1", userTag);
                        //map.put("elo_1", userElo);
                        map.put("id_2", data.getGamerID());
                        map.put("em_2", data.getGamerEmail());
                        map.put("tag_2", data.getGamerTag());
                        //map.put("elo2", data.getElo());
                        map.put("mode", data.getMode());
                        map.put("stk", data.getStake());

                        json.add(map);
                        gson = new GsonBuilder().create();
                        list = gson.toJson(json);

                        //if(opponentData.size() == 1) {

                            params.put("challengeData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                            client.post("http://betlogic.co/FUTPRO/insert_challenge_data.php", params, new AsyncHttpResponseHandler());


                    }

                }


            }
        });



        return row;

    }





    public class ViewHolder{


        public TextView gamerTag;
        public TextView challenge;
        public TextView gamerForm;


    }






}
