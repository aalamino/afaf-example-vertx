package com.afaf.example_vertx.di;

import javax.inject.Singleton;

import com.afaf.example_vertx.handlers.JokerImpl;

import dagger.Component;

@Singleton
@Component(modules = {AdapterModule.class, CommonsModule.class, ServiceModule.class, VertxModule.class})
public interface HandlerComponent {

	JokerImpl buildJoker();

}
