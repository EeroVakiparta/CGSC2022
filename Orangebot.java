//Bundle uploaded at 05/01/2022 23:43:00
import java.util.*;
import java.util.stream.Collectors;
class Command {
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
class Controller {
    public int[] shortCut(Hero hero, Monster monster) {
        monster.getVx();
        monster.getVy();
    }
    public double getMonstersDistanceToBase(boolean isHomeBaseLeft, Monster monster) {
        int homeX = 0;
        int homeY = 0;
        if (!isHomeBaseLeft) {
            homeX = 17630;
            homeY = 9000;
        }
        return getDistanceBetween(homeX, homeY, monster.getX(), monster.getY());
    }
    public double getDistanceBetween(int x1, int y1, int x2 int y2) {
        return =Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
class Hero {
    int id, x, y;
    Command command = null;
    Monster closestMonster;
    boolean hasCommand = false;
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
    public boolean hasCommand() {
        if (this.command == null) {
            return false;
        } else {
            return true;
        }
    }
    public Command getCommand() {
        /*
        if(command == null){
                System.err.println("No command set, waiting... fix this");
                return new Command("WAIT", this.x,this.y);
        }
        */
        return command;
    }
    public void setCommand(Command command) {
        this.hasCommand = true;
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
                ", Command=" + command +
                ", Closest=" + closestMonster +
                '}';
    }
}
class Monster {
    int id, x, y;
    int nearBase;
    int threatFor; // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
    int targeted;
    int health;
    int vx; // trajectory
    int vy;
    public Monster(int id, int x, int y, int nearBase, int threatFor, int health, int vx, int vy) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nearBase = nearBase;
        this.threatFor = threatFor;
        this.health = health;
        this.vx = vx;
        this.vy = vy;
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
    public int getTargeteders() {
        return targeted;
    }
    public void setTargeted(int targeted) {
        this.targeted = targeted;
    }
    public void minusTargeter() {
        if (targeted < 1) {
            System.err.Println("ERROR unrealist amount of targeters")
        } else {
            this.targeted = targeted - 1;
        }
    }
    public void plusTargeter() {
        if (targeted > 2) {
            System.err.Println("ERROR unrealist amount of targeters")
        } else {
            this.targeted = targeted + 1;
        }
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getVx() {
        return vx;
    }
    public void setVx(int vx) {
        this.vx = vx;
    }
    public int getVy() {
        return vy;
    }
    public void setVy(int vy) {
        this.vy = vy;
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
        int mana = 0;
        boolean isHomeBaseLeft;
        int enemyBaseX = 0;
        int enemyBaseY = 0;
        if (baseX < 1000) {
            isHomeBaseLeft = true;
            enemyBaseX = 17630;
            enemyBaseY = 9000;
        } else {
            isHomeBaseLeft = false;
            enemyBaseX = 0;
            enemyBaseY = 0;
        }
        // game loop
        while (true) {
            List<Monster> monsters = new ArrayList<>();
            List<Hero> heroes = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                int health = in.nextInt(); // Your base health
                mana = in.nextInt(); // Ignore in the first league; Spend ten mana to cast a spell
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
                        Monster m = new Monster(id, x, y, nearBase, threatFor, health, vx, vy);
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
            List<Monster> wanderingMonsters = new ArrayList<>();
            int visibleMonsters = 0;
            for (int i = 0; i < monsters.size(); i++) {
                if (monsters.get(i).getThreatFor() == 1 || monsters.get(i).isNearBase() == 1) {
                    dangerousMonsters.add(monsters.get(i));
                    //System.err.println(monsters.get(i) + " as dangerous.");
                    visibleMonsters++;
                } else if (monsters.get(i).getThreatFor() == 0) {
                    wanderingMonsters.add(monsters.get(i));
                    //System.err.println(monsters.get(i) + " as wandering.");
                    visibleMonsters++;
                }
            }
            System.err.println("Dangerous:" + dangerousMonsters.size() + " wandering:" + wanderingMonsters.size());
            System.err.println("Heroes:" + heroes.size());
            if (monsters.size() == 0) {
                System.err.println("No monsters to begin with?.");
            }
            int x1, x2, y1, y2;
            double distance;
            double closestDistance = 999999.9;
            if (dangerousMonsters.size() > 0) {
                System.err.println("Dangerous mosnters sorting.");
                for (int i = 0; i < heroes.size(); i++) {
                    Monster targetMonster = null;
                    ;
                    if (dangerousMonsters.size() > 0) {
                        targetMonster = dangerousMonsters.get(0);
                    }
                    Hero theHero = heroes.get(i);
                    x1 = theHero.getX();
                    y1 = theHero.getY();
                    for (int j = 0; j < dangerousMonsters.size(); j++) {
                        Monster theMonster = dangerousMonsters.get(j);
                        x2 = theMonster.getX();
                        y2 = theMonster.getY();
                        distance = getDistanceBetween(x1, y1, x2, y2);
                        if (getMonstersDistanceToBase(isHomeBaseLeft, theMonster) < 5000 && targetMonster.getTargeteders() < 1) {
                            targetMonster.plusTargeter();
                            targetMonster = theMonster;
                        } else if (distance < closestDistance) {
                            closestDistance = distance;
                            targetMonster = theMonster;
                            theHero.setClosestMonster(targetMonster);
                            System.err.println(targetMonster.getId() + " set closest for:" + theHero.getId());
                        }
                    }
                    // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
                    if (targetMonster != null && dangerousMonsters.size() > 0) {
                        if (distance < 400) {
                            Command heroCommand = new Command("MOVE", targetMonster.getX(), targetMonster.getY());
                        } else {
                            Command heroCommand = new Command("MOVE", targetMonster.getVx(), targetMonster.getVy());
                        }
                        if (mana > 9 && closestDistance < 2190) {
                            if (targetMonster.getX() < 2000 && targetMonster.getY() < 2000 && targetMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() > 15000 && targetMonster.getY() > 7000 && targetMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() < 2000 && targetMonster.getY() < 2000 && targetMonster.getThreatFor() == 1) {
                                heroCommand = new Command("SPELL", "CONTROL", targetMonster.getId(), enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() > 15000 && targetMonster.getY() > 7000 && targetMonster.getThreatFor() == 1) {
                                heroCommand = new Command("SPELL", "CONTROL", targetMonster.getId(), enemyBaseX, enemyBaseY);
                            } else {
                            }
                        }
                        // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
                        if (mana > 200 && closestDistance < 2190) {
                            if (targetMonster.getX() < 4800 && targetMonster.getY() < 4800 && targetMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() > 15000 && targetMonster.getY() > 7000 && targetMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() < 4800 && targetMonster.getY() < 4800 && (targetMonster.getThreatFor() == 1 || targetMonster.getThreatFor() == 0)) {
                                heroCommand = new Command("SPELL", "CONTROL", targetMonster.getId(), enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() > 15000 && targetMonster.getY() > 7000 && (targetMonster.getThreatFor() == 1 || targetMonster.getThreatFor() == 0)) {
                                heroCommand = new Command("SPELL", "CONTROL", targetMonster.getId(), enemyBaseX, enemyBaseY);
                            } else {
                            }
                        }
                        theHero.setCommand(heroCommand);
                        System.err.println(heroCommand.toString());
                        for (int k = 0; k < dangerousMonsters.size(); k++) {
                            if (targetMonster.equals(dangerousMonsters.get(k))) {
                                dangerousMonsters.get(k).plusTargeter();
                            }
                        }
                    } else {
                        System.err.println("No dangerous monsters anywhere");
                        //theHero.setCommand(new Command(Command.WAIT,theHero.getX(),theHero.getY()));
                    }
                }
            }
            if (wanderingMonsters.size() > 0 && dangerousMonsters.size() < 3) {
                System.err.println("wanderingMonsters exsists");
                for (int i = 0; i < heroes.size(); i++) {
                    Monster closestMonster = null;
                    ;
                    if (wanderingMonsters.size() > 0) {
                        closestMonster = wanderingMonsters.get(0);
                    }
                    Hero theHero = heroes.get(i);
                    x1 = theHero.getX();
                    y1 = theHero.getY();
                    for (int j = 0; j < wanderingMonsters.size(); j++) {
                        x2 = wanderingMonsters.get(j).getX();
                        y2 = wanderingMonsters.get(j).getY();
                        distance = getDistanceBetween(x1, y1, x2, y2);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestMonster = wanderingMonsters.get(j);
                            theHero.setClosestMonster(closestMonster);
                        }
                    }
                    if (closestMonster != null && theHero.hasCommand() == false) {
                        if (distance < 400) {
                            Command heroCommand = new Command("MOVE", targetMonster.getX(), targetMonster.getY());
                        } else {
                            Command heroCommand = new Command("MOVE", targetMonster.getVx(), targetMonster.getVy());
                        }
                        if (mana > 200 && closestDistance < 2199 && closestMonster.getHealth() > 13) {
                            if (closestMonster.getX() < 8000 && closestMonster.getY() < 4500 && closestMonster.getThreatFor() == 0) {
                                attackCommand = new Command("SPELL", "CONTROL", closestMonster.getId(), enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() > 8000 && closestMonster.getY() > 4500 && closestMonster.getThreatFor() == 0) {
                                attackCommand = new Command("SPELL", "CONTROL", closestMonster.getId(), enemyBaseX, enemyBaseY);
                            } else {
                            }
                        }
                        theHero.setCommand(attackCommand);
                        System.err.println(attackCommand.toString());
                        for (int k = 0; k < wanderingMonsters.size(); k++) {
                            if (closestMonster.equals(wanderingMonsters.get(k))) {
                                wanderingMonsters.get(k).plusTargeter();
                            }
                        }
                    } else {
                        System.err.println("No Monsters anywhere" + theHero.getId());
                        //theHero.setCommand(new Command(Command.WAIT,theHero.getX(),theHero.getY()));
                    }
                }
            }
            if (visibleMonsters == 0) {
                // first crude attempt of starting formation
                System.err.println("Heroes to formation");
                if (baseX == 0) {
                    heroes.get(0).setCommand(new Command(Command.MOVE, 4800, 1500));
                    heroes.get(1).setCommand(new Command(Command.MOVE, 1800, 4000));
                    heroes.get(2).setCommand(new Command(Command.MOVE, 4000, 4000));
                } else {
                    heroes.get(0).setCommand(new Command(Command.MOVE, 16000, 4500));
                    heroes.get(1).setCommand(new Command(Command.MOVE, 12000, 5200));
                    heroes.get(2).setCommand(new Command(Command.MOVE, 13000, 7500));
                }
            }
/*
            for(int i = 0; 0 < 2; i++){
                System.err.println(i);
                if(heroes.get(i).hasCommand()){
                    System.err.println("hero " + i + " has command");
                }else if(!heroes.get(i).hasCommand()){
                   heroes.get(i).setCommand(new Command(Command.MOVE, 1100,1100));
                }
            }*/
            for (int i = 0; i < heroesPerPlayer; i++) {
                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                // 1. Gather data of monster and own players
                // 2. Determine which monters should be delt first.
                // 3. Assign monsters to heroes depending on proximity
                // 4. Deside if having a "goal keeper- hero" is a good approach.
                // 5. See If can advance league with just this.
                // In the first league: MOVE <x> <y> | WAIT; In later leagues: | SPELL <spellParams>;
                System.err.println(heroes.get(i).toString());
                System.err.println(heroes.get(i).getCommand());
                System.out.println(heroes.get(i).getCommand().toString());
            }
        }
    }
}
/*
Spells
        Your team will also acquire 1 point of mana per damage dealt to a monster, even from monsters with no health left.
        Mana is shared across the team and heroes can spend 10 mana points to cast a spell.
        A spell command has parameters, which you must separate with a space.
        command	parameters	effect	range
        WIND	<x> <y>	All entities (except your own heroes) within 1280 units are pushed 2200 units in the direction from the spellcaster to x,y.	1280
        SHIELD	<entityId>	The target entity cannot be targeted by spells for the next 12 rounds.	2200
        CONTROL	<entityId> <x> <y>	Override the target's next action with a step towards the given coordinates.	2200
        A hero may only cast a spell on entities that are within the spell's range from the hero.
*/
class Spell {
    static final String WIND = "WIND";
    static final String SHIELD = "SHIELD";
    static final String CONTROL = "CONTROL";
}
