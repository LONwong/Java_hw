
public class HW2 {
    private int hour;
    private int minute;
    private boolean is24Hour = true; // 默认24小时制

    // 默认构造函数，时间为0:0
    public HW2() {
        this.hour = 0;
        this.minute = 0;
    }

    // 构造函数，指定时间
    public HW2(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // 获取小时
    public int getHour() {
        return hour;
    }

    // 获取分钟
    public int getMinute() {
        return minute;
    }

    // 切换计时方式
    public void set24Hour(boolean is24) {
        this.is24Hour = is24;
    }

    // 增加1小时
    public void addHour() {
        addHour(1);
    }

    // 增加指定小时
    public void addHour(int h) {
        hour = (hour + h) % 24;
    }

    // 增加1分钟
    public void addMinute() {
        addMinute(1);
    }

    // 增加指定分钟
    public void addMinute(int m) {
        int total = hour * 60 + minute + m;
        hour = (total / 60) % 24;
        minute = total % 60;
    }

    // toString方法
    @Override
    public String toString() {
        if (is24Hour) {
            return String.format("%02d:%02d", hour, minute);
        } else {
            int h = hour % 12;
            if (h == 0) h = 12;
            String ampm = (hour < 12) ? "AM" : "PM";
            return String.format("%02d:%02d%s", h, minute, ampm);
        }
    }

    // 简单测试
    public static void main(String[] args) {
    HW2 t = new HW2();
        System.out.println("默认时间: " + t);
        t.addHour();
        System.out.println("增加1小时: " + t);
        t.addHour(5);
        System.out.println("增加5小时: " + t);
        t.addMinute();
        System.out.println("增加1分钟: " + t);
        t.addMinute(59);
        System.out.println("增加59分钟: " + t);
        t.set24Hour(false);
        System.out.println("12小时制显示: " + t);
        t.set24Hour(true);
        System.out.println("24小时制显示: " + t);
    HW2 t2 = new HW2(23, 59);
        System.out.println("指定时间: " + t2);
        t2.addMinute();
        System.out.println("指定时间+1分钟: " + t2);
    }
}