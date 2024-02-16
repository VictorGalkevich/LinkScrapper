package edu.java.bot.util;

import edu.java.bot.entity.Link;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
import static java.util.regex.Pattern.compile;

@Component
@UtilityClass
public class LinkUtil {
    private static final Pattern PATTERN =
        compile("^(https?://)?([^.]+\\.)*(?<secondLevelDomain>[^. ]+)\\.(?<firstLevelDomain>[^. ]+)(/([^. ]+)?)?");

    public static Link parse(String uri) {
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
