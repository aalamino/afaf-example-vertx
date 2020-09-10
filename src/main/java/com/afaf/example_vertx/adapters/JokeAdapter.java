package com.afaf.example_vertx.adapters;

import com.afaf.example_vertx.domain.Joke;
import com.afaf.example_vertx.dto.JokeDTO;
import com.masmovil.commons.adapter.DomainAdapter;

public class JokeAdapter implements DomainAdapter<JokeDTO, Joke>{

	@Override
	public JokeDTO adapt(Joke joke) {
		return new JokeDTO(joke.getSetup(), joke.getDelivery());
	}
	

}
