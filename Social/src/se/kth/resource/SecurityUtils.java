package se.kth.resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils
{
	public static String getHash(String value) throws NoSuchAlgorithmException
	{
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] bytes = value.getBytes();
	    byte[] digest = sha256.digest(bytes);
	    
		 String hash = "";
		 
		 for(byte b: digest) {
		     hash += String.format("%02x", b);
		 }
		 
		 return hash;
	}
}
