package com.afaf.example_vertx.afaf_example_vertx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.afaf.example_vertx.MainExampleVertx;
import com.afaf.example_vertx.services.JokerService;
import com.afaf.example_vertx.services.JokerServiceImpl;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {
	
	private WebClient webClient;
	
	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		webClient = WebClient.create(vertx);
		vertx.deployVerticle(new MainExampleVertx(), testContext.succeeding(
				id -> testContext.completeNow())
    	);
	}
  
	@Test
	void getJokeTest(Vertx vertx, VertxTestContext testContext) {
		JokerService jokerService = new JokerServiceImpl(webClient);
		jokerService.getJoke().subscribe(joke -> {
			testContext.completeNow();
			System.out.println(joke.getSetup());
			System.out.println(joke.getDelivery());
		});
	}

}
