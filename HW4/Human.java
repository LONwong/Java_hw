package HW4;
public class Human implements Measurable {
    private double height;  // 身高

    public Human(double height) {
        this.height = height;
    }

    // 获取身高作为测量值
    public double getMeasurement() {
        return height;
    }
}
