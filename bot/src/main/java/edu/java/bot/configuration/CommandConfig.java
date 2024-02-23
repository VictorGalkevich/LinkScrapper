package edu.java.bot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "command.description")
@Getter
@Setter
public class CommandConfig {
    private String help = "show available commands";
    private String start = "start the bot";
    private String list = "list tracked links";
    private String track = "start tracking a link";
    private String untrack = "stop tracking a link";
}
