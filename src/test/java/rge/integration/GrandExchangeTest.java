package rge.integration;

import org.junit.Test;
import rge.engine.GrandExchange;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.IsNull.notNullValue;

public class GrandExchangeTest {
    @Test
    public void fetchesItemName1() throws IOException {
        assertThat(GrandExchange.get("Santa hat").name, equalTo("Santa hat"));
    }

    @Test
    public void fetchesItemName2() throws IOException {
        assertThat(GrandExchange.get("Bandos godsword").name, equalTo("Bandos godsword"));
    }

    @Test
    public void fetchesItemImage1() throws IOException {
        assertThat(GrandExchange.get("Santa hat").imageUrl, startsWith("data:image/gif;base64"));
    }

    @Test
    public void fetchesItemImage2() throws IOException {
        assertThat(GrandExchange.get("Bandos godsword").imageUrl, both(containsString("http://")).and(containsString("Bandos_godsword.png")));
    }

    @Test
    public void fetchesGrandExchangePrice1() throws IOException {
        assertThat(GrandExchange.get("Santa hat").price, notNullValue());
    }

    @Test
    public void fetchesGrandExchangePrice2() throws IOException {
        assertThat(GrandExchange.get("Bandos godsword").price, notNullValue());
    }
}
