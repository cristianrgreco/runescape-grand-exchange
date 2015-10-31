package rge.integration;

import org.junit.Test;
import rge.engine.GrandExchange;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.IsNull.notNullValue;

public class GrandExchangeTest {
    @Test
    public void fetchesItemName() throws IOException {
        assertThat(GrandExchange.get("Santa hat").name, equalTo("Santa hat"));
    }

    @Test
    public void fetchesItemImage() throws IOException {
        assertThat(GrandExchange.get("Santa hat").imageUrl, both(containsString("http://")).and(containsString("Santa_hat.png")));
    }

    @Test
    public void fetchesGrandExchangePrice() throws IOException {
        assertThat(GrandExchange.get("Santa hat").price, notNullValue());
    }
}
