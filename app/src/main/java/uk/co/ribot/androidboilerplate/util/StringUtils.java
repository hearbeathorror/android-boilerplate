package uk.co.ribot.androidboilerplate.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class StringUtils {
    private static final String DATE_FORMAT_DD_MMMM_YYYY = "dd MMMM yyyy";
    public static boolean isEmpty(@Nullable String value) {
        return TextUtils.isEmpty(value);
    }

    public static String getFullName(String firstName, String lastName) {
        return new StringBuilder(firstName).append(' ').append(lastName).toString();
    }

    public static String getBirthDate(Date birthDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DD_MMMM_YYYY, Locale.getDefault());
        return sdf.format(birthDate);
    }
}
