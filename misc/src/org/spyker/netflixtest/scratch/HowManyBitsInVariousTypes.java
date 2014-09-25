package org.spyker.netflixtest.scratch;

import java.text.NumberFormat;

public class HowManyBitsInVariousTypes {
    
    private static int[] lookupVals;

    public static void main(String[] args) {
        //int testInt1 = (int)(Math.random() * (double)Integer.MAX_VALUE);
        //int testInt1 = Integer.MAX_VALUE;
        //int testInt1 = Integer.MIN_VALUE;
        //int testInt1 = (int)(Math.random() * (double)Integer.MIN_VALUE);
        //int testInt1 = 0;
        int testInt1 = -2;
        System.out.println("min int = " + NumberFormat.getNumberInstance().format(Integer.MIN_VALUE));
        System.out.println("max int = " + NumberFormat.getNumberInstance().format(Integer.MAX_VALUE));
        System.out.println("the orig int = " + NumberFormat.getNumberInstance().format(testInt1));
        System.out.println("the orig int = " + getPaddedHexStringInt(testInt1));
        printInHex(testInt1);
        System.out.println("how many bits (slow) = " + howManyBitsSlow(testInt1));
        
        initializeLookupVals();
        System.out.println("how many bits (quick) = " + howManyBitsQuick(testInt1));
    }
    
    public static void printInHex(int theInt) {
        // integer is 32 bits = 4 bytes
        byte topTopByte = (byte)(theInt >> 24);
        byte midTopByte = (byte)(theInt >> 16);
        byte midBotByte = (byte)(theInt >> 8);
        byte botBotByte = (byte)(theInt);
        
        System.out.println("tt = " + getPaddedHexStringByte(topTopByte));
        System.out.println("mt = " + getPaddedHexStringByte(midTopByte));
        System.out.println("mb = " + getPaddedHexStringByte(midBotByte));
        System.out.println("bb = " + getPaddedHexStringByte(botBotByte));
        
        
        int newInt = 0x00000000;
        System.out.println("newInt now equals = " + getPaddedHexStringInt(newInt));
        newInt = newInt | (topTopByte << 24);
        System.out.println("newInt now equals = " + getPaddedHexStringInt(newInt));
        newInt = newInt | ((0xFF & midTopByte) << 16);
        System.out.println("newInt now equals = " + getPaddedHexStringInt(newInt));
        newInt = newInt | ((0xFF & midBotByte) << 8);
        System.out.println("newInt now equals = " + getPaddedHexStringInt(newInt));
        newInt = newInt | (0xFF & botBotByte);
        System.out.println("newInt now equals = " + getPaddedHexStringInt(newInt));
        System.out.println("the new int = " + NumberFormat.getNumberInstance().format(newInt));
    }
    
    private static String getPaddedHexStringByte(byte theByte) {
        String hexString = String.format("%02X ", theByte);
        return hexString;
    }
    
    private static String getPaddedHexStringInt(int theInt) {
      String zeros = "00000000";
      String hexString = Integer.toHexString(theInt);
      int len = hexString.length();
      
      String result = "0x";
      result = result + zeros.substring(0, 8 - len);
      result = result + hexString;
      return result;
    }
    
    private static int howManyBitsSlow(int i) {
        int num = 0;
        for (int ii = 0; ii <= 31; ii++) {
            byte theByte = (byte)(i >>> ii);
            theByte = (byte)(theByte & 0x01);
            if (theByte == 1) {
                num++;
            }
        }
        return num;
    }
    
    private static int howManyBitsQuick(int i) {
        byte b1 = (byte)(i >> 24);
        byte b2 = (byte)(i >> 16);
        byte b3 = (byte)(i >> 8);
        byte b4 = (byte)(i);
        
        return lookup(b1) + lookup(b2) + lookup(b3) + lookup(b4);
    }
    
    private static int lookup(byte b) {
        System.out.println(getPaddedHexStringByte(b));
        int theInt = 0;
        // TODO: Understand why this 0xFF is needed)
        int theInt2 = theInt | b;
        System.out.println(getPaddedHexStringInt(theInt2));
        theInt = theInt | (0xFF & b);
        System.out.println(getPaddedHexStringInt(theInt));
        return lookupVals[theInt];
    }
    
    private static void initializeLookupVals() {
        lookupVals = new int[0xFF+1];
        for (int ii = 0; ii <= 0xFF; ii++) {
            int numBits = howManyBitsSlow(ii);
            lookupVals[ii] = numBits;
        }
    }
}
