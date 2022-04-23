package src;

public class Hero {
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
        if(this.command == null){
            return false;
        }else{
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
