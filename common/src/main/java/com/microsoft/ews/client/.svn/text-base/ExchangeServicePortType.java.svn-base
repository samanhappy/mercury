package com.microsoft.ews.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import com.microsoft.schemas.exchange.services._2006.messages.AddDelegateResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.AddDelegateType;
import com.microsoft.schemas.exchange.services._2006.messages.ConvertIdResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.ConvertIdType;
import com.microsoft.schemas.exchange.services._2006.messages.CopyFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CopyFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.CopyItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CopyItemType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateAttachmentResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateAttachmentType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateManagedFolderRequestType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateManagedFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteAttachmentResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteAttachmentType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteItemType;
import com.microsoft.schemas.exchange.services._2006.messages.ExpandDLResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.ExpandDLType;
import com.microsoft.schemas.exchange.services._2006.messages.FindFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.FindFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemType;
import com.microsoft.schemas.exchange.services._2006.messages.GetAttachmentResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetAttachmentType;
import com.microsoft.schemas.exchange.services._2006.messages.GetDelegateResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.GetDelegateType;
import com.microsoft.schemas.exchange.services._2006.messages.GetEventsResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetEventsType;
import com.microsoft.schemas.exchange.services._2006.messages.GetFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.GetItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetItemType;
import com.microsoft.schemas.exchange.services._2006.messages.GetUserAvailabilityRequestType;
import com.microsoft.schemas.exchange.services._2006.messages.GetUserAvailabilityResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetUserOofSettingsResponse;
import com.microsoft.schemas.exchange.services._2006.messages.MoveFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.MoveFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.MoveItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.MoveItemType;
import com.microsoft.schemas.exchange.services._2006.messages.RemoveDelegateResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.RemoveDelegateType;
import com.microsoft.schemas.exchange.services._2006.messages.ResolveNamesResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.ResolveNamesType;
import com.microsoft.schemas.exchange.services._2006.messages.SendItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.SendItemType;
import com.microsoft.schemas.exchange.services._2006.messages.SetUserOofSettingsResponse;
import com.microsoft.schemas.exchange.services._2006.messages.SubscribeResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.SubscribeType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderHierarchyResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderHierarchyType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderItemsResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderItemsType;
import com.microsoft.schemas.exchange.services._2006.messages.UnsubscribeResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.UnsubscribeType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateDelegateResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateDelegateType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateItemType;
import com.microsoft.schemas.exchange.services._2006.types.AvailabilityProxyRequestType;
import com.microsoft.schemas.exchange.services._2006.types.ExchangeImpersonationType;
import com.microsoft.schemas.exchange.services._2006.types.SerializedSecurityContextType;

