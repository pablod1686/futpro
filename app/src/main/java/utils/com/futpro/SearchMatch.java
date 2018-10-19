package utils.com.futpro;


import android.content.Context;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import profile.ProfileData;

/**
 * Created by jpablo09 on 11/17/2017.
 */
public class SearchMatch {


    private HashMap<String, ArrayList<ProfileData>> teamRatings =  new HashMap<>();
    private ArrayList<ProfileData> teamsData =  new ArrayList<>();

    private ArrayList<String> grpLvlOne =  new ArrayList<>();
    private ArrayList<String> grpLvlTwo =  new ArrayList<>();
    private ArrayList<String> grpLvlThree = new ArrayList<>();
    private ArrayList<String> grpLvlFour =  new ArrayList<>();
    private ArrayList<String> grpLvlFive=  new ArrayList<>();
    private Random rand = new Random();
    public List<String> pair = new LinkedList<>();




    public SearchMatch(){


    }



    public void  pickNRandom() {


        List<String> random = grpLvlThree;

        Log.d("MYSQL", "grp :" + random);

        for (int i = 0; i < random.size(); i++) {

            if(random.get(i).equals("Milan FC")) {

                pair.add(random.get(i));

            }

        }

        //Log.d("MYSQL", "pairs :" + pair);

        int index = rand.nextInt(random.size());

        if(!random.get(index).equals("Milan FC")) {

            String item = random.get(index);
            pair.add(item);


            random.removeAll(pair);


        }else{

            String item = random.get(index + 1);
            pair.add(item);

            random.removeAll(pair);

        }


    }



    public void rankGroups(){

            for(int i = 0 ; i < teamsData.size(); i++) {

                if (Integer.parseInt(teamsData.get(i).getGamerLvl()) == 5) {

                    grpLvlFive.add(teamsData.get(i).getTeamName());
                }

                if (Integer.parseInt(teamsData.get(i).getGamerLvl()) >= 4) {

                    grpLvlFour.add(teamsData.get(i).getTeamName());
                }

                if (Integer.parseInt(teamsData.get(i).getGamerLvl()) >= 3 && Integer.parseInt(teamsData.get(i).getGamerLvl()) < 5) {

                    grpLvlThree.add(teamsData.get(i).getTeamName());

                }


                if (Integer.parseInt(teamsData.get(i).getGamerLvl()) >= 2 && Integer.parseInt(teamsData.get(i).getGamerLvl()) < 4) {

                    grpLvlTwo.add(teamsData.get(i).getTeamName());
                }

                if (Integer.parseInt(teamsData.get(i).getGamerLvl()) >= 1 && Integer.parseInt(teamsData.get(i).getGamerLvl()) < 3) {

                    grpLvlOne.add(teamsData.get(i).getTeamName());
                }

            }



    }






    public HashMap<String, ArrayList<ProfileData>> calculateTeamRating(ArrayList<PlayerCard> data){


        DecimalFormat df = new DecimalFormat("#");

        //HashMap<String, ArrayList<ProfileData>> teamRatings =  new HashMap<>();
        ArrayList<ProfileData> gamer_data =  new ArrayList<>();

        int rating ;
        String team = "";
        double weight3 = 25;
        double maxTeamRating = 100;
        double totalMatchPts;
        double win = 3;
        double draw = 1;
        double totalPts;
        double winPts;
        double drawPts;
        double avgWinPct ;
        int weight1 = 40;
        double weight2 = 35;
        double maxAI = 7;
        int level = 0;
        String lvl  ;
        double finalRating  ;
        String gamerID = "";
        String gamerEmail = "";
        String gamerTag = "";
        String playerRating = "";
        String console = "";
        String elo = "";

        for (int i = 0; i < data.size(); i++ ){


                gamerID = data.get(i).getGamerID();
                gamerEmail = data.get(i).getGamerEmail();
                gamerTag = data.get(i).getGamerTag();
                team = data.get(i).getTeamName();
                lvl = data.get(i).getLevelAI();
                rating = Integer.parseInt(data.get(i).getTeamRating());
                winPts = Integer.parseInt(data.get(i).getW()) * win;
                drawPts = Integer.parseInt(data.get(i).getD()) * draw;
                totalPts = winPts + drawPts;
                totalMatchPts = (Integer.parseInt(data.get(i).getW()) + Integer.parseInt(data.get(i).getD()) + Integer.parseInt(data.get(i).getL())) * 3;
                avgWinPct = totalPts /totalMatchPts;
                console = data.get(i).getConsole();
                elo = data.get(i).getElo();


            switch (lvl) {

                case "Ultimate":
                    level = 7;
                    break;
                case "Legendary":
                    level = 6;
                    break;
                case "World Class":
                    level = 5;
                    break;
                case "Professional":
                    level = 4;
                    break;
                case "Semi Pro":
                    level = 3;
                    break;
                case "Amateur":
                    level = 2;
                    break;
                case "Beginner":
                    level = 1;
                    break;
            }

            finalRating = (avgWinPct * weight1)  + ((level / maxAI) * weight3)  + ((rating /maxTeamRating) * weight2);

            Log.d("Rating", "Fifa Rating :" + team + "-" + (avgWinPct * weight1) + "-" + ((level / maxAI) * weight3)  + "-" + ((rating /maxTeamRating) * weight2));

            if (Integer.parseInt(df.format(finalRating)) >= 90) {

                playerRating = "Ultimate";

            } else if (Integer.parseInt(df.format(finalRating)) >= 80  &&Integer.parseInt(df.format(finalRating)) <= 90) {


                playerRating = "Legendary";

            } else if (Integer.parseInt(df.format(finalRating)) >= 70 &&Integer.parseInt(df.format(finalRating)) <= 80) {


                playerRating = "World Class";

            } else if (Integer.parseInt(df.format(finalRating)) >= 60 && Integer.parseInt(df.format(finalRating)) <= 70) {

                playerRating = "Pro";

            } else {

                playerRating = "Amateur'";

            }




            teamsData.add(new ProfileData(gamerID, gamerEmail, gamerTag, team, playerRating, console, elo));

        }

        //Log.d("MYSQL", "pre Mapping :" + team);

        gamer_data.add(new ProfileData(gamerID, gamerEmail, gamerTag, team, playerRating, console, elo));


        for (int j =0; j < gamer_data.size(); j ++) {


            if (gamer_data.get(j).getEmail().equals(gamerEmail)) {

                teamRatings.put(gamerEmail, gamer_data);


            }
        }

        //Log.d("MYSQL", "gamer Map :" + teamsData.size());


        return teamRatings;

    }



}
