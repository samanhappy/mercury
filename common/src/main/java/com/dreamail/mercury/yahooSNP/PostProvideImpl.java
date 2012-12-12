package com.dreamail.mercury.yahooSNP;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PostProvideImpl extends AbstractSNPProvide {
	public static final Logger logger = LoggerFactory
			.getLogger(PostProvideImpl.class);

	public HttpEntity createRequestEntity(final String requestMsg) {
		ContentProducer cp = new ContentProducer() {
			public void writeTo(OutputStream outstream) throws IOException {
				Writer writer = new OutputStreamWriter(outstream, "UTF-8");
				writer.write(requestMsg);
				writer.flush();
				writer.close();
			}
		};
		return new EntityTemplate(cp);
	}

	@Override
	public HttpResponse executeMethod(String uri, SNPContext context,
			HttpEntity entity) {
		HttpResponse response = null;
		DefaultHttpClient httpClient = null;
		HttpPost postMethod = null;
		try {
			postMethod = (HttpPost) getMethod(uri);
			postMethod.setEntity(entity);
			httpClient = getClient();
			if (null == context.getCookieStore()) {
				response = httpClient.execute(postMethod);
			} else {
				response = httpClient.execute(postMethod,
						getLocalContext(context.getCookieStore()));
			}
		} catch (ClientProtocolException e) {
			logger.error("failed to get instance for httpclient.");
		} catch (IOException e) {
			logger.error("error when execute method for httpclient.");
		} finally {
			postMethod.abort();
			closeResource(httpClient);
		}
		return response;
	}

	@Override
	public HttpUriRequest getMethod(String uri) {
		return new HttpPost(uri);
	}
}
