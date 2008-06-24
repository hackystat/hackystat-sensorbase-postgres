package org.hackystat.sensorbase.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * /**
 * Provides access to the values stored in the sensorbase.properties file. 
 * @author Philip Johnson
 * @author Austen Ito
 */
public class PostgresServerProperties {
  /** The postgres server username. */
  public static final String POSTGRES_USER = "postgres.user";
  /** The postgres server password. */
  public static final String POSTGRES_PASSWORD = "postgres.password";
  /** Where we store the properties. */
  private Properties properties;

  /**
   * Creates a new PostgresServerProperties instance. Prints an error to the console if
   * problems occur on loading.
   */
  public PostgresServerProperties() {
    try {
      initializeProperties();
    }
    catch (Exception e) {
      System.out.println("Error initializing postgres server properties.");
    }
  }

  /**
   * Reads in the properties in ~/.hackystat/sensorbase.properties if this file
   * exists, and provides default values for all properties not mentionned in
   * this file.
   * @throws Exception if errors occur.
   */
  private void initializeProperties() throws Exception {
    String userHome = System.getProperty("user.home");
    String propFile = userHome + "/.hackystat/sensorbase/sensorbase.properties";
    this.properties = new Properties();
    // Set defaults for 'standard' operation.
    properties.setProperty(POSTGRES_USER, "");
    properties.setProperty(POSTGRES_PASSWORD, "");

    FileInputStream stream = null;
    try {
      stream = new FileInputStream(propFile);
      properties.load(stream);
      System.out.println("Loading SensorBase properties from: " + propFile);
    }
    catch (IOException e) {
      System.out.println(propFile + " not found. Using default sensorbase properties.");
    }
    finally {
      if (stream != null) {
        stream.close();
      }
    }
    trimProperties(properties);
  }

  /**
   * Returns the value of the Server Property specified by the key.
   * @param key Should be one of the public static final strings in this class.
   * @return The value of the key, or null if not found.
   */
  public String get(String key) {
    return this.properties.getProperty(key);
  }

  /**
   * Ensures that the there is no leading or trailing whitespace in the property
   * values. The fact that we need to do this indicates a bug in Java's
   * Properties implementation to me.
   * @param properties The properties.
   */
  private void trimProperties(Properties properties) {
    // Have to do this iteration in a Java 5 compatible manner. no
    // stringPropertyNames().
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      String propName = (String) entry.getKey();
      properties.setProperty(propName, properties.getProperty(propName).trim());
    }
  }
}
