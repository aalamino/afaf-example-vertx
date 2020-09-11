package com.afaf.example_vertx.handlers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.afaf.example_vertx.domain.Joke;
import com.afaf.example_vertx.dto.JokeDTO;
import com.afaf.example_vertx.services.JokerService;
import com.masmovil.commons.adapter.DomainAdapter;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.json.Json;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;

@ExtendWith(MockitoExtension.class)
public class TestJokerMockito {

	private Joker joker;

	@Mock
	private JokerService jokerService;
	@Mock
	private DomainAdapter<JokeDTO, Joke> adapter;

	private JokeDTO jokeDTO;
	private Joke joke;

	@Mock
	private RoutingContext routingContext;

	@Mock
	private HttpServerResponse response;


	@BeforeEach
	public void setup() {
		joker = new JokerImpl(jokerService, adapter);
		jokeDTO = new JokeDTO("setup test", "delivery test");
		joke = new Joke("setup test", "delivery test");
	}

	@Test
	void getJokeTest() {
		doReturn(jokeDTO).when(adapter).adapt(joke);
		when(jokerService.getJoke()).thenReturn(Single.just(joke));

		doReturn(response).when(routingContext).response();
		doReturn(response).when(response).setStatusCode(HttpResponseStatus.OK.code());

		joker.getJoke(routingContext);
		verify(response).end(Json.encodePrettily(jokeDTO));
	}

}
