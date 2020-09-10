package com.afaf.example_vertx.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.vertx.reactivex.ext.web.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class TestJokerServiceMockito {

	@Mock
	private WebClient webClient;
	private JokerService jokerService;
	
	@BeforeEach
	void prepare() {
		jokerService = new JokerServiceImpl(webClient);
	}
	
	@RepeatedTest(3)
	@DisplayName("Check that jokerService gets a joke")
	void getJokeTest() {
		when(jokerService.getJoke()).thenReturn(any());
	}

}
