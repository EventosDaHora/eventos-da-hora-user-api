package br.com.eventosdahora.api.exception;

public class PasswordDontMatchException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7465528018838209313L;

	public PasswordDontMatchException(String msg) {
        super(msg);
    }

}
