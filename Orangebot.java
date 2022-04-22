//Bundle uploaded at 04/21/2022 18:00:01

class Command {
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

class Hero {
    int id, x, y;
    Command command;

    public Hero(int id, int x, int y, Command command) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Hero{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", command=" + command +
                '}';
    }
}

package src;

public class Monster {
    int id, x, y;
    boolean nearBase;
    int threatFor; // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither

    public Monster(int id, int x, int y, boolean nearBase, int threatFor) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nearBase = nearBase;
        this.threatFor = threatFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isNearBase() {
        return nearBase;
    }

    public void setNearBase(boolean nearBase) {
        this.nearBase = nearBase;
    }

    public int getThreatFor() {
        return threatFor;
    }

    public void setThreatFor(int threatFor) {
        this.threatFor = threatFor;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", nearBase=" + nearBase +
                ", threatFor=" + threatFor +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Monster)) return false;
        if (!super.equals(object)) return false;

        Monster monster = (Monster) object;

        if (id != monster.id) return false;

        return true;
    }
}


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int baseX = in.nextInt(); // The corner of the map representing your base
        int baseY = in.nextInt();
        int heroesPerPlayer = in.nextInt(); // Always 3
        // game loop
        while (true) {
            List<Monster> monsters = new ArrayList<>();
            List<Hero> heroes = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                int health = in.nextInt(); // Your base health
                int mana = in.nextInt(); // Ignore in the first league; Spend ten mana to cast a spell
            }
            int entityCount = in.nextInt(); // Amount of heros and monsters you can see
            for (int i = 0; i < entityCount; i++) {
                int id = in.nextInt(); // Unique identifier
                int type = in.nextInt(); // 0=monster, 1=your hero, 2=opponent hero
                int x = in.nextInt(); // Position of this entity
                int y = in.nextInt();
                int shieldLife = in.nextInt(); // Ignore for this league; Count down until shield spell fades
                int isControlled = in.nextInt(); // Ignore for this league; Equals 1 when this entity is under a control spell
                int health = in.nextInt(); // Remaining health of this monster
                int vx = in.nextInt(); // Trajectory of this monster
                int vy = in.nextInt();
                int nearBase = in.nextInt(); // 0=monster with no target yet, 1=monster targeting a base
                int threatFor = in.nextInt(); // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
                switch (type) {
                    case 0: // monster
                        monster.add(new Monster(id, x, y, nearBase, threatFor))
                        break;
                    case 1: // hero
                        heros.add(new Hero(id, x, y))
                        break;
                    case 2: // enermy
                        break;
                }
            }
            List<Monster> dangerousMonsters = new ArrayList<>();
            for (int i = 0; i < monsters.size(); i++) {
                if (monsters.get(i).getThreatFor == 1) {
                    dangerousMonsters.add(monsters.get(i));
                    System.err.println("Added monster id: " + monsters.get(i) + " as dangerous.")
                }
            }

            int x1, x2, y1, y2;
            double distance;
            double closestDistance:

            if (dangerousMonsters.size() > 0) {
                for (int i = 0; i < heroes.size(); i++) {
                    Monster closestMonster;
                    Hero theHero heroes.get(i);
                    x1 = theHero.getX;
                    y1 = theHero.getY;
                    for (int j = 0; j < dangerousMonsters.size(); j++) {
                        x2 = dangerousMonsters.get(j).getX;
                        y2 = dangerousMonsters.get(j).getY;
                        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestMonster = dangerousMonsters.get(j);
                            theHero.get(i).setClosestMonster(closestMonster);
                        }
                    }

                    Command attackCommand = new Command("MOVE", closestMonster.x, closestMonster.y)
                    theHero.setCommand(attackCommand);

                    for (int i = 0; i < dangerousMonsters.size(); i++) {
                        if (closestMonster.equals(dangerousMonsters.get(i)) {
                            dangerousMonsters.remove(i);
                        }
                    }

                }
            }

            for (int i = 0; i < heroesPerPlayer; i++) {
                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                // 1. Gather data of monster and own players
                // 2. Determine which monters should be delt first.

                // 3. Assign monsters to heroes depending on proximity
                // 4. Deside if having a "goal keeper- hero" is a good approach.
                // 5. See If can advance league with just this.
                // In the first league: MOVE <x> <y> | WAIT; In later leagues: | SPELL <spellParams>;
                heroes.get(i).getCommand.toString;
            }
        }
    }
}
