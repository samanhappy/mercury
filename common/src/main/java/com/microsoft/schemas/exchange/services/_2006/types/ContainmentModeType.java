
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for ContainmentModeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContainmentModeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FullString"/>
 *     &lt;enumeration value="Prefixed"/>
 *     &lt;enumeration value="Substring"/>
 *     &lt;enumeration value="PrefixOnWords"/>
 *     &lt;enumeration value="ExactPhrase"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ContainmentModeType {

    @XmlEnumValue("ExactPhrase")
    EXACT_PHRASE("ExactPhrase"),
    @XmlEnumValue("FullString")
    FULL_STRING("FullString"),
    @XmlEnumValue("Prefixed")
    PREFIXED("Prefixed"),
    @XmlEnumValue("PrefixOnWords")
    PREFIX_ON_WORDS("PrefixOnWords"),
    @XmlEnumValue("Substring")
    SUBSTRING("Substring");
    private final String value;

    ContainmentModeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContainmentModeType fromValue(String v) {
        for (ContainmentModeType c: ContainmentModeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
