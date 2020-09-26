package com.afaf.tertiary.webclient;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import com.afaf.domain.adapter.JokeAdapter;
import com.afaf.secondary.service.api.JokerService;
import com.afaf.secondary.service.api.impl.JokerServiceImpl;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import com.afaf.tertiary.webclient.api.impl.JokerWebClientImpl;
import io.reactivex.Single;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

@ExtendWith(VertxExtension.class)
public class TestJokerServiceVertx {
	
	private JokerService jokerService;
	private JokerWebClient jokerWebClient;
	private WebClient webClient;
	private JokeAdapter jokeAdapter;

	@BeforeEach
	void prepare(Vertx vertx, VertxTestContext testContext) {
		webClient = WebClient.create(vertx);
		var jokesUrlAny = "https://sv443.net/jokeapi/v2/joke/Any?type=twopart";
		var jokesUrlProgramming = "https://sv443.net/jokeapi/v2/joke/Programming?type=twopart";
		jokeAdapter = new JokeAdapter();
		jokerWebClient = new JokerWebClientImpl(webClient, jokeAdapter);
		jokerService = new JokerServiceImpl(jokerWebClient, jokesUrlAny, jokesUrlProgramming);
		testContext.completeNow();
	}

	@RepeatedTest(3)
	@DisplayName("Check that jokerService gets a joke filled")
	void getJokeTest(VertxTestContext testContext) {
		jokerService.getJoke().subscribe(joke -> {
			testContext.verify(() -> {
				assertNotNull(joke);
				assertNotNull(joke.getSetup());
				assertNotNull(joke.getDelivery());
				testContext.completeNow();
			});
		}, (Single::error));
	}

}
