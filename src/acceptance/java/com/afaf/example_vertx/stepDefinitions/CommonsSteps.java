package com.afaf.example_vertx.stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.afaf.example_vertx.commons.CommonDto;
import cucumber.api.java8.En;
import io.vertx.core.json.JsonObject;

public class CommonsSteps implements En {

	private String path;
	private Map<String, String> headers;
	private HttpClient client = HttpClientBuilder.create().build();

	public static int DEFAULT_SERVER_PORT = 8080;
	public static String DEFAULT_SERVER_ADDR = "127.0.0.1";

	public CommonsSteps() {
		Given("a server endpoint {string}", (String path) -> {
			this.path = path;
		});
		
		Given("a header with name ([^\"]*) and value ([^\"]*)$", (String name, String value) -> {
			if (headers == null) {
				headers = new HashMap<>();
			}
			headers.put(name, value);
		});

		When("I send a GET request to the endpoint", () -> {
			String envAddr = System.getenv("SERVER_ADDR");
			String addr = envAddr == null || envAddr.equals("") ? DEFAULT_SERVER_ADDR : envAddr;
			HttpGet httpGet = new HttpGet("http://" + addr + ":" + CommonsSteps.DEFAULT_SERVER_PORT + path);

			if (headers != null) {
				headers.entrySet().stream().forEach(entry -> httpGet.setHeader(entry.getKey(), entry.getValue()));
			}

			HttpResponse response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			CommonDto.getInstance().setStatus(statusCode);
			CommonDto.getInstance().setBody(EntityUtils.toString(response.getEntity(), "UTF-8"));
		});


		Then("contains field {string} not empty", (String fieldName) -> {
			assertTrue(!new JsonObject(CommonDto.getInstance().getBody()).getString(fieldName).isEmpty());
		});

		Then("I get an HTTP {string}", (String expectedHTTP) -> {
			assertEquals(CommonDto.getInstance().getBody(), Integer.parseInt(expectedHTTP), CommonDto.getInstance().getStatus());
		});
	}


}
