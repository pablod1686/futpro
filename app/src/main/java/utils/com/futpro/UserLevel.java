package utils.com.futpro;

import java.util.Comparator;

/**
 * Created by jpablo09 on 8/8/2018.
 */



public class UserLevel {

    private int eloRating;
    private String gamerTag;


    public UserLevel(String gamerTag){

        this.gamerTag = gamerTag;

    }

    public UserLevel(int eloRatingP, String gamerTag){

        super();
        this.eloRating = eloRatingP;
        this.gamerTag = gamerTag;

    }

    public int getEloRating(){

        return eloRating;

    }

    public String getGamerTag(){

        return gamerTag;

    }

}

class MyComparator implements Comparator<UserLevel> {
    public int compare(UserLevel a, UserLevel b) {
        Integer aLvl, bLvl;

        aLvl = a.getEloRating();
        bLvl = b.getEloRating();

        return bLvl.compareTo(aLvl);
    }
    // No need to override equals.
}



