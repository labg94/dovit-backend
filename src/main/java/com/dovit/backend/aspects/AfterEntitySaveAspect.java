package com.dovit.backend.aspects;

import com.dovit.backend.security.UserPrincipal;
import com.dovit.backend.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AfterEntitySaveAspect {

  private final AuditService auditService;

  @AfterReturning(value = "execution(* com.dovit.backend.repositories.*Repository.save*(..))")
  @Transactional
  public void afterSavingEntity(JoinPoint joinPoint) {
    UserPrincipal loggedUser =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Object argument = joinPoint.getArgs()[0];
    String entityName = argument.getClass().getSimpleName();
    String auditMessage = String.format("Save or update over %s entity", entityName);
    log.info(auditMessage + " by userId: " + loggedUser.getId());
    auditService.registerAudit(argument, auditMessage, "OK", loggedUser.getId());
  }

  @AfterThrowing(value = "execution(* com.dovit.backend.repositories.*Repository.save*(..))")
  public void afterThrowingSavingEntity(JoinPoint joinPoint) {
    UserPrincipal loggedUser =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Object argument = joinPoint.getArgs()[0];
    String entityName = argument.getClass().getSimpleName();
    String auditMessage = String.format("Error Save or update over %s entity", entityName);
    log.error(auditMessage + " by userId: " + loggedUser.getId());
    auditService.registerAudit(argument, auditMessage, "ERROR", loggedUser.getId());
  }
}
