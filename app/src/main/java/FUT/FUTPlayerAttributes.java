package FUT;

/**
 * Created by jpablo09 on 10/15/2018.
 */

public class FUTPlayerAttributes {

    private String plauerName;
    private String playerRtg;
    private String playerPOS;
    private String playerClub;
    private String playerLeague;
    private String playerNation;
    private String pace;
    private String shot;
    private String pass;
    private String dribble;
    private String defense;
    private String physical;
    private String weakFoot;
    private String skill;

    public FUTPlayerAttributes(String plauerName, String playerRtg, String playerPOS){

        this.plauerName = plauerName;
        this.playerRtg = playerRtg;
        this.playerPOS = playerPOS;



    }

    public FUTPlayerAttributes(String plauerName, String playerRtg, String playerPOS, String playerClub, String playerLeague, String playerNation, String pace,
                               String shot, String pass, String dribble, String defense, String physical, String weakFoot, String skill){

        this.plauerName = plauerName;
        this.playerRtg = playerRtg;
        this.playerPOS = playerPOS;
        this.playerClub =  playerClub;
        this.playerLeague = playerLeague;
        this.playerNation = playerNation;
        this.pace = pace;
        this.shot = shot;
        this.pass = pass;
        this.dribble = dribble;
        this.defense = defense;
        this.physical = physical;
        this.weakFoot = weakFoot;
        this.skill = skill;


    }


    public String getPlauerName(){
        return plauerName;
    }


    public String getPlayerRtg() {
        return playerRtg;
    }

    public String getPlayerPOS() {
        return playerPOS;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public String getPlayerLeague() {
        return playerLeague;
    }

    public String getPlayerNation() {
        return playerNation;
    }

    public String getPace() {
        return pace;
    }

    public String getShot() {
        return shot;
    }

    public String getPass() {
        return pass;
    }

    public String getDribble() {
        return dribble;
    }

    public String getDefense() {
        return defense;
    }

    public String getPhysical() {
        return physical;
    }

    public String getWeakFoot() {
        return weakFoot;
    }

    public String getSkill() {
        return skill;
    }


}
