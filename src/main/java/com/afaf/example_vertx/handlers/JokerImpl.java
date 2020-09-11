package com.afaf.example_vertx.handlers;

import javax.inject.Inject;

import com.afaf.example_vertx.domain.Joke;
import com.afaf.example_vertx.dto.JokeDTO;
import com.afaf.example_vertx.services.JokerService;
import com.masmovil.commons.adapter.DomainAdapter;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

public class JokerImpl implements Joker {

	private JokerService jokerService;
	private DomainAdapter<JokeDTO, Joke> adapter;

	@Inject
	public JokerImpl(JokerService jokerService, DomainAdapter<JokeDTO, Joke> adapter) {
		this.jokerService = jokerService;
		this.adapter = adapter;
	}

	@Override
	public void addHandlersTo(Router router) {
		addGetHandlerTo(router, RESOURCE_NAME, this::getJoke);
	}

	@Override
	public void getJoke(final RoutingContext context) {
		jokerService.getJoke().subscribe(joke -> {
			makeResponse(context, HttpResponseStatus.OK.code(), Json.encodePrettily(adapter.adapt(joke)));
		}, context::fail);
	}

}
