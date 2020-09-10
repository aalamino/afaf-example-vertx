package com.afaf.example_vertx.dto;

public class JokeDTO {
	
	private String setup;
	private String delivery;
    
    public JokeDTO() {
    	
    }
    
    public JokeDTO(String setup, String delivery) {
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


}
