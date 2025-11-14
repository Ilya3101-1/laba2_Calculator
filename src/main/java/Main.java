
import java.util.*;

/**
 * Главный класс приложения "Калькулятор математических выражений".
 * Предоставляет интерфейс командной строки для взаимодействия с пользователем.
 *
 * @author Ilya
 * @version 1.0
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Калькулятор выражений");
        System.out.println("Операции: + - * / ( )");
        System.out.println("Можно использовать переменные (только буквы)");
        System.out.println("Пример: (2 + a) - (b + 3)");
        System.out.println();

        while (true) {
            try {
                System.out.print("Введите выражение (или 'выход' для выхода): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("выход")) {
                    break;
                }

                if (input.isEmpty()) {
                    continue;
                }

                // Проверка корректности
                if (!Calculator.isValidExample(input)) {
                    System.out.println("Ошибка: выражение записано некорректно!");
                    continue;
                }

                // Ищем переменные
                Set<String> variables = Calculator.findVariables(input);
                Calculator parser = new Calculator(input);

                // Запрашиваем значения переменных
                for (String var : variables) {
                    System.out.print("Введите значение для " + var + ": ");
                    double value = scanner.nextDouble();
                    scanner.nextLine(); // очищаем буфер
                    parser.setVariable(var, value);
                }

                double result = parser.evaluate();
                System.out.println("Результат: " + result);
                System.out.println();

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println();
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }
}