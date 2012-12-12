
package com.microsoft.schemas.exchange.services._2006.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRealItemsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRealItemsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Item" type="{http://schemas.microsoft.com/exchange/services/2006/types}ItemType"/>
 *           &lt;element name="Message" type="{http://schemas.microsoft.com/exchange/services/2006/types}MessageType"/>
 *           &lt;element name="CalendarItem" type="{http://schemas.microsoft.com/exchange/services/2006/types}CalendarItemType"/>
 *           &lt;element name="Contact" type="{http://schemas.microsoft.com/exchange/services/2006/types}ContactItemType"/>
 *           &lt;element name="DistributionList" type="{http://schemas.microsoft.com/exchange/services/2006/types}DistributionListType"/>
 *           &lt;element name="MeetingMessage" type="{http://schemas.microsoft.com/exchange/services/2006/types}MeetingMessageType"/>
 *           &lt;element name="MeetingRequest" type="{http://schemas.microsoft.com/exchange/services/2006/types}MeetingRequestMessageType"/>
 *           &lt;element name="MeetingResponse" type="{http://schemas.microsoft.com/exchange/services/2006/types}MeetingResponseMessageType"/>
 *           &lt;element name="MeetingCancellation" type="{http://schemas.microsoft.com/exchange/services/2006/types}MeetingCancellationMessageType"/>
 *           &lt;element name="Task" type="{http://schemas.microsoft.com/exchange/services/2006/types}TaskType"/>
 *           &lt;element name="PostItem" type="{http://schemas.microsoft.com/exchange/services/2006/types}PostItemType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRealItemsType", propOrder = {
    "itemOrMessageOrCalendarItem"
})
public class ArrayOfRealItemsType {

    @XmlElements({
        @XmlElement(name = "Contact", required = true, type = ContactItemType.class),
        @XmlElement(name = "CalendarItem", required = true, type = CalendarItemType.class),
        @XmlElement(name = "Item", required = true),
        @XmlElement(name = "Task", required = true, type = TaskType.class),
        @XmlElement(name = "MeetingResponse", required = true, type = MeetingResponseMessageType.class),
        @XmlElement(name = "PostItem", required = true, type = PostItemType.class),
        @XmlElement(name = "MeetingRequest", required = true, type = MeetingRequestMessageType.class),
        @XmlElement(name = "Message", required = true, type = MessageType.class),
        @XmlElement(name = "DistributionList", required = true, type = DistributionListType.class),
        @XmlElement(name = "MeetingCancellation", required = true, type = MeetingCancellationMessageType.class),
        @XmlElement(name = "MeetingMessage", required = true, type = MeetingMessageType.class)
    })
    protected List<ItemType> itemOrMessageOrCalendarItem;

    /**
     * Gets the value of the itemOrMessageOrCalendarItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemOrMessageOrCalendarItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemOrMessageOrCalendarItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactItemType }
     * {@link CalendarItemType }
     * {@link ItemType }
     * {@link TaskType }
     * {@link MeetingResponseMessageType }
     * {@link PostItemType }
     * {@link MeetingRequestMessageType }
     * {@link MessageType }
     * {@link DistributionListType }
     * {@link MeetingCancellationMessageType }
     * {@link MeetingMessageType }
     * 
     * 
     */
    public List<ItemType> getItemOrMessageOrCalendarItem() {
        if (itemOrMessageOrCalendarItem == null) {
            itemOrMessageOrCalendarItem = new ArrayList<ItemType>();
        }
        return this.itemOrMessageOrCalendarItem;
    }

}
