package com.afaf.tertiary.webclient.api;

import com.afaf.domain.model.Joke;
import io.reactivex.Single;

public interface JokerWebClient {
	
	Single<Joke> getJoke(final String url);

}
