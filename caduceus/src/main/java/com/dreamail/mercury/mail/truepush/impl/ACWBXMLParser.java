package com.dreamail.mercury.mail.truepush.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.kxml2.io.KXmlParser;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

public class ACWBXMLParser {
	private static final Logger logger = LoggerFactory
			.getLogger(ACWBXMLParser.class);
	private static String[] Contacts = new String[] { "Anniversary", // 0x05
			"AssistantName", // 0x06
			"AssistantTelephoneNumber", // 0x07
			"Birthday", // 0x08
			null, null, null, "Business2PhoneNumber", // 0x0C
			"BusinessCity", // 0x0D
			"BusinessCountry", // 0x0E
			"BusinessPostalCode", // 0x0F
			"BusinessState", // 0x10
			"BusinessStreet", // 0x11
			"BusinessFaxNumber", // 0x12
			"BusinessPhoneNumber", // 0x13
			"CarPhoneNumber", // 0x14
			"Categories", // 0x15
			"Category", // 0x16
			"Children", // 0x17
			"Child", // 0x18
			"CompanyName", // 0x19
			"Department", // 0x1A
			"Email1Address", // 0x1B
			"Email2Address", // 0x1C
			"Email3Address", // 0x1D
			"FileAs", // 0x1E
			"FirstName", // 0x1F
			"Home2PhoneNumber", // 0x20
			"HomeCity", // 0x21
			"HomeCountry", // 0x22
			"HomePostalCode", // 0x23
			"HomeState", // 0x24
			"HomeStreet", // 0x25
			"HomeFaxNumber", // 0x26
			"HomePhoneNumber", // 0x27
			"JobTitle", // 0x28
			"LastName", // 0x29
			"MiddleName", // 0x2A
			"MobilePhoneNumber", // 0x2B
			"OfficeLocation", // 0x2C
			"OtherCity", // 0x2D
			"OtherCountry", // 0x2E
			"OtherPostalCode", // 0x2F
			"OtherState", // 0x30
			"OtherStreet", // 0x31
			"PagerNumber", // 0x32
			"RadioPhoneNumber", // 0x33
			"Spouse", // 0x34
			"Suffix", // 0x35
			"Title", // 0x36
			"Webpage", // 0x37
			"YomiCompanyName", // 0x38
			"YomiFirstName", // 0x39
			"YomiLastName", // 0x3A
			null, "Picture", // 0x3C
			"Alias", // 0x3D
			"WeightedRank" // 0x3E
	};

	private static String[] AirSyncBase = new String[] { "BodyPreference", // 0x05
			"AirSyncBase_Type", // 0x06
			"TruncationSize", // 0x07
			"AllOrNone", // 0x08
			null, "Body", // 0x0A
			"Data", // 0x0B
			"EstimatedDataSize", // 0x0C
			"Truncated", // 0x0D
			"AirSyncBase_Attachments", // 0x0E
			"AirSyncBase_Attachment", // 0x0F
			"DisplayName", // 0x10
			"FileReference", // 0x11
			"Method", // 0x12
			"ContentId", // 0x13
			"ContentLocation", // 0x14
			"IsInline", // 0x15
			"NativeBodyType", // 0x16
			"ContentType", // 0x17
			"Preview", // 0x18
			"BodyPart", // 0x1A
			"AirSyncBase_Status" // 0x1B
	};

	private static String[] AirSync = new String[] { "Sync", // 0x05
			"Responses", // 0x06
			"Add", // 0x07
			"Change", // 0x08
			"Delete", // 0x09
			"Fetch", // 0x0A
			"AirSync_SyncKey", // 0x0B
			"ClientId", // 0x0C
			"ServerId", // 0x0D
			"AirSync_Status", // 0x0E
			"AirSync_Collection", // 0x0F
			"AirSync_Class", // 0x10
			null, "AirSync_CollectionId", // 0x12
			"GetChanges", // 0x13
			"MoreAvailable", // 0x14
			"WindowSize", // 0x15
			"Commands", // 0x16
			"Options", // 0x17
			"FilterType", // 0x18
			"Truncation", // 0x19
			"RTFTruncation", // 0x1A
			"Conflict", // 0x1B
			"AirSync_Collections", // 0x1C
			"ApplicationData", // 0x1D
			"DeletesAsMoves", // 0x1E
			null, "Supported", // 0x20
			"SoftDelete", // 0x21
			"MIMESupport", // 0x22
			"MIMETruncation", // 0x23
			"Wait", // 0x24
			"Limit", // 0x25
			"Partial", // 0x26
			"ConversationMode", // 0x27
			"MaxItems", // 0x28
			"HeartbeatInterval" // 0x29
	};

