package PostAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonToObj {
    public static void main(String[] args) throws JsonProcessingException {

        String jsonObject = RestAPI.postRequest("http://localhost:8090/ssi_audit/preScript","{\n" +
                "    \"Target_Audit_Schema_Nm\": \"NOSQL_AUDIT\",\n" +
                "    \"JOB_NUM\": 6,\n" +
                "    \"STATUS_NUM\":1,\n" +
                "    \"Source_Zone_Id\": \"Asia/Jerusalem\",\n" +
                "    \"CURRENT_BATCH_START_DATE\": \"2022-12-30 15:19:00\"\n" +
                "}");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> jsonMap = objectMapper.readValue(jsonObject,
                new TypeReference<List<Map<String,Object>>>(){});

        System.out.println("jsonMap: "+jsonMap);
    }
}
