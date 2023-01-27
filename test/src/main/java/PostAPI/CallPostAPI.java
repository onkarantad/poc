package PostAPI;

public class CallPostAPI {
    public static void main(String[] args) {
        String requestURL = "http://localhost:8444/dedup/nrt";
     //   String requestURL = "https://api.first.org/data/v1/countries";

        String jsonBody = "{\n" +
                "    \"key1\": \"1988-09-13T00:00:00\",\n" +
                "    \"key2\": \"desdssne@abc.com\",\n" +
                "    \"key3\": \"24365434\"\n" +
                "}";
      //  String response = PostAPI.postRequest(requestURL, jsonBody);
       // System.out.println("response: "+response);

       String a =  RestAPI.postRequest(requestURL, jsonBody);
        System.out.println("a: "+a);
        String b =  RestAPI.postRequest("https://api.first.org/data/v1/countries", jsonBody);
        System.out.println("b: "+b);
    }
}
