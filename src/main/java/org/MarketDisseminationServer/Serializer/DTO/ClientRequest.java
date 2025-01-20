package org.MarketDisseminationServer.Serializer.DTO;

import lombok.Getter;

@Getter
public class ClientRequest {
    private String action;

    private int securityID;

    private int username;

    public ClientRequest() {

    }

    public ClientRequest(String action, int securityID, int username) {
        this.action = action;
        this.securityID = securityID;
        this.username = username;
    }

    public String toString() {
        return "ClientRequest{" +
                "action='" + action + '\'' +
                ", securityID=" + securityID +
                ", username='" + username + '\'' +
                '}';
    }
}
