/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.dreamail.mercury.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple SSL chat client modified from {@link TelnetClient}.
 *
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 *
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 *
 */
public class SecureChatClient{
	public static ClientBootstrap bootstrap;
	private static final Logger logger = LoggerFactory
	.getLogger(SecureChatClient.class);
	private ChannelHandlerContext ctx;
	
	static{
		 bootstrap = new ClientBootstrap(
	                new NioClientSocketChannelFactory(
	                        Executors.newCachedThreadPool(),
	                        Executors.newCachedThreadPool()));
	}
    public ChannelFuture start() throws Exception {
        String host = "imap.gmail.com";
        int port = 993;
        // Configure the client.
        // Configure the pipeline factory.
        SecureChatClientHandler scHandler = new SecureChatClientHandler();
        scHandler.registerObserver(new SecureChatClient(ctx));
        SecureChatClientPipelineFactory factory = new SecureChatClientPipelineFactory(scHandler);
        bootstrap.setPipelineFactory(factory);

        // Start the connection attempt.
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
        return future;
    }
    
    /**
     * 
     * @param message
     */
	public void update(String message) {
		logger.info("return message:"+message);
//		logger.info("bytesize=------------------"+message.getBytes().length+"-----------"+message.length());
		ctx.getChannel().write(message);
	}
	public SecureChatClient(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
	}
}
