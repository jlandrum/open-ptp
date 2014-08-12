package com.jameslandrum.openptp;

public class Response {
    private int mLength;
    private int mCommand;
    private ByteArray mPayload;

    public Response() {
        mPayload = new ByteArray();
    }

    public int getInt(int sessionId, int i) {
        return 0;
    }

    protected void setLength(int length) {
        this.mLength = length;
    }

    public int getLength() {
        return mLength;
    }

    public void setCommand(int command) {
        this.mCommand = command;
    }

    public void setPayload(byte[] payload) {
        mPayload.clear();
        mPayload.add(payload);
    }

    @Override
    public String toString() {
        return "Length: " + mLength + " Command: " + mCommand + " Payload: " + mPayload.toString();
    }

    public ByteArray getPayload() {
        return mPayload;
    }

    public class ResponseFactory {

    }
}
