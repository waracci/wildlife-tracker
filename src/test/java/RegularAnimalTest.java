import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class RegularAnimalTest{
  RegularAnimal testRegularAnimal;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    testRegularAnimal = new RegularAnimal("sloth");
  }

  @Test
  public void animals_instantiatesCorrectly_true() {
    assertTrue(testRegularAnimal instanceof RegularAnimal);
  }

  @Test
  public void name_instantiatesCorrectly_true() {
    assertEquals("sloth", testRegularAnimal.getName());
  }


  @Test
  public void equals_returnsTrueIfPropertiesAreSame_true(){
    RegularAnimal testRegularAnimal2 = new RegularAnimal("sloth");
    assertTrue(testRegularAnimal.equals(testRegularAnimal2));
  }

  @Test
  public void save_insertsRegularAnimalIntoDatabase_RegularAnimal() {
    testRegularAnimal.save();
    RegularAnimal testRegularAnimal2 = null;
    try(Connection con = DB.sql2o.open()){
      testRegularAnimal2 = con.createQuery("SELECT * FROM animals WHERE name='sloth'").throwOnMappingFailure(false)
      .executeAndFetchFirst(RegularAnimal.class);
    }
    assertTrue(testRegularAnimal2.equals(testRegularAnimal));
  }



  @Test
  public void all_returnsAllInstancesOfPerson_true() {
    testRegularAnimal.save();
    RegularAnimal testRegularAnimal2 = new RegularAnimal("squirrel");
    testRegularAnimal2.save();
    assertEquals(true, RegularAnimal.all().get(0).equals(testRegularAnimal));
    assertEquals(true, RegularAnimal.all().get(1).equals(testRegularAnimal2));
  }

  @Test
  public void save_assignsIdToRegularAnimal() {
    testRegularAnimal.save();
    RegularAnimal testRegularAnimal2 = RegularAnimal.all().get(0);
    assertEquals(testRegularAnimal.getId(), testRegularAnimal2.getId());
  }

  @Test
  public void find_returnsRegularAnimalWithSameId_secondRegularAnimal() {
    testRegularAnimal.save();
    RegularAnimal testRegularAnimal2 = new RegularAnimal("squirrel");
    testRegularAnimal2.save();
    assertEquals(RegularAnimal.find(testRegularAnimal2.getId()), testRegularAnimal2);
  }
}
