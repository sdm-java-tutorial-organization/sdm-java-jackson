import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JacksonWirteValue {

    public static ObjectMapper om = new ObjectMapper();

    public static void main(String[] args) {

        // print json
        Model modelA = new Model(1,2, "server information: 01. Korea,time: 9,myString:\"\"\"\"\"\"");
        Model modelB = new Model(1,2, "server information: 01. Korea,time: 9,myString:\"\"\"\"\"\"");
        System.out.printf("[modelA] ");
        String strOfModelA = printJson(modelA);
        // {"a":1,"b":2,"myStrA":"server information: 01. Korea,time: 9,myString:\"\"\"\"\"\""}

        // parse string(json)
        Map<String, Object> myJson = (Map<String, Object>) parseString(strOfModelA);
        System.out.printf("[modelA(read->write)] ");
        strOfModelA = printJson(myJson);
        //  {"a":1,"b":2,"myStrA":"server information: 01. Korea,time: 9,myString:\"\"\"\"\"\""}

        // print list<Json>
        List<Model> models = Arrays.asList(modelA, modelB);
        System.out.printf("[models] ");
        printJson(models);

    }

    // print json
    static String printJson(Object obj) {
        String stringJson = null;
        try {
            stringJson = om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(stringJson);
        return stringJson;
    }

    // parse string(json)
    static Object parseString(String strOfJson) {
        Map<String, Object> myJson = null;
        try {
            // JSON 문자열을 Map or List Object 로 변환
            myJson = om.readValue(strOfJson, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myJson;
    }

    // Data
    static class Model {
        public int a;
        public int b;
        public String myStrA;
        public Model(int a, int b, String myStrA) {
            this.a = a;
            this.b = b;
            this.myStrA = myStrA;
        }
    }
}
