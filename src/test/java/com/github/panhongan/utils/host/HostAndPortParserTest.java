package com.github.panhongan.utils.host;

import org.junit.Test;

/**
 * @author lalalu plus
 * @since 2017.7.10
 */

public class HostAndPortParserTest {

    @Test
    public void testParse_Ok() {
        String uri = "192.168.0.10:2181,192.168.0.11:2181,192.168.0.12:2181";
        assert (HostAndPortParser.parse(uri).size() == 3);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_DirtyData() {
        String uri1 = "192.168.0.10:2181,192.168.0.11:2181,192.168.0.13:abc";
        HostAndPortParser.parse(uri1);
    }
}
