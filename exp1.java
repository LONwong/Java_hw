import java.time.*;
import java.util.Scanner;

class Circle {
    private double radius;
    public Circle() { this.radius = 0; }
    public Circle(double radius) { this.radius = radius; }
    public void setRadius(double r) { this.radius = r; }
    public double getRadius() { return radius; }
    public double findArea() { return Math.PI * radius * radius; }
}

class PassObject {
    public void printAreas(Circle c, int times) {
        for (int i = 1; i <= times; i++) {
            c.setRadius(i);
            System.out.println("Radius: " + i + " Area: " + c.findArea());
        }
    }
}

class SentenceTransformer {
    public static String transform(String input) {
        if (input == null || input.isEmpty()) return "";
        char end = input.charAt(input.length() - 1);
        boolean hadPunct = (end == '.' || end == '!' || end == '?');
        if (hadPunct) input = input.substring(0, input.length() - 1);

        String[] words = input.trim().split("\\s+");
        if (words.length < 2) {
            String base = capitalizeFirst(input.trim());
            return ensureQuestion(base);
        }
        StringBuilder front = new StringBuilder();
        for (int i = 2; i < words.length; i++) {
            if (i > 2) front.append(" ");
            front.append(words[i]);
        }
        String frontStr = front.toString().trim();
        frontStr = frontStr.isEmpty() ? "" : capitalizeFirst(frontStr);

        String tail = (words[0] + " " + words[1]).toLowerCase();
        if (frontStr.isEmpty()) return capitalizeFirst(tail) + "?";
        return frontStr + ", " + tail + "?";
    }

    private static String capitalizeFirst(String s) {
        if (s.isEmpty()) return s;
        char first = s.charAt(0);
        if (Character.isLetter(first)) return Character.toUpperCase(first) + s.substring(1);
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isLetter(sb.charAt(i))) {
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
                break;
            }
        }
        return sb.toString();
    }
    private static String ensureQuestion(String s) {
        s = s.replaceAll("[.!?]+\\s*$", "");
        return s + "?";
    }
}

class BirthdayCalendar {
    public static void run(Scanner sc) {
        System.out.println("请输入出生日期，格式如：2007 12 3");
        int by, bm, bd;
        try {
            by = sc.nextInt();
            bm = sc.nextInt();
            bd = sc.nextInt();
            sc.nextLine(); 
        } catch (Exception e) {
            System.out.println("输入无效。示例：2007 12 3");
            if (sc.hasNextLine()) sc.nextLine();
            return;
        }

        int thisYear = LocalDate.now().getYear();
        if (bm < 1 || bm > 12) {
            System.out.println("月份无效。");
            return;
        }
        if (by > thisYear) {
            System.out.println("出生年不能大于今年。");
            return;
        }

        int[] weekdayCount = new int[8];
        int validYears = 0;

        for (int y = by; y <= thisYear; y++) {
            YearMonth ym = YearMonth.of(y, bm);
            int daysInMonth = ym.lengthOfMonth();
            boolean validBirthday = bd >= 1 && bd <= daysInMonth;

            System.out.println(y);
            printMonthHeader();                    // Mon Tue Wed Thu Fri Sat Sun
            printMonthBody(ym, validBirthday ? bd : -1);

            if (validBirthday) {
                DayOfWeek dow = LocalDate.of(y, bm, bd).getDayOfWeek();
                weekdayCount[dow.getValue()]++;
                validYears++;
            }
            System.out.println();
        }

        // 百分比输出
        if (validYears == 0) {
            System.out.println("没有可统计的生日日期");
            return;
        }
        String[] labels = {"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        for (int i = 1; i <= 7; i++) {
            double pct = weekdayCount[i] * 1.0 / validYears;
            System.out.printf("%s: %.3f%n", labels[i], pct);
        }
    }

    private static void printMonthHeader() {
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
    }

    private static void printMonthBody(YearMonth ym, int bday) {
        LocalDate first = ym.atDay(1);
        int firstDOW = first.getDayOfWeek().getValue(); 
        
        for (int i = 1; i < firstDOW; i++) {
            System.out.print("    ");
        }
        int days = ym.lengthOfMonth();
        for (int d = 1; d <= days; d++) {
            boolean mark = (d == bday);
            
            String cell = String.format("%2d%s ", d, mark ? "*" : " ");
            System.out.print(cell);
            
            DayOfWeek dow = LocalDate.of(ym.getYear(), ym.getMonthValue(), d).getDayOfWeek();
            if (dow == DayOfWeek.SUNDAY) System.out.println();
        }
        if (LocalDate.of(ym.getYear(), ym.getMonthValue(), days).getDayOfWeek() != DayOfWeek.SUNDAY) {
            System.out.println();
        }
    }
}

public class exp1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Circle c = new Circle();
        PassObject po = new PassObject();

        while (true) {
            System.out.println("\n=== 任务菜单 ===");
            System.out.println("1) 打印圆面积 (1~n)");
            System.out.println("2) 英文句子转换");
            System.out.println("3) 生日月历与统计");
            System.out.println("0) 退出");
            System.out.print("请选择: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": {
                    System.out.print("请输入n（正整数，默认5）: ");
                    String nStr = sc.nextLine().trim();
                    int n;
                    try { n = Integer.parseInt(nStr); if (n <= 0) n = 5; }
                    catch (Exception e) { n = 5; }
                    po.printAreas(c, n);
                    break;
                }
                case "2": {
                    System.out.println("input:");
                    String line = sc.nextLine().trim();
                    String out = SentenceTransformer.transform(line);
                    System.out.println("output：");
                    System.out.println(out);
                    break;
                }
                case "3": {
                    BirthdayCalendar.run(sc);
                    break;
                }
                case "0":
                    System.out.println("已退出。");
                    sc.close();
                    return;
                default:
                    System.out.println("无效选择。");
            }
        }
    }
}
