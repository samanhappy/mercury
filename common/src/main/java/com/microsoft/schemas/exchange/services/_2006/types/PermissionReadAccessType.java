
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for PermissionReadAccessType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PermissionReadAccessType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="FullDetails"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum PermissionReadAccessType {

    @XmlEnumValue("FullDetails")
    FULL_DETAILS("FullDetails"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    PermissionReadAccessType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PermissionReadAccessType fromValue(String v) {
        for (PermissionReadAccessType c: PermissionReadAccessType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
