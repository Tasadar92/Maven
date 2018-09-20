package com.sopra;

import java.math.BigInteger;

public class France extends IBAN {
	public France() {
		super("FR");
	}

	/**
	 * @param a : account number (11 an)
	 * @param b1 : bank (5n) 
	 * @param b2 : branch (5n) 
	 *
	 * @return bban = b1(5n)+b2(5n)+a(11an)+c(2n) / c(2n)=checkDigitFr(a,b1,b2). 
	 */
	public  String generateBban(String account, String bank, String branch){
		return bank+branch+account+checkDigit(account, bank, branch);
	}
	/**
	 *
	 * check a Rib code
	 * @return boolean
	 */
	public boolean checkBban(String rib) {

		return checkDigit(rib.substring(10,21),rib.substring(0,5),rib.substring(5,10)).equals(rib.substring(21));
	}

	/**
	 * @param account
	 * @param bank
	 * @param branch
	 * @return c(2n) "Clé RIB"
	 */
	public  String checkDigit(String account, String bank, String branch) {
		String rib=bank+branch+account+"00";
		StringBuilder extendedRib = new StringBuilder(rib.length());

		for (char currentChar : rib.toCharArray()) {
			//Works on base 36
			int currentCharValue = Character.digit(currentChar, Character.MAX_RADIX);
			//Convert character to simple digit
			extendedRib.append(currentCharValue<10?currentCharValue:(currentCharValue + (int) StrictMath.pow(2,(currentCharValue-10)/9))%10);
		}

		int remainder= 97-new BigInteger(extendedRib.toString()).mod(new BigInteger("97")).intValue();
		if(remainder<10){
			return (remainder == 0)? "97":"0"+remainder;
		} else {
			return String.valueOf(remainder);
		}

	}
}