package com.example.shop.repositories.customizations;

public class OperatorQuery {
    public static String getOperator(String operator){
        return switch (operator) {
            case "<" -> "<";
            case ">" -> ">";
            case "-" -> "=";
            case ">=" -> ">=";
            case "<=" -> "<=";
            case "!" -> "";
            default -> "like";
        };
    }
}
