package com.afaf.domain.di;

import javax.inject.Singleton;
import com.afaf.primary.rest.api.impl.JokerImpl;
import dagger.Component;

@Singleton
@Component(modules = {AdapterModule.class, CommonsModule.class, ServiceModule.class, WebClientModule.class,
		VertxModule.class})
public interface HandlerComponent {

	JokerImpl buildJoker();

}
