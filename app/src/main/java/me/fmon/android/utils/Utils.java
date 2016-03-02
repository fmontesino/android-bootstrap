package me.fmon.android.utils;

import android.util.Base64;
import android.webkit.MimeTypeMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\" +
                ".[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String authHeader(String user, String pass) {
        String credentials = user + ":" + pass;
        return "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }

    public static boolean isSuccess(int code) {
        return code == Constants.WS_OK;
    }

    public static String getError(int code) {
        String error = "Unknown Error";
        switch (code) {
            case Constants.WS_AUTH_NEEDED:
                error = "Unauthorized";
                break;
            case Constants.WS_BAD_REQUEST:
                error = "Bad Request";
                break;
            case Constants.WS_INTERNAL_ERROR:
                error = "Internal Error";
                break;
            case Constants.WS_NOT_FOUND:
                error = "Not Found";
                break;
        }
        return error;
    }

    public static String getRelativeDate(DateTime dateTime) {

        if (dateTime == null)
            return "";

        Date ds = new Date();
        DateTime today = new DateTime(ds);

        return dateTime.withTimeAtStartOfDay().equals(today.withTimeAtStartOfDay()) ? "Today" :
                DateTimeFormat.forPattern("EEEE, dd MMMM yyyy").withLocale(Locale.ENGLISH).print
                        (dateTime);
    }

    public static String getRelativeDateTime(DateTime dateTime) {

        if (dateTime == null)
            return "";

        Date ds = new Date();
        DateTime today = new DateTime(ds);

        String date = dateTime.withTimeAtStartOfDay().equals(today.withTimeAtStartOfDay()) ?
                "Today" : DateTimeFormat.forPattern("EEEE, dd MMMM").withLocale(Locale.ENGLISH)
                .print(dateTime);

        return date + ", " + getHour(dateTime);
    }

    public static DateTime getDateTimeDay(DateTime dateTime) {
        return dateTime.withTimeAtStartOfDay();
    }

    public static String getHour(DateTime dateTime) {
        if (dateTime == null)
            return "";

        return DateTimeFormat.forPattern("HH:mm").withLocale(Locale.ENGLISH).print(dateTime);
    }

}
