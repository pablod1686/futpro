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
    private String modeType;
    private String modeStake;

    public Challenge(String pairKey, String gamerID, String gamerEmail, String gamerTag, String opponentID, String opponentEmail, String opponentTag, String modeType, String modeStake){

        this.pairKey = pairKey;
        this.gamerID = gamerID;
        this.gamerEmail = gamerEmail;
        this.gamerTag = gamerTag;
        this.opponentID =  opponentID;
        this.opponentEmail =  opponentEmail;
        this.opponentTag = opponentTag;
        this.modeType =  modeType;
        this.modeStake =  modeStake;

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

}
