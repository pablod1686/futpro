package utils.com.futpro;


import java.util.ArrayList;

import java.util.Random;
import java.util.TreeSet;



/**
 * Created by jpablo09 on 10/31/2017.
 */




public class TestFile {


    public static void main(String[] args) {


       //OpponentData data = new OpponentData();

       //System.out.println(data.playerCards.get(0).getGamerTag());

        //long startTime = System.currentTimeMillis();



        UserLevel u1 = new UserLevel(100, "Juan");
        UserLevel u2 = new UserLevel(150, "Emmanuel");
        UserLevel u3 = new UserLevel(200, "Jose");
        UserLevel u4 = new UserLevel(102, "Diego");
        UserLevel u5 = new UserLevel(99, "Jhon");
        UserLevel u6 = new UserLevel(89, "Julio");
        UserLevel u7 = new UserLevel(75, "Sergio");
        UserLevel u8 = new UserLevel(110, "David");


        ArrayList<UserLevel> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        list.add(u6);
        list.add(u7);
        list.add(u8);

        ArrayList<UserLevel> lobby = new ArrayList<>();



        TreeSet<UserLevel> tree = new TreeSet<>(new MyComparator());
        tree.add(u1);
        tree.add(u2);
        tree.add(u3);
        tree.add(u4);
        tree.add(u5);
        tree.add(u6);
        tree.add(u7);
        tree.add(u8);



        //System.out.println(tree);

        for (UserLevel aTree : tree) {

            if(aTree.getEloRating() >= list.get(0).getEloRating() - 10 && aTree.getEloRating() <= list.get(0).getEloRating() + 10
                    && !aTree.getGamerTag().equals(list.get(0).getGamerTag())){

                    lobby.add(new UserLevel(aTree.getEloRating(), aTree.getGamerTag()));

            }

        }



        ArrayList<UserLevel> matchUp = new ArrayList<>();
        Random rand = new Random();
        int pick = rand.nextInt(lobby.size());
        matchUp.add(new UserLevel(list.get(0).getGamerTag()));
        matchUp.add(new UserLevel(lobby.get(pick).getGamerTag()));




       for(int i = 0; i < list.size(); i++){

           String gamer = list.get(i).getGamerTag();

           for(int x = 0; x < matchUp.size(); x++){

               if (matchUp.get(x).getGamerTag().equals(gamer)){

                   list.remove(i);

               }

           }

       }



        for(int i = 0; i < matchUp.size(); i++) {

            //System.out.println(matchUp.get(i).getGamerTag());

        }

        System.out.println();

        for(int i = 0; i < list.size(); i++) {

            //System.out.println(list.get(i).getGamerTag());

        }

        //System.out.println();
        //System.out.println(matchUp.get(0).getGamerTag());
        //System.out.println(matchUp.get(1).getGamerTag());

        //long stopTime = System.currentTimeMillis();
        //long elapsedTime = stopTime - startTime;
        //System.out.println(elapsedTime);






    }

}
