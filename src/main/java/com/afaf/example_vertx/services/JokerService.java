package com.afaf.example_vertx.services;

import com.afaf.example_vertx.domain.Joke;

import io.reactivex.Single;

public interface JokerService {

	Single<Joke> getJoke();

	// TODO move this hardcoded Integer to a config file
	Integer jokesRetries = 3;
	// Integer jokesRetries = Config.getInstance().getJokesRetries().orElse(Config.DEFAULT_RETRIES);

}
