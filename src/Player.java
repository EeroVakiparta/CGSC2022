import java.util.Scanner;

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
                        Monster m = new Monster(id, x, y, nearBase, threatFor, health);
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
                    Monster closestMonster = null;
                    ;
                    if (dangerousMonsters.size() > 0) {
                        closestMonster = dangerousMonsters.get(0);
                    }
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
                            System.err.println(closestMonster.getId() + " set closest for:" + theHero.getId());
                        }
                    }
                    // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
                    if (closestMonster != null && dangerousMonsters.size() > 0) {
                        Command heroCommand = new Command("MOVE", closestMonster.getX(), closestMonster.getY());
                        if (mana > 9 && closestDistance < 2190) {
                            if (closestMonster.getX() < 2000 && closestMonster.getY() < 2000 && closestMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() > 15000 && closestMonster.getY() > 7000 && closestMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() < 2000 && closestMonster.getY() < 2000 && closestMonster.getThreatFor() == 1) {
                                heroCommand = new Command("SPELL", "CONTROL", closestMonster.getId(), enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() > 15000 && closestMonster.getY() > 7000 && closestMonster.getThreatFor() == 1) {
                                heroCommand = new Command("SPELL", "CONTROL", closestMonster.getId(), enemyBaseX, enemyBaseY);
                            } else {

                            }
                        }
                        // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
                        if (mana > 200 && closestDistance < 2190) {
                            if (closestMonster.getX() < 4800 && closestMonster.getY() < 4800 && closestMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() > 15000 && closestMonster.getY() > 7000 && closestMonster.getThreatFor() == 1 && closestDistance < 1240) {
                                heroCommand = new Command("SPELL", "WIND", enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() < 4800 && closestMonster.getY() < 4800 && (closestMonster.getThreatFor() == 1 || closestMonster.getThreatFor() == 0)) {
                                heroCommand = new Command("SPELL", "CONTROL", closestMonster.getId(), enemyBaseX, enemyBaseY);
                            } else if (closestMonster.getX() > 15000 && closestMonster.getY() > 7000 && (closestMonster.getThreatFor() == 1 || closestMonster.getThreatFor() == 0)) {
                                heroCommand = new Command("SPELL", "CONTROL", closestMonster.getId(), enemyBaseX, enemyBaseY);
                            } else {

                            }
                        }


                        theHero.setCommand(heroCommand);
                        System.err.println(heroCommand.toString());

                        for (int k = 0; k < dangerousMonsters.size(); k++) {
                            if (closestMonster.equals(dangerousMonsters.get(k))) {
                                dangerousMonsters.get(k).setTargeted(true);
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
                        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestMonster = wanderingMonsters.get(j);
                            theHero.setClosestMonster(closestMonster);
                        }
                    }
                    if (closestMonster != null && theHero.hasCommand() == false) {
                        Command attackCommand = new Command("MOVE", closestMonster.getX(), closestMonster.getY());

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
                                wanderingMonsters.get(k).setTargeted(true);
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