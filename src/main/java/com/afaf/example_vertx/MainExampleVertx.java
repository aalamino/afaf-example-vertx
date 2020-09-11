package com.afaf.example_vertx;

import com.afaf.example_vertx.verticles.Server;

import io.netty.channel.DefaultChannelId;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;

public class MainExampleVertx extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainExampleVertx.class);

	public static void main(String[] args) {
		DefaultChannelId.newInstance();
		DeploymentOptions defaultDeploymentOptions = new DeploymentOptions();
		defaultDeploymentOptions.setInstances(1);
		VertxOptions vertxOptions = new VertxOptions();
		final Vertx vertx = Vertx.vertx(vertxOptions);
		vertx.rxDeployVerticle(Server.class.getName(), defaultDeploymentOptions).subscribe(verticleID -> {
			LOGGER.info(String.format("verticle ID %s deployed!", verticleID));
		});
	}
}
