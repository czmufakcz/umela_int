import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[][] array = new int[][] {
                { 7, 2, 4 },
                { 5, 0, 6 },
                { 8, 3, 1 }
        };
        
        int[][] array1 = new int[][] {
                { 8, 0, 6 },
                { 5, 4, 7 },
                { 2, 3, 1 }
        };

        int[][] array2 = new int[][] {
                { 1, 2, 3 },
                { 8, 0, 4 },
                { 7, 6, 5 }
        };

        int[][] arrayTest = new int[][] {
                { 7, 2, 4 },
                { 5, 0, 6 },
        };

        int[][] arrayTestResult = new int[][] {
                { 0, 2, 4 },
                { 7, 5, 6 },
        };

        int[][] resultState = new int[][] {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 }
        };

        StateSpace stateSpace = new StateSpace();
        stateSpace.setResult(new State(resultState, 0, 0));
        // stateSpace.setResult(new State(arrayTestResult, 0, 0));
        // TESTARRAY
        // stateSpace.setDefaultArray(arrayTest, 1, 1);
        // ARRAY
        stateSpace.setDefaultArray(array, 1, 1);
        // ARRAY1
        // stateSpace.setDefaultArray(array1, 1, 0);
        // ARRAY2
        // stateSpace.setDefaultArray(array2, 1, 1);

        stateSpace.run();

        System.out.println("Seznam kroku");
        List<State> list = stateSpace.getPathList();
        for (State state : list) {
            System.out.println(state.toString());
        }

        stateSpace.printCloseList();
    }

}
