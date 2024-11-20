package com.zync.network.account.infrastructure.generator;

import org.springframework.stereotype.Component;

@Component
public class DefaultCodeGenerator implements CodeGenerator{
    private static final int NUMBER_DIGIT = 6;
    private static final String DIGITS = "1234567890";

    @Override
    public String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < NUMBER_DIGIT; i++) {
            int index = (int)(Math.random() * 100) % DIGITS.length();
            stringBuilder.append(DIGITS.charAt(index));
        }
        return stringBuilder.toString();
    }

}
