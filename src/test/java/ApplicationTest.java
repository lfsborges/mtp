package com.lindacare.test.mtp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindacare.test.mtp.model.Message;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ApplicationTest {

    @Test
    public void testWebPage() throws Exception {


        byte[] jsonData = Files.readAllBytes(Paths.get("src/test/java/data.json"));

        ObjectMapper mapper = new ObjectMapper();

        Message[] data = mapper.readValue(jsonData, Message[].class);


        for (int i=0; i < data.length; i++) {
            StringEntity entity = new StringEntity(mapper.writeValueAsString(data[i]),
                    ContentType.APPLICATION_JSON);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("https://market-trade-processor-fb.herokuapp.com/api/messages/");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            assertThat(200,equalTo(response.getStatusLine().getStatusCode()));
        }

    }
}