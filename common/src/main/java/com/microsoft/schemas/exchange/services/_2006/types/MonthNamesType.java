
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for MonthNamesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MonthNamesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="January"/>
 *     &lt;enumeration value="February"/>
 *     &lt;enumeration value="March"/>
 *     &lt;enumeration value="April"/>
 *     &lt;enumeration value="May"/>
 *     &lt;enumeration value="June"/>
 *     &lt;enumeration value="July"/>
 *     &lt;enumeration value="August"/>
 *     &lt;enumeration value="September"/>
 *     &lt;enumeration value="October"/>
 *     &lt;enumeration value="November"/>
 *     &lt;enumeration value="December"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum MonthNamesType {

    @XmlEnumValue("April")
    APRIL("April"),
    @XmlEnumValue("August")
    AUGUST("August"),
    @XmlEnumValue("December")
    DECEMBER("December"),
    @XmlEnumValue("February")
    FEBRUARY("February"),
    @XmlEnumValue("January")
    JANUARY("January"),
    @XmlEnumValue("July")
    JULY("July"),
    @XmlEnumValue("June")
    JUNE("June"),
    @XmlEnumValue("March")
    MARCH("March"),
    @XmlEnumValue("May")
    MAY("May"),
    @XmlEnumValue("November")
    NOVEMBER("November"),
    @XmlEnumValue("October")
    OCTOBER("October"),
    @XmlEnumValue("September")
    SEPTEMBER("September");
    private final String value;

    MonthNamesType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MonthNamesType fromValue(String v) {
        for (MonthNamesType c: MonthNamesType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
