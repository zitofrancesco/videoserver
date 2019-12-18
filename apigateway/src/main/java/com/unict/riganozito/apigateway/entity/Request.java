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
    private String method;

    @NotNull
    private String uri;

    @NotNull
    private Long payloadInputSize;

    @NotNull
    private Long payloadOutputSize;

    @NotNull
    private Float responseTime;

    private String error;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String type) {
        this.method = type;
    }

    public Long getPayloadInputSize() {
        return payloadInputSize;
    }

    public void setPayloadInputSize(Long payloadInputSize) {
        this.payloadInputSize = payloadInputSize;
    }

    public Long getPayloadOutputSize() {
        return payloadOutputSize;
    }

    public void setPayloadOutputSize(Long payloadOutputSize) {
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
