package com.afaf.example_vertx.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.afaf.example_vertx.MainExampleVertx;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {


	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		vertx.deployVerticle(new MainExampleVertx(), testContext.succeeding(id -> testContext.completeNow()));
	}

	@Test
	@DisplayName("Execute main during 3 seconds")
	void mainExampleVertxTest(VertxTestContext testContext) {
		try {
			MainExampleVertx.main(null);
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		testContext.completeNow();
	}


}
