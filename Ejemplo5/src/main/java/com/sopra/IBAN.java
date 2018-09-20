package com.sopra;

import java.math.BigInteger;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.sopra.util.SepaConsts;




public abstract class IBAN {
	
	/** Logger */
	private static final Logger logger = Logger.getLogger(IBAN.class);
	
	private static String countryCode;
	
    IBAN(String countryCode){
		
    	IBAN.countryCode=countryCode;
	}
	
	
	

	/**
	 * 
	 * @param countryCode
	 * @param bban
	 * @param bankIdentifier (mandatory for some countries : Estonia, Gibraltar, Hungary, Ireland... :-()
	 * 
	 * @return iban
	 */

	public  String generateIBANAfterValidation(String bban){
		String iban = bban + countryCode + "00";  
		BigInteger numericIBAN = getNumericIBAN(iban, true);
		int checkDigit = 98-numericIBAN.mod(new BigInteger("97")).intValue();
		/*if ("PT".equals(countryCode))
			return  countryCode+ "50" + checkDigit + bban;
		else*/
			return  countryCode+ (checkDigit<10 ? "0":"") + checkDigit + bban;
	}
	
	
	
	public  String generateIBAN(String bban) {
	logger.info("bban.length() " + bban.length());
	if((getValidIBANLength()-4)!=bban.length()) return null ;//"invalid length bban";
	logger.info("valid length");
	
	if(checkBban(bban)){
		logger.info("valid check");		
		return generateIBANAfterValidation(bban);
	}
			else return null; //"invalid BBAN";// TODO Auto-generated method stub
		
	}
	
	

	
	
	public abstract boolean checkBban(String bban);
	
	public abstract String generateBban(String a, String b, String c);

	public String generateConcatenatedBban(String bban){
		if (countryCode.equals("HU"))
			return generateBban(bban.substring(7, 24), bban.substring(0, 7), "");
		if (countryCode.equals("PL"))
			return generateBban(bban.substring(8, 24), bban.substring(0, 8), "");
		if (countryCode.equals("LU"))
			return generateBban(bban.substring(3, 16), bban.substring(0, 3), "");
		if (countryCode.equals("RO"))
			return generateBban(bban.substring(4, 20), bban.substring(0, 4), "");
		if (countryCode.equals("PT"))
			return generateBban(bban.substring(8, 19), bban.substring(0, 4), bban.substring(4, 8));
		if (countryCode.equals("FR") || countryCode.equals("MC"))
			return generateBban(bban.substring(10, 21), bban.substring(0, 5), bban.substring(5, 10));
		if (countryCode.equals("IT"))
			return generateBban(bban.substring(10, 22), bban.substring(0, 5), bban.substring(5, 10));
		if (countryCode.equals("BE"))
			return generateBban(bban.substring(3, 10),bban.substring(0, 3), "");
		if (countryCode.equals("ES"))
			return generateBban(bban.substring(8, 18), bban.substring(0, 4), bban.substring(4, 8));
		if (countryCode.equals("DE"))
			return generateBban(bban.substring(8, 18), bban.substring(0, 8), "");
		return null;
	}

	/**
	 * 
	 * @param iban
	 * @param isCheckDigitAtEnd (�true� if you need to move first four characters to end of string to put check digit at end)
	 * @return numeric iban format. 
	 */
	
   
	public static  BigInteger getNumericIBAN(String iban, boolean isCheckDigitAtEnd) {
    
		String endCheckDigitIBAN = iban;
        
        if(!isCheckDigitAtEnd) {
            endCheckDigitIBAN = iban.substring(4) + iban.substring(0, 4);
        }
        StringBuffer numericIBAN = new StringBuffer();
        for (int i = 0; i < endCheckDigitIBAN.length(); i++) {
        	
                numericIBAN.append(Character.digit(endCheckDigitIBAN.charAt(i), Character.MAX_RADIX));
        }
        return new BigInteger(numericIBAN.toString());
   }
	
	
	

	/**
	 * Determines if the given IBAN is valid based on the check digit.
	 * To validate the checksum:
	 * 1. Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid.
	 * 2. Move the four initial characters to the end of the string.
	 * 3. Replace the letters in the string with digits, expanding the string as necessary, such that A=10, B=11 and Z=35.
	 * 4. Convert the string to an integer and mod-97 the entire number.
	 * If the remainder is 1 you have a valid IBAN number.
	 * @param iban
	 * @return boolean indicating if specific IBAN has a valid check digit
	 */


	public static  boolean isCheckDigitValid(String iban) {
		if (iban==null) return false;
    
	
		logger.info("iban.length() " + iban.length());
		int validIBANLength = getValidIBANLength();
		if (validIBANLength < 4) return false;
		if (iban.length() != validIBANLength) return false;
    
		BigInteger numericIBAN = getNumericIBAN(iban, false);

		int checkDigit = numericIBAN.mod(new BigInteger("97")).intValue();
		return checkDigit == 1;
	}
	

	  
	/**
     * Using the IBAN.properties file gets the valid fixed length value for a country code.
     * Only uses the first 2 characters of the given string.
     * @param countryCode
     * @return
     */
    
	public static  int getValidIBANLength() {
        String length = ResourceBundle.getBundle(SepaConsts.IbanProperties).getString("length."+countryCode);
        if (length == null) return -1;
        return Integer.valueOf(length).intValue();
    }






	public String getCountryCode() {
		return countryCode;
	}




	public void setCountryCode(String countryCode) {
		IBAN.countryCode = countryCode;
	}


	

	
	
	
	

}