package com.example;

import io.nitric.faas.Faas;
import io.nitric.faas.NitricEvent;
import io.nitric.faas.NitricFunction;
import io.nitric.faas.NitricResponse;

public class HelloWorld implements NitricFunction {

    @Override
    public NitricResponse handle(NitricEvent event) {
        return NitricResponse.build("Hello World");
    }

    public static void main(String[] args) {
        new Faas().start(new HelloWorld());
    }

}
