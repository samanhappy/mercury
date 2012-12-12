
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for ExchangeVersionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExchangeVersionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Exchange2007"/>
 *     &lt;enumeration value="Exchange2007_SP1"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ExchangeVersionType {

    @XmlEnumValue("Exchange2007")
    EXCHANGE_2007("Exchange2007"),
    @XmlEnumValue("Exchange2007_SP1")
    EXCHANGE_2007_SP_1("Exchange2007_SP1");
    private final String value;

    ExchangeVersionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExchangeVersionType fromValue(String v) {
        for (ExchangeVersionType c: ExchangeVersionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
