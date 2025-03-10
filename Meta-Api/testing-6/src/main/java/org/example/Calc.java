package org.example;

import java.io.*;
import java.util.*;

public class Calc {
    public static void main(String[] args) {
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
        String sIn;

        try {
            System.out.println("Введите выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и ):");
            sIn = d.readLine();
            sIn = opn(sIn);
            System.out.println(calculate(sIn));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Преобразовать строку в обратную польскую нотацию
     * @param sIn Входная строка
     * @return Выходная строка в обратной польской нотации
     */
    public static String opn(String sIn) throws CalcException {

        StringBuilder sbStack = new StringBuilder(), sbOut = new StringBuilder();
        char cIn, cTmp;

        for (int i = 0; i < sIn.length(); i++) {
            cIn = sIn.charAt(i);
            if (isOp(cIn)) {
                while (sbStack.length() > 0) {
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                    if (isOp(cTmp) && (opPrior(cIn) <= opPrior(cTmp))) {
                        sbOut.append(" ").append(cTmp).append(" ");
                        sbStack.setLength(sbStack.length()-1);
                    } else {
                        sbOut.append(" ");
                        break;
                    }
                }
                sbOut.append(" ");
                sbStack.append(cIn);
            } else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                /////////////
                if(sbStack.length() == 1){
                    throw new CalcException("Ошибка разбора скобок. Проверьте правильность выражения.");
                }
                ////////////
                cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        throw new CalcException("Ошибка разбора скобок. Проверьте правильность выражения.");
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length()-1);
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else {
                // Если символ не оператор - добавляем в выходную последовательность
                sbOut.append(cIn);
            }
        }

        // Если в стеке остались операторы, добавляем их в входную строку
        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }

        return  sbOut.toString();
    }

    /**
     * Функция проверяет, является ли текущий символ оператором
     */
    private static boolean isOp(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
            case '!':
            case '%':
                return true;
        }
        return false;
    }

    /**
     * Возвращает приоритет операции
     * @param op char
     * @return byte
     */
    private static byte opPrior(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1; // Тут остается + и -
    }

    /**
     * Считает выражение, записанное в обратной польской нотации
     * @param sIn строка с арифметическим выражением
     * @return double result
     */
    public static double calculate(String sIn) throws CalcException {
        if(sIn.trim().length() == 0){
            throw new CalcException("введена пустая строка ");
        }

        double dA, dB;
        String sTmp;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer st = new StringTokenizer(sIn);
        while (st.hasMoreTokens()) {
            try {
                sTmp = st.nextToken().trim();

                if (1 == sTmp.length() && isOp(sTmp.charAt(0))) {
                    if ((stack.size() < 2 && sTmp.charAt(0) != '!') || (sTmp.charAt(0) == '!' && stack.size() < 1) ) {
                        throw new CalcException("Неверное количество данных в стеке для операции " + sTmp);
                    }
                    dB = stack.pop();
                    dA = 0.0;
                    if(sTmp.charAt(0) != '!'){
                        dA = stack.pop();
                    }
                    // Считываем первый символ
                    switch (sTmp.charAt(0)) {
                        case '+':
                            dA += dB;
                            break;
                        case '-':
                            dA -= dB;
                            break;
                        case '/':
                            dA /= dB;
                            break;
                        case '*':
                            dA *= dB;
                            break;
                        case '%':
                            dA %= dB;
                            break;
                        case '^':
                            dA = Math.pow(dA, dB);
                            break;
                        case '!':
                            dA = factorial((long)dB);
                            break;
                        default:
                            throw new CalcException("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                } else {
                    try {
                        dA = Double.parseDouble(sTmp);
                        stack.push(dA);
                    } catch (NumberFormatException e) {
                        throw new CalcException("Символ не является числом");
                    }
                }
            } catch (CalcException e) {
                throw new CalcException("Недопустимый символ в выражении");
            }
        }

        if (stack.size() > 1) {
            throw new CalcException("Количество операторов не соответствует количеству операндов");
        }

        return stack.pop();
    }

    public static long factorial(long n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return result;
    }
}
