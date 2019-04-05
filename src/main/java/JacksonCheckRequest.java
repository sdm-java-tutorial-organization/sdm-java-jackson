import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonCheckRequest {

    /**
     * 서버입장에선 전달된 `Request`에 항상 올바르지 않은 데이터가 있을 것이라 생각해야 합니다.
     *  - `double quotes` 가 문자열에 포함되어 있을 경우
     *  - 예외를 제외하고 `backslash` 를 사용할 경우
     *
     * " 에 \ 가 처리되어 넘어오지 않는 경우는 흔합니다. 따라서 다음의 경우에 까지 예외처리를 하진 않습니다.
     * 다만 \ + 일반문자의 조합으로 넘어오는 경우는 찾기가 어려울 뿐더러 에러를 야기합니다.
     *
     * # 특수문자종류
     * \"
     * \\
     * \/
     * \b
     * \f
     * \n
     * \r
     * \t
     * \u
     * */

    public static void main(String[] args) {

    }

    public static void planA() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    }

    public static void planB() throws Exception {
        /*String jsonData = "{ \"provider\" : null , \"password\" : \"a\", \"userid\" : \"mlpdemo\\\mlpdemoins\" }";
        ObjectMapper mapper=new ObjectMapper();
        System.out.println(mapper.readTree(jsonData));*/
    }
}
