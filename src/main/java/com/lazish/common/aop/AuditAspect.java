package com.lazish.common.aop;

import com.lazish.common.aop.annotations.Audit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Around("@annotation(audit)")
    public Object audit(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        String actor = resolveActor();
        String entityId = resolveEntityId(joinPoint, audit.entityIdParam());

        try {
            Object result = joinPoint.proceed();
            logger.info("AUDIT action={} entity={} entityId={} actor={}",
                    audit.action(), audit.entity(), entityId, actor);
            return result;
        } catch (Throwable ex) {
            logger.warn("AUDIT_FAILED action={} entity={} entityId={} actor={} error={}",
                    audit.action(), audit.entity(), entityId, actor, ex.getClass().getSimpleName());
            throw ex;
        }
    }

    private String resolveActor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "anonymous";
        }
        return authentication.getName();
    }

    private String resolveEntityId(ProceedingJoinPoint joinPoint, String entityIdParam) {
        if (entityIdParam == null || entityIdParam.isBlank()) {
            return "n/a";
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (parameterNames == null) {
            return "n/a";
        }
        for (int i = 0; i < parameterNames.length; i++) {
            if (entityIdParam.equals(parameterNames[i])) {
                Object value = args[i];
                return value == null ? "n/a" : value.toString();
            }
        }
        return "n/a";
    }
}

