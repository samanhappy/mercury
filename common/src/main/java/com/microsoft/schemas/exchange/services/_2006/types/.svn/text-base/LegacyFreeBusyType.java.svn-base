
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for LegacyFreeBusyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LegacyFreeBusyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Free"/>
 *     &lt;enumeration value="Tentative"/>
 *     &lt;enumeration value="Busy"/>
 *     &lt;enumeration value="OOF"/>
 *     &lt;enumeration value="NoData"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum LegacyFreeBusyType {

    @XmlEnumValue("Busy")
    BUSY("Busy"),
    @XmlEnumValue("Free")
    FREE("Free"),
    @XmlEnumValue("NoData")
    NO_DATA("NoData"),
    OOF("OOF"),
    @XmlEnumValue("Tentative")
    TENTATIVE("Tentative");
    private final String value;

    LegacyFreeBusyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LegacyFreeBusyType fromValue(String v) {
        for (LegacyFreeBusyType c: LegacyFreeBusyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
