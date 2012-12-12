package com.microsoft.ews.client;

import javax.jws.WebService;
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

@WebService(serviceName = "ExchangeWebService", targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages", endpointInterface = "com.microsoft.ews.client.ExchangeServicePortType")
public class ExchangeWebServiceImpl implements ExchangeServicePortType {

	public GetEventsResponseType getEvents(
			GetEventsType GetEvents,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public CopyFolderResponseType copyFolder(
			CopyFolderType CopyFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public SyncFolderItemsResponseType syncFolderItems(
			SyncFolderItemsType SyncFolderItems,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public FindFolderResponseType findFolder(
			FindFolderType FindFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public GetUserOofSettingsResponse getUserOofSettings(
			com.microsoft.schemas.exchange.services._2006.messages.GetUserOofSettingsRequest GetUserOofSettingsRequest,
			ExchangeImpersonationType ExchangeImpersonation,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public UpdateFolderResponseType updateFolder(
			UpdateFolderType UpdateFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public DeleteAttachmentResponseType deleteAttachment(
			DeleteAttachmentType DeleteAttachment,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public FindItemResponseType findItem(FindItemType FindItem) {
		throw new UnsupportedOperationException();
	}

	public SendItemResponseType sendItem(SendItemType SendItem) {
		throw new UnsupportedOperationException();
	}

	public DeleteItemResponseType deleteItem(DeleteItemType DeleteItem) {
		throw new UnsupportedOperationException();
	}

	public CreateItemResponseType createItem(CreateItemType CreateItem) {
		throw new UnsupportedOperationException();
	}

	public GetFolderResponseType getFolder(
			GetFolderType GetFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public AddDelegateResponseMessageType addDelegate(
			AddDelegateType AddDelegate,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public SubscribeResponseType subscribe(
			SubscribeType Subscribe,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public GetUserAvailabilityResponseType getUserAvailability(
			GetUserAvailabilityRequestType GetUserAvailabilityRequest,
			SerializedSecurityContextType SerializedSecurityContext,
			AvailabilityProxyRequestType ProxyRequestTypeHeader,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext2,
			AvailabilityProxyRequestType ProxyRequestTypeHeader2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public CreateManagedFolderResponseType createManagedFolder(
			CreateManagedFolderRequestType CreateManagedFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public CopyItemResponseType copyItem(
			CopyItemType CopyItem,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public MoveItemResponseType moveItem(
			MoveItemType MoveItem,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public GetDelegateResponseMessageType getDelegate(
			GetDelegateType GetDelegate,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public RemoveDelegateResponseMessageType removeDelegate(
			RemoveDelegateType RemoveDelegate,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public MoveFolderResponseType moveFolder(
			MoveFolderType MoveFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public UpdateItemResponseType updateItem(
			UpdateItemType UpdateItem,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			Object TimeZoneContext,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Object TimeZoneContext2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public UpdateDelegateResponseMessageType updateDelegate(
			UpdateDelegateType UpdateDelegate,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public GetItemResponseType getItem(GetItemType GetItem) {
		throw new UnsupportedOperationException();
	}

	public DeleteFolderResponseType deleteFolder(
			DeleteFolderType DeleteFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public ConvertIdResponseType convertId(
			ConvertIdType ConvertId,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public CreateFolderResponseType createFolder(
			CreateFolderType CreateFolder,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public SetUserOofSettingsResponse setUserOofSettings(
			com.microsoft.schemas.exchange.services._2006.messages.SetUserOofSettingsRequest SetUserOofSettingsRequest,
			ExchangeImpersonationType ExchangeImpersonation,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public CreateAttachmentResponseType createAttachment(
			CreateAttachmentType CreateAttachment) {
		throw new UnsupportedOperationException();
	}

	public ResolveNamesResponseType resolveNames(
			ResolveNamesType ResolveNames,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public GetAttachmentResponseType getAttachment(
			GetAttachmentType GetAttachment) {
		throw new UnsupportedOperationException();
	}

	public UnsubscribeResponseType unsubscribe(
			UnsubscribeType Unsubscribe,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public SyncFolderHierarchyResponseType syncFolderHierarchy(
			SyncFolderHierarchyType SyncFolderHierarchy,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

	public ExpandDLResponseType expandDL(
			ExpandDLType ExpandDL,
			ExchangeImpersonationType ExchangeImpersonation,
			SerializedSecurityContextType SerializedSecurityContext,
			String MailboxCulture,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion,
			ExchangeImpersonationType ExchangeImpersonation2,
			SerializedSecurityContextType SerializedSecurityContext2,
			String MailboxCulture2,
			com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion RequestServerVersion2,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo,
			Holder<com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo> ServerVersionInfo2) {
		throw new UnsupportedOperationException();
	}

}
