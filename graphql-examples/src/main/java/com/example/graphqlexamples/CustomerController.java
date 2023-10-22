package com.example.graphqlexamples;

import com.example.graphqlexamples.models.AgeRangeFilter;
import com.example.graphqlexamples.models.Customer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
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
    public Flux<Customer> customers() {
        return flux;
    }

    @QueryMapping
    public Mono<Customer> customerById(@Argument("id") Integer id) {
        return Mono.from(flux.filter(a -> a.getId().equals(id)));
    }

    @QueryMapping
    public Flux<Customer> customersByAgeRange(@Argument("min") Integer min, @Argument("max") Integer max) {
        return flux.filter(a -> a.getAge() >= min
                && a.getAge() <= max);
    }

    @SchemaMapping(typeName = "Query")
    public Flux<Customer> customersByRange(@Argument AgeRangeFilter filter) {
        return flux
                .filter(a -> a.getAge() >= filter.getMinAge()
                        && a.getAge() <= filter.getMaxAge());
    }
}
