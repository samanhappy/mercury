package com.dreamail.mercury.netty.server;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.impl.Clean;
import com.dreamail.mercury.imap.impl.ExamineProcess;
import com.dreamail.mercury.imap.impl.FetchProcess;
import com.dreamail.mercury.imap.impl.ListProcess;
import com.dreamail.mercury.imap.impl.LoginProcess;
import com.dreamail.mercury.imap.impl.OtherProcess;
import com.dreamail.mercury.imap.impl.SearchProcess;
import com.dreamail.mercury.imap.impl.SelectProcess;
import com.dreamail.mercury.imap.impl.StatusProcess;
import com.dreamail.mercury.imap.impl.StoreProcess;

public class TelnetServerHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(TelnetServerHandler.class);

	private List<ImapProcessor> process;

	private ImapSession iSession;

	public TelnetServerHandler() {
		process = new ArrayList<ImapProcessor>();
		process.add(new OtherProcess());
		process.add(new LoginProcess());
		process.add(new SearchProcess());
		process.add(new FetchProcess());
		process.add(new ListProcess());
		process.add(new SelectProcess());
		process.add(new ExamineProcess());
		process.add(new StoreProcess());
		process.add(new StatusProcess());
		iSession = new ImapSession();
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info("ChannelStateEvent---------" + e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	static final ChannelGroup channels = new DefaultChannelGroup();

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("connected ............... ");
		// ctx.getChannel().write("* OK clickooImap ready" + "\r\n");
		ctx.getChannel().write("* OK IMAP4 ready" + "\r\n");
	}

	@Override
	public void messageReceived(final ChannelHandlerContext ctx,
			final MessageEvent e) {
		logger.info("channel's state : " + ctx.getChannel().isOpen());
		if (iSession.getThreadId() == 0) {
			iSession.setContext(ctx);
			iSession.setThreadId(Thread.currentThread().getId());
		}
		// logger.info("Thread id: " + Thread.currentThread().getId());
		String command = (String) e.getMessage();
		logger.info("client command: " + command);
		List<String> protocol = null;
		for (ImapProcessor processor : process) {
			if (processor.tag(command)) {
				try {
					processor.parser(command);
					protocol = processor.proces(command, iSession);
				} catch (Exception e1) {
					logger.error("server err....", e1);
					protocol = new ArrayList<String>();
					protocol
							.add("* BAD [CLIENTBUG] Invalid command or arguments");
				}
				break;
			}
			continue;
		}
		/**
		 * 如果为空则返回bad.
		 */
		if (protocol == null || protocol.size() < 1) {
			ctx.getChannel().write(
					"* BAD [CLIENTBUG] Invalid command or arguments" + "\r\n");
		} else {
			for (String message : protocol) {
				if (message != null && !"".equals(message)) {
					logger.info("return message:" + message);
					if (ctx.getChannel().isOpen()) {
						ctx.getChannel().write(message + "\r\n");
					}
				}
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.error("exception", e.getCause());
		/**
		 * 视为该帐号异常，关闭连接清空session
		 */
		new Clean().cleanSession(iSession);
		if (ctx.getChannel().isOpen()) {
			ctx.getChannel().close();
		}
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		logger.info("remove Channel............");
		new OtherProcess().deleteMessage(iSession);
		new Clean().cleanSession(iSession);
		e.getChannel().close();
		channels.remove(e.getChannel());
	}
}
