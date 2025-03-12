package br.com.fiap.lanchonete.infrastructure.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Aspect
@RequiredArgsConstructor
@Component
public class TransactionalAspect {

    /*
     * workaround para nao ocorrer dependencia circular no carregamento dos beans, caso referencie
     * diretamente a PlatformTransactionManager.
     */
    private final ApplicationContext applicationContext;

    @Pointcut("execution(* br.com.fiap.lanchonete.business.adapter.controller.*.*(..))")
    public void isController() {}

    @Pointcut("execution(* br.com.fiap.lanchonete.business.adapter.controller.*.find*(..))")
    public void find() {}

    @Pointcut("execution(* br.com.fiap.lanchonete.business.adapter.controller.*.getUseCase*(..))")
    public void getUseCase() {}

    @Around("isController() && find()")
    public Object aroundReadOnly(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Object response = null;
        PlatformTransactionManager transactionManager = this.applicationContext.getBean(PlatformTransactionManager.class);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setReadOnly(true);
        transactionTemplate.setTimeout(10000);  //miliseconds
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        response = transactionTemplate.execute(
                status -> {
                    try {
                        return joinPoint.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return response;
    }

    @Around("isController() && !find() && !getUseCase()")
    public Object aroundCommit(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Object response = null;
        PlatformTransactionManager transactionManager = this.applicationContext.getBean(PlatformTransactionManager.class);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setReadOnly(false);
        transactionTemplate.setTimeout(10000);  //miliseconds
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        response = transactionTemplate.execute(
                status -> {
                    try {
                        return joinPoint.proceed();
                    } catch (Throwable e) {
                        status.setRollbackOnly();
                        throw new RuntimeException(e);
                    }
                }
        );
        return response;
    }
}