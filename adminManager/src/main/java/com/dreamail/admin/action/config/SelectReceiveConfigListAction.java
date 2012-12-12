package com.dreamail.admin.action.config;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.dal.dao.ReceiveServerDao;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;
import com.opensymphony.xwork2.ActionSupport;

public class SelectReceiveConfigListAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private List<Clickoo_mail_receive_server> servers = new ArrayList<Clickoo_mail_receive_server>();
	
	@Override
	public String execute() throws Exception {
		servers = new ReceiveServerDao().getAllReceiveServer();
		return SUCCESS;
	}

	public List<Clickoo_mail_receive_server> getServers() {
		return servers;
	}

	public void setServers(List<Clickoo_mail_receive_server> servers) {
		this.servers = servers;
	}


	/*private void loadAllReceiveConfig() {
		List<Clickoo_mail_receive_server> receivers = new ReceiveServerDao().getAllReceiveServer();
		InPath inpath = null;
		for (Clickoo_mail_receive_server receiver : receivers) {
			ReceiveConfigStore store = new ReceiveConfigStore();
			store.setId(receiver.getId());
			store.setName(receiver.getName());
			store.setStatus(receiver.getStatus());
			store.setRetrytime(receiver.getRetrytime());
			store.setIntervaltime(receiver.getIntervaltime());
			store.setConnections(receiver.getConnections());
			
			inpath = JsonUtil.getInstance().parserInPath(receiver.getInPath());
			store.setCompositor(inpath.getCompositor());
			store.setInhost(inpath.getInhost());
			store.setProtocolType(inpath.getProtocolType());
			store.setReceivePort(inpath.getReceivePort());
			store.setReceiveTs(inpath.getReceiveTs());
			store.setSsl(inpath.getSsl());
			store.setSupportalluid(inpath.getSupportalluid());
			
			servers.add(store);
		}
	}*/
	
	
}
