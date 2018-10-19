package custom.menu.lists;



import java.util.ArrayList;

import utils.com.futpro.R;

/**
 * Created by jpablo09 on 7/21/2016.
 */
public class Modes {

    public ArrayList<String> leagues = new ArrayList<>();
    public ArrayList<Integer> images = new ArrayList<>();

    public Modes(){

        initLeagues();
        initLogos();

    }

    private void initLeagues(){

        leagues.add("BEST OF ONE");
        leagues.add("BEST OF THREE");
        leagues.add("BEST OF FIVE");
        leagues.add("6 MATCHES");
        leagues.add("7 MATCHES");
        leagues.add("8 MATCHES");


    }

    private void initLogos(){

        images.add(R.drawable.rey);
        images.add(R.drawable.rey);
        images.add(R.drawable.rey);
        images.add(R.drawable.rey);
        images.add(R.drawable.rey);
        images.add(R.drawable.rey);

    }

}
