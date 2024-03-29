package ua.com.alevel.util.valide;

import ua.com.alevel.util.ConstGlobal;

import java.util.regex.Pattern;

public final class ValidSession extends BaseValide {

    private ValidSession() {
    }

    public static boolean validDate(String date) {
        if (date.isEmpty()) {
            System.out.println(ConstGlobal.settings.getString("session.date.empty"));
            return false;
        }
        if (!Pattern.matches(REGEX_DATE, date)) {
            System.out.println(ConstGlobal.settings.getString("session.date.format"));
            return false;
        }
        return true;
    }
}
