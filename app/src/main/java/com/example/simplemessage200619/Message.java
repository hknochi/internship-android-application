package com.example.simplemessage200619;

public class Message {
   public Long msgId;
   public String msgContent;
   public String transmitterUsername;
   public String receiverUsername;
   public Long sendTime;


    @Override
    public String toString() {
        return "Message{" +
                "msgID=" + msgId +
                ", msgContent='" + msgContent + '\'' +
                ", transmitterUsername='" + transmitterUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }

    public Message (){

   }
    public Long getMsgID() {
        return msgId;
    }

    public void setMsgID(Long msgID) {
        this.msgId = msgID;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTransmitterUsername() {
        return transmitterUsername;
    }

    public void setTransmitterUsername(String transmitterUsername) {
        this.transmitterUsername = transmitterUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }
}
