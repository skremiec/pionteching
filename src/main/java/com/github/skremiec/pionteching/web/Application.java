package com.github.skremiec.pionteching.web;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.skremiec.pionteching.atmservice.Atm;
import com.github.skremiec.pionteching.atmservice.OrderCalculator;
import com.github.skremiec.pionteching.atmservice.ServiceRequest;
import com.github.skremiec.pionteching.onlinegame.Group;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.skremiec.pionteching.onlinegame.GroupsCalculator;
import com.github.skremiec.pionteching.transactions.Account;
import com.github.skremiec.pionteching.transactions.Transaction;
import com.github.skremiec.pionteching.transactions.TransactionProcessor;

import java.util.List;

@SpringBootApplication
@RestController
public class Application {
    private final OrderCalculator orderCalculator = new OrderCalculator();
    private final GroupsCalculator groupsCalculator = new GroupsCalculator();
    private final TransactionProcessor transactionProcessor = new TransactionProcessor();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostMapping("/atms/calculateOrder")
    public List<Atm> calculateOrder(@RequestBody List<ServiceRequest> serviceRequests) {
        return orderCalculator.calculate(serviceRequests);
    }

    @PostMapping("/onlinegame/calculate")
    public List<Group> calculate(@RequestBody CalculateGroupsRequest request) {
        return groupsCalculator.calculate(request.groupCount, request.clans);
    }

    @PostMapping("/transactions/report")
    public List<Account> report(@RequestBody List<Transaction> transactions) {
        return transactionProcessor.report(transactions);
    }

    @Bean
    public SimpleModule deserializers() {
        return new SimpleModule() {{
            addDeserializer(ServiceRequest.class, new ServiceRequestDeserializer());
            addSerializer(Group.class, new GroupSerializer());
        }};
    }
}
