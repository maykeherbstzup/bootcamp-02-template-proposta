package com.nossocartao.proposta.shared.validation;

import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    private Class<?> entityClass;
    private String fieldName;
    private String errorMessage;

    @PersistenceContext
    EntityManager em;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
        this.fieldName = constraintAnnotation.fieldName();
        this.errorMessage = constraintAnnotation.errorMessage();
    }

    @Override
    public boolean isValid(Object fieldValue, ConstraintValidatorContext context) {
        Query query = em.createQuery(this.getQuery());
        query.setParameter("fieldValue", fieldValue);

        List<?> result = query.getResultList();

        if (!result.isEmpty()) {
            throw new ApiErrorException(this.fieldName, this.errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return true;
    }

    private String getQuery() {
        return "SELECT 1 FROM " + this.entityClass.getName() + " WHERE " + this.fieldName + " = :fieldValue ";
    }
}
