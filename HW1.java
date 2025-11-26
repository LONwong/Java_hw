import java.util.Scanner;

public class HW1 {
    
    // 任务1：打印乘法表
    public static void multiplicationTable() {
        System.out.println("任务1：乘法表");
        System.out.println("* | 1 2 3 4 5 6 7 8 9");
        System.out.println("-------------------------------");
        
        for (int i = 1; i <= 9; i++) {
            System.out.print(" " + i + " |");
            for (int j = 1; j <= 9; j++) {
                System.out.printf(" %2d", i * j);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // 任务2：基本输入输出和求和
    public static void basicInputOutput() {
        System.out.println("任务2：基本输入输出");
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter an integer: ");
        int integer = scanner.nextInt();
        
        System.out.print("Enter a floating point number: ");
        double floating = scanner.nextDouble();
        
        scanner.nextLine(); // 消耗换行符
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        double sum = integer + floating;
        System.out.printf("Hi! %s, the sum of %d and %.2f is %.2f\n", name, integer, floating, sum);
        System.out.println();
    }
    
    // 任务3：二进制转十进制
    public static void binaryToDecimal() {
        System.out.println("任务3：二进制转十进制");
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a Binary string: ");
        String binaryStr = scanner.nextLine();
        
        // 验证二进制字符串
        if (!binaryStr.matches("[01]+")) {
            System.out.printf("Error: Invalid Binary String \"%s\"\n", binaryStr);
            System.out.println();
            return;
        }
        
        // 转换为十进制
        int decimal = 0;
        int length = binaryStr.length();
        for (int i = 0; i < length; i++) {
            char c = binaryStr.charAt(i);
            if (c == '1') {
                decimal += Math.pow(2, length - 1 - i);
            }
        }
        
        System.out.printf("The equivalent decimal number for binary \"%s\" is %d\n", binaryStr, decimal);
        System.out.println();
    }
    
    // 任务4：十六进制转二进制
    public static void hexToBinary() {
        System.out.println("任务4：十六进制转二进制");
        Scanner scanner = new Scanner(System.in);
        
        String[] hexBits = {
            "0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111",
            "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111"
        };
        
        System.out.print("Enter a Hexadecimal string: ");
        String hexStr = scanner.nextLine().toUpperCase();
        
        // 验证十六进制字符串
        if (!hexStr.matches("[0-9A-F]+")) {
            System.out.printf("Error: Invalid Hexadecimal String \"%s\"\n", hexStr);
            System.out.println();
            return;
        }
        
        // 转换为二进制
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i++) {
            char c = hexStr.charAt(i);
            int value;
            
            if (c >= '0' && c <= '9') {
                value = c - '0';
            } else {
                value = c - 'A' + 10;
            }
            
            binary.append(hexBits[value]);
            if (i < hexStr.length() - 1) {
                binary.append(" ");
            }
        }
        
        System.out.printf("The equivalent binary for hexadecimal \"%s\" is %s\n", hexStr, binary.toString());
        System.out.println();
    }
    
    // 主菜单
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("=== Java编程任务 ===");
            System.out.println("1. 打印乘法表");
            System.out.println("2. 基本输入输出和求和");
            System.out.println("3. 二进制转十进制");
            System.out.println("4. 十六进制转二进制");
            System.out.println("5. 退出");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    multiplicationTable();
                    break;
                case 2:
                    basicInputOutput();
                    break;
                case 3:
                    binaryToDecimal();
                    break;
                case 4:
                    hexToBinary();
                    break;
                case 5:
                    System.out.println("程序结束");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效选择，请重新输入！\n");
            }
        }
    }
}