package iascerinschi.fmi.usm.md;

import java.util.Calendar;

public class Utilities {

    public static String getParitate() {
        Calendar calender = Calendar.getInstance();
        if (calender.get(Calendar.WEEK_OF_YEAR) % 2 == 0)
            return "par";
        else
            return "imp";
    }

    public static String getParitateTitlu() {
        String paritate = "";
        if (Utilities.getParitate().equals("par"))
            paritate = "para";
        else
            paritate = "impara";

        String text = "Saptamana " + paritate;

        return text;
    }
}
