package com.benglasser.calculator.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.map.ObjectMapper;

import com.benglasser.calculator.operations.Operation;

/**
 * Created by bglasser on 9/13/15.
 */
@Slf4j
@RequiredArgsConstructor
@Path("calculator")
public class CalculatorResource
{

  private static ObjectMapper mapper = new ObjectMapper();
  private final List<Operation> operations;

  @GET
  public String getCalculator() throws Exception
  {
    return "Welcome to the calculator";
  }

  @GET
  @Path("{command}")
  public String command(@PathParam("command") String command,
      @QueryParam("number") List<Integer> numbers) throws Exception
  {
    for (Operation op : operations)
    {
      if (command.equals(op.getName()))
      {
        return mapper.writeValueAsString(op.calculate(numbers.get(0), numbers.get(1)));
      }
    }
    return null;
  }
}
