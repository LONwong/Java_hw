import java.text.DecimalFormat;

public class Pet implements Comparable<Pet> {

    private static final double BASE_VISIT = 85.0;
    private static final double SHOT_COST  = 30.0;

    private static final DecimalFormat MONEY = new DecimalFormat("0.00");

    private String name;
    private String owner;
    private double weight;

    private int visitCount;
    private double totalCost;

    public Pet(String name, String owner, double weight) {
        this.name = name;
        this.owner = owner;
        this.weight = weight;
        this.visitCount = 0;
        this.totalCost = 0.0;
    }
    
    protected void addCharge(double amount) {
        this.totalCost += amount;
    }

    protected static double shotPrice() {
        return SHOT_COST;
    }

    /** 就诊，shots 为本次注射针数 */
    public double visit(int shots) {
        double cost = BASE_VISIT + SHOT_COST * shots;
        this.visitCount++;
        this.totalCost += cost;
        return cost;
    }

    public double avgCost() {
        if (visitCount == 0) return 0.0;
        return totalCost / visitCount;
    }

    public String getName()   { return name; }
    public String getOwner()  { return owner; }
    public double getWeight() { return weight; }

    @Override
    public int compareTo(Pet other) {
        int c = this.owner.compareToIgnoreCase(other.owner);
        if (c != 0) return c;
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pet)) return false;
        Pet p = (Pet) o;
        return this.owner.equalsIgnoreCase(p.owner)
            && this.name.equalsIgnoreCase(p.name);
    }

    @Override
    public int hashCode() {
        return (owner.toLowerCase() + "|" + name.toLowerCase()).hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s (owner %s) %.1f lbs, $%s avg cost/visit  ",
                name, owner, weight, MONEY.format(avgCost()));
    }
}
