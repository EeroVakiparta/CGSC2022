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
            double distance = 99999.9;
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
                        Command heroCommand = new Command("MOVE", targetMonster.getVx(), targetMonster.getVy());
                        if (distance < 400) {
                            heroCommand = new Command("MOVE", targetMonster.getX(), targetMonster.getY());
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
                    Monster targetMonster = null;
                    if (wanderingMonsters.size() > 0) {
                        targetMonster = wanderingMonsters.get(0);
                    }
                    Hero theHero = heroes.get(i);
                    x1 = theHero.getX();
                    y1 = theHero.getY();
                    for (int j = 0; j < wanderingMonsters.size(); j++) {
                        x2 = wanderingMonsters.get(j).getX();
                        y2 = wanderingMonsters.get(j).getY();
                        distance = Util.getDistanceBetween(x1, y1, x2, y2);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            targetMonster = wanderingMonsters.get(j);
                            theHero.setClosestMonster(targetMonster);
                        }
                    }
                    if (targetMonster != null && theHero.hasCommand() == false) {
                        Command heroCommand = new Command("MOVE", targetMonster.getVx(), targetMonster.getVy());
                        if (distance < 400) {
                            heroCommand = new Command("MOVE", targetMonster.getX(), targetMonster.getY());
                        }
                        if (mana > 200 && closestDistance < 2199 && targetMonster.getHealth() > 13) {
                            if (targetMonster.getX() < 8000 && targetMonster.getY() < 4500 && targetMonster.getThreatFor() == 0) {
                                heroCommand = new Command("SPELL", "CONTROL", targetMonster.getId(), enemyBaseX, enemyBaseY);
                            } else if (targetMonster.getX() > 8000 && targetMonster.getY() > 4500 && targetMonster.getThreatFor() == 0) {
                                heroCommand = new Command("SPELL", "CONTROL", targetMonster.getId(), enemyBaseX, enemyBaseY);
                            } else {

                            }
                        }
                        theHero.setCommand(heroCommand);
                        System.err.println(heroCommand.toString());
                        for (int k = 0; k < wanderingMonsters.size(); k++) {
                            if (targetMonster.equals(wanderingMonsters.get(k))) {
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