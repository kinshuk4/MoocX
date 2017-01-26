// TimeProtocolConstants.java

package simple.timeprotocol;

import java.util.Calendar;

/**
 * Defines constants used by clients and servers implementing the time
 * protocol defined in RFC 868.
 */
public class TimeProtocolConstants {

  /**
   * Prevent instantiation
   */
  private TimeProtocolConstants() {}

  /**
   * The time protocol TCP port defined in RFC 868
   */
  public static final int TCP_PORT = 37;

  /**
   * The number of milliseconds between the RFC 868 epoch, January
   * 1st 1900, 0:0:0 UTC, and the Java epoch, January 1st 1970, 0:0:0 UTC
   */
  public static final long EPOCH_OFFSET_MILLIS;

  /**
   * Computes the epoch offset
   */
  static {
    Calendar calendar = 
      Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
    calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
    EPOCH_OFFSET_MILLIS = Math.abs(calendar.getTime().getTime());
  } 
}
