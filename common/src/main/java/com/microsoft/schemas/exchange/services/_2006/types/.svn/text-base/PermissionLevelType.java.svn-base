
package com.microsoft.schemas.exchange.services._2006.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for PermissionLevelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PermissionLevelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Owner"/>
 *     &lt;enumeration value="PublishingEditor"/>
 *     &lt;enumeration value="Editor"/>
 *     &lt;enumeration value="PublishingAuthor"/>
 *     &lt;enumeration value="Author"/>
 *     &lt;enumeration value="NoneditingAuthor"/>
 *     &lt;enumeration value="Reviewer"/>
 *     &lt;enumeration value="Contributor"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum PermissionLevelType {

    @XmlEnumValue("Author")
    AUTHOR("Author"),
    @XmlEnumValue("Contributor")
    CONTRIBUTOR("Contributor"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom"),
    @XmlEnumValue("Editor")
    EDITOR("Editor"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("NoneditingAuthor")
    NONEDITING_AUTHOR("NoneditingAuthor"),
    @XmlEnumValue("Owner")
    OWNER("Owner"),
    @XmlEnumValue("PublishingAuthor")
    PUBLISHING_AUTHOR("PublishingAuthor"),
    @XmlEnumValue("PublishingEditor")
    PUBLISHING_EDITOR("PublishingEditor"),
    @XmlEnumValue("Reviewer")
    REVIEWER("Reviewer");
    private final String value;

    PermissionLevelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PermissionLevelType fromValue(String v) {
        for (PermissionLevelType c: PermissionLevelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
