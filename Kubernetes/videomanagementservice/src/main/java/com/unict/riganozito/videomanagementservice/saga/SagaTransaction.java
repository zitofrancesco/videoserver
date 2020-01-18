package com.unict.riganozito.videomanagementservice.saga;

public class SagaTransaction {
    private SagaOperation operation;
    private SagaOperation compensatingTransaction;
    private SagaCondition conditionTransaction;

    public SagaTransaction(SagaOperation operation,SagaCondition condition) {
        this.operation = operation;
        this.compensatingTransaction = null;
        this.conditionTransaction = condition;
    }

    public SagaTransaction(SagaOperation operation, SagaOperation compensatingTransaction, SagaCondition condition) {
        this.operation = operation;
        this.compensatingTransaction = compensatingTransaction;
        this.conditionTransaction = condition;
    }

    public void invoke(Saga context) throws Exception {
        this.operation.action(context);
    }
    
    public void roolback(Saga context) throws Exception {
        if (this.compensatingTransaction != null)
            this.compensatingTransaction.action(context);
    }

    public boolean expressionResult(Saga context) {
        SagaCondition condition = this.conditionTransaction;
        if(condition==null)
            condition = SagaCondition.Default();
        return condition.expression(context);
    }


}