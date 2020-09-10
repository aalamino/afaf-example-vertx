package com.afaf.example_vertx.services;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

@ExtendWith(VertxExtension.class)
public class TestJokerServiceVertx {
	
	private WebClient webClient;
	private JokerService jokerService;
	
	@BeforeEach
	void prepare(Vertx vertx, VertxTestContext testContext) {
		webClient = WebClient.create(vertx);
		jokerService = new JokerServiceImpl(webClient);
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
		});
	}

}
