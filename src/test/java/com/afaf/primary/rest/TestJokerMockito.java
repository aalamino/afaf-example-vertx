package com.afaf.primary.rest;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.afaf.domain.adapter.JokeAdapter;
import com.afaf.domain.model.Joke;
import com.afaf.primary.rest.api.Joker;
import com.afaf.primary.rest.api.impl.JokerImpl;
import com.afaf.secondary.service.api.JokerService;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;

@ExtendWith(MockitoExtension.class)
public class TestJokerMockito {

	private Joker joker;

	@Mock
	private JokerService jokerService;
	@Mock
	private JokerWebClient jokerWebClient;
	@Mock
	private JokeAdapter adapter;

	private Joke joke;
	private JsonObject jokeJson;

	@Mock
	private RoutingContext routingContext;

	@Mock
	private HttpServerResponse response;


	@BeforeEach
	public void setup() {
		joker = new JokerImpl(jokerService, adapter);
		joke = new Joke("setup test", "delivery test");
		jokeJson = JsonObject.mapFrom(joke);
	}

	@Test
	void getJokeTest() {
		when(jokerService.getJoke()).thenReturn(Single.just(joke));

		doReturn(response).when(routingContext).response();
		doReturn(response).when(response).setStatusCode(HttpResponseStatus.OK.code());

		joker.getJoke(routingContext);
		verify(response).end(jokeJson.encodePrettily());
	}

}
