package com.afaf.domain.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Joke {

	@JsonProperty("setup")
	private String setup;
	@JsonProperty("delivery")
	private String delivery;

	@JsonCreator
	public Joke(@JsonProperty("setup") String setup, @JsonProperty("delivery") String delivery) {
		this.setup = setup;
		this.delivery = delivery;
	}


	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public Joke getMajorJoke(Joke otherJoke) {
		return (this.isNotBlank() && otherJoke.isNotBlank()) ? (this.length() >= otherJoke.length()) ? this : otherJoke
				: new Joke("", "");
	}

	public boolean isNotBlank() {
		return StringUtils.isNotBlank(setup) && StringUtils.isNotBlank(delivery);
	}

	public int length() {
		return setup.length() + delivery.length();
	}

}
