package HW4;
public class Main {
    public static void main(String[] args) {
        // 创建 DataSet 对象
        DataSet dataSet = new DataSet();

        // 添加 Human 和 Student 对象
        dataSet.add(new Human(170)); // 身高 170 cm
        dataSet.add(new Human(180)); // 身高 180 cm
        dataSet.add(new Student(95)); // 学生成绩 95 分
        dataSet.add(new Student(88)); // 学生成绩 88 分

        // 输出平均测量值和最大测量值
        System.out.println("平均测量值: " + dataSet.getAverage());
        System.out.println("最大测量值: " + dataSet.getMaximum());
    }
}
