package io.bootify.library.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.bootify.library.service.ReturnLoaningService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the idLoaning value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ReturnLoaningLoaningUnique.ReturnLoaningLoaningUniqueValidator.class
)
public @interface ReturnLoaningLoaningUnique {

    String message() default "{Exists.returnLoaning.loaning}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ReturnLoaningLoaningUniqueValidator implements ConstraintValidator<ReturnLoaningLoaningUnique, Integer> {

        private final ReturnLoaningService returnLoaningService;
        private final HttpServletRequest request;

        public ReturnLoaningLoaningUniqueValidator(final ReturnLoaningService returnLoaningService,
                final HttpServletRequest request) {
            this.returnLoaningService = returnLoaningService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Integer value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idReturnLoaning");
            if (currentId != null && value.equals(returnLoaningService.get(Integer.parseInt(currentId)).getLoaning())) {
                // value hasn't changed
                return true;
            }
            return !returnLoaningService.loaningExists(value);
        }

    }

}
