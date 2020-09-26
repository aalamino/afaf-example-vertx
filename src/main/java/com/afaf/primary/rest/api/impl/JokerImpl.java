package com.afaf.primary.rest.api.impl;

import javax.inject.Inject;
import com.afaf.domain.adapter.JokeAdapter;
import com.afaf.primary.rest.api.Joker;
import com.afaf.secondary.service.api.JokerService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

public class JokerImpl implements Joker {

	private JokerService jokerService;
	private JokeAdapter jokeAdapter;

	@Inject
	public JokerImpl(JokerService jokerService, JokeAdapter jokeAdapter) {
		this.jokerService = jokerService;
		this.jokeAdapter = jokeAdapter;
	}

	@Override
	public void addHandlersTo(Router router) {
		addGetHandlerTo(router, RESOURCE_NAME, this::getJoke);
	}

	@Override
	public void getJoke(final RoutingContext context) {
		jokerService.getJoke().subscribe(joke -> {
			makeResponse(context, HttpResponseStatus.OK.code(), jokeAdapter.adaptJokeToJson(joke).encodePrettily());
		}, context::fail);
	}

}
