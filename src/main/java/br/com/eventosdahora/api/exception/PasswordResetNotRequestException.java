package br.com.eventosdahora.api.exception;

public class PasswordResetNotRequestException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -481116466331429121L;

	public PasswordResetNotRequestException(String msg) {
        super(msg);
    }

}
