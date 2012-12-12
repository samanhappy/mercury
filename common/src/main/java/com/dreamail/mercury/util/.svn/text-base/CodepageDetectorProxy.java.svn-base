package com.dreamail.mercury.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cpdetector.io.AbstractCodepageDetector;
import cpdetector.io.ICodepageDetector;
import cpdetector.io.UnknownCharset;
import cpdetector.io.UnsupportedCharset;

public final class CodepageDetectorProxy extends AbstractCodepageDetector {

	private static final long serialVersionUID = -4059908925082958268L;

	private static CodepageDetectorProxy instance = null;

	private List<ICodepageDetector> detectors = new CopyOnWriteArrayList<ICodepageDetector>();

	public static CodepageDetectorProxy getInstance() {
		if (instance == null) {
			instance = new CodepageDetectorProxy();
		}
		return instance;
	}

	public boolean add(ICodepageDetector detector) {
		return this.detectors.add(detector);
	}

	public Charset detectCodepage(URL url) throws IOException {
		Charset ret = null;
		Iterator<ICodepageDetector> detectorIt = this.detectors.iterator();
		while (detectorIt.hasNext()) {
			ret = ((ICodepageDetector) detectorIt.next()).detectCodepage(url);
			if ((ret != null) && (ret != UnknownCharset.getInstance())
					&& (!(ret instanceof UnsupportedCharset))) {
				break;
			}

		}

		return ret;
	}

	public Charset detectCodepage(InputStream in, int length)
			throws IOException {
		Charset ret = null;
		int markLimit = length;
		Iterator<ICodepageDetector> detectorIt = this.detectors.iterator();
		while (detectorIt.hasNext()) {
			if (in.markSupported()) {
				in.mark(markLimit);
			}
			ret = ((ICodepageDetector) detectorIt.next()).detectCodepage(in,
					length);
			if (in.markSupported()) {
				try {
					in.reset();
				} catch (IOException ioex) {
					ioex.printStackTrace(System.err);
					markLimit *= 2;
				}
			}
			if ((ret != null) && (ret != UnknownCharset.getInstance())
					&& (!(ret instanceof UnsupportedCharset))) {
				break;
			}

		}

		return ret;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();
		Iterator<ICodepageDetector> it = this.detectors.iterator();
		int i = 1;
		while (it.hasNext()) {
			ret.append(i);
			ret.append(") ");
			ret.append(it.next().getClass().getName());
			ret.append("\n");
			i++;
		}
		return ret.toString();
	}
}
