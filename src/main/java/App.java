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
      request.session().removeAttribute("rangerName");
      response.redirect("/");
      return null;
    });

    get("/animals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("animals", RegularAnimal.all());
      model.put("endangered_animals", EndangeredAnimal.all());
      model.put("template", "templates/animals.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("endangered", EndangeredAnimal.class);
      model.put("template", "templates/animal-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/new", (request, response) -> {
      String name = request.queryParams("name");
      String species = request.queryParams("species");
      boolean endangered = request.queryParams("endangered") == null;
      if(endangered){
        String health = request.queryParams("health");
        String age = request.queryParams("age");
        EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, species, health, age);
        endangeredAnimal.save();
      } else{
        RegularAnimal regularAnimal = new RegularAnimal(name, species);
        regularAnimal.save();
      }
      response.redirect("/animals");
      return null;
    });

    get("/sightings", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("sightings", Sighting.all());
      model.put("template", "templates/sightings.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("template", "templates/sighting-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/new", (request, response) -> {
      String rangerName = request.session().attribute("rangerName");
      String location = request.queryParams("location");
      Sighting sighting = new Sighting(rangerName, location);
      sighting.save();
      String[] animalArray = request.queryParams("animal-names").split(",");
      Animal animal;
      for(String animalName : animalArray){
        animal = RegularAnimal.findByName(animalName);
        if(animal == null){
          animal = EndangeredAnimal.findByName(animalName);
        }
        if(animal != null){
          sighting.addAnimal(animal);
        } else {
          throw new NullPointerException("I'm sorry, We couldn't find one of your animals. Please add it to the database and update your sighting");
        }
      }
      response.redirect("/sightings");
      return null;
    });

    exception(NullPointerException.class, (exc, req, res) -> {
      res.status(500);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", exc.getMessage());
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      res.body(html);
    });
  }
}
