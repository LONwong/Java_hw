package HW4;
public class Student implements Measurable {
    private double score;  // 成绩

    public Student(double score) {
        this.score = score;
    }

    // 获取成绩作为测量值
    public double getMeasurement() {
        return score;
    }
}
