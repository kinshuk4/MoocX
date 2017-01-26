package org.mk.training.plexer.impl;

import org.mk.training.plexer.InputHandler;
import org.mk.training.plexer.InputHandlerFactory;

public class GenericInputHandlerFactory implements InputHandlerFactory {
	private final Class<? extends InputHandler> handlerClass;

	public GenericInputHandlerFactory(Class<? extends InputHandler> handlerClass) {
		this.handlerClass = handlerClass;
	}

	public InputHandler newHandler() throws IllegalAccessException,
			InstantiationException {
		return handlerClass.newInstance();
	}
}
