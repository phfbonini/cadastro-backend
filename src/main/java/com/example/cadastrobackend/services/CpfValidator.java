package com.example.cadastrobackend.services;

import com.example.cadastrobackend.annotation.ValidCpf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCpf, String> {
    @Override
    public void initialize(ValidCpf constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        // Remover caracteres especiais do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        // Verificar os dígitos verificadores
        int[] digits = cpf.chars().map(Character::getNumericValue).toArray();
        int calculatedDigit1 = calculateDigit(digits, 9);
        int calculatedDigit2 = calculateDigit(digits, 10);

        return digits[9] == calculatedDigit1 && digits[10] == calculatedDigit2;
    }

    private int calculateDigit(int[] digits, int position) {
        int sum = 0;
        int multiplier = position + 1;
        for (int i = 0; i < position; i++) {
            sum += digits[i] * multiplier;
            multiplier--;
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
