
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for MeetingAttendeeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MeetingAttendeeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Organizer"/>
 *     &lt;enumeration value="Required"/>
 *     &lt;enumeration value="Optional"/>
 *     &lt;enumeration value="Room"/>
 *     &lt;enumeration value="Resource"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum MeetingAttendeeType {

    @XmlEnumValue("Optional")
    OPTIONAL("Optional"),
    @XmlEnumValue("Organizer")
    ORGANIZER("Organizer"),
    @XmlEnumValue("Required")
    REQUIRED("Required"),
    @XmlEnumValue("Resource")
    RESOURCE("Resource"),
    @XmlEnumValue("Room")
    ROOM("Room");
    private final String value;

    MeetingAttendeeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MeetingAttendeeType fromValue(String v) {
        for (MeetingAttendeeType c: MeetingAttendeeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
