package fr.univ_lyon1.info.m1.tp_test;

import java.util.ArrayList;

public class CharManipulator implements ICharManipulator {

    @Override
    public String invertOrder(String s) {
        String s_inverted = new String();
        for (int i = s.length() - 1; i >= 0; i--) {
            s_inverted += s.charAt(i);
        }
        return s_inverted;
    }

    @Override
    public String invertCase(String s) {
        if (s.isEmpty()) {
            return "";
        }
        String s_invertCase = new String();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                s_invertCase += Character.toLowerCase(s.charAt(i));
            } else {
                s_invertCase += Character.toUpperCase(s.charAt(i));
            }
        }
        return s_invertCase;
    }

    public String removePattern(String string, String string2) {
        if (string.isEmpty() && string2.isEmpty()) {
            return "";
        } else if (string.isEmpty()) {
            return "";
        } else if (string2.isEmpty()) {
            return string;
        } else if (string == string2) {
            return "";
        }

        return string.replace(string2, "");
    }

}
