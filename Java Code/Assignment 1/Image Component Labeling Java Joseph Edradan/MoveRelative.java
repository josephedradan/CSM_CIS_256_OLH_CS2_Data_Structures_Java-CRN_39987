public enum MoveRelative {
    UP(-1, 0, "Up"),
    DOWN(1, 0, "Down"),
    RIGHT(0, 1, "Right"),
    LEFT(0, -1, "Left");

    private int rowRelative;
    private int columnRelative;
    private String direction;

    MoveRelative(int rowRelative, int columnRelative, String direction) {
        this.rowRelative = rowRelative;
        this.columnRelative = columnRelative;
        this.direction = direction;
    }

    public int getRowRelative() {
        return rowRelative;
    }

    public int getColumnRelative() {
        return columnRelative;
    }

    public String getDirection() {
        return direction;
    }

}
