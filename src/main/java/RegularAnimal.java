import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class RegularAnimal extends Animal {

  public RegularAnimal(String name){
    this.name = name;
    endangered = false;
  }

  public static List<RegularAnimal> all() {
    String sql = "SELECT * FROM animals WHERE endangered = 'false'";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(RegularAnimal.class);
    }
  }

  public static RegularAnimal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals where id=:id";
      RegularAnimal animal = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(RegularAnimal.class);
      return animal;
    }
  }
}
