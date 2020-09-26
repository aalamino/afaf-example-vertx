package com.afaf.domain.di;

import javax.inject.Singleton;
import com.afaf.domain.adapter.JokeAdapter;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import com.afaf.tertiary.webclient.api.impl.JokerWebClientImpl;
import dagger.Module;
import dagger.Provides;
import io.vertx.reactivex.ext.web.client.WebClient;

@Module
public class WebClientModule {

	@Provides
	@Singleton
	public JokerWebClient provideJokerWebClient(WebClient webClient, JokeAdapter jokeAdapter) {
		return new JokerWebClientImpl(webClient, jokeAdapter);
	}

}
