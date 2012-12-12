
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for MeetingRequestTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MeetingRequestTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="FullUpdate"/>
 *     &lt;enumeration value="InformationalUpdate"/>
 *     &lt;enumeration value="NewMeetingRequest"/>
 *     &lt;enumeration value="Outdated"/>
 *     &lt;enumeration value="SilentUpdate"/>
 *     &lt;enumeration value="PrincipalWantsCopy"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum MeetingRequestTypeType {

    @XmlEnumValue("FullUpdate")
    FULL_UPDATE("FullUpdate"),
    @XmlEnumValue("InformationalUpdate")
    INFORMATIONAL_UPDATE("InformationalUpdate"),
    @XmlEnumValue("NewMeetingRequest")
    NEW_MEETING_REQUEST("NewMeetingRequest"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Outdated")
    OUTDATED("Outdated"),
    @XmlEnumValue("PrincipalWantsCopy")
    PRINCIPAL_WANTS_COPY("PrincipalWantsCopy"),
    @XmlEnumValue("SilentUpdate")
    SILENT_UPDATE("SilentUpdate");
    private final String value;

    MeetingRequestTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MeetingRequestTypeType fromValue(String v) {
        for (MeetingRequestTypeType c: MeetingRequestTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
