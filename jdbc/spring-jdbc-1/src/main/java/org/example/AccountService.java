package org.example;

@FunctionalInterface
public interface AccountService {
    void transfer(long from, long to, long amount);
}
