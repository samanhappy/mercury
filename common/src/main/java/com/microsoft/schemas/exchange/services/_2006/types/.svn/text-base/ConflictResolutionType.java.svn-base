
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for ConflictResolutionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConflictResolutionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NeverOverwrite"/>
 *     &lt;enumeration value="AutoResolve"/>
 *     &lt;enumeration value="AlwaysOverwrite"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ConflictResolutionType {

    @XmlEnumValue("AlwaysOverwrite")
    ALWAYS_OVERWRITE("AlwaysOverwrite"),
    @XmlEnumValue("AutoResolve")
    AUTO_RESOLVE("AutoResolve"),
    @XmlEnumValue("NeverOverwrite")
    NEVER_OVERWRITE("NeverOverwrite");
    private final String value;

    ConflictResolutionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConflictResolutionType fromValue(String v) {
        for (ConflictResolutionType c: ConflictResolutionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
