package com.afaf.example_vertx.commons;

import java.util.logging.Logger;

import cucumber.api.java.After;

public class CommonsHooks {

	private final static Logger LOGGER = Logger.getLogger(CommonsHooks.class.getName());

	@After("@CleanScenario")
	public void cleanScenario() {
		LOGGER.info("Clean Scenario");
	}

}
