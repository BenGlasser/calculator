package com.benglasser.calculator.rest;

import com.benglasser.calculator.app.operators.Add;
import com.benglasser.calculator.app.operators.Divide;
import com.benglasser.calculator.app.operators.Subtract;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by bglasser on 9/13/15.
 */


@Path("calculator")
public class CalculatorResource {

    private static ObjectMapper mapper = new ObjectMapper();

    @GET
    public String getCalculator() throws Exception {
        return "Welcome to the calculator";
    }

    @GET
    @Path("add")
    public String add(@QueryParam("number") List<Integer> numbers) throws Exception {
        return mapper.writeValueAsString(add(numbers.get(0), numbers.get(1)));
    }

    @GET
    @Path("subtract")
    public String subtract(@QueryParam("number") List<Integer> numbers) throws Exception {
        return mapper.writeValueAsString(subtract(numbers.get(0), numbers.get(1)));
    }

    @GET
    @Path("divide")
    public String divide(@QueryParam("number") List<Integer> numbers) throws Exception {
        return mapper.writeValueAsString(divide(numbers.get(0), numbers.get(1)));
    }

    private int add(int num1, int num2) {
        return new Add(num1, num2).calculate();
    }

    private int subtract(int num1, int num2) {
        return new Subtract(num1, num2).calculate();
    }

    private int divide(int num1, int num2) {
        return new Divide(num1, num2).calculate();
    }
}
