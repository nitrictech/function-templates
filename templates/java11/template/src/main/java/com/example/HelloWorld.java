package com.example;

import io.nitric.faas.Faas;
import io.nitric.faas.Trigger;
import io.nitric.faas.NitricFunction;
import io.nitric.faas.Response;

public class HelloWorld implements NitricFunction {

    @Override
    public Response handle(Trigger trigger) {
        return trigger.defaultResponse("Hello World".getBytes());
    }

}
