package src;

public class Monster {
    int id, x, y;
    int nearBase;
    int threatFor; // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither
    boolean isTargeted;
    int health;

    public Monster(int id, int x, int y, int nearBase, int threatFor, int health) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nearBase = nearBase;
        this.threatFor = threatFor;
        this.isTargeted = false;
        this.health = health;
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

    public boolean isTargeted() {
        return isTargeted;
    }

    public void setTargeted(boolean targeted) {
        isTargeted = targeted;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
