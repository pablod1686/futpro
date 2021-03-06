package profile;

/**
 * Created by jpablo09 on 10/8/2018.
 */

public class Challenge {

    private String pairKey;
    private String gamerID;
    private String gamerEmail;
    private String gamerTag;
    private String opponentID;
    private String opponentEmail;
    private String opponentTag;
    private String opponentTeam;
    private String modeType;
    private String modeStake;
    private String pTag;
    private String pStatus;

    public Challenge(String pairKey, String gamerID, String gamerEmail, String gamerTag, String opponentID, String opponentEmail, String opponentTag, String opponentTeam, String modeType, String modeStake){

        this.pairKey = pairKey;
        this.gamerID = gamerID;
        this.gamerEmail = gamerEmail;
        this.gamerTag = gamerTag;
        this.opponentID =  opponentID;
        this.opponentEmail =  opponentEmail;
        this.opponentTag = opponentTag;
        this.opponentTeam = opponentTeam;
        this.modeType =  modeType;
        this.modeStake =  modeStake;

    }

    public Challenge(String pairKey, String gamerID, String gamerEmail, String gamerTag, String opponentID, String opponentEmail, String opponentTag, String opponentTeam, String modeType, String modeStake, String pTag, String pStatus){

        this.pairKey = pairKey;
        this.gamerID = gamerID;
        this.gamerEmail = gamerEmail;
        this.gamerTag = gamerTag;
        this.opponentID =  opponentID;
        this.opponentEmail =  opponentEmail;
        this.opponentTag = opponentTag;
        this.opponentTeam = opponentTeam;
        this.modeType =  modeType;
        this.modeStake =  modeStake;
        this.pTag = pTag;
        this.pStatus = pStatus;

    }

    public String getPairKey(){

        return pairKey;
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

    public String getOpponentID(){

        return opponentID;
    }

    public String getOpponentEmail(){

        return opponentEmail;
    }

    public String getOpponentTag(){

        return opponentTag;
    }

    public String getModeType(){

        return modeType;
    }

    public String getModeStake(){

        return modeStake;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }


    public String getpStatus() {
        return pStatus;
    }

    public String getpTag() {
        return pTag;
    }
}
