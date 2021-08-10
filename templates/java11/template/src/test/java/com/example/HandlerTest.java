package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.nitric.mock.MockTrigger;

public class HandlerTest {
    
    @Test
    public void test_handle() {

        var trigger = MockTrigger.newHttpTriggerBuilder()
            .setMethod("GET")
            .setPath("/")
            .build();

        var response = new Handler().handle(trigger);

        assertEquals("Nitric Java 11 Template - Success", response.getDataAsText());
    }

}