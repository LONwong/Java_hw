package HW4;
public class DataSet {
    private double sum;       // 数据总和
    private double maximum;   // 最大测量值
    private int count;        // 数据数量

    public DataSet() {
        sum = 0;
        count = 0;
        maximum = Double.NEGATIVE_INFINITY; // 初始化为负无穷，确保任何值都能比它大
    }

    // 接收 Measurable 对象，计算总和和最大值
    public void add(Measurable obj) {
        double measurement = obj.getMeasurement();
        sum += measurement;
        if (count == 0 || maximum < measurement) maximum = measurement;
        count++;
    }

    // 返回数据的平均值
    public double getAverage() {
        if (count == 0) return 0;  // 防止除以0
        return sum / count;
    }

    // 返回最大测量值
    public double getMaximum() {
        return maximum;
    }
}
