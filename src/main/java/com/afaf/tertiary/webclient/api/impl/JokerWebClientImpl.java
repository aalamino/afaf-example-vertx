package com.afaf.tertiary.webclient.api.impl;

import javax.inject.Inject;
import com.afaf.domain.adapter.JokeAdapter;
import com.afaf.domain.model.Joke;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.client.WebClient;

public class JokerWebClientImpl implements JokerWebClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(JokerWebClientImpl.class);

	private WebClient webClient;
	private JokeAdapter jokeAdapter;

	@Inject
	public JokerWebClientImpl(final WebClient webClient, JokeAdapter jokeAdapter) {
		this.webClient = webClient;
		this.jokeAdapter = jokeAdapter;
	}

	@Override
	public Single<Joke> getJoke(String url) {
		Integer jokesRetries = 3;
		// Integer jokesRetries = Config.getInstance().getJokesRetries().orElse(Config.DEFAULT_RETRIES);
		return webClient.getAbs(url).putHeader("Accept", "application/json").rxSend().retry(jokesRetries)
				.flatMap(response -> {
					LOGGER.info("Received response with status code : " + response.statusCode());
					return Single.just(jokeAdapter.adapt(response.bodyAsJsonObject()));
				}).doOnError(System.err::println).onErrorResumeNext(Single::error);
	}

}
