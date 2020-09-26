package com.afaf.secondary.service.api.impl;

import javax.inject.Inject;
import com.afaf.domain.model.Joke;
import com.afaf.secondary.service.api.JokerService;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import io.reactivex.Single;

public class JokerServiceImpl implements JokerService {

	private final JokerWebClient jokerWebClient;
	private final String jokesUrlAny;
	private final String jokesUrlProgramming;

	@Inject
	public JokerServiceImpl(final JokerWebClient jokerWebClient, final String jokeEndpointA,
			final String jokeEndpointB) {
		this.jokerWebClient = jokerWebClient;
		this.jokesUrlAny = jokeEndpointA;
		this.jokesUrlProgramming = jokeEndpointB;
	}

	@Override
	public Single<Joke> getJoke() {
		return getJokeAny().zipWith(getJokeProgramatic(), (any, prog) -> any.getMajorJoke(prog));
	}

	private Single<Joke> getJokeAny() {
		return jokerWebClient.getJoke(jokesUrlAny);
	}

	private Single<Joke> getJokeProgramatic() {
		return jokerWebClient.getJoke(jokesUrlProgramming);
	}


}
