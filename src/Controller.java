package src;

public class Controller {


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

