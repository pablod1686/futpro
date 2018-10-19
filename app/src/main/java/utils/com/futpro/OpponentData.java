package utils.com.futpro;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by jpablo09 on 10/30/2017.
 */
public class OpponentData {

    public ArrayList<PlayerCard> playerCards = new ArrayList<>();



    public OpponentData(){

        initCard();

        //Log.d("MYSQL", "gamer Data :" + copy.get(0).getGamerTag());

    }

   private void initCard(){

        addPlayerCard("Verdolaga FC", "80", "30", "30", "87", "World Class");
        addPlayerCard("FutStarz FC", "75", "36", "35", "89", "Ultimate");
        addPlayerCard("TeamSkkkkkr FC", "29", "12", "19", "84", "Legendary");
        addPlayerCard("Milan FC", "75", "36", "35", "89", "Ultimate");
        addPlayerCard("Real Madrid", "75", "36", "35", "89", "Ultimate");
        addPlayerCard("Barcelona FC", "75", "36", "35", "89", "Ultimate");
        addPlayerCard("Chelsea FC", "75", "36", "35", "89", "Ultimate");

    }

    private void addPlayerCard(String team, String win, String draw,  String loss, String rating, String level){


        playerCards.add(new PlayerCard(team, win, draw, loss, rating, level));

    }





}
