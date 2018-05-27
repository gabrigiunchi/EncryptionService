package main;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * AES cipher implementation
 * 
 * @author Gabriele Giunchi
 *
 */
public class AES {
	
	private static final String ALGORITHM = "AES";
	private Key secretKey;
	
	private AES() {} 
	
	private AES(final String key) {
		this.setKey(key);
	}
	
	public static AES getInstance() {
		return new AES();
	}
	
	/**
	 * Create a new instance of the AES cipher initialized with the given key
	 * 
	 * @param key
	 * @return
	 */
	public static AES getInstance(final String key) {
		return new AES(key);
	}
	
	private Cipher getCipher(final int mode) {
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(mode, secretKey);
			return cipher;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isKeyInitialized() {
		return this.secretKey != null;
	}
	
	public void setKey(final String keyString) {
		try {
        	final MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = keyString.getBytes(StandardCharsets.UTF_8);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKey = new SecretKeySpec(key, ALGORITHM);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Encrypt an array of bytes
	 * 
	 * @param plaintext
	 * @return
	 */
	public byte[] encrypt(final byte[] plainText) {
		try {
			final Cipher cipher = this.getCipher(Cipher.ENCRYPT_MODE);
			return cipher.doFinal(plainText);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		return new byte[0];
	}
	
	/**
	 * Decrypt an array of bytes
	 * 
	 * @param cipherText
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception 
	 */
	public byte[] decrypt(final byte[] cipherText) throws IllegalBlockSizeException, BadPaddingException {
		final Cipher cipher = this.getCipher(Cipher.DECRYPT_MODE);
		return cipher.doFinal(cipherText);
	}
	
	/**
	 * Encrypt the given plaintex with the key previously provided and return a string representation
	 * @param plainText
	 * @return
	 */
	public String encrypt(final String plainText) {
        return Base64.getEncoder().encodeToString(this.encrypt(plainText.getBytes(StandardCharsets.UTF_8)));
	}
	
	
	/**
	 * Dencrypt the given ciphertext with the key previously provided
	 * @param cipherText
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public String decrypt(final String cipherText) throws IllegalBlockSizeException, BadPaddingException {
        return new String(this.decrypt(Base64.getDecoder().decode(cipherText)), StandardCharsets.UTF_8);
	}
}