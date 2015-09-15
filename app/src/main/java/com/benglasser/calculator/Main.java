package com.benglasser.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.benglasser.calculator.modules.RestModule;
import com.benglasser.calculator.operations.Operation;
import com.benglasser.calculator.operations.OpreationManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import edu.emory.mathcs.backport.java.util.Arrays;

@Slf4j
public class Main
{

  public static final int PORT = 8000;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final List<Operation> operations = loadOperations();

  public static void main(String[] args) throws Exception
  {

    log.info("Starting Jetty...");
    Executor exec = new ThreadPoolExecutor(10, 10, 10, TimeUnit.MINUTES,
        new ArrayBlockingQueue<Runnable>(10));

    exec.execute(() -> {
      try
      {
        startServer();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    });

    //wait for server to start up
    Thread.sleep(10000);
    exec.execute(Main::startCli);
  }

  private static void startCli()
  {
    while (true)
    {
      String command = getCommand();

      operations.forEach(operation ->
      {
        if (operation.getName().equals(command))
        {
          try
          {
            System.out.println(String.format("Enter first number to %s: ",
                operation.getName()));
            int num1 = Integer.parseInt(br.readLine());
            System.out.println(String.format("Enter first number to %s: ",
                operation.getName()));
            int num2 = Integer.parseInt(br.readLine());
            System.out.println(String.format("Result: %d", operation.calculate(num1, num2)));
          }
          catch (IOException e)
          {
            e.printStackTrace();
            System.out.print("Must enter an integer");
          }
        }
      });

    }
  }

  private static void printResult(int result)
  {
    System.out.println(String.format("Result: %d", result));
  }

  @SneakyThrows
  private static String getCommand()
  {
    operations.forEach(operation -> System.out.println(operation.getName()));
    return br.readLine();
  }

  @SneakyThrows
  private static List<Integer> getNumbers(String command)
  {
    System.out.print(String.format("Enter first number to %s: ", command));
    int num1 = Integer.parseInt(br.readLine());
    System.out.print(String.format("Enter second number to %s: ", command));
    int num2 = Integer.parseInt(br.readLine());
    return Arrays.asList(new Integer[] { num1, num2 });
  }

  private static int parseInt(String num) throws Exception
  {
    try
    {
      return Integer.parseInt(num);
    }
    catch (Exception e)
    {
      System.out.println("argument was not an integer.");
      throw new Exception(e.getMessage());
    }

  }

  protected static Server startServer() throws Exception
  {
    Injector injector = Guice.createInjector(new RestModule(operations));

    Server server = new Server(PORT);

    ServletContextHandler context = new ServletContextHandler(server, "/",
        ServletContextHandler.SESSIONS);
    context.addFilter(GuiceFilter.class, "/*", EnumSet.<javax.servlet.DispatcherType> of(
        javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));

    context.addServlet(DefaultServlet.class, "/*");

    log.info("Starting Jetty on port [{}]", PORT);
    server.start();

    server.join();

    return server;
  }

  private static List<Operation> loadOperations()
  {
    OpreationManager manager = new OpreationManager();
    try
    {
      return manager.loadPlugins();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("Failed to load plugins");
    }
    return Collections.emptyList();
  }

}
