package PostAPI;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.*;

public class PostAPI {
    //static HttpClient httpClient = HttpClients.createDefault();
    static CloseableHttpClient client = HttpClients.createDefault();

    public static String postRequest(String requestURL, String jsonBody) {
        System.out.println("post 1");
        HttpPost postRequest = new HttpPost(requestURL);
        System.out.println("post 2");
        StringEntity requestBody = new StringEntity(jsonBody);
        System.out.println("post 3");
        postRequest.addHeader("content-type", "application/json");
        postRequest.addHeader("Accept", "application/json");
        postRequest.setEntity(requestBody);
        System.out.println("post 4");
        //HttpResponse response = null;
        CloseableHttpResponse response = null;
        try {
            response = client.execute(postRequest);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        InputStream result = null;
        try {
            result = response.getEntity().getContent();
        } catch (UnsupportedOperationException | IOException e1) {
            e1.printStackTrace();
        }
        Reader reader = new InputStreamReader(result);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    builder.append(line);
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

}
