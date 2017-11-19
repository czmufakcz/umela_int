import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StateTest {
    int[][] array = new int[][] {
            { 0, 1, 2 },
            { 3, 4, 5 },
            { 6, 7, 8 }
    };

    @Test
    public void test() {

        State state = new State(array, 0, 0);
        State state1 = new State(array, 0, 0);
        assertEquals(state, state1);
    }

    @Test
    public void test1() {

        State state = new State(array, 0, 0);
        State state1 = new State(array, 0, 0);
        assertEquals(0, state.getMetric(state1));
    }

}
