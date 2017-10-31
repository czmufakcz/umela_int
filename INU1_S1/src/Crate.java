
public class Crate<V, E> {
    private final V data;
    private final E action;

    public Crate(V vertex, E action) {
        this.data = vertex;
        this.action = action;
    }

    public V getData() {
        return data;
    }

    public E getAction() {
        return action;
    }

    @Override
    public String toString() {
        String stringAction = action == null ? "null" : action.toString();
        return "Action: " + stringAction + "\n" + data.toString();
    }
}
