package br.com.eventosdahora.api.exception;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -7816417838686821453L;

	public UnauthorizedException() {
        super("Unauthorized");
    }
}
