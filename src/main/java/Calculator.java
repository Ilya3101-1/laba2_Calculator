
import java.util.*;

/**
 * Калькулятор математических выражений с поддержкой переменных.
 * Выполняет вычисления выражений с операциями: +, -, *, /, скобки.
 * Поддерживает переменные, значения которых задаются пользователем.
 *
 * @author Ilya
 * @version 1.0
 */
public class Calculator
{
    /** Исходное математическое выражение */
    private String example;

    /** Словарь для хранения переменных и их значений */
    private Map<String, Double> variables;

    /**
     * Создает новый калькулятор для указанного выражения.
     * Удаляет все пробелы из выражения для упрощения parsing.
     *
     * @param example математическое выражение для вычисления
     */
    public Calculator(String example)
    {
        this.example = example.replaceAll("\\s+", "");
        this.variables = new HashMap<>();
    }

    /**
     * Устанавливает значение переменной.
     * Если переменная уже существует, ее значение перезаписывается.
     *
     * @param name имя переменной (только буквенные символы)
     * @param value числовое значение переменной
     */
    public void setVariable(String name, double value)
    {
        variables.put(name, value);
    }

    /**
     * Вычисляет значение выражения.
     * Заменяет переменные на их значения и выполняет вычисления.
     * Использует рекурсивный parsing выражений.
     *
     * @return результат вычисления выражения
     * @throws RuntimeException если выражение содержит ошибки или деление на ноль
     */
    public double evaluate()
    {
        String processedExpr = example;
        for (String var : variables.keySet())
        {
            processedExpr = processedExpr.replace(var, variables.get(var).toString());
        }
        return evaluateexample(processedExpr);
    }

    /**
     * Рекурсивно вычисляет математическое выражение.
     * Обрабатывает операторы с учетом приоритета: сложение/вычитание, умножение/деление.
     * Поддерживает скобки для изменения порядка вычислений.
     *
     * @param expr выражение для вычисления (без пробелов)
     * @return результат вычисления
     * @throws RuntimeException если выражение некорректно или содержит деление на ноль
     */

    private double evaluateexample(String expr)
    {
        // Ищем самые внешние скобки
        int bracketCount = 0;
        int operatorPos = -1;

        // Ищем операторы с наименьшим приоритетом (сложение/вычитание)
        for (int i = expr.length() - 1; i >= 0; i--)
        {
            char c = expr.charAt(i);
            if (c == ')') bracketCount++;
            else if (c == '(') bracketCount--;

            if (bracketCount == 0)
            {
                if (c == '+' || c == '-')
                {
                    operatorPos = i;
                    break;
                }
            }
        }


        // Если ничего, то вычисляем значение

        if (operatorPos != -1)
        {
            double left = evaluateexample(expr.substring(0, operatorPos));
            double right = evaluateexample(expr.substring(operatorPos + 1));
            return expr.charAt(operatorPos) == '+' ? left + right : left - right;
        }

        // Ищем умножение/деление
        bracketCount = 0;
        for (int i = expr.length() - 1; i >= 0; i--)
        {
            char c = expr.charAt(i);
            if (c == ')') bracketCount++;
            else if (c == '(') bracketCount--;

            if (bracketCount == 0)
            {
                if (c == '*' || c == '/')
                {
                    operatorPos = i;
                    break;
                }
            }
        }

        // Если ничего, то вычисляем значение

        if (operatorPos != -1)
        {
            double left = evaluateexample(expr.substring(0, operatorPos));
            double right = evaluateexample(expr.substring(operatorPos + 1));
            if (expr.charAt(operatorPos) == '*')
            {
                return left * right;
            } else
            {
                if (right == 0) throw new RuntimeException("Деление на ноль!");
                return left / right;
            }
        }

        // Обработка скобок
        if (expr.startsWith("(") && expr.endsWith(")"))
        {
            return evaluateexample(expr.substring(1, expr.length() - 1));
        }

        // Если это просто число
        try
        {
            return Double.parseDouble(expr);
        } catch (NumberFormatException e)
        {
            throw new RuntimeException("Некорректное выражение: " + expr);
        }
    }

    /**
     * Проверяет корректность математического выражения.
     * Проверяет баланс скобок и отсутствие двойных операторов.
     *
     * @param expr выражение для проверки
     * @return true если выражение корректно, false в противном случае
     */

    public static boolean isValidExample(String expr)
    {
        expr = expr.replaceAll("\\s+", "");

        // Проверка скобок


        int bracketCount = 0;
        for (char c : expr.toCharArray())
        {
            if (c == '(') bracketCount++;
            else if (c == ')') bracketCount--;
            if (bracketCount < 0) return false;
        }
        if (bracketCount != 0) return false;

        // Проверка на двойные операторы
        for (int i = 0; i < expr.length() - 1; i++)

        {
            char c1 = expr.charAt(i);
            char c2 = expr.charAt(i + 1);
            if ("+-*/".indexOf(c1) >= 0 && "+-*/".indexOf(c2) >= 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Находит все переменные в выражении.
     * Переменные состоят только из буквенных символов.
     * Игнорирует числовые значения и операторы.
     *
     * @param expr выражение для анализа
     * @return множество уникальных имен переменных
     */

    public static Set<String> findVariables(String expr)

    {
        Set<String> vars = new HashSet<>();
        StringBuilder currentVar = new StringBuilder();

        for (char c : expr.toCharArray())
        {
            if (Character.isLetter(c)) {
                currentVar.append(c);
            } else {
                if (currentVar.length() > 0) {
                    vars.add(currentVar.toString());
                    currentVar.setLength(0);
                }
            }
        }

        if (currentVar.length() > 0)
        {
            vars.add(currentVar.toString());
        }

        return vars;
    }
}