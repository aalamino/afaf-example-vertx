package com.afaf.example_vertx.services;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.afaf.example_vertx.domain.Joke;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.reactivex.observers.TestObserver;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.junit.Assert.assertTrue;

import io.vertx.reactivex.ext.web.client.WebClient;

import java.io.IOException;
import java.net.ServerSocket;

@ExtendWith(VertxExtension.class)
public class TestJokerServiceWireMock {

	@Mock
	private WebClient webClient;
	private JokerService jokerService;
	private WireMockServer wireMockServer;
	private int wireMockPort;
	
	@BeforeEach
	void prepare(Vertx vertx, VertxTestContext testContext) throws IOException {

		webClient = WebClient.create(vertx);

		// mock third party server
		wireMockPort = getFreePort();
		wireMockServer = new WireMockServer(wireMockPort, wireMockPort + 1);
		wireMockServer.start();

		// service instance
		var jokeURL = String.format("http://localhost:%d/lalala", wireMockPort);
		jokerService = new JokerServiceImpl(webClient, jokeURL, jokeURL );
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
		stubFor(get(anyUrl()).withHeader("Accept", equalTo("application/json")).willReturn(aResponse().withStatus(200).withBody("{\"error\":false,\"category\":\"Programming\",\"type\":\"twopart\",\"setup\":\".NET developers are picky when it comes to food.\",\"delivery\":\"They only like chicken NuGet.\",\"flags\":{\"nsfw\":false,\"religious\":false,\"political\":false,\"racist\":false,\"sexist\":false},\"id\":49,\"lang\":\"en\"}")));

		// execute
		jokerService.getJoke().subscribe(result -> {
			assertTrue(result.getDelivery().equalsIgnoreCase("They only like chicken NuGet."));
			assertTrue(result.getSetup().equalsIgnoreCase(".NET developers are picky when it comes to food."));
			testContext.completeNow();
		});

	}

	private int getFreePort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		final int port = socket.getLocalPort();
		socket.close();
		return port;
	}
}
