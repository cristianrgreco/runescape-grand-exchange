package rge.integration;

import org.junit.Test;
import rge.engine.GrandExchange;
import rge.engine.Item;

import java.io.IOException;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
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
    public void fetchesItemNameSpecialEncoding() throws IOException {
        assertThat(GrandExchange.get("Red h'ween Mask").name, equalTo("Red h'ween mask"));
    }

    @Test
    public void fetchesItemImage1() throws IOException {
        assertThat(GrandExchange.get("Santa hat").imageUrl, both(containsString("http://")).and(containsString("Santa_hat.png")));
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

    @Test
    public void fetchesGrandExchangePrice3() throws IOException {
        assertThat(GrandExchange.get("Crystal seed").price, nullValue());
    }

    @Test
    public void handlesItemsWhichDoNotExist() throws IOException {
        assertThat(GrandExchange.get("What"), sameBeanAs(new Item("Not found", null, null)));
    }

    @Test
    public void handlesInvalidUrls() throws IOException {
        assertThat(GrandExchange.get("Hi"), sameBeanAs(new Item("Not found", null, null)));
    }
}
