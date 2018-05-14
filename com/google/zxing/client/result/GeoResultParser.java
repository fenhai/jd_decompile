package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.jingdong.jdma.JDMaInterface;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: TbsSdkJava */
public final class GeoResultParser extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    public GeoParsedResult parse(Result result) {
        double d = JDMaInterface.PV_UPPERLIMIT;
        Matcher matcher = GEO_URL_PATTERN.matcher(ResultParser.getMassagedText(result));
        if (!matcher.matches()) {
            return null;
        }
        String group = matcher.group(4);
        try {
            double parseDouble = Double.parseDouble(matcher.group(1));
            if (parseDouble > 90.0d || parseDouble < -90.0d) {
                return null;
            }
            double parseDouble2 = Double.parseDouble(matcher.group(2));
            if (parseDouble2 > 180.0d || parseDouble2 < -180.0d) {
                return null;
            }
            if (matcher.group(3) != null) {
                double parseDouble3 = Double.parseDouble(matcher.group(3));
                if (parseDouble3 < JDMaInterface.PV_UPPERLIMIT) {
                    return null;
                }
                d = parseDouble3;
            }
            return new GeoParsedResult(parseDouble, parseDouble2, d, group);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
