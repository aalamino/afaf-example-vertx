package com.afaf.tertiary.webclient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.afaf.domain.adapter.JokeAdapter;
import com.afaf.secondary.service.api.JokerService;
import com.afaf.secondary.service.api.impl.JokerServiceImpl;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import com.afaf.tertiary.webclient.api.impl.JokerWebClientImpl;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

@ExtendWith(VertxExtension.class)
public class TestJokerServiceWireMock {

	private JokerService jokerService;
	private WebClient webClient;
	private JokerWebClient jokerWebClient;
	private JokeAdapter jokeAdapter;
	private WireMockServer wireMockServer;
	private int wireMockPort;

	private String setupStub = ".NET developers are picky when it comes to food.";
	private String deliveryStub = "They only like chicken NuGet.";

	@BeforeEach
	void prepare(Vertx vertx, VertxTestContext testContext) throws IOException {
		// mock third party server
		wireMockPort = getFreePort();
		wireMockServer = new WireMockServer(wireMockPort, wireMockPort + 1);
		wireMockServer.start();

		webClient = WebClient.create(vertx);

		// service instance
		var jokeURL = String.format("http://localhost:%d/lalala", wireMockPort);
		jokeAdapter = new JokeAdapter();
		jokerWebClient = new JokerWebClientImpl(webClient, jokeAdapter);
		jokerService = new JokerServiceImpl(jokerWebClient, jokeURL, jokeURL);
		testContext.completeNow();
	}

	@AfterEach
	void tearDown() {
		wireMockServer.stop();
	}

	@DisplayName("Check that jokerService gets a joke")
	@Test
	void getJokeTest(VertxTestContext testContext) {
		// stub
		configureFor("localhost", wireMockPort);
		stubFor(get(anyUrl()).withHeader("Accept", equalTo("application/json"))
				.willReturn(aResponse().withStatus(200).withBody(getWithBodyStub())));
		// execute
		jokerService.getJoke().subscribe(result -> {
			assertTrue(result.getSetup().equalsIgnoreCase(setupStub));
			assertTrue(result.getDelivery().equalsIgnoreCase(deliveryStub));
			testContext.completeNow();
		});
	}

	private int getFreePort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		final int port = socket.getLocalPort();
		socket.close();
		return port;
	}

	private String getWithBodyStub() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("{");
		sBuilder.append("\"error\":false");
		sBuilder.append(",\"category\":\"Programming\"");
		sBuilder.append(",\"type\":\"twopart\"");
		sBuilder.append(",\"setup\":\"").append(setupStub).append("\"");
		sBuilder.append(",\"delivery\":\"").append(deliveryStub).append("\"");
		sBuilder.append(",\"flags\":{");
		sBuilder.append("\"nsfw\":false");
		sBuilder.append(",\"religious\":false");
		sBuilder.append(",\"political\":false");
		sBuilder.append(",\"racist\":false");
		sBuilder.append(",\"sexist\":false}");
		sBuilder.append(",\"id\":49");
		sBuilder.append(",\"lang\":\"en\"");
		sBuilder.append("}");
		return sBuilder.toString();
	}

}
