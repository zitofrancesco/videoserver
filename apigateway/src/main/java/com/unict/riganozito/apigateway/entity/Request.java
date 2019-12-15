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


}