@WebService(name = "ExchangeServicePortType", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ExchangeServicePortType {

	@WebMethod(operationName = "GetEvents", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetEvents")
	@WebResult(name = "GetEventsResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetEventsResponseType getEvents(
			@WebParam(name = "GetEvents", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") GetEventsType GetEvents,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "CopyFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/CopyFolder")
	@WebResult(name = "CopyFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public CopyFolderResponseType copyFolder(
			@WebParam(name = "CopyFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") CopyFolderType CopyFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "SyncFolderItems", action = "http://schemas.microsoft.com/exchange/services/2006/messages/SyncFolderItems")
	@WebResult(name = "SyncFolderItemsResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public SyncFolderItemsResponseType syncFolderItems(
			@WebParam(name = "SyncFolderItems", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") SyncFolderItemsType SyncFolderItems,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "FindFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/FindFolder")
	@WebResult(name = "FindFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public FindFolderResponseType findFolder(
			@WebParam(name = "FindFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") FindFolderType FindFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "GetUserOofSettings", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetUserOofSettings")
	@WebResult(name = "GetUserOofSettingsResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetUserOofSettingsResponse getUserOofSettings(
			@WebParam(name = "GetUserOofSettingsRequest", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") com.microsoft.schemas.exchange.services._2006.messages.GetUserOofSettingsRequest GetUserOofSettingsRequest,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "UpdateFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/UpdateFolder")
	@WebResult(name = "UpdateFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public UpdateFolderResponseType updateFolder(
			@WebParam(name = "UpdateFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") UpdateFolderType UpdateFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "DeleteAttachment", action = "http://schemas.microsoft.com/exchange/services/2006/messages/DeleteAttachment")
	@WebResult(name = "DeleteAttachmentResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public DeleteAttachmentResponseType deleteAttachment(
			@WebParam(name = "DeleteAttachment", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") DeleteAttachmentType DeleteAttachment,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "FindItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/FindItem")
	@WebResult(name = "FindItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public FindItemResponseType findItem(
			@WebParam(name = "FindItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") FindItemType FindItem);

	@WebMethod(operationName = "SendItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/SendItem")
	@WebResult(name = "SendItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public SendItemResponseType sendItem(
			@WebParam(name = "SendItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") SendItemType SendItem);

	@WebMethod(operationName = "DeleteItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/DeleteItem")
	@WebResult(name = "DeleteItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public DeleteItemResponseType deleteItem(
			@WebParam(name = "DeleteItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") DeleteItemType DeleteItem);

	@WebMethod(operationName = "CreateItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/CreateItem")
	@WebResult(name = "CreateItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public CreateItemResponseType createItem(
			@WebParam(name = "CreateItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") CreateItemType CreateItem);

	@WebMethod(operationName = "GetFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetFolder")
	@WebResult(name = "GetFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetFolderResponseType getFolder(
			@WebParam(name = "GetFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") GetFolderType GetFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "AddDelegate", action = "http://schemas.microsoft.com/exchange/services/2006/messages/AddDelegate")
	@WebResult(name = "AddDelegateResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public AddDelegateResponseMessageType addDelegate(
			@WebParam(name = "AddDelegate", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") AddDelegateType AddDelegate,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "Subscribe", action = "http://schemas.microsoft.com/exchange/services/2006/messages/Subscribe")
	@WebResult(name = "SubscribeResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public SubscribeResponseType subscribe(
			@WebParam(name = "Subscribe", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") SubscribeType Subscribe,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "GetUserAvailability", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetUserAvailability")
	@WebResult(name = "GetUserAvailabilityResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetUserAvailabilityResponseType getUserAvailability(
			@WebParam(name = "GetUserAvailabilityRequest", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") GetUserAvailabilityRequestType GetUserAvailabilityRequest,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "ProxyRequestTypeHeader", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") AvailabilityProxyRequestType ProxyRequestTypeHeader,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "ProxyRequestTypeHeader", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) AvailabilityProxyRequestType ProxyRequestTypeHeader2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "CreateManagedFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/CreateManagedFolder")
	@WebResult(name = "CreateManagedFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public CreateManagedFolderResponseType createManagedFolder(
			@WebParam(name = "CreateManagedFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") CreateManagedFolderRequestType CreateManagedFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "CopyItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/CopyItem")
	@WebResult(name = "CopyItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public CopyItemResponseType copyItem(
			@WebParam(name = "CopyItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") CopyItemType CopyItem,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "MoveItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/MoveItem")
	@WebResult(name = "MoveItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public MoveItemResponseType moveItem(
			@WebParam(name = "MoveItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") MoveItemType MoveItem,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "GetDelegate", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetDelegate")
	@WebResult(name = "GetDelegateResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetDelegateResponseMessageType getDelegate(
			@WebParam(name = "GetDelegate", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") GetDelegateType GetDelegate,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "RemoveDelegate", action = "http://schemas.microsoft.com/exchange/services/2006/messages/RemoveDelegate")
	@WebResult(name = "RemoveDelegateResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public RemoveDelegateResponseMessageType removeDelegate(
			@WebParam(name = "RemoveDelegate", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") RemoveDelegateType RemoveDelegate,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "MoveFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/MoveFolder")
	@WebResult(name = "MoveFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public MoveFolderResponseType moveFolder(
			@WebParam(name = "MoveFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") MoveFolderType MoveFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "UpdateItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/UpdateItem")
	@WebResult(name = "UpdateItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public UpdateItemResponseType updateItem(
			@WebParam(name = "UpdateItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") UpdateItemType UpdateItem,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "TimeZoneContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") Object TimeZoneContext,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "TimeZoneContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) Object TimeZoneContext2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "UpdateDelegate", action = "http://schemas.microsoft.com/exchange/services/2006/messages/UpdateDelegate")
	@WebResult(name = "UpdateDelegateResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public UpdateDelegateResponseMessageType updateDelegate(
			@WebParam(name = "UpdateDelegate", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") UpdateDelegateType UpdateDelegate,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "GetItem", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetItem")
	@WebResult(name = "GetItemResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetItemResponseType getItem(
			@WebParam(name = "GetItem", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") GetItemType GetItem);

	@WebMethod(operationName = "DeleteFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/DeleteFolder")
	@WebResult(name = "DeleteFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public DeleteFolderResponseType deleteFolder(
			@WebParam(name = "DeleteFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") DeleteFolderType DeleteFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "ConvertId", action = "http://schemas.microsoft.com/exchange/services/2006/messages/ConvertId")
	@WebResult(name = "ConvertIdResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public ConvertIdResponseType convertId(
			@WebParam(name = "ConvertId", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") ConvertIdType ConvertId,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "CreateFolder", action = "http://schemas.microsoft.com/exchange/services/2006/messages/CreateFolder")
	@WebResult(name = "CreateFolderResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public CreateFolderResponseType createFolder(
			@WebParam(name = "CreateFolder", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") CreateFolderType CreateFolder,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "SetUserOofSettings", action = "http://schemas.microsoft.com/exchange/services/2006/messages/SetUserOofSettings")
	@WebResult(name = "SetUserOofSettingsResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public SetUserOofSettingsResponse setUserOofSettings(
			@WebParam(name = "SetUserOofSettingsRequest", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") com.microsoft.schemas.exchange.services._2006.messages.SetUserOofSettingsRequest SetUserOofSettingsRequest,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "CreateAttachment", action = "http://schemas.microsoft.com/exchange/services/2006/messages/CreateAttachment")
	@WebResult(name = "CreateAttachmentResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public CreateAttachmentResponseType createAttachment(
			@WebParam(name = "CreateAttachment", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") CreateAttachmentType CreateAttachment);

	@WebMethod(operationName = "ResolveNames", action = "http://schemas.microsoft.com/exchange/services/2006/messages/ResolveNames")
	@WebResult(name = "ResolveNamesResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public ResolveNamesResponseType resolveNames(
			@WebParam(name = "ResolveNames", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") ResolveNamesType ResolveNames,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "GetAttachment", action = "http://schemas.microsoft.com/exchange/services/2006/messages/GetAttachment")
	@WebResult(name = "GetAttachmentResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public GetAttachmentResponseType getAttachment(
			@WebParam(name = "GetAttachment", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") GetAttachmentType GetAttachment);

	@WebMethod(operationName = "Unsubscribe", action = "http://schemas.microsoft.com/exchange/services/2006/messages/Unsubscribe")
	@WebResult(name = "UnsubscribeResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public UnsubscribeResponseType unsubscribe(
			@WebParam(name = "Unsubscribe", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") UnsubscribeType Unsubscribe,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "SyncFolderHierarchy", action = "http://schemas.microsoft.com/exchange/services/2006/messages/SyncFolderHierarchy")
	@WebResult(name = "SyncFolderHierarchyResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public SyncFolderHierarchyResponseType syncFolderHierarchy(
			@WebParam(name = "SyncFolderHierarchy", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") SyncFolderHierarchyType SyncFolderHierarchy,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

	@WebMethod(operationName = "ExpandDL", action = "http://schemas.microsoft.com/exchange/services/2006/messages/ExpandDL")
	@WebResult(name = "ExpandDLResponse", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages")
	public ExpandDLResponseType expandDL(
			@WebParam(name = "ExpandDL", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages") ExpandDLType ExpandDL,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") ExchangeImpersonationType ExchangeImpersonation,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") SerializedSecurityContextType SerializedSecurityContext,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") String MailboxCulture,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types") com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			@WebParam(name = "ExchangeImpersonation", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) ExchangeImpersonationType ExchangeImpersonation2,
			@WebParam(name = "SerializedSecurityContext", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) SerializedSecurityContextType SerializedSecurityContext2,
			@WebParam(name = "MailboxCulture", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) String MailboxCulture2,
			@WebParam(name = "RequestServerVersion", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true) com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			@WebParam(name = "ServerVersionInfo", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/types", header = true, mode = WebParam.Mode.OUT) Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2);

}
