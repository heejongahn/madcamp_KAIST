package com.example.junhong.tabview2;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Junhong on 2015-12-28.
 */
public class ServiceHandler {
    private StringBuffer buffer;
    private String TAG = "ServiceHandler";
    private String _url;
    private Context mContext;

    public ServiceHandler(String url, Context con) {
        _url = url;
        mContext = con;
        buffer = new StringBuffer();
    }

    public String loadPage(){
        String result = null;
        String[] seperated = _url.split(":");
        if(seperated[0].matches("https")){  //if it is https security sites
            InputStream caInput = null;
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                caInput = this.mContext.getResources().openRawResource(R.raw.pappalardo);
                Certificate ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

                // Create a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

                // Create a TrustManager that trusts the CAs in our KeyStore
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                // Tell the URLConnection to use a SocketFactory from our SSLContext
                URL url = new URL(_url);
                HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
                urlConnection.setSSLSocketFactory(context.getSocketFactory());

                //In order to load 한글 page
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                readStream(br);
                result = buffer.toString();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } finally {
                try {
                    caInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else { //without https site
            try {
                URL url = new URL(_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
                result = buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private void readStream(InputStream in) {
        int c;
        try {
            while((c = in.read()) != -1){
                buffer.append((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readStream(BufferedReader br) {
        int c;
        try {
            while((c = br.read()) != -1){
                buffer.append((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
