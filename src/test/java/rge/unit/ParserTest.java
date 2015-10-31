package rge.unit;

import org.junit.Test;
import rge.engine.Parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParserTest {
    @Test
    public void transformsTheInputToLowerCase() {
        String expected = "santa_hat";
        String actual = Parser.parse("Santa_Hat");

        assertThat(expected, equalTo(actual));
    }

    @Test
    public void transformsSpacesToUnderscores() {
        String expected = "bandos_god_sword";
        String actual = Parser.parse("Bandos God Sword");

        assertThat(expected, equalTo(actual));
    }
}
