import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //before filters
    before("/sightings/*", (request, response) -> {
      String rangerName= request.session().attribute("rangerName");
      if(rangerName==null){
        response.redirect("/");
        halt();
      }
    });

    before("/sightings", (request, response) -> {
      String rangerName= request.session().attribute("rangerName");
      if(rangerName==null){
        response.redirect("/");
        halt();
      }
    });
  }
}