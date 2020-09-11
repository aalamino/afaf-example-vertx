package com.afaf.example_vertx.handlers;

import org.apache.commons.lang3.StringUtils;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.reactivex.ext.web.RoutingContext;

public class HelloWorld {

	public void helloByName(final RoutingContext context) {

		String name = StringUtils.isNotBlank(context.request().getParam("name")) ? context.request().getParam("name")
				: "world";
		context.response().putHeader("content-type", "text/plain").setStatusCode(HttpResponseStatus.OK.code())
				.end(String.format("hello %s!", name));
	}
}
