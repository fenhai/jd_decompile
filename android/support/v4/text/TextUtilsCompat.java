package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Locale;

/* compiled from: TbsSdkJava */
public final class TextUtilsCompat {
    static String ARAB_SCRIPT_SUBTAG = "Arab";
    static String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT = new Locale("", "");

    /* compiled from: TbsSdkJava */
    private static class TextUtilsCompatImpl {
        TextUtilsCompatImpl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                switch (charAt) {
                    case '\"':
                        stringBuilder.append("&quot;");
                        break;
                    case '&':
                        stringBuilder.append("&amp;");
                        break;
                    case '\'':
                        stringBuilder.append("&#39;");
                        break;
                    case '<':
                        stringBuilder.append("&lt;");
                        break;
                    case '>':
                        stringBuilder.append("&gt;");
                        break;
                    default:
                        stringBuilder.append(charAt);
                        break;
                }
            }
            return stringBuilder.toString();
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            if (!(locale == null || locale.equals(TextUtilsCompat.ROOT))) {
                String maximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale);
                if (maximizeAndGetScript == null) {
                    return getLayoutDirectionFromFirstChar(locale);
                }
                if (maximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || maximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) {
                    return 1;
                }
            }
            return 0;
        }

        private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
            switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                case (byte) 1:
                case (byte) 2:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    /* compiled from: TbsSdkJava */
    private static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        TextUtilsCompatJellybeanMr1Impl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            return TextUtilsCompatJellybeanMr1.htmlEncode(str);
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale(locale);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new TextUtilsCompatJellybeanMr1Impl();
        } else {
            IMPL = new TextUtilsCompatImpl();
        }
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        return IMPL.htmlEncode(str);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        return IMPL.getLayoutDirectionFromLocale(locale);
    }

    private TextUtilsCompat() {
    }
}
