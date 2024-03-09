package edu.java.bot.util;

import java.net.URI;
import java.net.URL;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkUtil {

    public static URI parse(String str) {
        try {
            URI uri = URI.create(str);
            URL url = uri.toURL();
            return url.toURI();
        } catch (Exception e) {
            return null;
        }
    }
}
