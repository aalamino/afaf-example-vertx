package com.afaf.domain.di;

import javax.inject.Singleton;
import com.afaf.secondary.service.api.JokerService;
import com.afaf.secondary.service.api.impl.JokerServiceImpl;
import com.afaf.tertiary.webclient.api.JokerWebClient;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

	@Provides
	@Singleton
	public JokerService provideJokerService(JokerWebClient jokerWebClient) {
		// TODO move this hardcoded URL to a config file
		var jokesUrlAny = "https://sv443.net/jokeapi/v2/joke/Any?type=twopart";
		// var jokesUrlAny = Config.getInstance().getJokesUrlAny();
		var jokesUrlProgramming = "https://sv443.net/jokeapi/v2/joke/Programming?type=twopart";
		// var jokesUrlProgramming = Config.getInstance().getJokesUrlProgramming();

		return new JokerServiceImpl(jokerWebClient, jokesUrlAny, jokesUrlProgramming);
	}
}
