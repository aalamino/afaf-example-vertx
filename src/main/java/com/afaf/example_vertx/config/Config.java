package com.afaf.example_vertx.config;

import java.util.Optional;

import com.masmovil.commons.config.SharedConfig;

public class Config extends SharedConfig {

	private static Config INSTANCE;
	private static final Byte LOCK = (byte) 0;

	private Config() {
		super();
		var conf = Config.getSuperInstance();
		if (conf.getConfig().isPresent()) {
			this.config = conf.getConfig().get();
		}
	}

	/**
	 * @return a reference to the single instance of Config.
	 */
	public static Config getInstance() {
		if (INSTANCE == null) {
			synchronized (LOCK) {
				if (INSTANCE == null) {
					INSTANCE = new Config();
				}
			}
		}
		return INSTANCE;
	}

	public String getProjectID() {
		return getValue("gcloud.projectId").toString();
	}

	public boolean isTracingEnabled() {
		return (Boolean) getValue("gcloud.tracing.enabled");
	}


	public String getJokesUrl() {
		return getValue("exampleJokes.url").toString();
	}

	public Optional<Integer> getJokesRetries() {
		return Optional.ofNullable((Integer) getValue("exampleJokes.retries"));
	}

}
