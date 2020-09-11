package com.afaf.example_vertx.handlers;

import com.masmovil.commons.handler.DefaultRestHandler;

import io.vertx.reactivex.ext.web.RoutingContext;

public interface Joker extends DefaultRestHandler {

	String RESOURCE_NAME = "";

	void getJoke(final RoutingContext routingContext);

}
