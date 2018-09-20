package com.soprasteria;

import com.sopra.France;

import junit.framework.TestCase;

public class Test extends TestCase {
	
	 public void testPath (){
		  France iban= new France();
	
		  assertEquals(27, iban.getValidIBANLength());
		 
	}

}
