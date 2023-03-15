package com.ll;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {
        exp = stripOuterBrackets(exp);

        // 단일항이 입력되면 바로 리턴
        if (!exp.contains(" ")) return Integer.parseInt(exp);
        exp = exp.replace("(", "");
        exp = exp.replace(")", "");

        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");

        boolean needToCompound = needToMulti && needToPlus;

        if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return run(newExp);
        } else if (needToPlus) {
            exp = exp.replaceAll("- ", "+ -");

            String[] bits = exp.split(" \\+ ");

            int sum = 0;

            for (int i = 0; i < bits.length; ++i)
                sum += Integer.parseInt(bits[i]);

            return sum;
        } else if (needToMulti) {
            String[] bits = exp.split(" \\* ");

            int sum = 1;

            for (int i = 0; i < bits.length; ++i)
                sum *= Integer.parseInt(bits[i]);

            return sum;
        }

        throw new RuntimeException("해석 불가");
    }

    private static String stripOuterBrackets(String exp) {
        if ( exp.charAt(0) == '(' && exp.charAt(exp.length() - 1) == ')' ) {
            exp = exp.substring(1, exp.length() - 1);
        }

        return exp;
    }
}
