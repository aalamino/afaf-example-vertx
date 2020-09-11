package com.afaf.example_vertx.di;

import javax.inject.Singleton;

import com.afaf.example_vertx.adapters.JokeAdapter;
import com.afaf.example_vertx.domain.Joke;
import com.afaf.example_vertx.dto.JokeDTO;
import com.masmovil.commons.adapter.DomainAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

	@Provides
	@Singleton
	public DomainAdapter<JokeDTO, Joke> provideJokeDTOAdapter() {
		return new JokeAdapter();
	}
}
