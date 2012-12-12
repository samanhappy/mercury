
package com.microsoft.ews.client;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class ExchangeWebServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap<QName, Endpoint> endpoints = new HashMap<QName, Endpoint>();
    private Service service0;

    public ExchangeWebServiceClient() {
        create0();
        Endpoint ExchangeWebPortEP = service0 .addEndpoint(new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeWebPort"), new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServiceBinding"), "https://exchange.server/ews/exchange.asmx");
        endpoints.put(new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeWebPort"), ExchangeWebPortEP);
        Endpoint ExchangeServicePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServicePortTypeLocalEndpoint"), new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServicePortTypeLocalBinding"), "xfire.local://ExchangeWebService");
        endpoints.put(new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServicePortTypeLocalEndpoint"), ExchangeServicePortTypeLocalEndpointEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection<Endpoint> getEndpoints() {
        return endpoints.values();
    }

    @SuppressWarnings("unused")
	private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap<String, Boolean> props = new HashMap<String, Boolean>();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.microsoft.ews.client.ExchangeWebServiceImpl.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServicePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServiceBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public ExchangeServicePortType getExchangeWebPort() {
        return ((ExchangeServicePortType)(this).getEndpoint(new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeWebPort")));
    }

    public ExchangeServicePortType getExchangeWebPort(String url) {
        ExchangeServicePortType var = getExchangeWebPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ExchangeServicePortType getExchangeServicePortTypeLocalEndpoint() {
        return ((ExchangeServicePortType)(this).getEndpoint(new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeServicePortTypeLocalEndpoint")));
    }

    public ExchangeServicePortType getExchangeServicePortTypeLocalEndpoint(String url) {
        ExchangeServicePortType var = getExchangeServicePortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
