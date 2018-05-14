package org.apache.http;

import java.io.IOException;
import org.apache.http.protocol.HttpContext;

/* compiled from: TbsSdkJava */
public interface HttpResponseInterceptor {
    void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException;
}
