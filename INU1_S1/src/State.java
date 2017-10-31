import java.awt.Point;

public class State {
    private static int COUNTER_STATE = 0;

    private int[][] array;
    private final int rows;
    private final int columns;
    private final int serialNumber;
    private Point blankPoint;

    public State(int[][] array, int blankPointX, int blankPointY) {
        this.array = array;
        this.rows = array.length;
        this.columns = array[0].length;
        this.blankPoint = new Point(blankPointX, blankPointY);
        this.serialNumber = COUNTER_STATE++;
    }


    public State move(MOVE move) {
        switch (move) {
        case UP:
            return generateState(blankPoint.x, blankPoint.y - 1, move);
        case RIGHT:
            return generateState(blankPoint.x + 1, blankPoint.y, move);
        case DOWN:
            return generateState(blankPoint.x, blankPoint.y + 1, move);
        case LEFT:
            return generateState(blankPoint.x - 1, blankPoint.y, move);
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("My SN: " + serialNumber);
        builder.append(System.lineSeparator());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                builder.append("[" + array[i][j] + "] ");
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private int[][] copy2Darray() {
        int[][] newArray = new int[this.array.length][];
        for (int i = 0; i < this.array.length; i++) {
            int[] aMatrix = this.array[i];
            int aLength = aMatrix.length;
            newArray[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, newArray[i], 0, aLength);
        }
        return newArray;
    }

    private State generateState(int x, int y, MOVE move) {
        Point newBlankPoint = new Point(x, y);

        // Out of range checking
        if (newBlankPoint.getY() < 0 || newBlankPoint.getX() > columns - 1 || newBlankPoint.getY() > rows - 1 || newBlankPoint.getX() < 0) {
            return null;
        }

        int[][] newArray = copy2Darray();
        State state = new State(newArray, newBlankPoint.x, newBlankPoint.y);

        int p = newArray[newBlankPoint.y][newBlankPoint.x];
        newArray[newBlankPoint.y][newBlankPoint.x] = newArray[this.blankPoint.y][this.blankPoint.x];
        newArray[this.blankPoint.y][this.blankPoint.x] = p;
        return state;
    }

    public boolean sameArray(State state) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (this.array[i][j] != state.array[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int generateKey() {
        int sum = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum += (array[i][j] * (i + 1) * (j + 1) * 131);
            }
        }
        return sum;
    }

    @Override
    public int hashCode() {
        return generateKey();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State state = (State) obj;
        return this.sameArray(state);
    }

    public static void reduceCounter() {
        COUNTER_STATE--;
    }

    public static int counterState() {
        return COUNTER_STATE;
    }

}
