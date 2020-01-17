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

    public boolean isRetriableTransaction() {
        return this.compensatingTransaction == null;
    }

    public SagaOperation getOperation() {
        return operation;
    }

    public SagaOperation getCompensatingTransaction() {
        return compensatingTransaction;
    }

    public boolean expressionResult(Saga saga) {
        SagaCondition condition = this.conditionTransaction;
        if(condition==null)
            condition = SagaCondition.Default();
        return condition.expression(saga);
    }


}