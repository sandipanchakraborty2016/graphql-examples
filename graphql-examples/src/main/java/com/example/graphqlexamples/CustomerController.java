package com.example.graphqlexamples;

import com.example.graphqlexamples.models.Customer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    private final Flux<Customer> flux = Flux
            .just(Customer.builder().id(1).age(1).name("Test1").build(),
                    Customer.builder().id(2).age(2).name("Test2").build(),
                    Customer.builder().id(3).age(3).name("Test3").build());

    @QueryMapping
    public Flux<Customer> customers(){
        return flux;
    }

    @QueryMapping
    public Mono<Customer> customerById(@Argument("id") Integer id){
        return Mono.from(flux.filter(a -> a.getId().equals(id)));
    }

}