	private static String[] FolderHierarchy = new String[] { null, null,
			"DisplayName", // 0x07
			"ServerId", // 0x08
			"ParentId", // 0x09
			"Type", // 0x0A
			null, "FolderHierarchy_Status", // 0x0C
			null, "Changes", // 0x0E
			"Add", // 0x0F
			"Delete", // 0x10
			"Update", // 0x11
			"FolderHierarchy_SyncKey", // 0x12
			"FolderCreate", // 0x13
			"FolderDelete", // 0x14
			"FolderUpdate", // 0x15
			"FolderSync", // 0x16
			"Count" // 0x17
	};

	private static String[] Email = new String[] { "Email_Attachment",
			"Email_Attachments", "Att_Name", "Att_Size", "unknown_0x09", "Att_Method",
			"unknown_0x0B", "InternetCPID_1", "FlagStatus_1", "Flag_1", "DateReceived",// 0x0F
			"Display_Name", "DisplayTo",// 0x11
			"Importance",// 0x12
			"MessageClass",// 0x13
			"Subject",// 0x14
			"Read",// 0x15
			"To",// 0x16
			"CC",// 0x17
			"From",// 0x18
			"ReplyTo",// 0x19
			"AllDayEvent",// 0x1A
			"Categories",// 0x1B
			"Category",// 0x1C
			"DTStamp",// 0x1D
			"EndTime",// 0x1E
			"InstanceType",// 0x1F
			"BusyStatus",// 0x20
			"Location",// 0x21
			"MeetingRequest",// 0x22
			"Organizer",// 0x23
			"RecurrenceId",// 0x24
			"Reminder",// 0x25
			"ResponseRequested",// 0x26
			"Recurrences",// 0x27
			"Recurrence",// 0x28
			"Recurrence_Type",// 0x29
			"Recurrence_Until",// 0x2A
			"Recurrence_Occurrences",// 0x2B
			"Recurrence_Interval",// 0x2C
			"Recurrence_DayOfWeek",// 0x2D
			"Recurrence_DayOfMonth",// 0x2E
			"Recurrence_WeekOfMonth",// 0x2F
			"Recurrence_MonthOfYear",// 0x30
			"StartTime",// 0x31
			"Sensitivity",// 0x32
			"TimeZone",// 0x33
			"GlobalObjId",// 0x34
			"ThreadTopic",// 0x35
			"InternetCPID",// 0x36
			"Flag",// 0x37
			"FlagStatus",// 0x38
			"ContentClass",// 0x39
			"FlagType",// 0x3A
			"CompleteTime",// 0x3B
			"DisallowNewTimeProposal",// 0x3C
			"FlagType",// 0x3D
			"CompleteTime",// 0x3E
			"DisallowNewTimeProposal"// 0x3F
	};

	private static String[] Setting = new String[] { "Settings",// 0x05
			"Setting_Status",// 0x06
			"Get",// 0x07
			"Set",// 0x08
			"Oof",// 0x09
			"OofState",// 0x0A
			"StartTime",// 0x0B
			"EndTime",// 0x0C
			"OofMessage",// 0x0D
			"AppliesToInternal",// 0x0E
			"AppliesToExternalKnown",// 0x0F
			"AppliesToExternalUnknown",// 0x10
			"Enabled",// 0x11
			"ReplyMessage",// 0x12
			"BodyType",// 0x13
			"DevicePassword",// 0x14
			"Password",// 0x15
			"DeviceInformaton",// 0x16
			"Model",// 0x17
			"IMEI",// 0x18
			"FriendlyName",// 0x19
			"OS",// 0x1A
			"OSLanguage",// 0x1B
			"PhoneNumber",// 0x1C
			"UserInformation",// 0x1D
			"EmailAddresses",// 0x1E
			"SmtpAddress",// 0x1F
			"UserAgent",// 0x20
			"EnableOutboundSMS",// 0x21
			"MobileOperator",// 0x22
			"PrimarySmtpAddress",// 0x23
			"Accounts",// 0x24
			"Account",// 0x25
			"AccountId",// 0x26
			"AccountName",// 0x27
			"UserDisplayName",// 0x28
			"SendDisabled",// 0x29
			"RightsManagementInformation"// 0x2B
	};

