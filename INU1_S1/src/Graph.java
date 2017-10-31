import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Graph<V, E> {
    private Map<V, Vertex<V, E>> vertexMap;
    private V initPoint;

    public Graph() {
        vertexMap = new HashMap<V, Vertex<V, E>>();
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (V v : vertexMap.keySet()) {
            s.append("\n    " + v);
            s.append("------------------------------- \n");
            s.append("------------------------------- \n");
            s.append("----NASLEDNICI---\n");
            s.append("\n" + "-> " + vertexMap.get(v).listSuccesors + "\n");
            s.append("<------------------------------->\n");
            s.append("\n");

        }

        return s.toString();
    }
    
    public void addVertex(V vertex) {
        if (vertexMap.containsKey(vertex))
            return;
        if (vertexMap.isEmpty()) {
            initPoint = vertex;
        }
        vertexMap.put(vertex, new Vertex<V, E>(vertex));
    }

    public boolean contains(V vertex) {
        return vertexMap.containsKey(vertex);
    }


    public void addEdges(V from, V to, E action) {
        this.addVertex(from);
        this.addVertex(to);
        vertexMap.get(from).listSuccesors.add(new Edges<E>(action, vertexMap.get(to)));
        if (vertexMap.get(to).parrent == null) {
            if (vertexMap.get(to).data != initPoint) {
                vertexMap.get(to).parrent = vertexMap.get(from);
                vertexMap.get(to).parrentAction = action;
            }

        }

    }

    public V findVertex(V key) {
        if (this.contains(key)) {
            throw new NullPointerException("Node is not exist");
        }
        return vertexMap.get(key).data;
    }

    private class Vertex<V, E> {
        private final V data;
        private final List<Edges<E>> listSuccesors;
        private Vertex<V, E> parrent;
        private E parrentAction;

        public Vertex(V data) {
            this.data = data;
            this.listSuccesors = new LinkedList<>();
        }

        public void setParrentAction(E action) {
            this.parrentAction = action;
        }

        @Override
        public String toString() {
            return this.data.toString();
        }

    }

    public List<V> getSuccessors(V key) {
        if (this.contains(key)) {
            throw new NullPointerException("Node is not exist");
        }
        Vertex<V, E> vertex = vertexMap.get(key);
        List<V> list = new ArrayList<>();
        for (Edges<E> e : vertex.listSuccesors) {
            list.add(e.destination.data);
        }
        return list;
    }

    public Stack<Crate<V, E>> getPredecessors(V key) {
        Vertex<V, E> vertex = vertexMap.get(key);
        Stack<Crate<V, E>> list = new Stack<>();
        while (vertex != null) {
            list.push(new Crate<V, E>(vertex.data, vertex.parrentAction));
            vertex = vertex.parrent;
        }
        return list;
    }


    private class Edges<E> {
        private final Vertex<V, E> destination;
        private final E action;

        public Edges(E action, Vertex<V, E> dest) {
            this.action = action;
            this.destination = dest;
        }

        @Override
        public String toString() {
            return destination.toString() + "ACTION: " + action.toString() + "\n" + "\n";
        }

    }



}
