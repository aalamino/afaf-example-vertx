package com.afaf.domain.verticle;

import java.util.Arrays;
import com.afaf.domain.config.Config;
import com.afaf.domain.di.DaggerHandlerComponent;
import com.afaf.primary.rest.api.Joker;
import com.afaf.primary.rest.api.impl.HelloWorld;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;
import io.vertx.reactivex.ext.web.handler.LoggerHandler;
import io.vertx.reactivex.ext.web.handler.ResponseTimeHandler;

public class Server extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	private HelloWorld helloWorld;
	private Joker joker;

	@Override
	public Completable rxStart() {
		vertx.exceptionHandler(error -> LOGGER.info(error.getMessage() + error.getCause()
				+ Arrays.toString(error.getStackTrace()) + error.getLocalizedMessage()));

		// var sharedConfig = Config.getInstance();
		var httpHost = Config.DEFAULT_HOST;
		var httpPort = Config.DEFAULT_PORT;
		// var httpHost = sharedConfig.getHost().orElse(Config.DEFAULT_HOST);
		// var httpPort = sharedConfig.getPort().orElse(Config.DEFAULT_PORT);

		helloWorld = new HelloWorld();
		var handlerComponent = DaggerHandlerComponent.create();
		joker = handlerComponent.buildJoker();

		return OpenAPI3RouterFactory.rxCreate(vertx, "webroot/swagger/swagger.yml").doOnError(LOGGER::error)
				.map(routerFactory -> {
					addGlobalHandlers(routerFactory);
					routeHandlersBySwaggerOperationId(routerFactory);
					Router router = routerFactory.getRouter();
					// router.get("/hello/:name").handler(helloWorld::helloByName);
					// router.get("/joker").handler(joker::getJoke);
					return router;
				}).flatMap(router -> startServer(httpHost, httpPort, router)).flatMapCompletable(httpServer -> {
					LOGGER.info("HTTP server started on http://{0}:{1}", httpHost, httpPort);
					return Completable.complete();
				});
	}

	@SuppressWarnings("unchecked")
	private void addGlobalHandlers(OpenAPI3RouterFactory routerFactory) {
		routerFactory.addGlobalHandler(CorsHandler.create("*")).addGlobalHandler(LoggerHandler.create())
				.addGlobalHandler(ResponseTimeHandler.create()).addGlobalHandler(BodyHandler.create());
	}

	private void routeHandlersBySwaggerOperationId(OpenAPI3RouterFactory routerFactory) {
		routerFactory.addHandlerByOperationId("helloByName", helloWorld::helloByName);
		routerFactory.addHandlerByOperationId("getJoke", joker::getJoke);
	}

	private Single<HttpServer> startServer(String httpHost, Integer httpPort, Router router) {
		return vertx.createHttpServer().requestHandler(router).rxListen(httpPort, httpHost);
	}


}
