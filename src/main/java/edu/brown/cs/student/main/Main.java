package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");
          int numArgs = arguments.length - 1;
          MathBot computer = new MathBot();
          Stars star = new Stars();
          switch (arguments[0]) {
            case "add":
              if (numArgs < 2) {
                System.out.print("ERROR: add expects 2 arguments, only received" + numArgs);
              } else {
                try {
                  double num1 = Double.parseDouble(arguments[1]);
                  double num2 = Double.parseDouble(arguments[2]);
                  System.out.println(computer.add(num1, num2));
                } catch (Exception e) {
                  System.out.println("ERROR: Improper input");
                }
              }
              break;
            case "subtract":
              if (numArgs < 2) {
                System.out.print("ERROR: subtract expects 2 arguments, only received" + numArgs);
              } else {
                try {
                  double num1 = Double.parseDouble(arguments[1]);
                  double num2 = Double.parseDouble(arguments[2]);
                  System.out.println(computer.subtract(num1, num2));
                } catch (Exception e) {
                  System.out.println("ERROR: Improper input");
                }
              }
              break;
            case "stars":
              if (numArgs < 1) {
                System.out.print("ERROR: stars expect 1 arguments, got 0");
              } else {
                try {
                  star.storeStars(arguments[1]);
                  int count = star.getDataset().size();
                  System.out.println("Read " + count + " stars from " + arguments[1]);
                } catch (Exception e) {
                  e.printStackTrace();
                  System.out.println("ERROR: data ingestion error");
                }
              }
              break;
            case "naive_neighbors":
              switch (numArgs) {
                case 2:
                  try {
                    System.out.println("ARGUMENT INPUT" + arguments.toString());
                    int k = Integer.parseInt(arguments[1]);
                    List<Integer> ids = star.getNeighborsFromStar(k, arguments[2]);
                    ids.forEach(System.out::println);
                  } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ERROR: " + e);
                  }
                  break;
                case 4:
                  try {
                    int k = Integer.parseInt(arguments[1]);
                    double x = Double.parseDouble(arguments[2]);
                    double y = Double.parseDouble(arguments[3]);
                    double z = Double.parseDouble(arguments[4]);
                    double[] pos = new double[] {x, y, z};
                    List<Integer> ids = star.getNeighborsFromPosition(k, pos);
                    ids.forEach(System.out::println);
                  } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ERROR: " + e);
                  }
                  break;
                default:
                  System.out.println("ERROR: incorrect command structure for naive_neighbors");
              }
              break;
            default:
              System.out.println("ERROR: incorrect command");
              break;
          }
        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
