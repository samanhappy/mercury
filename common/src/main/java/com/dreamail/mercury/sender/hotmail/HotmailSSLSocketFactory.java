package com.dreamail.mercury.sender.hotmail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HotmailSSLSocketFactory extends SSLSocketFactory {
    
    // ------------------------------------------------------------ Private data
    
    private SSLSocketFactory sslSocketFactory;
    private SocketFactory    socketFactory;
    
    // ------------------------------------------------------------ Constructors
    
    public HotmailSSLSocketFactory() {
        try {
            
            socketFactory = SocketFactory.getDefault();
 
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init( null,
                    new TrustManager[] {new DummyTrustManager()},
                    new java.security.SecureRandom());
            sslSocketFactory = (SSLSocketFactory) sslcontext.getSocketFactory();
            
        } catch(Exception e) {
            System.out.println("Error while creating HotmailSSLSocketFactory: " + e.getMessage());
        }
    }
    
    // ---------------------------------------------------------- Public methods
    
    public static SocketFactory getDefault() {
        return new HotmailSSLSocketFactory();
    }
    
    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean flag)
    throws IOException {
        return sslSocketFactory.createSocket(socket, s, i, flag);
    }
    
    @Override
    public Socket createSocket( InetAddress inaddr, int i,
            InetAddress inaddr1, int j) throws IOException {
        return socketFactory.createSocket( inaddr, i, inaddr1, j);
    }
    
    @Override
    public Socket createSocket( InetAddress inaddr, int i)
    throws IOException {
        return socketFactory.createSocket( inaddr, i);
    }
    
    @Override
    public Socket createSocket( String s, int i, InetAddress inaddr, int j)
    throws IOException {
        return socketFactory.createSocket( s, i, inaddr, j);
    }
    
    @Override
    public Socket createSocket( String s, int i) throws IOException {
        return socketFactory.createSocket( s, i);
    }
    
    @Override
    public Socket createSocket() throws IOException {
        return socketFactory.createSocket();
    }
    
    @Override
    public String[] getDefaultCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }
    
    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }    
}
