package com.dreamail.mercury.mail.truepush.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class ASParser implements IASParser {
	private static IASParser myself = new ASParser();

	public static IASParser getInstance() {
		return myself;
	}

	public Object fromXml(String xml) {
		return null;
	}

	public IASResponse fromXml(String cmdName, InputStream in) {
		IBindingFactory f = null;
		try {
			if (IASResponse.FOLDERSYNC.equals(cmdName)) {
				f = BindingDirectory.getFactory(FolderSync.class);
			} else if (IASResponse.SYNC.equals(cmdName)) {
				f = BindingDirectory.getFactory(Sync.class);
			} else if (IASResponse.GETITEMESTIMATE.equals(cmdName)) {
				f = BindingDirectory.getFactory(GetItemEstimate.class);
			} else if (IASResponse.PING.equals(cmdName)) {
				f = BindingDirectory.getFactory(Ping.class);
			}
		} catch (JiBXException e) {
			e.printStackTrace();
		}

		if (f != null) {
			IUnmarshallingContext unContext;
			try {
				unContext = f.createUnmarshallingContext();
				return (IASResponse) unContext
						.unmarshalDocument(new InputStreamReader(in));
			} catch (JiBXException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String toXml(Object o) {
		ByteArrayOutputStream out = toXmlOutputStream(o);
		if (out != null) {
			try {
				return out.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public InputStream toXmlInputStream(Object o) {
		ByteArrayOutputStream out = toXmlOutputStream(o);
		if (out != null) {
			return new ByteArrayInputStream(out.toByteArray());
		}
		return null;
	}

	public ByteArrayOutputStream toXmlOutputStream(Object o) {
		IMarshallingContext context;
		try {
			context = BindingDirectory.getFactory(o.getClass())
					.createMarshallingContext();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			context.marshalDocument(o, "UTF-8", null, out);
			return out;
		} catch (JiBXException e) {
			e.printStackTrace();
		}
		return null;
	}

}
