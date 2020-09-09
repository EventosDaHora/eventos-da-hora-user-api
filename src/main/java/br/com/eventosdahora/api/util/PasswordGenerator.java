package br.com.eventosdahora.api.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class PasswordGenerator {

	private static final char[] ALL_CHARS = new char[62];
    private static final Random RANDOM = new Random();
    private static final int EXPIRATION = 60 * 24;

    static {
        for (int i = 48, j = 0; i < 123; i++) {
            if (Character.isLetterOrDigit(i)) {
                ALL_CHARS[j] = (char) i;
                j++;
            }
        }
    }

    public static String getRandomPassword(final int length) {
        final char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = ALL_CHARS[RANDOM.nextInt(ALL_CHARS.length)];
        }
        return new String(result);
    }

    public static String getRandomPassword() {
        return getRandomPassword(8);
    }
    
    public static String getRandomToken() {
    	return UUID.randomUUID().toString();
    }
    
    public static Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
}
