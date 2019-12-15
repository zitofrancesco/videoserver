package com.unict.riganozito.apigateway.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String type;

    @NotNull
    private Integer payloadInputSize;

    @NotNull
    private Integer payloadOutputSize;

    @NotNull
    private Float responseTime;

    private String error;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPayloadInputSize() {
        return payloadInputSize;
    }

    public void setPayloadInputSize(Integer payloadInputSize) {
        this.payloadInputSize = payloadInputSize;
    }

    public Integer getPayloadOutputSize() {
        return payloadOutputSize;
    }

    public void setPayloadOutputSize(Integer payloadOutputSize) {
        this.payloadOutputSize = payloadOutputSize;
    }

    public Float getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Float responseTime) {
        this.responseTime = responseTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
