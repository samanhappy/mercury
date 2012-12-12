package com.dreamail.mercury.netty.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

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

import com.dreamail.mercury.sendMail.configure.SendMailPropertiesDeploy;
import com.dreamail.mercury.sender.control.ConnectionControl;
import com.dreamail.mercury.smtp.SmtpProcessor;
import com.dreamail.mercury.smtp.SmtpSession;
import com.dreamail.mercury.smtp.impl.AuthLoginProcess;
import com.dreamail.mercury.smtp.impl.DataProcess;
import com.dreamail.mercury.smtp.impl.EhloProcess;
import com.dreamail.mercury.smtp.impl.ExpnProcess;
import com.dreamail.mercury.smtp.impl.MailProcess;
import com.dreamail.mercury.smtp.impl.QuitProcess;
import com.dreamail.mercury.smtp.impl.RcptProcess;
import com.dreamail.mercury.smtp.impl.RestProcess;

public class TelnetServerHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(TelnetServerHandler.class);
	static final ChannelGroup channels = new DefaultChannelGroup();

	private static int nettyConnections = 0;

	public synchronized static void addNetty() {
		nettyConnections++;
		logger.info(">>>>>>>>After add concurrent netty connection is : "
				+ nettyConnections);
	}

	public synchronized static void reduceNetty() {
		nettyConnections--;
		logger.info(">>>>>>>>After reduce concurrent netty connection is : "
				+ nettyConnections);
	}

	private List<SmtpProcessor> process;

	private SmtpSession sesson;

	public TelnetServerHandler() {
		process = new ArrayList<SmtpProcessor>();
		// process.add(new OtherProcess());
		process.add(new AuthLoginProcess());
		process.add(new DataProcess());
		process.add(new EhloProcess());
		process.add(new ExpnProcess());
		// process.add(new HelpProcess());
		process.add(new MailProcess());
		// process.add(new NoopProcess());
		process.add(new QuitProcess());
		process.add(new RestProcess());
		process.add(new RcptProcess());
		// process.add(new VeryProcess());
		sesson = new SmtpSession();
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx,
			final ChannelStateEvent e) throws Exception {
		addNetty();
		ctx.getChannel().write(
				"220 ****************************************" + "\r\n");
		// 设置连接超时
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// 超时断开手机和服务器的链接，清除缓存并返回提示错误的协议
				if (e.getChannel().isOpen()) {
					e.getChannel().close();
				}
				new SmtpSession().cleanSession(sesson);
			}
		}, Long.parseLong(SendMailPropertiesDeploy.getConfigureMap().get(
				"out_time")) * 1000);

	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		reduceNetty();
		new SmtpSession().cleanSession(sesson);
		if (e.getChannel().isOpen()) {
			e.getChannel().close();
		}
		channels.remove(e.getChannel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.error("-----------exceptionCaught---------: " + e.getCause());
		new SmtpSession().cleanSession(sesson);
		if (ctx.getChannel().isOpen()) {
			ctx.getChannel().close();
		}
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info("ChannelStateEvent---------" + e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		// logger.info("Thread id: " + Thread.currentThread().getId());
		if (sesson.getThreadId() == 0) {
			sesson.setContext(ctx);
			sesson.setThreadId(Thread.currentThread().getId());
		}
		String command = (String) e.getMessage();
		logger.info("client command: " + command);
		List<String> protocol = null;
		for (SmtpProcessor processor : process) {
			if (processor.tag(command, sesson)) {
				try {
					processor.parser(command);
					protocol = processor.proces(command, sesson);
				} catch (Exception e1) {
					if (e1 instanceof MessagingException) {
						ConnectionControl.yahooReduce();
					}
					logger.error("server err....", e1);
				}
				break;
			}
			continue;
		}
		if (protocol != null && protocol.size() > 0) {
			for (String message : protocol) {
				if (message != null && !"".equals(message)) {
					logger.info("return message:" + message);
					ctx.getChannel().write(message + "\r\n");
				}
			}
		}
	}

}
