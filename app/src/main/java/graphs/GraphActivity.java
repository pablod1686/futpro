package graphs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import utils.com.futpro.R;

/**
 * Created by jpablo09 on 9/22/2018.
 */

public class GraphActivity extends Activity{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_formation);

        RelativeLayout v =  findViewById(R.id.formation_view);
        FomationView myView = new FomationView(this);
        v.addView(myView);



    }

    public static class FomationView extends View{

        Paint paint = new Paint();
        Paint textPaint = new Paint();




        private TeamFormation[] vertices = {
                new TeamFormation("LW", 125, 50), new TeamFormation("ST", 320, 25), new TeamFormation("RW", 510, 50),
                new TeamFormation("LM,", 125, 150), new TeamFormation("CAM", 320, 125), new TeamFormation("RM", 510, 150),
                new TeamFormation("LB", 75, 250), new TeamFormation("RB", 555, 250),
                new TeamFormation("CB", 200, 275), new TeamFormation("CB", 435, 275),
                new TeamFormation("GK", 320, 300)
        };

        private int[][] edges = {
                {0,1},{0,3},
                {1,0},{1,2},{1,4},
                {2,1},{2,5},
                {3,4},{3,6},
                {5,4},{5,7},
                {8,9},{8,6},{8,3},
                {9,7},{9,5},
                {10,8},{10,9}

        };


        Bitmap bitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.frame );
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmap, 50, 75, false);



        private Graph<? extends  Displayable> graph;
        private Context context;



        public FomationView(Context context) {
            super(context);
            this.context = context;
            //setGraph(teamFormationGraph);

        }

        public void setGraph(Graph<? extends  Displayable> graph){

            this.graph = graph;
        }

        @SuppressLint("DrawAllocation")
        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            paint.setColor(Color.parseColor("#ffff00"));
            paint.setStrokeWidth(6);

            //ColorFilter filter = new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.color), PorterDuff.Mode.SRC_IN);
            //textPaint.setColorFilter(filter);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(20);
            textPaint.setStrokeWidth(5);


            List<? extends  Displayable> vertices = graph.getVertices();

            for(int i = 0 ; i < graph.getSize(); i++){
                List<Integer> neighbors = graph.getNeighbors(i);
                int x1 = graph.getVertex(i).getX();
                int y1 = graph.getVertex(i).getY();
                for (int v: neighbors){
                    int x2 = graph.getVertex(v).getX();
                    int y2 = graph.getVertex(v).getY();


                    //canvas.drawLine(x1,y1,x2,y2,textPaint);
                }

            }

            for (int i = 0; i < graph.getSize(); i++){
                int x = vertices.get(i).getX();
                int y = vertices.get(i).getY();
                //String name = vertices.get(i).getName();
                //canvas.drawBitmap(resizedBitmap,x-25, y-3, paint);
                canvas.drawCircle(x ,y + 10,15,paint);
                //canvas.drawText(name, x - 15,  y -25, textPaint);


            }



        }

        //private Graph<TeamFormation> teamFormationGraph = new UnweightedGraph<>(edges, vertices);





    }


    static  class TeamFormation implements Displayable{

        private int x, y;
        private String name;

        TeamFormation(String name, int x, int y){
            this.name = name;
            this.x = x;
            this.y = y;

        }

        @Override
        public int getX(){
            return x;
        }

        @Override
        public int getY(){
            return y;
        }

        @Override
        public String getName() {
            return name;
        }
    }

}
