package com.afaf.domain.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;

@Module
public class CommonsModule {

	@Provides
	@Singleton
	public WebClient provideWebClient() {
		return WebClient.create(Vertx.currentContext().owner());
	}

}
