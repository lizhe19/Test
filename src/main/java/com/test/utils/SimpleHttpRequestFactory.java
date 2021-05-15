package com.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Slf4j
public class SimpleHttpRequestFactory extends SimpleClientHttpRequestFactory {

    public SimpleHttpRequestFactory() {
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            this.prepareHttpsConnection((HttpsURLConnection)connection);
        }

        super.prepareConnection(connection, httpMethod);
    }

    private void prepareHttpsConnection(HttpsURLConnection connection) {
        connection.setHostnameVerifier(
            new SkipHostnameVerifier());

        try {
            connection.setSSLSocketFactory(this.createSslSocketFactory());
        } catch (Exception var3) {
            ;
        }

    }

    private SSLSocketFactory createSslSocketFactory() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[] {
                new SkipX509TrustManager()},
            new SecureRandom());
        return context.getSocketFactory();
    }

    private static class SkipX509TrustManager implements X509TrustManager {
        private SkipX509TrustManager() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    static class SkipHostnameVerifier implements HostnameVerifier {

        private SkipHostnameVerifier() {
        }

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }
}
