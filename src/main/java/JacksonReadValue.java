import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JacksonReadValue {

    public static void main(String[] args) {

        ObjectMapper om = new ObjectMapper();
        String jsonStr = "{" +
                "\"result\":true," +
                "\"msg\":\"\"," +
                "\"value\":" +
                "[" +
                "{\"optionType\":81,\"curPoint\":1,\"emblemNo\":1,\"goalPoint\":1,\"optionValue\":10,\"achivId\":71200803,\"title\":\"명예의 전당 탑 100\",\"classType\":0,\"desc\":\"명예의 전당 전투력 부문에서 {0}위를 달성하세요.\"}," +
                "{\"optionType\":3,\"curPoint\":180,\"emblemNo\":5,\"updateDate\":1544437447,\"goalPoint\":180,\"optionValue\":20000,\"achivId\":71114100,\"title\":\"무한의 탑 도전\",\"classType\":0,\"desc\":\"무한의 탑을 {0}층까지 클리어하세요.\"}," +
                "{\"optionType\":2,\"curPoint\":90,\"emblemNo\":4,\"goalPoint\":90,\"optionValue\":50000,\"achivId\":71111500,\"title\":\"르 의 별\",\"classType\":0,\"desc\":\"{1} 모든 난이도의 별을 모두 모으세요.\"}," +
                "{\"optionType\":1,\"curPoint\":1,\"emblemNo\":10,\"updateDate\":1544063594,\"goalPoint\":30,\"optionValue\":10000,\"achivId\":72170100,\"title\":\"[한정] 난투장 구경\",\"classType\":0,\"desc\":\"난투장에 {0}회 이상 참가하세요.\"}" +
                "]" +
                "}\n";
        try {
            // JSON 문자열을 Map or List Object 로 변환
            Map<String, Object> m = om.readValue(jsonStr, new TypeReference<Map<String, Object>>(){});
            System.out.println("json to object : " + m);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
