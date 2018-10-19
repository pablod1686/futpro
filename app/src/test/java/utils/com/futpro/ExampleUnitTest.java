package utils.com.futpro;

import java.util.ArrayList;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {


    public static ArrayList<PlayerCard> card(){

        OpponentData opponent = new OpponentData();

        return opponent.playerCards;
    }

    public static void main(String[] args){


        System.out.println(card());


    }

}