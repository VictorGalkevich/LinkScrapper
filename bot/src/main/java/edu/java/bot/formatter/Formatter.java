package edu.java.bot.formatter;

public interface Formatter {
    String bold(String str);

    String italic(String str);

    String code(String msg, ProgrammingLanguage lang);

    enum ProgrammingLanguage {
        CPP,
        JAVA,
        GO;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
