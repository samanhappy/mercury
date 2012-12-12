package com.dreamail.mercury.netty.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class TelnetClientHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger
			.getLogger(TelnetClientHandler.class.getName());

	// 用户名 amt@clickooinc.local
	// 密码 Mypassword01
	// 服务器地址 10.101.101.221
	// SMTP端口25
	// 无SSL
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		// Print out the line received from the server.
		logger.info("messageReceived------------------------" + e.getMessage());
		if ("220 ****************************************".equals(e
				.getMessage())) {
			List<String> list = getProtolList();
			for (int i = 0; i < list.size(); i++) {
				ctx.getChannel().write(list.get(i) + "\r\n");
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e
				.getCause());
		e.getChannel().close();
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.log(Level.INFO, "----------------------------");
		super.channelConnected(ctx, e);
	}

	private List<String> getProtolList() {
		List<String> list = new ArrayList<String>();
		list.add("EHLO [10.116.143.234]");
		list.add("AUTH LOGIN");
		list.add("a2FpX2xpX21pbmRfMkB5YWhvby5jb20=");
		list.add("YXJjaGVybWluZA==");
		list.add("RSET");
		list.add("MAIL FROM:<001211@archermind.com>");
		list.add("RCPT TO:<001211@archermind.com>");
//		list.add("RCPT TO:<archermind163@163.com>");
//		list.add("RCPT TO:<archermind126@126.com>");
		list.add("DATA");
		list.add("From: kai_li_mind_2@yahoo.com");
		list.add("Reply-to: kai_li_mind_2@yahoo.com");
		list.add("To: 001211@archermind.com");
//		list.add("Cc: archermind163@163.com, archermind126@126.com");
		list.add("Subject: =?UTF-8?B?5ZWm5ZWm5ZWm5ZWm5ZWm5ZWm5ZWm5ZWm?=");
		list.add("Date: Fri, 20 May 2011 14:37:26 +0800");
		list.add("Message-ID: <VVSNhzn2FFg8.2llGspM9@58.64.174.218>");
		list.add("X-Mailer: EPOC Email Version 2.10");
		list.add("MIME-Version: 1.0");
		list.add("Content-Language: i-default");
		list.add("Content-Type: text/plain; charset=UTF-8");
		list.add("Content-Transfer-Encoding: quoted-printable");
		list.add("");
		list.add("=E5=95=A6=E5=95=A6=E5=95=A6=E5=95=A6=E6=9D=A5=E4=BA=86");
		list.add("");
		list.add(".");
		// list.add("QUIT");
		return list;
	}
}
