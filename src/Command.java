package src;

public class Command {
    static final String WAIT = "WAIT";
    static final String MOVE = "MOVE";

    static Command parse(String action) {
        String[] parts = action.split(" ");
        switch (parts[0]) {
            case MOVE:
                return new Command(MOVE, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
            default:
                return new Command(WAIT);
        }
    }

    String type;
    Integer targetCellIdx;
    Integer sourceCellIdx;

    public Command(String type, Integer sourceCellIdx, Integer targetCellIdx) {
        this.type = type;
        this.targetCellIdx = targetCellIdx;
        this.sourceCellIdx = sourceCellIdx;
    }

    public Command(String type, Integer targetCellIdx) {
        this(type, null, targetCellIdx);
    }

    public Command(String type) {
        this(type, null, null);
    }

    @Override
    public String toString() {
        if (type.equals(WAIT)) {
            return Command.WAIT;
        }
        if (type.equals(MOVE)) {
            return String.format("%s %d %d", type, sourceCellIdx, targetCellIdx);
        }
        return String.format("%s %d", type, targetCellIdx);
    }
}

