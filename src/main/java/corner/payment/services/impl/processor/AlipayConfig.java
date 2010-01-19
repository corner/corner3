/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-27
 */
package corner.payment.services.impl.processor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * alipay-config
 *
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
@XmlRootElement(name = "config")
public class AlipayConfig {
    private String service = "trade_create_by_buyer";
    private String charset = "utf-8";
    private String returnUrl = "fee/alipayresult";
    private String notifyUrl = "fee/alipaynotify";
    private String paymentType = "1";
    private String seller = "service@fepss.com";

    private String partnerId;
    private String key;
    private String showUrl;

    @XmlElement(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @XmlElement(name = "partnerId")
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    @XmlElement(name = "service")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @XmlElement(name = "charset")
    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @XmlElement(name = "returnUrl")
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @XmlElement(name = "paymentType")
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @XmlElement(name = "notifyUrl")
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }


    @XmlElement(name = "showUrl")
    public String getShowUrl() {
        return showUrl;
    }
    public void setShowUrl(String showUrl){
       this.showUrl = showUrl;
    }
}
