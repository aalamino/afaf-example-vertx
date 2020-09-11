package com.afaf.example_vertx.commons;

public class CommonDto {

	private static CommonDto instance;
	private static final Byte LOCK = Byte.valueOf((byte) 0);

	public static CommonDto getInstance() {
		if (instance == null) {
			synchronized (LOCK) {
				if (instance == null) {
					instance = new CommonDto();
				}
			}
		}

		return instance;
	}

	private CommonDto() {}

	private int status;
	private String body;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
