package com.example;

import io.nitric.faas.Faas;
import io.nitric.faas.Trigger;
import io.nitric.faas.NitricFunction;
import io.nitric.faas.Response;

public class Handler implements NitricFunction {

    @Override
    public Response handle(Trigger trigger) {
        return trigger.buildResponse("Nitric Java 11 Template - Success");
    }

    public static void main(String[] args) {
        Faas.start(new Handler());
    }

}
