package com.nossocartao.proposta.shared.validation;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class DocumentValidator implements ConstraintValidator<Document, String> {

    @Override
    public boolean isValid(String document, ConstraintValidatorContext constraintValidatorContext) {
        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        return cnpjValidator.isValid(document, null) || cpfValidator.isValid(document, null);
    }
}

