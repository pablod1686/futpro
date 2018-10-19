package graphs;

import java.util.List;

/**
 * Created by jpablo09 on 9/21/2018.
 */

public interface Graph<V> {

    public int getSize();

    public List<V> getVertices();

    public V getVertex(int index);

    public int getIndex(V v);

    public List<Integer> getNeighbors(int index);

    public int getDegree(int v);

    public void getEdges();

    public void clear();

    public void addVertex(V vertex);

    public void addEdge(int u, int v);

    public AbstractGraph<V>.Tree dfs(int v);

    public AbstractGraph<V>.Tree bfs(int v);


}
