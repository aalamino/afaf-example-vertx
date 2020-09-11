package com.afaf.example_vertx.services;

import javax.inject.Inject;

import com.afaf.example_vertx.domain.Joke;

import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.client.WebClient;

public class JokerServiceImpl implements JokerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JokerServiceImpl.class);
	
	private final WebClient webClient;
	private final String jokesUrlAny;
	private final String jokesUrlProgramming;
	
	@Inject
	public JokerServiceImpl(final WebClient webClient, final String jokeEndpointA, final String jokeEndpointB) {
		this.webClient = webClient;
		this.jokesUrlAny = jokeEndpointA;
		this.jokesUrlProgramming = jokeEndpointB;
	}
	
	@Override
	public Single<Joke> getJoke() {
		return getJokeAny()
				.zipWith(getJokeProgramatic(), 
						(any, prog) -> any.getMajorJoke(prog)
				);
	}
	
	private Single<Joke> getJokeAny(){
		return commonsGetJoke(jokesUrlAny);
	}
	
	private Single<Joke> getJokeProgramatic(){
		return commonsGetJoke(jokesUrlProgramming);
	}
	
	private Single<Joke> commonsGetJoke(String url){
		return webClient.getAbs(url)
				.putHeader("Accept", "application/json")
				.rxSend()
				.retry(jokesRetries)
				.flatMap(response 
						-> {
							LOGGER.info("Received response with status code : " + 
									response.statusCode());
							return Single.just(response.bodyAsJsonObject()
									.mapTo(Joke.class));
						}
				)
				.doOnError(System.err::println)
				.onErrorResumeNext(Single::error);
	}
	
}
