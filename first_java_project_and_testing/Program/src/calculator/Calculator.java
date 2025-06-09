package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int MAX_SIZE = 3;
    private static int currentSize = 0;
    private static final double[] results = new double[MAX_SIZE];
    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        while (true) {
            if (currentSize >= MAX_SIZE) {
                printAllResults();
                exit();
                break;
            }

            System.out.print("\nВведите два числа через запятую: ");
            String[] input = scanner.nextLine().split("[,\\s]+");
            System.out.print("Введите оператор (+, -, *, /): ");
            char operator = scanner.nextLine().trim().charAt(0);
            double[] values;

            try {
                values = Arrays.stream(input)
                        .map(String::trim)
                        .mapToDouble(Double::parseDouble)
                        .toArray();

                double result;

                switch (operator) {
                    case '+' -> result = values[0] + values[1];
                    case '-' -> result = values[0] - values[1];
                    case '*' -> result = values[0] * values[1];
                    case '/' -> result = values[0] / values[1];
                    default -> {
                        System.out.println("Оператор не распознан :(");
                        continue;
                    }
                }

                results[currentSize++] = result;
                System.out.printf("Результат --> %.1f %c %.1f = %.1f", values[0], operator, values[1], result);

            } catch (Exception e) {
                System.out.println("Что-то пошло не так!");
            }
        }
    }

    private static void printAllResults() {
        cleanSb();
        sb.append("\nПамять переполнена!");
        sb.append("\n:: Результаты ::");
        for (int i = 0; i < currentSize; i++) {
            sb.append("\n").append(i + 1).append(") ").append(String.format("%.1f", results[i]));
        }
        System.out.println(sb);
    }

    private static void exit() {
        System.out.println("Пока-пока!");
        scanner.close();
    }

    private static void cleanSb() {
        sb.setLength(0);
    }
}
