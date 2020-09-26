package com.afaf.domain.di;

import javax.inject.Singleton;
import com.afaf.domain.adapter.JokeAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

	@Provides
	@Singleton
	public JokeAdapter provideJokeAdapter() {
		return new JokeAdapter();
	}
}
