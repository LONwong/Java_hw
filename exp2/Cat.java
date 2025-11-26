public class Cat extends Pet {

    private boolean outside; // 默认 false = inside

    public Cat(String name, String owner, double weight) {
        super(name, owner, weight);
        this.outside = false;
    }

    public void goOutside() { this.outside = true; }

    @Override
    public double visit(int shots) {
        int effectiveShots = shots + (outside ? 1 : 0);
        double base = super.visit(effectiveShots);
        // 每次洗牙
        addCharge(20.0);
        return base + 20.0;
    }

    @Override
    public String toString() {
        String where = outside ? "outside cat" : "inside cat";
        return where + " " + super.toString();
    }
}
