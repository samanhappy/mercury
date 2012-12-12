package com.dreamail.mercury.petasos.gzip;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZipResponse extends HttpServletResponseWrapper {
	private GZipStream stream;
	private PrintWriter writer;

	public GZipResponse(HttpServletResponse response) throws IOException {
		super(response);
		stream = new GZipStream(response.getOutputStream());
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer == null) {
			writer = new PrintWriter(new OutputStreamWriter(getOutputStream(),
					getCharacterEncoding()));
		}
		return writer;
	}

	public void flush() throws IOException {
		if (writer != null) {
			writer.flush();
		}
		stream.finish();
	}

}
