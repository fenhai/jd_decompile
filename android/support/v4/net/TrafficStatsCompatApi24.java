package android.support.v4.net;

import android.net.TrafficStats;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.net.DatagramSocket;
import java.net.SocketException;

@RestrictTo({Scope.GROUP_ID})
/* compiled from: TbsSdkJava */
public class TrafficStatsCompatApi24 {
    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        TrafficStats.tagDatagramSocket(datagramSocket);
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        TrafficStats.untagDatagramSocket(datagramSocket);
    }
}
