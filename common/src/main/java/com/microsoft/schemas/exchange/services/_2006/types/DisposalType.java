
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for DisposalType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DisposalType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HardDelete"/>
 *     &lt;enumeration value="SoftDelete"/>
 *     &lt;enumeration value="MoveToDeletedItems"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum DisposalType {

    @XmlEnumValue("HardDelete")
    HARD_DELETE("HardDelete"),
    @XmlEnumValue("MoveToDeletedItems")
    MOVE_TO_DELETED_ITEMS("MoveToDeletedItems"),
    @XmlEnumValue("SoftDelete")
    SOFT_DELETE("SoftDelete");
    private final String value;

    DisposalType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DisposalType fromValue(String v) {
        for (DisposalType c: DisposalType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
