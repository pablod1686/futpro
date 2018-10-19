package graphs;

import java.util.List;

/**
 * Created by jpablo09 on 9/22/2018.
 */

public class UnweightedGraph<V> extends AbstractGraph<V>{

    public UnweightedGraph(){

    }

    public UnweightedGraph(int[][] edges, V[] vertices){
        super(edges, vertices);
    }

    public UnweightedGraph(List<Edge> edges, List<V> vertices){
        super(edges, vertices);
    }

    public UnweightedGraph(List<Edge> edges, int numberOfVertices){
        super(edges, numberOfVertices);
    }

    public UnweightedGraph(int[][] edges, int numberOfVertices){
        super(edges, numberOfVertices);
    }

    @Override
    public void getEdges() {

    }
}
