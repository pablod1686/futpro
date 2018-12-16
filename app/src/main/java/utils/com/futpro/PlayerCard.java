package utils.com.futpro;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by jpablo09 on 10/30/2017.
 */
class PlayerCard {

    private String gamerID;
    private String gamerEmail;
    private String gamerTag;
    private String lobbyDate;
    private String mode;
    private String stake;
    private String teamName;
    private String w;
    private String d;
    private String l;
    private String teamRating;
    private String levelAI;
    private String console;
    private String gamerLvl;
    private String elo;
    private String search;


    public  PlayerCard(){


    }

    public PlayerCard(String gamerTag) {

        this.gamerTag = gamerTag;

    }

    public PlayerCard(String elo,String gamerTag) {


        this.elo = elo;
        this.gamerTag = gamerTag;

    }


    public PlayerCard(String team, String gamerTag, String rating, String console) {

        this.teamName = team;
        this.gamerTag = gamerTag;
        this.gamerLvl = rating;
        this.console = console;

    }

    public PlayerCard(String email, String team, String gamerTag, String rating, String console) {

        this.gamerEmail = email;
        this.teamName = team;
        this.gamerTag = gamerTag;
        this.gamerLvl = rating;
        this.console = console;

    }



    public PlayerCard(String team, String win, String draw, String loss, String rating, String level) {

        this.teamName = team;
        this.w = win;
        this.d = draw;
        this.l = loss;
        this.teamRating = rating;
        this.levelAI = level;

    }

    public PlayerCard(String id, String email, String team, String gamerTag, String rating, String console, String elo) {

        this.gamerID = id;
        this.gamerEmail = email;
        this.teamName = team;
        this.gamerTag = gamerTag;
        this.gamerLvl = rating;
        this.console = console;
        this.elo = elo;

    }






    public PlayerCard(String email, String gamerTag, String date, String modeType, String modeStk, String team, String level, String console, String elo, String search) {


        this.gamerEmail = email;
        this.gamerTag = gamerTag;
        this.lobbyDate = date;
        this.mode = modeType;
        this.stake = modeStk;
        this.teamName = team;
        this.gamerLvl = level;
        this.console = console;
        this.elo = elo;
        this.search = search;

    }


    public PlayerCard(String id, String email, String gamerTag,String team, String win, String draw, String loss, String rating, String level, String console, String elo) {

        this.gamerID = id;
        this.gamerEmail = email;
        this.gamerTag = gamerTag;
        this.teamName = team;
        this.w = win;
        this.d = draw;
        this.l = loss;
        this.teamRating = rating;
        this.levelAI = level;
        this.console = console;
        this.elo = elo;

    }


    public String getGamerID(){

        return gamerID;

    }

    public String getGamerEmail(){

        return gamerEmail;
    }


    public String getGamerTag(){

        return gamerTag;

    }

    public String getMode(){

        return mode;

    }

    public String getStake(){

        return stake;

    }

    public String getGamerLvl(){

        return gamerLvl;

    }

    public String getElo(){

        return elo;

    }


    public String getTeamName(){

        return teamName;
    }



    public String getW(){

        return w;
    }



    public String getD(){

        return d;
    }



    public String getL(){

        return l;
    }



    public String getTeamRating(){

        return teamRating;
    }



    public String getLevelAI(){

        return levelAI;
    }




    public String getConsole(){

        return console;

    }


    public String getLobbyDate() {
        return lobbyDate;
    }

    public String getSearch() {
        return search;
    }
}

class MyComparator implements Comparator<PlayerCard> {
    public int compare(PlayerCard a, PlayerCard b) {
        Integer aLvl, bLvl;

        aLvl = Integer.parseInt(a.getElo());
        bLvl = Integer.parseInt(b.getElo());

        return bLvl.compareTo(aLvl);
    }
    // No need to override equals.
}
