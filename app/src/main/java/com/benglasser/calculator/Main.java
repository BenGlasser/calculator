package com.benglasser.calculator;

import com.benglasser.calculator.app.operators.Add;
import com.benglasser.calculator.app.operators.Divide;
import com.benglasser.calculator.app.operators.Subtract;
import com.benglasser.calculator.modules.RestModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import edu.emory.mathcs.backport.java.util.Arrays;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {

    public static final int PORT = 8000;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        log.info("Starting Jetty...");
        Executor exec = new ThreadPoolExecutor(10, 10, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10));

        exec.execute(() -> {
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //wait for server to start up
        Thread.sleep(10000);
        exec.execute(Main::startCli);
    }

    private static void startCli() {
        while (true) {
            String command = getCommand();
            List<Integer> numbers;

            switch (command.toLowerCase()) {
                case "a":
                    numbers = getNumbers("Add");
                    printResult(add(numbers.get(0), numbers.get(1)));
                    break;
                case "s":
                    numbers = getNumbers("Subtract");
                    printResult(subtract(numbers.get(0), numbers.get(1)));
                    break;
                case "d":
                    numbers = getNumbers("Divide");
                    printResult(divide(numbers.get(0), numbers.get(1)));
                    break;
                default:
                    System.out.println("Command not supported");
            }
        }
    }

    private static int add(int num1, int num2) {
        return new Add(num1, num2).calculate();
    }

    private static int subtract(int num1, int num2) {
        return new Subtract(num1, num2).calculate();
    }

    private static int divide(int num1, int num2) {
        return new Divide(num1, num2).calculate();
    }

    private static void printResult(int result) {
        System.out.println(String.format("Result: %d", result));
    }

    @SneakyThrows
    private static String getCommand() {
        System.out.println(
                "\n(A)dd\n" +
                        "(S)ubtract\n" +
                        "(D)ivide\n"
        );
        return br.readLine();
    }

    @SneakyThrows
    private static List<Integer> getNumbers(String command) {
        System.out.print(String.format("Enter first number to %s: ", command));
        int num1 = Integer.parseInt(br.readLine());
        System.out.print(String.format("Enter second number to %s: ", command));
        int num2 = Integer.parseInt(br.readLine());
        return Arrays.asList(new Integer[]{num1, num2});
    }

    private static int parseInt(String num) throws Exception {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            System.out.println("argument was not an integer.");
            throw new Exception(e.getMessage());
        }

    }

    protected static Server startServer() throws Exception {
        Injector injector = Guice.createInjector(new RestModule());

        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.<javax.servlet.DispatcherType>of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));

        context.addServlet(DefaultServlet.class, "/*");

        log.info("Starting Jetty on port [{}]", PORT);
        server.start();

        server.join();

        return server;
    }

}
