package br.com.eventosdahora.api.exception;

public class TokenDontMatchException extends RuntimeException {
	
	private static final long serialVersionUID = -5556973234368559512L;

	public TokenDontMatchException(String msg) {
        super(msg);
    }

}
