package com.afaf.example_vertx.di;

import dagger.Provides;
import dagger.Module;
import io.vertx.reactivex.core.Vertx;
import javax.inject.Singleton;

@Module
public class VertxModule {

	@Provides
	@Singleton
	public Vertx provideVertx() {
		return Vertx.currentContext().owner();
	}

}
