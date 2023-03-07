package fr.thivard.jsonparser;

import fr.thivard.jsonparser.exceptions.PrimitiveException;
import fr.thivard.jsonparser.models.Car;
import fr.thivard.jsonparser.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class JSONParserTest {

    @Test
    void parse1() throws PrimitiveException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        //Given
        List<Car> cars = new ArrayList<>();
        Car bmw = new Car("BMW", "M5", 625L);
        Car peu = new Car("Peugeot", "407", 241L);

        //When
        cars.add(bmw);
        cars.add(peu);
        JSONArray json = JSONParser.parse(cars);

        //Then
        Assertions.assertEquals(2, json.length());
        Assertions.assertEquals("BMW", json.getJSONObject(0).getString("brand"));
        Assertions.assertEquals("Peugeot", json.getJSONObject(1).getString("brand"));
    }

    @Test
    void parse2() throws PrimitiveException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        //Given
        Car reu = new Car("Renault", "Clio", 90L);

        //When
        JSONObject json = JSONParser.parse(reu);

        //Then
        Assertions.assertEquals( "Renault", json.getString("brand"));
        Assertions.assertEquals( "Clio", json.getString("model"));
        Assertions.assertEquals( 90L, json.getLong("power"));
    }

    @Test
    void parse3() throws PrimitiveException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        //Given
        Car car = new Car("BMW", "M4", 510L);
        User user  = new User("joristhivard@gmail.com", "Joris", "Thivard");
        user.addCar(car);

        //When
        User[] users = {user};
        JSONArray json = JSONParser.parse(users);

        //Then
        Assertions.assertEquals(1, json.length());
        Assertions.assertEquals("Thivard", json.getJSONObject(0).getString("lastName"));
        Assertions.assertEquals("M4", json.getJSONObject(0).getJSONArray("cars").getJSONObject(0).getString("model"));
    }
}