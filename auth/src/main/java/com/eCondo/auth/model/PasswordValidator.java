package com.eCondo.auth.model;

import java.util.HashSet;

import com.eCondo.auth.exceptions.InvalidPasswordException;

public class PasswordValidator {

    public PasswordValidator() {}

    public Boolean validate(final String password) throws InvalidPasswordException{
        int[] count = {0};

        if(!minSize(password)){
            throw new InvalidPasswordException("Invalid password - min size is 8.");

        }

        hasUpperCase(password, count);
        hasLowerCase(password, count);
        hasDigit(password, count);
        hasNonAlphaNumeric(password, count);

        if(count[0] < 3)
            throw new InvalidPasswordException("Invalid password - has to have at least three of: lowecase, uppercase, digit or non alphanumeric character");
        
        return true;
    }

    private Boolean hasUpperCase(final String password, int[] count){
        HashSet<Character> hs = new HashSet<>();
        String upperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        for(int i = 0; i < upperCase.length(); i++)
            hs.add(upperCase.toCharArray()[i]);

        for(Character c : password.toCharArray()){
            if(hs.contains(c)){
                count[0]++;
                return true;
            }
        }
        return false;
    }

    private Boolean hasLowerCase(final String password, int[] count){
        HashSet<Character> hs = new HashSet<>();
        String lowerCase = "qwertyuiopasdfghjklzxcvbnm";
        for(int i = 0; i < lowerCase.length(); i++)
            hs.add(lowerCase.toCharArray()[i]);

        for(Character c : password.toCharArray()){
            if(hs.contains(c)){
                count[0]++;
                return true;
            }
        }
        return false;
    }

    private Boolean hasDigit(final String password, int[] count){
        HashSet<Character> hs = new HashSet<>();
        String digits = "0123456789";
        for(int i = 0; i < digits.length(); i++)
            hs.add(digits.toCharArray()[i]);

        for(Character c : password.toCharArray()){
            if(hs.contains(c)){
                count[0]++;
                return true;
            }        
        }
        return false;
    }

    private Boolean hasNonAlphaNumeric(final String password, int[] count){
        HashSet<Character> hs = new HashSet<>();
        String charSet = "'-!\"#$%&()*,./:;?@[]^_`{|}~+<=>";
        for(int i = 0; i < charSet.length(); i++)
            hs.add(charSet.toCharArray()[i]);

        for(Character c : password.toCharArray()){
            if(hs.contains(c)){
                count[0]++;
                return true;
            }
        }
        return false;
    }

    private Boolean minSize(final String password){
        int minSize = 8;

        return password.length() >= minSize;
    }

    
    
}
