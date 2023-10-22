package com.example.graphqlexamples;

import com.example.graphqlexamples.models.Customer;
import com.example.graphqlexamples.models.CustomerOrder;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    Map<String, List<CustomerOrder>> map = Map
            .of("Test1", List.of(
                            CustomerOrder.create(UUID.randomUUID(), "Order1 for Test1"),
                            CustomerOrder.create(UUID.randomUUID(), "Order2 for Test1")),
                    "Test2", List.of(
                            CustomerOrder.create(UUID.randomUUID(), "Order1 for Test2"),
                            CustomerOrder.create(UUID.randomUUID(), "Order2 for Test2"))
//                    ,
//                    "Test3", List.of(
//                            CustomerOrder.create(UUID.randomUUID(), "Order1 for Test3"),
//                            CustomerOrder.create(UUID.randomUUID(), "Order2 for Test3"))
            );

//    @SchemaMapping(typeName = "Customer")
//    Flux<CustomerOrder> orders(Customer customer, @Argument Integer limit) {
//        return Flux.fromIterable(map.get(customer.getName())).take(limit);
//    }

    //N+1 problem solution
    @BatchMapping(typeName = "Customer")
    Flux<List<CustomerOrder>> orders(List<Customer> customers) {
        return Flux.fromIterable(customers.stream().map(Customer::getName)
                .map(a -> map.getOrDefault(a, Collections.emptyList()))
//                .flatMap(List::stream)
                .collect(Collectors.toList()));
    }
}
