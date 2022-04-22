//Bundle uploaded at 04/21/2022 18:00:01
import java.util.*;
import java.util.stream.Collectors;
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
    Monster closestMonster;

    public Hero(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

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
        if(command == null){
            System.err.println("No command set, waiting... fix this");
            return new Command("WAIT", this.x,this.y);
        }
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Monster getClosestMonster() {
        return closestMonster;
    }

    public void setClosestMonster(Monster closestMonster) {
        this.closestMonster = closestMonster;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Hero{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +

                '}';
    }
}


class Monster {
    int id, x, y;
    int nearBase;
    int threatFor; // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither

    public Monster(int id, int x, int y, int nearBase, int threatFor) {
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

    public int isNearBase() {
        return nearBase;
    }

    public void setNearBase(int nearBase) {
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
                        Monster m = new Monster(id, x, y, nearBase, threatFor);
                        monsters.add(m);
                        break;
                    case 1: // hero
                        heroes.add(new Hero(id, x, y));
                        break;
                    case 2: // enermy
                        break;
                }
            }
            List<Monster> dangerousMonsters = new ArrayList<>();
            for (int i = 0; i < monsters.size(); i++) {
                if (monsters.get(i).getThreatFor() == 1) {
                    dangerousMonsters.add(monsters.get(i));
                    System.err.println("Added monster id: " + monsters.get(i) + " as dangerous.");
                }
            }
            List<Monster> wanderingMonsters = new ArrayList<>();
            for (int i = 0; i < monsters.size(); i++) {
                if (monsters.get(i).getThreatFor() == 0) {
                    wanderingMonsters.add(monsters.get(i));
                    System.err.println("Added monster id: " + monsters.get(i) + " as wandering.");
                }
            }

            if(monsters.size() == 0){
                System.err.println("No monsters.");
            }

            int x1, x2, y1, y2;
            double distance;
            double closestDistance = 99999.9;

            if (dangerousMonsters.size() > 0) {
                for (int i = 0; i < heroes.size(); i++) {
                    Monster closestMonster = dangerousMonsters.get(0);
                    Hero theHero = heroes.get(i);
                    x1 = theHero.getX();
                    y1 = theHero.getY();
                    for (int j = 0; j < dangerousMonsters.size(); j++) {
                        x2 = dangerousMonsters.get(j).getX();
                        y2 = dangerousMonsters.get(j).getY();
                        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestMonster = dangerousMonsters.get(j);
                            theHero.setClosestMonster(closestMonster);
                        }
                    }
                    if(closestMonster != null){
                        Command attackCommand = new Command("MOVE", closestMonster.getX(), closestMonster.getY());
                        theHero.setCommand(attackCommand);
                        System.err.println(attackCommand.toString());

                        for (int k = 0; k < dangerousMonsters.size(); k++) {
                            if (closestMonster.equals(dangerousMonsters.get(k))) {
                                dangerousMonsters.remove(k);
                            }
                        }
                    } else{
                        System.err.println("No dangerous monsters anywhere");
                        theHero.setCommand(new Command(Command.WAIT,theHero.getX(),theHero.getY()));
                    }
                }
            }else{
                for (int i = 0; i < heroes.size(); i++) {
                    Monster closestMonster = wanderingMonsters.get(0);
                    Hero theHero = heroes.get(i);
                    x1 = theHero.getX();
                    y1 = theHero.getY();
                    for (int j = 0; j < wanderingMonsters.size(); j++) {
                        x2 = wanderingMonsters.get(j).getX();
                        y2 = wanderingMonsters.get(j).getY();
                        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestMonster = wanderingMonsters.get(j);
                            theHero.setClosestMonster(closestMonster);
                        }
                    }
                    if(closestMonster != null){
                        Command attackCommand = new Command("MOVE", closestMonster.getX(), closestMonster.getY());
                        theHero.setCommand(attackCommand);
                        System.err.println(attackCommand.toString());

                        for (int k = 0; k < wanderingMonsters.size(); k++) {
                            if (closestMonster.equals(wanderingMonsters.get(k))) {
                                wanderingMonsters.remove(k);
                            }
                        }
                    } else{
                        System.err.println("No Monsters anywhere");
                        theHero.setCommand(new Command(Command.WAIT,theHero.getX(),theHero.getY()));
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
                Hero thisHero = heroes.get(i);
                System.err.println(thisHero.toString());
                System.err.println(thisHero.getCommand());
                System.out.println(thisHero.getCommand().toString());

            }
        }
    }
}
