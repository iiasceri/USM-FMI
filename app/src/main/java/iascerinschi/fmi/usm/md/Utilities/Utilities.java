package iascerinschi.fmi.usm.md.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import java.util.Calendar;


public class Utilities {

    static String serverURL = "http://192.168.0.104:8080";

    public static String getServerURL() {
        return serverURL;
    }

    /**
     * RETURN JSON filter "par", "impar"
     * @return type of week odd/even as defined in JSON
     */
    public static String getParitate() {
        Calendar calender = Calendar.getInstance();
        if (calender.get(Calendar.WEEK_OF_YEAR) % 2 == 0)
            return "par";
        else
            return "imp";
    }

    /**
     * RETURN TITLE FOR ACTIVITIES (TOOLBAR)
     * @return type of week odd/even as String
     */
    public static String getParitateTitlu() {

        String paritate = "";
        if (Utilities.getParitate().equals("par"))
            paritate = "para";
        else
            paritate = "impara";

        return "Saptamana " + paritate;
    }

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     * @return true or false if device has internet
     */
    public static boolean checkConnection(@NonNull Context context) {
        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
    
}
