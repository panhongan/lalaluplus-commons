package com.github.panhongan.utils.conf;

import org.junit.Test;

/**
 * @author lalalu plus
 * @since 2017.3.2
 */

public class ConfigTest {

    @Test
    public void testParse_Ok() {
        Config config = new Config();
        config.parse("conf/conf-demo.properties");
        assert (config.isNotEmpty());
    }

    @Test
    public void testAddProperty_Null() {
        Config config = new Config();
        config.addProperty(null, null);
        assert (config.isEmpty());
    }

    @Test
    public void testAddProperty_Ok() {
        Config config = new Config();
        config.addProperty("a", "a");
        assert (config.isNotEmpty());
    }

    @Test
    public void testGetters_Ok() {
        Config config = new Config();
        config.addProperty("name", "pha");
        config.addProperty("age", "123");
        config.addProperty("money", "12345");
        assert ((config.getString("name").equals("pha")));
        assert (config.getShort("age", (short) 0) == (short) 123);
        assert (config.getInt("age", 0) == 123);
        assert (config.getLong("money") == 12345L);
        assert (config.getLong("money1", 0) == 0L);
    }
}
