package com.github.skremiec.pionteching.atmservice;

import lombok.Data;

@Data
public class ServiceRequest {
    private final Atm atm;
    private final Type requestType;

    public ServiceRequest(final Atm atm, final Type requestType) {
        this.atm = atm;
        this.requestType = requestType;
    }

    public ServiceRequest(final int regionId, final Type requestType, final int atmId) {
        this(new Atm(regionId, atmId), requestType);
    }

    int getRegionId() {
        return atm.getRegionId();
    }

    public enum Type {
        FAILURE_RESTART,
        PRIORITY,
        SIGNAL_LOW,
        STANDARD,
    }
}
