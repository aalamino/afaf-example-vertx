package com.afaf.example_vertx.services;

import com.afaf.example_vertx.domain.Joke;

import io.reactivex.Single;

public interface JokerService {
	
	Single<Joke> getJoke();

	String jokesUrlAny = "https://sv443.net/jokeapi/v2/joke/Any?type=twopart";
	String jokesUrlProgramming= "https://sv443.net/jokeapi/v2/joke/Programming?type=twopart";
//	String jokesUrl = Config.getInstance().getJokesUrl();
	Integer jokesRetries = 3;
//	Integer jokesRetries = Config.getInstance().getJokesRetries().orElse(Config.DEFAULT_RETRIES);

}
