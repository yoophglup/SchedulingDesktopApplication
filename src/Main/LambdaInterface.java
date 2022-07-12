package Main;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/** This interface is for the second required lambda expression.
 * The lambda expression is called from Reports
 */
public interface LambdaInterface {
    String concat(String s1,String s2,String s3);

    /** This interface is for the first required lambda expressions
     * This lambda expression is called from Appointmentpicker
     */
    public interface ZonedTime {
    ZonedDateTime String2Zoned(String s, ZoneId z);
}}