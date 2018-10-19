package profile;

/**
 * Created by jpablo09 on 12/27/2017.
 */

public class ProfileData {

    private String gamerID;
    private String email;
    private String gamerTag;
    private String teamName;
    private String console;
    private String gamerLvl;
    private String eloRating;

    public  ProfileData(){


    }

    public  ProfileData(String  teamName){

        this.teamName = teamName;

    }

    public ProfileData(String ID, String email, String gamerTag,  String  teamName, String level, String console, String eloRating) {

        this.gamerID = ID;
        this.email = email;
        this.gamerTag = gamerTag;
        this.teamName = teamName;
        this.gamerLvl = level;
        this.console = console;
        this.eloRating = eloRating;
    }


    public String getID(){

        return gamerID;
    }

    public String getEmail(){

        return email;
    }


    public String getGamerTag(){

        return gamerTag;
    }


    public String getConsole(){

        return console;

    }

    public String getGamerLvl(){

        return gamerLvl;
    }

    public String getTeamName(){

        return teamName;
    }

    public String getEloRating(){

        return eloRating;
    }



}
