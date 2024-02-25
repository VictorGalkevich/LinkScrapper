package edu.java.bot.util;

import edu.java.bot.entity.Link;
import java.net.URI;
import java.net.URL;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkUtil {

    public static Link parse(String str) {
        try {
            URI uri = URI.create(str);
            URL url = uri.toURL();
            return Link.builder()
                    .uri(url.toString().charAt(str.length() - 1) == '/'
                            ? str
                            : str + "/")
                    .host(url.getHost())
                    .protocol(url.getProtocol())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
