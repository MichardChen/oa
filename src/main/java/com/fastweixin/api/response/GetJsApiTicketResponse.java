package com.fastweixin.api.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author XXXX
 */
public class GetJsApiTicketResponse extends BaseResponse {

    private String ticket;

    @JSONField(name = "expires_in")
    private Integer expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