	private static String[] Ping = new String[] { "Ping",// 0x05
			"AutdState",// 0x06
			"Ping_Status",// 0x07
			"HeartbeatInterval",// 0x08
			"Folders",// 0x09
			"Folder",// 0x0A
			"Id",// 0x0B
			"Ping_Class",// 0x0C
			"MaxFolders"// 0x0D
	};

	private static String[] ItemEstimate = new String[] { "GetItemEstimate",// 0x05
			"Version",// 0x06
			"ItemEstimate_Collections",// 0x07
			"ItemEstimate_Collection",// 0x08
			"ItemEstimate_Class",// 0x09
			"ItemEstimate_CollectionId",// 0x0A
			"DateTime",// 0x0B
			"Estimate",// 0x0C
			"Response",// 0x0D
			"ItemEstimate_Status"// 0x0E
	};

	public static byte[] getWBXML(Object o) {
		KXmlParser parser = new KXmlParser();
		System.out.println(ASParser.getInstance().toXml(o));
		try {
			parser.setInput(ASParser.getInstance().toXmlInputStream(o), "UTF-8");
			Document dom = new Document();
			dom.parse(parser);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ACWBSer ser = new ACWBSer();
			ser.setTagTable(0, AirSync);
			ser.setTagTable(1, Contacts);
			ser.setTagTable(2, Email);
			ser.setTagTable(6, ItemEstimate);
			ser.setTagTable(7, FolderHierarchy);
			ser.setTagTable(13, Ping);
			ser.setTagTable(17, AirSyncBase);
			ser.setTagTable(18, Setting);
			ser.setOutput(out, null);
			dom.write(ser);
			ser.flush();
			return out.toByteArray();
		} catch (XmlPullParserException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}

	public static IASResponse parseWBXML(InputStream in) {
		ACWbxmlPar parser = new ACWbxmlPar();
		parser.setTagTable(0, AirSync);
		parser.setTagTable(1, Contacts);
		parser.setTagTable(2, Email);
		parser.setTagTable(6, ItemEstimate);
		parser.setTagTable(7, FolderHierarchy);
		parser.setTagTable(13, Ping);
		parser.setTagTable(17, AirSyncBase);
		parser.setTagTable(18, Setting);
		try {
			parser.setInput(in, "UTF-8");

			Document dom = new Document();
			dom.parse(parser);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			KXmlSerializer ser = new KXmlSerializer();
			ser.setOutput(out, null);
			dom.write(ser);
			ser.flush();

			byte[] b = out.toByteArray();
			logger.info("Response:::"+new String(b));
			return ASParser.getInstance()
					.fromXml(dom.getRootElement().getName(),
							new ByteArrayInputStream(b));
		} catch (XmlPullParserException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}

	@Test
	public void testPaseWBXML() throws FileNotFoundException,
			XmlPullParserException, IOException {
		ACWBXMLParser.parseWBXML(new ByteArrayInputStream(new byte[] { 3, 1,
				106, 0, 0, 6, 69, 71, 72, 73, 3, 67, 111, 110, 116, 97, 99,
				116, 115, 0, 1, 74, 3, 50, 58, 48, 0, 1, 0, 0, 88, 3, 48, 0, 1,
				75, 3, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 55, 57, 56, 70,
				67, 53, 55, 57, 65, 55, 52, 65, 70, 50, 50, 66, 51, 49, 54, 57,
				49, 50, 48, 49, 49, 48, 84, 48, 56, 48, 57, 50, 53, 46, 54, 51,
				48, 48, 49, 48, 49, 48, 49, 84, 48, 48, 48, 48, 48, 48, 46, 48,
				48, 48, 0, 1, 1, 1, 1 }));
	}
}
