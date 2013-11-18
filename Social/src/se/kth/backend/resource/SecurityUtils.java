package se.kth.backend.resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils
{
	public static String getHash(String value)
	{
	    MessageDigest sha256;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
		    byte[] bytes = value.getBytes();
		    byte[] digest = sha256.digest(bytes);
			 String hash = "";
			 
			 for(byte b: digest) {
			     hash += String.format("%02x", b);
			 }
			 
			 return hash;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
}
