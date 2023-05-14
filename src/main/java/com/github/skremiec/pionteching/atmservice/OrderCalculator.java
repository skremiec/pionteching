package com.github.skremiec.pionteching.atmservice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCalculator {

    private static final Comparator<ServiceRequest> BY_REGION_AND_TYPE = Comparator
        .comparing(ServiceRequest::getRegionId)
        .thenComparing(ServiceRequest::getRequestType);

    public List<Atm> calculate(final List<ServiceRequest> serviceRequests) {
        return serviceRequests
            .stream()
            .sorted(BY_REGION_AND_TYPE)
            .map(ServiceRequest::getAtm)
            .distinct()
            .collect(Collectors.toList());
    }
}
