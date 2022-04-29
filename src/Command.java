package src;

public class Command {
    static final String WAIT = "WAIT";
    static final String MOVE = "MOVE";
    static final String SPELL = "SPELL";
    static final String CONTROL = "CONTROL";
    static final String WIND = "WIND";

    static Command parse(String action) {
        String[] parts = action.split(" ");
        switch (parts[0]) {
            case MOVE:
                return new Command(MOVE, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
            case SPELL:
                switch (parts[1]) {
                    case CONTROL:
                        return new Command(SPELL, CONTROL, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
                    case WIND:
                        return new Command(SPELL, WIND, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
                    default:
                        return new Command(WAIT);
                }
            default:
                return new Command(WAIT);
        }
    }

    String type;
    String spell;
    Integer targetEntityId;
    Integer targetCellIdx;
    Integer sourceCellIdx;

    public Command(String type, String spell, Integer targetEntityId, Integer sourceCellIdx, Integer targetCellIdx) {
        this.type = type;
        this.spell = spell;
        this.targetEntityId = targetEntityId;
        this.targetCellIdx = targetCellIdx;
        this.sourceCellIdx = sourceCellIdx;
    }

    public Command(String type, String spell, Integer sourceCellIdx, Integer targetCellIdx) {
        this.type = type;
        this.spell = spell;
        this.targetEntityId = null;
        this.targetCellIdx = targetCellIdx;
        this.sourceCellIdx = sourceCellIdx;
    }

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
        if (type.equals(SPELL)) {
            if (spell.equals(CONTROL)) {
                return String.format("%s %s %d %d %d", type, spell, targetEntityId, sourceCellIdx, targetCellIdx);
            } else if (spell.equals(WIND)) {
                return String.format("%s %s %d %d", type, spell, sourceCellIdx, targetCellIdx);
            }

        }
        return String.format("%s %d", type, targetCellIdx);
    }
}


