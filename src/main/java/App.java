import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //Index page
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("recentSightings", Sighting.mostRecent());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Log in
    post("/", (request, response) -> {
      String rangerName = request.queryParams("rangerName");
      request.session().attribute("rangerName", rangerName);
      response.redirect("/");
      return null;
    });

    //Log out
    post("/logout", (request, response) -> {
      String rangerName = request.queryParams("rangerName");
      request.session().removeAttribute("rangerName")
      response.redirect("/");
      return null;
    });

    get("/animals"(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("animals", RegularAnimal.all());
      model.put("endangered_animals", EndangeredAnimal.all());
      model.put("template", "templates/animals.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/new"(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("endangered", EndangeredAnimal.class);
      model.put("template", "templates/animal-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings"(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("sightings", Sighting.all());
      model.put("template", "templates/sightings.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/new"(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("template", "templates/sighting-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
