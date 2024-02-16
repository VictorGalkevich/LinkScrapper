package edu.java.bot.util;

import edu.java.bot.entity.Link;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

@Component
public class LinkUtil {
    private static final Pattern PATTERN = compile("^(https?://)?([^.]+\\.)*(?<secondLevelDomain>[^. ]+)\\.(?<firstLevelDomain>[^. ]+)(/([^. ]+)?)?");

    public static Link parse(String uri){
        return Link.builder()
            .uri(uri.charAt(uri.length() - 1) == '/'
                ? uri
                : uri + "/")
            .build();
    }

    public static boolean isValid(String obj) {
        return PATTERN.matcher(obj).matches();
    }
}
