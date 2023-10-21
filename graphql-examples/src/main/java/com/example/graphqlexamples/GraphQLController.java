package com.example.graphqlexamples;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
public class GraphQLController {

    @QueryMapping("sayHello")
    public Mono<String> helloWorld(){
        return Mono.just("Hello world").delayElement(Duration.ofMillis(1000));
    }

    @QueryMapping("sayHelloTo")
    public Mono<String> sayHelloTo(@Argument("name") String value){
        return Mono.fromSupplier(() -> "Hello " + value).delayElement(Duration.ofMillis(1000));
    }

    @QueryMapping
    public Mono<Double> random(){
        return Mono.just(Math.random()).delayElement(Duration.ofMillis(1000));
    }
}
