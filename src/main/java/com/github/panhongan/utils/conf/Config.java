package com.github.panhongan.utils.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * common config
 *
 * @author lalalu plus
 * @since 2017.5.6
 */

public class Config implements Serializable {

    private static final long serialVersionUID = 3641484340330072531L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private Properties properties = new Properties();

    /**
     * @param configFile Config File
     */
    public void parse(String configFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (StringUtils.isEmpty(line) || line.startsWith("#")) {
                    continue;
                }

                int pos = line.indexOf('=');
                if (pos != -1) {
                    String key = line.substring(0, pos).trim();
                    String value = line.substring(pos + 1).trim();
                    properties.put(key, value);
                } else {
                    LOGGER.warn("invalid line : {}", line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param resourceFile Resource File
     */
    public void parseResourceFile(String resourceFile) {
        try {
            properties.load(this.getClass().getResourceAsStream(resourceFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param key Key
     * @param value Value
     */
    public void addProperty(String key, String value) {
        if (key != null && value != null) {
            properties.put(key, value);
        }
    }

    /**
     * @param key Key
     * @return Value
     */
    public String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public String getString(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    /**
     * @param key Key
     * @return Value
     */
    public short getShort(String key) {
        return Short.valueOf(properties.getProperty(key));
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public short getShort(String key, short defaultVal) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Short.valueOf(value);
        } else {
            return defaultVal;
        }
    }

    /**
     * @param key Key
     * @return Value
     */
    public int getInt(String key) {
        return Integer.valueOf(properties.getProperty(key));
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public int getInt(String key, int defaultVal) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Integer.valueOf(value);
        } else {
            return defaultVal;
        }
    }

    /**
     * @param key Key
     * @return Value
     */
    public long getLong(String key) {
        return Long.valueOf(properties.getProperty(key));
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public long getLong(String key, long defaultVal) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Long.valueOf(value);
        } else {
            return defaultVal;
        }
    }

    /**
     * @param key Key
     * @return Value
     */
    public float getFloat(String key) {
        return Float.valueOf(properties.getProperty(key));
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public float getFloat(String key, float defaultVal) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Float.valueOf(value);
        } else {
            return defaultVal;
        }
    }

    /**
     * @param key Key
     * @return Value
     */
    public double getDouble(String key) {
        return Double.valueOf(properties.getProperty(key));
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public double getDouble(String key, double defaultVal) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Double.valueOf(value);
        } else {
            return defaultVal;
        }
    }

    /**
     * @param key Key
     * @return Value
     */
    public boolean getBoolean(String key) {
        return Boolean.valueOf(properties.getProperty(key));
    }

    /**
     * @param key Key
     * @param defaultVal Default value
     * @return Value
     */
    public boolean getBoolean(String key, boolean defaultVal) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Boolean.valueOf(value);
        } else {
            return defaultVal;
        }
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public Set<String> keySet() {
        return properties.keySet().stream().map(x -> (String) x).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return properties.toString();
    }
}
