package dev.feldmann.redstonegang.common.utils.formaters;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<String> quebra(int praquebra, String s) {
        List<String> list = new ArrayList();
        int sempula = 0;
        int lastpulo = 0;
        for (int x = 0; x < s.length(); x++) {
            sempula++;
            if (sempula >= praquebra) {
                if (s.charAt(x) == ' ') {
                    list.add(s.substring(lastpulo, x).trim());
                    lastpulo = x;
                    sempula = 0;
                }
            }
        }
        list.add(s.substring(lastpulo, s.length()).trim());
        return list;
    }

    public static boolean isOnlyNumbersAndLetters(String s) {
        return s.matches("([A-Za-z0-9])+");
    }

    public static String quebraNewLine(int praquebra, String s) {
        int sempula = 0;
        int lastpulo = 0;
        String r = "";
        for (int x = 0; x < s.length(); x++) {
            sempula++;
            if (sempula >= praquebra) {
                if (s.charAt(x) == ' ') {
                    if (!r.isEmpty()) {
                        r += '\n';
                    }
                    r += s.substring(lastpulo, x).trim();
                    lastpulo = x;
                    sempula = 0;
                }
            }
        }
        if (!r.isEmpty()) {
            r += '\n';
        }
        r += s.substring(lastpulo, s.length()).trim();
        return r;
    }

    public static String capitalizeFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }

    public static String generateSpaces(int qtd) {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < qtd; x++) {
            builder.append(' ');
        }
        return builder.toString();
    }

    public static String[] combineArgs(String... args) {
        String data = "";
        for (String ar : args) {
            if (!data.isEmpty()) {
                data += " ";
            }
            data += ar;
        }
        List<String> tokens = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        boolean insideQuote = false;

        for (char c : data.toCharArray()) {

            boolean fechou = false;
            if (c == '"') {
                insideQuote = !insideQuote;
                if (!insideQuote) {
                    fechou = true;
                }
            }


            if (c == ' ' && !insideQuote) {//when space is not inside quote split..
                tokens.add(sb.toString()); //token is ready, lets add it to list
                sb.delete(0, sb.length()); //and reset StringBuilder`s content
            } else {
                if (fechou) {
                    sb.deleteCharAt(0);
                } else {
                    sb.append(c);//else add character to token
                }
            }
        }
//lets not forget about last token that doesn't have space after it
        if (sb.length() > 0)
            tokens.add(sb.toString());
        String[] array = tokens.toArray(new String[0]);
        return array;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
