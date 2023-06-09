package InterFace;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HTTPRequest {

    public static String getRequest(String url) throws Exception{
        final CloseableHttpClient httpClient   = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            String result = "";
            if (entity != null)
                result = EntityUtils.toString(entity);
            return result;
        }
    }
}
