package org.example.calculate.operators;

import org.example.calculate.PositiveNumber;

public interface NewArithmeticOperator {
    boolean supports(String operator);
    int calculate(PositiveNumber operand1, PositiveNumber operand2);
}
