package com.example;

import org.junit.jupiter.api.Test;

import io.nitric.faas.NitricEvent;

import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldTest {

    @Test public void test_handle() {
        var event = NitricEvent.newBuilder().build();

        var response = new HelloWorld().handle(event);
        assertEquals("Hello World", response.getBodyText());
    }

}