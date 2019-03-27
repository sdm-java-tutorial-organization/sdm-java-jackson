import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonWirteValue {

    public static void main(String[] args) {

        ObjectMapper om = new ObjectMapper();
        Model model = new Model(1,2);

        String stringJson = null;
        try {
            stringJson = om.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(stringJson);

    }

    static class Model {
        public int a;
        public int b;
        public Model(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
