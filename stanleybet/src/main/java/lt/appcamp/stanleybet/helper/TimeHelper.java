package lt.appcamp.stanleybet.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tadas on 6/13/13.
 */
public class TimeHelper {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    /**
     *
     * @return date string yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDate() {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(cal.getTime());
    }

    /**
     *
     * @return date string yyyy-MM-dd HH:mm:ss
     *
     * GMT Zone
     *
     */
    public static String getNowDateGMT() {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cal.getTime());
    }



    /**
     * getTimeFromDuration
     * @param duration in seconds
     * @return 1d H:mm:ss
     */
    public static String getTimeFromDuration(long duration){
        long days = TimeUnit.SECONDS.toDays(duration);
        long hours = TimeUnit.SECONDS.toHours(duration) - (days*24);
        long minute = TimeUnit.SECONDS.toMinutes(duration)-(hours*60)-(days*24*60);
        long seconds = TimeUnit.SECONDS.toSeconds(duration)- (minute*60)-(hours*3600)-(days*24*3600);

        String result = "";

        if(days>0){
            result+=days+" d. ";
        }

        result+=hours+":"+String.format("%02d",minute)+":"+String.format("%02d",seconds);

        return result;
    }

    /**
     * getTimeFromDurationHuman
     * @param duration in seconds
     * @return 1d H:mm Or 1d Or H:mm
     */
    public static String getTimeFromDurationHuman(long duration){
        long days = TimeUnit.SECONDS.toDays(duration);
        long hours = TimeUnit.SECONDS.toHours(duration) - (days*24);
        long minute = TimeUnit.SECONDS.toMinutes(duration)-(hours*60)-(days*24*60);

        String result = "";

        if(days>0){
            result+=days+" d. ";
        }

        if(hours>0 || minute>0){
            result+=hours+":"+String.format("%02d",minute);
        }

        return result;
    }


    /**
     *
     * @return timestamp in seconds
     */
    public static long getNowTimestamp(){
        Date date = new Date();
        return date.getTime()/1000;
    }

    /**
     *
     * @return timestamp in seconds
     *
     * GMT timezone
     */
    public static long getNowTimestampGMT(){
        String dateGMT = getNowDateGMT();
        return getTimestamp(dateGMT);
    }

    /**
     *
     * @param dateString
     * @return timestamp in seconds
     */
    public static long getTimestamp(String dateString)  {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = sdf.parse(dateString);
            return date.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return getNowTimestamp();
        }
    }

    /**
     *  Format from 0000-00-00 00:00:00 to 0000-00-00
     * @param dateFrom
     * @return dateTo
     */
    public static String getShortDate(String dateFrom) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        final SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT_SHORT);
        try {
            Date date = sdf.parse(dateFrom);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateFrom;
        }
    }

    /**
     * getShortDate
     * @param year
     * @param monthOfYear 0-11 Compatible with Calendar
     * @param dayOfMonth
     * @return
     */
    public static String getShortDate(int year, int monthOfYear, int dayOfMonth) {
        return year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth);
    }
}
