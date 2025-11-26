public class Dog extends Pet {

    private enum Size { SMALL, MEDIUM, LARGE }
    private Size size;

    public Dog(String name, String owner, double weight, String sizeLabel) {
        super(name, owner, weight);
        String s = sizeLabel == null ? "" : sizeLabel.trim().toLowerCase();
        switch (s) {
            case "medium": this.size = Size.MEDIUM; break;
            case "large":  this.size = Size.LARGE;  break;
            default:       this.size = Size.SMALL;
        }
    }

    @Override
    public double visit(int shots) {
        double base = super.visit(shots);
        // 指甲
        addCharge(15.0);
        double deltaPerShot = 0.0;
        if (size == Size.MEDIUM) deltaPerShot = 2.5;
        else if (size == Size.LARGE) deltaPerShot = 5.0;
        double extra = 15.0 + deltaPerShot * shots;
        addCharge(deltaPerShot * shots); // base 已包含 shots*30，这里只补差价
        return base + extra;
    }

    @Override
    public String toString() {
        String tag = (size == Size.LARGE) ? "large dog"
                   : (size == Size.MEDIUM) ? "medium dog"
                   : "small dog";
        return tag + " " + super.toString();
    }
}
