package com.afaf.domain.adapter;

import com.afaf.domain.model.Joke;
import com.masmovil.commons.adapter.DomainAdapter;
import io.vertx.core.json.JsonObject;

public class JokeAdapter implements DomainAdapter<Joke, JsonObject> {

	@Override
	public Joke adapt(JsonObject jsonObject) {
		return jsonObject.mapTo(Joke.class);
	}

	public JsonObject adaptJokeToJson(Joke joke) {
		return JsonObject.mapFrom(joke);
	}
	

}
