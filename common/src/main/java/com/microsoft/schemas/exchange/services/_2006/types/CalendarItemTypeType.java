
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for CalendarItemTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalendarItemTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Single"/>
 *     &lt;enumeration value="Occurrence"/>
 *     &lt;enumeration value="Exception"/>
 *     &lt;enumeration value="RecurringMaster"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum CalendarItemTypeType {

    @XmlEnumValue("Exception")
    EXCEPTION("Exception"),
    @XmlEnumValue("Occurrence")
    OCCURRENCE("Occurrence"),
    @XmlEnumValue("RecurringMaster")
    RECURRING_MASTER("RecurringMaster"),
    @XmlEnumValue("Single")
    SINGLE("Single");
    private final String value;

    CalendarItemTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalendarItemTypeType fromValue(String v) {
        for (CalendarItemTypeType c: CalendarItemTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
