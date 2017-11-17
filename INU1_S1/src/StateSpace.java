import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StateSpace {
    private final Stack<State> stackOpen;
    private State defaultState;
    private final HashSet<State> arrayClose;
    private final Graph<State, MOVE> graph;
    private final List<State> pathList;
    private State result;
    private int countPass = 1;
    private boolean founded = false;
    private int maxLevel = 0;

    public StateSpace() {
        stackOpen = new Stack<>();
        arrayClose = new HashSet<>();
        pathList = new LinkedList<>();
        graph = new Graph<>();
    }

    public void setDefaultArray(int[][] array, int blankPointX, int blankPointY) {
        defaultState = new State(array, blankPointX, blankPointY);
        stackOpen.add(defaultState);
    }

    public void setResult(State state) {
        this.result = state;
    }

    public void run() {
        while (!founded) {
            while (!stackOpen.isEmpty()) {
                if (founded) {
                    break;
                }
                lifeCycle();
                System.out.println("LEVEL " + maxLevel);
                String foundedString = founded ? "NASEL" : "NENASEL";
                System.out.println("Vysledek:" + foundedString);

            }
            stackOpen.push(defaultState);
            maxLevel++;
        }

    }


    public void lifeCycle() {
        State stateHeader = stackOpen.peek();
        for (MOVE move : MOVE.values()) {
            State state = stateHeader.move(move);
            if (state == null || stackOpen.contains(state)) {
                continue;
            }
            state.setLevel(stateHeader.getLevel() + 1);
            if (maxLevel != stateHeader.getLevel()) {
                stackOpen.push(state);
            } else {
                continue;
            }

            if (state.equals(result)) {
                this.founded = true;
                break;
            }
            lifeCycle();
            if (this.founded) {
                break;
            }

        }
        if (this.founded == true) {
            pathList.add(stackOpen.pop());
        } else {
            stackOpen.pop();
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

    public void setMaxLevel(int maxLevel){
        this.maxLevel = maxLevel + 1;
    }
    
    public List<State> getPathList() {
        return pathList;
    }

}
