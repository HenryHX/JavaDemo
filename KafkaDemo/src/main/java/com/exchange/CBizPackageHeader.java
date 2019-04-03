package com.exchange;

import io.netty.buffer.ByteBuf;

public class CBizPackageHeader {
    char version;
    char packageType;
    char chain;
    char padding__;
    int  topicID;
    int  tID;
    int  requestID;
    int  frontID;
    int  fieldCount;
    int  sessionID;
    int  time;
    long sequenceNo;
    long lastSn;

    public CBizPackageHeader() {
        version = 'A';
    }

    public CBizPackageHeader(int tid) {
        version = 'A';
        settID(tid);
    }

    public static int sizeof() {
        return (Byte.SIZE * 4 + Integer.SIZE * 7 + Long.SIZE * 2) / Byte.SIZE;
    }

    public boolean readFromBuf(ByteBuf buf) {
        if (!buf.isReadable(sizeof()))
            return false;

        this.setVersion((char) buf.readByte());
        this.setPackageType((char) buf.readByte());
        this.setChain((char) buf.readByte());
        this.setPadding__((char) buf.readByte());
        this.setTopicID(StringEncodeHelper.ctoji(buf.readInt()));
        this.settID(StringEncodeHelper.ctoji(buf.readInt()));
        this.setRequestID(StringEncodeHelper.ctoji(buf.readInt()));
        this.setFrontID(StringEncodeHelper.ctoji(buf.readInt()));
        this.setFieldCount(StringEncodeHelper.ctoji(buf.readInt()));
        this.setSessionID(StringEncodeHelper.ctoji(buf.readInt()));
        this.setTime(StringEncodeHelper.ctoji(buf.readInt()));

        this.setSequenceNo(StringEncodeHelper.ctojl(buf.readLong()));
        this.setLastSn(StringEncodeHelper.ctojl(buf.readLong()));

        return true;
    }

    public void writeToBuf(ByteBuf buf) {
        buf.writeByte(getVersion());
        buf.writeByte(getPackageType());
        buf.writeByte(getChain());
        buf.writeByte(getPadding__());

        buf.writeInt(StringEncodeHelper.ctoji(getTopicID()));
        buf.writeInt(StringEncodeHelper.ctoji(gettID()));
        buf.writeInt(StringEncodeHelper.ctoji(getRequestID()));
        buf.writeInt(StringEncodeHelper.ctoji(getFrontID()));
        buf.writeInt(StringEncodeHelper.ctoji(getFieldCount()));
        buf.writeInt(StringEncodeHelper.ctoji(getSessionID()));
        buf.writeInt(StringEncodeHelper.ctoji(getTime()));

        buf.writeLong(StringEncodeHelper.ctojl(getSequenceNo()));
        buf.writeLong(StringEncodeHelper.ctojl(getLastSn()));
    }

    public char getVersion() {
        return version;
    }

    public void setVersion(char version) {
        this.version = version;
    }

    public char getPackageType() {
        return packageType;
    }

    public void setPackageType(char packageType) {
        this.packageType = packageType;
    }

    public char getChain() {
        return chain;
    }

    public void setChain(char chain) {
        this.chain = chain;
    }

    public char getPadding__() {
        return padding__;
    }

    public void setPadding__(char padding__) {
        this.padding__ = padding__;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getFrontID() {
        return frontID;
    }

    public void setFrontID(int frontID) {
        this.frontID = frontID;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public long getLastSn() {
        return lastSn;
    }

    public void setLastSn(long lastSn) {
        this.lastSn = lastSn;
    }
}
