package PostAPI;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.*;

public class RestAPI {

    public static String postRequest(String requestURL, String jsonBody)  {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(requestURL);

        StringEntity entity = new StringEntity(jsonBody);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        String result = null;
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity2 = response.getEntity();
            result = EntityUtils.toString(entity2);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getRequest(String requestURL, String jsonBody) {
        HttpGet httpGet = new HttpGet(requestURL);
        CloseableHttpClient client = HttpClients.createDefault();

        StringEntity entity = new StringEntity(jsonBody);
        httpGet.setEntity(entity);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");

        String result = null;
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity2 = response.getEntity();
            result = EntityUtils.toString(entity2);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
            <dependency>
            <groupId>org.apache.httpcomponents.core5</groupId>
            <artifactId>httpcore5</artifactId>
            <version>5.1.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents.client5</groupId>
            <artifactId>httpclient5</artifactId>
            <version>5.1.3</version>
        </dependency>
     */

}
