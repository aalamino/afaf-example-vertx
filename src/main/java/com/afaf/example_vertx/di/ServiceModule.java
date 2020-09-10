package com.afaf.example_vertx.di;

import javax.inject.Singleton;

import com.afaf.example_vertx.services.JokerService;
import com.afaf.example_vertx.services.JokerServiceImpl;

import dagger.Module;
import dagger.Provides;
import io.vertx.reactivex.ext.web.client.WebClient;

@Module
public class ServiceModule {
	
	@Provides
	@Singleton
	public JokerService provideJokerService(WebClient webClient) {
		return new JokerServiceImpl(webClient);
	}
}
