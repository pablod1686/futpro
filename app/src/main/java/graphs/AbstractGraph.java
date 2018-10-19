package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jpablo09 on 9/21/2018.
 */

public abstract class AbstractGraph<V> implements Graph<V> {

    protected List<V> vertices = new ArrayList<>();
    protected List<List<Integer>> neighbros = new ArrayList<>();

    protected AbstractGraph(){


    }

    protected AbstractGraph(int[][] edges, V[] vertices){


        for(int i = 0; i < vertices.length; i++)
            this.vertices.add(vertices[i]);

        createAdjancyLists(edges, vertices.length);

    }

    protected AbstractGraph(List<Edge> edges, List<V> vertices){
        for(int i = 0; i < vertices.size(); i++)
            this.vertices.add(vertices.get(i));

        createAdjancyLists(edges, vertices.size());
    }

    protected AbstractGraph(List<Edge> edges, int numberOfVertices){
        for(int i = 0; i < numberOfVertices; i++)
            vertices.add((V) (new Integer(i)));


        createAdjancyLists(edges, numberOfVertices);
    }

    protected AbstractGraph(int[][] edges, int numberOfVertices){
        for(int i = 0; i < numberOfVertices; i++)
            vertices.add((V) (new Integer(i)));

        createAdjancyLists(edges, numberOfVertices);
    }

    public void createAdjancyLists(int[][] edges, int numberOfVertices){

        for(int i = 0; i < numberOfVertices; i++){

            neighbros.add(new ArrayList<Integer>());

        }
        for(int i = 0; i < edges.length; i++){

            int u = edges[i][0];
            int v = edges[i][1];
            neighbros.get(u).add(v);

        }
    }


    public void createAdjancyLists(List<Edge> edges, int numberOfVertices){

        for(int i = 0; i < numberOfVertices; i++){

            neighbros.add(new ArrayList<Integer>());

        }

        for(Edge edge: edges){
            neighbros.get(edge.u).add(edge.v);

        }
    }

    @Override
    public int getSize(){
        return vertices.size();
    }

    @Override
    public List<V> getVertices(){
        return vertices;
    }

    @Override
    public V getVertex(int index){
        return vertices.get(index);

    }

    @Override
    public int getIndex(V v){
        return vertices.indexOf(v);
    }

    @Override
    public List<Integer> getNeighbors(int index){
        return neighbros.get(index);
    }

    @Override
    public int getDegree(int v){
        return neighbros.get(v).size();
    }

   @Override
   public  void clear(){
        vertices.clear();
        neighbros.clear();
   }

   @Override
   public void addVertex(V vertex){
       vertices.add(vertex);
       neighbros.add(new ArrayList<Integer>());

   }

   @Override
   public void addEdge(int u, int v){
       neighbros.get(u).add(v);
       neighbros.get(v).add(u);
   }


    public static class Edge{

        public int u;
        public int v;

        public Edge(int u, int v){

            this.u = u;
            this.v = v;

        }

    }


    @Override
    public Tree dfs(int v){
       List<Integer> searchOrder = new ArrayList<>();
       int[] parent = new int[vertices.size()];
       for(int i = 0; i  < parent.length; i++)
           parent[i] = -1;

       boolean[] isVisited =  new boolean[vertices.size()];

       dfs(v, parent, searchOrder, isVisited);

        return new Tree(v, parent, searchOrder);
    }

    private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited){

        searchOrder.add(v);
        isVisited[v] = true;

        for (int i: neighbros.get(v)){

            if(!isVisited[i]){

                parent[i] = v;
                dfs(i, parent, searchOrder, isVisited);
            }
        }
    }


    @Override
    public Tree bfs(int v){
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for(int i = 0; i  < parent.length; i++)
            parent[i] = -1;

        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] isVisited =  new boolean[vertices.size()];
        queue.offer(v);
        isVisited[v] = true;

        while (!queue.isEmpty()){
            int u = queue.poll();
            searchOrder.add(u);
            for (int w: neighbros.get(u)){
                if(!isVisited[w]){
                    queue.offer(w);
                    parent[w] = u;
                    isVisited[w] = true;
                }
            }

        }

        return new Tree(v,parent,searchOrder);
    }




    public class Tree{

       private int root;
       private int[] parent;
       private List<Integer> searchOrder;


       public Tree(int root, int[] parent, List<Integer> searchOrder){

           this.root = root;
           this.parent = parent;
           this.searchOrder = searchOrder;

       }

       public int getRoot(){

           return root;
       }

       public int[] getParent(){

           return parent;
       }

       public List<Integer> getSearchOrder(){

           return searchOrder;
       }

       public int getNumberOfVerticesFound(){

           return searchOrder.size();
       }


       public List<V> getPath(int index){
           ArrayList<V> path = new ArrayList<>();

           do{
               path.add(vertices.get(index));
               index = parent[index];
           }while (index != -1);

           return path;
       }



    }



}
