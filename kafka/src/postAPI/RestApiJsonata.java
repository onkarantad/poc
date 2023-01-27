//package postAPI;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//
//public class RestApiJsonata {
//
//
//	static HttpClient httpClient = HttpClients.createDefault();
//
//	public static String postRequest(String requestURL, String jsonBody) throws IOException {
//		HttpPost postRequest = new HttpPost(requestURL);
//		StringEntity requestBody = new StringEntity(jsonBody);
//		postRequest.addHeader("content-type", "application/json");
//		postRequest.addHeader("Accept", "application/json");
//		postRequest.setEntity(requestBody);
//		HttpResponse response = httpClient.execute(postRequest);
//
//		InputStream result = response.getEntity().getContent();
//		Reader reader = new InputStreamReader(result);
//		BufferedReader bufferedReader = new BufferedReader(reader);
//		StringBuilder builder = new StringBuilder();
//		while (true) {
//			try {
//				String line = bufferedReader.readLine();
//				if (line != null) {
//					builder.append(line);
//				} else {
//					break;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return builder.toString();
//	}
//
//
//}
