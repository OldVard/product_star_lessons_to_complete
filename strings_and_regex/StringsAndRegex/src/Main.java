import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String line = """
                Для выполнения первого задания напишите
                строки кода, которые рассчитывают
                количество букв и функцию вывода результата (количества).
                """;
    // подходящий
    static String number1 = "88005553535";
    // неподходящий
    static String number2 = "68005553535";

    public static void main(String[] args) {
        printLine("Кол-во символов: %d\n", countAllSymbols("е"));
        System.out.println("-------------------");
        printLine("Номер подходит? %s\n", isNumberMatchesFormat(number1));
        System.out.println("-------------------");

        deleteAllLettersAndSpaces();
    }

    // ex 1 (удаляем все совпадения, разница между исходным и настоящим - кол-во вхождений)
    public static int countAllSymbols(String symbol) {
        return line.length() - line.toLowerCase().replaceAll(symbol, "").length();
    }

    // ex 2
    public static String isNumberMatchesFormat(String phoneNumber) {
        String regex = "^(\\+7|8)\\d{10}$";
        return phoneNumber.matches(regex) ? "Подходит!" : "Неподходит :(";
    }

    // ex 3
    public static void deleteAllLettersAndSpaces() {
        String regex = "[а-яёА-ЯЁa-zA-Z\\s]";
        printLine(line.replaceAll(regex, ""));
    }

    private static void printLine(String s) {
        System.out.println(s);
    }

    private static void printLine(String s, Object... args) {
        System.out.printf(s, args);
    }
}