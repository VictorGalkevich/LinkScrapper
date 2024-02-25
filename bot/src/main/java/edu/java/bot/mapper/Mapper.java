package edu.java.bot.mapper;

public interface Mapper<F, T> {
    T map(F from);
}
