package com.unict.riganozito.videomanagementservice.saga;

public interface SagaOperation {
    public void action(Saga saga) throws Exception;
}