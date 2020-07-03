package com.dovit.backend.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Ramón París
 * @since 06-08-2019
 */
public class RutValidator implements ConstraintValidator<Rut, String> {

  @Override
  public boolean isValid(String rut, ConstraintValidatorContext context) {
    boolean validacion = false;
    try {
      rut = rut.toUpperCase();
      rut = rut.replace(".", "");
      rut = rut.replace("-", "");
      int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

      char dv = rut.charAt(rut.length() - 1);

      int m = 0, s = 1;
      for (; rutAux != 0; rutAux /= 10) {
        s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
      }
      if (dv == (char) (s != 0 ? s + 47 : 75)) {
        validacion = true;
      }

    } catch (NumberFormatException ignored) {
    } catch (Exception e) {
    }

    if (!validacion) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("RUT Inválido").addConstraintViolation();
    }

    return validacion;
  }
}
