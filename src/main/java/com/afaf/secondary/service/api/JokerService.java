package com.afaf.secondary.service.api;

import com.afaf.domain.model.Joke;
import io.reactivex.Single;

public interface JokerService {

	Single<Joke> getJoke();


}
