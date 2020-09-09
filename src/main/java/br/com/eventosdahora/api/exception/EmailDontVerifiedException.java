package br.com.eventosdahora.api.exception;

public class EmailDontVerifiedException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = -7465528018838209313L;

	public EmailDontVerifiedException(String msg) {
        super(msg);
    }

}
