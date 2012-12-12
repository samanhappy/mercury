
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for SensitivityChoicesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SensitivityChoicesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Normal"/>
 *     &lt;enumeration value="Personal"/>
 *     &lt;enumeration value="Private"/>
 *     &lt;enumeration value="Confidential"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum SensitivityChoicesType {

    @XmlEnumValue("Confidential")
    CONFIDENTIAL("Confidential"),
    @XmlEnumValue("Normal")
    NORMAL("Normal"),
    @XmlEnumValue("Personal")
    PERSONAL("Personal"),
    @XmlEnumValue("Private")
    PRIVATE("Private");
    private final String value;

    SensitivityChoicesType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SensitivityChoicesType fromValue(String v) {
        for (SensitivityChoicesType c: SensitivityChoicesType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
