package com.unict.riganozito.videomanagementservice.saga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Saga {

    private List<SagaTransaction> operations;
    private Integer indexOperation;
    private HashMap<String, Object> data;
    
    
    public Saga() {
        this.operations = new ArrayList<>();
        this.indexOperation = 0;
        this.data = new HashMap<>();
    }

    public void addTransaction(SagaOperation operation, SagaOperation compensatingTransaction) {
        operations.add(new SagaTransaction(operation, compensatingTransaction));
    }

    public void addTransaction(SagaOperation operation) {
        operations.add(new SagaTransaction(operation));
    }

    public int getCountTransaction() {
        return operations.size();
    }

    public SagaTransaction currentTransaction() {
        return this.operations.get(this.indexOperation);
    }

    public Object getData(String key)
    {
        return this.data.get(key);
    }

    public void setData(String key,Object value) {
        this.data.put(key, value);
    }

    public void executive() throws Exception {
        if(this.indexOperation!=0)
            throw new Exception("Invalid operation");
        this.indexOperation = 0;
        for (SagaTransaction sagaTransaction : this.operations) {
            try {
                sagaTransaction.getOperation().action(this);
                this.indexOperation++;
            } catch (Exception ex) {
                this.roolback();
                throw ex;
            }
        }
    }
    
    public void roolback() throws Exception {
        if (this.indexOperation ==0 )
            throw new Exception("Invalid operation");

        for (int i = this.indexOperation; i > 0; i--) {
            SagaTransaction transaction = this.operations.get(i);
            if(!transaction.isRetriableTransaction())
                transaction.getCompensatingTransaction().action(this);
            this.indexOperation--;
        }
    }

}