package com.soprasteria;

import java.util.MissingResourceException;

import com.sopra.IBAN;

import junit.framework.TestCase;

public class IBANTest extends TestCase {

	
	   /**
     * @see IBAN#isCheckDigitValid(String)
     */
    public void testIsCheckDigitValid() {
        //Austria
        assertTrue(IBAN.isCheckDigitValid("AT611904300234573201"));
        //Belgium
        assertTrue(IBAN.isCheckDigitValid("BE62510007547061"));
        //Cyprus
        assertTrue(IBAN.isCheckDigitValid("CY17002001280000001200527600"));
        //France
        assertTrue(IBAN.isCheckDigitValid("FR1420041010050500013M02606"));
        //Greece
        assertTrue(IBAN.isCheckDigitValid("GR1601101250000000012300695"));
        //Ireland
        assertTrue(IBAN.isCheckDigitValid("IE29AIBK93115212345678"));
        //Malta
        assertTrue(IBAN.isCheckDigitValid("MT84MALT011000012345MTLCAST001S"));
        // Italy
        assertTrue(IBAN.isCheckDigitValid("IT30S0103002800000003802826"));
        // Spain 
        assertTrue(IBAN.isCheckDigitValid("ES5600810189860001234731"));
        // Germany
        assertTrue(IBAN.isCheckDigitValid("DE48545100670106398674"));
    }

    public void testIsCheckDigitValidTooShort() {
        //Austria
        assertFalse(IBAN.isCheckDigitValid("AT61190430023457321"));
        //Belgium
        assertFalse(IBAN.isCheckDigitValid("BE6251000754061"));
        //Cyprus
        assertFalse(IBAN.isCheckDigitValid("CY1700200128000001200527600"));
        //France
        assertFalse(IBAN.isCheckDigitValid("FR142004101005050013M02606"));
        //Greece
        assertFalse(IBAN.isCheckDigitValid("GR160110125000000012300695"));
        //Ireland
        assertFalse(IBAN.isCheckDigitValid("IE29AIBK9311522345678"));
    }

    public void testIsCheckDigitValidTooLong() {
        //Austria
        assertFalse(IBAN.isCheckDigitValid("AT6119043002345A73201"));
        //Belgium
        assertFalse(IBAN.isCheckDigitValid("BE625100074547061"));
        //Cyprus
        assertFalse(IBAN.isCheckDigitValid("CY170020012800000B01200527600"));
        //France
        assertFalse(IBAN.isCheckDigitValid("FR142004101005050J0013M02606"));
        //Greece
        assertFalse(IBAN.isCheckDigitValid("GR16011012500090000012300695"));
        //Ireland
        assertFalse(IBAN.isCheckDigitValid("IE29AIBK931152132345678"));
    }

    public void testIsCheckDigitValidWrongAccountNumbers() {
        //Austria
        assertFalse(IBAN.isCheckDigitValid("AT611904300234A73201"));
        //Belgium
        assertFalse(IBAN.isCheckDigitValid("BE62510074547061"));
        //Cyprus
        assertFalse(IBAN.isCheckDigitValid("CY17002002800000B01200527600"));
        //France
        assertFalse(IBAN.isCheckDigitValid("FR142004101005050J0013M0266"));
        //Greece
        assertFalse(IBAN.isCheckDigitValid("GR1601012500090000012300695"));
        //Ireland
        assertFalse(IBAN.isCheckDigitValid("IE29AIBK93152132345678"));
    }

    public void testIsCheckDigitValidWrongCountryCode() {
        //No IBAN format for this country code ZZ
        try {
            assertFalse(IBAN.isCheckDigitValid("ZZ611904300234A73201"));
        } catch (MissingResourceException mre) {
            //Expecting a missing resource exception about the iban length of ZZ
            assertTrue(mre.getMessage().indexOf("key length.ZZ") > 0);
        }
    }
}
