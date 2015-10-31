package rge.engine;

public class Parser {
    private Parser() {
    }

    public static String parse(String input) {
        return input
                .replace(' ', '_')
                .toLowerCase();
    }
}
