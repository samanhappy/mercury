
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for IdFormatType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IdFormatType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EwsLegacyId"/>
 *     &lt;enumeration value="EwsId"/>
 *     &lt;enumeration value="EntryId"/>
 *     &lt;enumeration value="HexEntryId"/>
 *     &lt;enumeration value="StoreId"/>
 *     &lt;enumeration value="OwaId"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum IdFormatType {

    @XmlEnumValue("EntryId")
    ENTRY_ID("EntryId"),
    @XmlEnumValue("EwsId")
    EWS_ID("EwsId"),
    @XmlEnumValue("EwsLegacyId")
    EWS_LEGACY_ID("EwsLegacyId"),
    @XmlEnumValue("HexEntryId")
    HEX_ENTRY_ID("HexEntryId"),
    @XmlEnumValue("OwaId")
    OWA_ID("OwaId"),
    @XmlEnumValue("StoreId")
    STORE_ID("StoreId");
    private final String value;

    IdFormatType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IdFormatType fromValue(String v) {
        for (IdFormatType c: IdFormatType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
