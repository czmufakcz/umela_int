import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class StateSpace {
    private final PriorityQueue<State> queueOpen;
    private final Graph<State, MOVE> graph;
    private final HashSet<State> arrayClose;
    private State result;
    private int countPass = 1;
    private boolean founded = false;

    public StateSpace() {
        queueOpen = new PriorityQueue<State>();
        graph = new Graph<>();
        arrayClose = new HashSet<>();
    }

    public void setDefaultArray(int[][] array, int blankPointX, int blankPointY) {
        State state = new State(array, blankPointX, blankPointY);
        queueOpen.add(state);
        graph.addVertex(state);
        lifeCycle();
    }

    public void setResult(State state) {
        this.result = state;
    }

    public void run() {
        while (!queueOpen.isEmpty()) {
            if (founded) {
                break;
            }
            lifeCycle();
            countPass++;
            if (countPass % 10000 == 0) {
                System.out.println(countPass);
            }

        }
        System.out.println("KONEC " + countPass);
        System.out.println("Pocet STAVU " + State.counterState());
        String foundedString = founded ? "NASEL" : "NENASEL";
        System.out.println("Vysledek:" + foundedString);
    }


    public void lifeCycle() {
        State stateHeader = queueOpen.poll();
        for (MOVE move : MOVE.values()) {
            State state = stateHeader.move(move);
            if (state == null) {
                continue;
            }

            state.setHeuresticPlusLenghtPath(state.getMetric(result));

            if (!isExistStateInCloseList(state)) {
                arrayClose.add(state);
                queueOpen.add(state);

            } else {

                State.reduceCounter();
            }

            graph.addEdges(stateHeader, state, move);

            if (state.equals(result)) {
                this.founded = true;
                break;
            }

        }

    }


    public Stack<Crate<State, MOVE>> getPredecessors(int[][] array, int blankPointX, int blankPointY) {
        return graph.getPredecessors(new State(array, blankPointX, blankPointY));
    }

    public void printCloseList() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("graph.txt", "UTF-8");
            writer.println(graph.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // System.out.println(model.toString());
    }

    public boolean isExistStateInCloseList(State state) {
        return arrayClose.contains(state);
    }
}
