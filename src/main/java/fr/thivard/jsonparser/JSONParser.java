package fr.thivard.jsonparser;

import fr.thivard.jsonparser.exceptions.PrimitiveException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SuppressWarnings("unused")
public class JSONParser {
    private static final String GET = "get";

    private JSONParser() {}

    public static JSONArray parse(List<?> list) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, PrimitiveException {
        JSONArray json = new JSONArray();
        if(isPrimitiveList(list)){
            for(Object object : list){
                json.put(object);
            }
        } else {
            for(Object object : list){
                Field[] fields = object.getClass().getDeclaredFields();
                JSONObject jsonObject = new JSONObject();
                build(object, jsonObject, fields);
                json.put(jsonObject);
            }
        }
        return json;
    }

    public static JSONObject parse (Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, PrimitiveException {
        if(isPrimitiveObject(object)){
            throw new PrimitiveException();
        }
        JSONObject json = new JSONObject();
        Field[] fields = object.getClass().getDeclaredFields();
        build(object, json, fields);
        return json;
    }

    public static JSONArray parse (Object[] objects) throws PrimitiveException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JSONArray json = new JSONArray();
        if(isPrimitiveObject(objects[0])){
            for(Object object : objects){
                json.put(object);
            }
        }
        else{
            for(Object object : objects){
                Field[] fields = object.getClass().getDeclaredFields();
                JSONObject jsonObject = new JSONObject();
                build(object, jsonObject, fields);
                json.put(jsonObject);
            }
        }
        return json;
    }

    private static void build(Object object, JSONObject json, Field[] fields) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, PrimitiveException {
        for (Field field : fields){
            String fieldCapsName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String fieldTypeName = field.getType().getName();
            if (fieldTypeName.equals("java.util.List")){
                json.put(field.getName(),parse((List<?>) object.getClass().getMethod(GET+fieldCapsName).invoke(object)));
            } else if(!isPrimitiveField(field)) {
                json.put(field.getName(), parse(object.getClass().getMethod(GET+fieldCapsName).invoke(object)));
            } else {
                json.put(field.getName(), object.getClass().getMethod(GET+fieldCapsName).invoke(object));
            }
        }
    }

    private static boolean isPrimitiveField(Field field){
        return switch (field.getType().getName()) {
            case "java.lang.String", "java.lang.Integer", "java.lang.Boolean", "java.lang.Double", "java.lang.Long", "java.lang.Character", "java.lang.Byte", "java.lang.Short", "java.lang.Float" -> true;
            default -> false;
        };
    }

    private static boolean isPrimitiveObject(Object object){
        return switch (object.getClass().getName()) {
            case "java.lang.String", "java.lang.Integer", "java.lang.Boolean", "java.lang.Double", "java.lang.Long", "java.lang.Character", "java.lang.Byte", "java.lang.Short", "java.lang.Float" -> true;
            default -> false;
        };
    }

    private static boolean isPrimitiveList(List<?> list){
        return isPrimitiveObject(list.get(0));
    }
}
