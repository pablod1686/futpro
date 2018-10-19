package utils.com.futpro;

import java.text.DecimalFormat;

/**
 * Created by jpablo09 on 12/17/2017.
 */

public class ELORating {

    /*
    K-factor is basically a measure of how strong a match will impact the players’ ratings.
    called the K-factor and basically a measure of how strong a match will impact the players’ ratings.
    If you set K too low the ratings will hardly be impacted by the matches and very stable ratings (too stable) will occur.
    On the other hand, if you set it too high, the ratings will fluctuate wildly according to the current performance.

        Players below 2100: K-factor of 32 used
        Players between 2100 and 2400: K-factor of 24 used
        Players above 2400: K-factor of 16 used.

     */

    private final int K_FACTOR_ONE = 32;
    private final int K_FACTOR_TWO = 24;
    private final int K_FACTOR_THREE = 16;

    private final int WIN = 1;
    private final int LOSS = 0;



    public ELORating(){


    }



    public double rRating(int rank){


        double r = rank / 400;

        r = Math.pow(10, r);

        return r;


    }


    public double eRating(double p1, double p2){


        double e;

        e = p1 / (p1 + p2);

        return e;

    }

    public double sRating(int status){


        double p1 = rRating(2400);
        double p2 = rRating(2000);


        double player;

        player = eRating(p1, p2);

        double sRating;

        sRating = status - player;

        return sRating;
    }


    //find final ELO-Rating

    public double pRating(int rank, int status){

        double s = sRating(status);
        double r1;

        r1 = rank + (K_FACTOR_ONE * s);

        return r1;


    }


}
