package io.ebean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class to determine the ebean version.
 *
 * @author Roland Praml, FOCONIS AG
 */
public class EbeanVersion {

  private static final Logger log = LoggerFactory.getLogger("io.ebean");

  private static String version = "unknown";
  static {
    try {
      Properties prop = new Properties();
      try (InputStream in = DB.class.getResourceAsStream("/META-INF/maven/io.ebean/ebean/pom.properties")) {
        if (in != null) {
          prop.load(in);
          in.close();
          version = prop.getProperty("version");
        }
      }
      log.info("ebean version: {}", version);
    } catch (IOException e) {
      log.warn("Could not determine ebean version: {}", e.getMessage());
    }
  }

  private EbeanVersion() {
    // hide
  }

  /**
   * Returns the ebean version (read from /META-INF/maven/io.ebean/ebean/pom.properties)
   */
  public static String getVersion() {
    return version;
  }

}
