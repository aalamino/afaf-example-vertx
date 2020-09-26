package com.afaf.primary.rest.api;

import com.masmovil.commons.handler.DefaultRestHandler;

import io.vertx.reactivex.ext.web.RoutingContext;

public interface Joker extends DefaultRestHandler {

	String RESOURCE_NAME = "";

	void getJoke(final RoutingContext routingContext);

}
