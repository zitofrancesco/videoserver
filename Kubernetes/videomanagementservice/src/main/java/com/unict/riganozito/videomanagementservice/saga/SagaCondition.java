package com.unict.riganozito.videomanagementservice.saga;

public interface SagaCondition {
    public boolean expression(Saga context);


    public static SagaCondition Default() {
        return new SagaCondition(){
            @Override
            public boolean expression(Saga context) {
                return true;
            }
        };
    }
}