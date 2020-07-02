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

import java.util.HashMap;
import java.util.Map;

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
    UserPrincipal loggedUser = getContextUser();
    Object argument = joinPoint.getArgs()[0];
    String entityName = argument.getClass().getSimpleName();
    Map<String, Object> persistedEntity = this.createMap(joinPoint);
    String auditMessage = String.format("Save or update over %s entity", entityName);
    log.info(auditMessage + " by userId: " + loggedUser.getId());
    auditService.registerAudit(persistedEntity, auditMessage, "OK", loggedUser.getId());
  }

  @AfterThrowing(value = "execution(* com.dovit.backend.repositories.*Repository.save*(..))")
  public void afterThrowingSavingEntity(JoinPoint joinPoint) {
    UserPrincipal loggedUser = getContextUser();
    Object argument = joinPoint.getArgs()[0];
    String entityName = argument.getClass().getSimpleName();
    Map<String, Object> persistedEntity = this.createMap(joinPoint);
    String auditMessage = String.format("Error Save or update over %s entity", entityName);
    log.error(auditMessage + " by userId: " + loggedUser.getId());
    auditService.registerAudit(persistedEntity, auditMessage, "ERROR", loggedUser.getId());
  }

  private UserPrincipal getContextUser() {
    try {
      return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      return new UserPrincipal();
    }
  }

  private Map<String, Object> createMap(JoinPoint joinPoint) {
    Object argument = joinPoint.getArgs()[0];
    String entityName = argument.getClass().getSimpleName();
    Map<String, Object> map = new HashMap<>();
    map.put(entityName.toLowerCase(), argument);
    return map;
  }
}
