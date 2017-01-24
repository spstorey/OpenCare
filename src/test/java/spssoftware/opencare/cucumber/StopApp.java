package spssoftware.opencare.cucumber;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

public class StopApp {

    public static void main(String... args) throws InterruptedException {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/shutdown");
        try {
            client.execute(httpPost);
            Thread.sleep(1000);
        } catch (Exception e) {
            // Any exceptions probably mean the app isn't running which is fine
        } finally {
            httpPost.releaseConnection();
        }
    }
}