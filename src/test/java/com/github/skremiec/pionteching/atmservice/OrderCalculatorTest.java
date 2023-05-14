package com.github.skremiec.pionteching.atmservice;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderCalculatorTest {
    @Test
    public void signalLowAfterPriority() {
        val signalLow = new ServiceRequest(1, ServiceRequest.Type.SIGNAL_LOW, 1);
        val priority = new ServiceRequest(1, ServiceRequest.Type.PRIORITY, 2);

        val result = new OrderCalculator().calculate(List.of(signalLow, priority));

        assertEquals(List.of(priority.getAtm(), signalLow.getAtm()), result);
    }

    @Test
    public void failureRestartAsFirst() {
        val priority = new ServiceRequest(1, ServiceRequest.Type.PRIORITY, 1);
        val failureRestart = new ServiceRequest(1, ServiceRequest.Type.FAILURE_RESTART, 2);

        val result = new OrderCalculator().calculate(List.of(priority, failureRestart));

        assertEquals(List.of(failureRestart.getAtm(), priority.getAtm()), result);
    }

    @Test
    public void priorityBeforeStandard() {
        val standard = new ServiceRequest(1, ServiceRequest.Type.STANDARD, 1);
        val priority = new ServiceRequest(1, ServiceRequest.Type.PRIORITY, 2);

        val result = new OrderCalculator().calculate(List.of(standard, priority));

        assertEquals(List.of(priority.getAtm(), standard.getAtm()), result);
    }

    @Test
    public void regionsInAscendingOrder() {
        val region4 = new ServiceRequest(4, ServiceRequest.Type.STANDARD, 1);
        val region1 = new ServiceRequest(1, ServiceRequest.Type.STANDARD, 1);

        val result = new OrderCalculator().calculate(List.of(region4, region1));

        assertEquals(List.of(region1.getAtm(), region4.getAtm()), result);
    }

    @Test
    public void atmCanAppearOnlyOnce() {
        val first = new ServiceRequest(1, ServiceRequest.Type.STANDARD, 1);
        val second = new ServiceRequest(1, ServiceRequest.Type.SIGNAL_LOW, 1);

        val result = new OrderCalculator().calculate(List.of(first, second));

        assertEquals(List.of(second.getAtm()), result);
    }

    @Test
    public void example1() {
        val result = new OrderCalculator().calculate(List.of(
            new ServiceRequest(4, ServiceRequest.Type.STANDARD, 1),
            new ServiceRequest(1, ServiceRequest.Type.STANDARD, 1),
            new ServiceRequest(2, ServiceRequest.Type.STANDARD, 1),
            new ServiceRequest(3, ServiceRequest.Type.PRIORITY, 2),
            new ServiceRequest(3, ServiceRequest.Type.STANDARD, 1),
            new ServiceRequest(2, ServiceRequest.Type.SIGNAL_LOW, 1),
            new ServiceRequest(5, ServiceRequest.Type.STANDARD, 2),
            new ServiceRequest(5, ServiceRequest.Type.FAILURE_RESTART, 1)
        ));

        assertEquals(List.of(
            new Atm(1, 1),
            new Atm(2, 1),
            new Atm(3, 2),
            new Atm(3, 1),
            new Atm(4, 1),
            new Atm(5, 1),
            new Atm(5, 2)
        ), result);
    }

    @Test
    public void example2() {
        val result = new OrderCalculator().calculate(List.of(
            new ServiceRequest(1, ServiceRequest.Type.STANDARD, 2),
            new ServiceRequest(1, ServiceRequest.Type.STANDARD, 1),
            new ServiceRequest(2, ServiceRequest.Type.PRIORITY, 3),
            new ServiceRequest(3, ServiceRequest.Type.STANDARD, 4),
            new ServiceRequest(4, ServiceRequest.Type.STANDARD, 5),
            new ServiceRequest(5, ServiceRequest.Type.PRIORITY, 2),
            new ServiceRequest(5, ServiceRequest.Type.STANDARD, 1),
            new ServiceRequest(3, ServiceRequest.Type.SIGNAL_LOW, 2),
            new ServiceRequest(2, ServiceRequest.Type.SIGNAL_LOW, 1),
            new ServiceRequest(3, ServiceRequest.Type.FAILURE_RESTART, 1)
        ));

        assertEquals(List.of(
            new Atm(1, 2),
            new Atm(1, 1),
            new Atm(2, 3),
            new Atm(2, 1),
            new Atm(3, 1),
            new Atm(3, 2),
            new Atm(3, 4),
            new Atm(4, 5),
            new Atm(5, 2),
            new Atm(5, 1)
        ), result);
    }
}
