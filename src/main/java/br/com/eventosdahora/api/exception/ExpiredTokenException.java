package br.com.eventosdahora.api.exception;

public class ExpiredTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 6861839449192496434L;

	public ExpiredTokenException(String msg) {
        super(msg);
    }

}
