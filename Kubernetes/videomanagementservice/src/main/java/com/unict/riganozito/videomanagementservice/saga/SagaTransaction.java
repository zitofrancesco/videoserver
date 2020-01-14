package com.unict.riganozito.videomanagementservice.saga;

public class SagaTransaction {
    private SagaOperation operation;
    private SagaOperation compensatingTransaction;

    public SagaTransaction(SagaOperation operation) {
        this.operation = operation;
        this.compensatingTransaction = null;
    }

    public boolean isRetriableTransaction(){
        return this.compensatingTransaction==null;
    }

    public SagaTransaction(SagaOperation operation, SagaOperation compensatingTransaction) {
        this.operation = operation;
        this.compensatingTransaction = compensatingTransaction;
    }

    public SagaOperation getOperation() {
        return operation;
    }

    public SagaOperation getCompensatingTransaction() {
        return compensatingTransaction;
    }


}