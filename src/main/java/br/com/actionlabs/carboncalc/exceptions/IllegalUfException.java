package br.com.actionlabs.carboncalc.exceptions;

public class IllegalUfException extends RuntimeException {
    public IllegalUfException(String uf) {
        super("Invalid UF: " + uf);
    }
}
