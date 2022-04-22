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
