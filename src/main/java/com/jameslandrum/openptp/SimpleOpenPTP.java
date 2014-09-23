package com.jameslandrum.openptp;

import com.jameslandrum.openptp.responders.DeviceInfoResponseParser;
import com.jameslandrum.openptp.responders.GenericResponseParser;
import com.jameslandrum.openptp.responders.ObjectInfoResponseParser;
import com.jameslandrum.openptp.responders.RawDataResponseParser;
import com.jameslandrum.openptp.responders.ResponseParser;
import com.jameslandrum.openptp.responders.i32ArrayResponseParser;

import java.io.IOException;
import java.io.OutputStream;

/**
 * SimpleOpenPTP.java
 * Adds functions to OpenPTP to simplify use.
 * (C) 2014 James Landrum
 */

public class SimpleOpenPTP extends OpenPTP {
    private int mTransactionId;

    /**
     * Creates an instance of the SimpleOpenPTP
     *
     * @param logger The logger to use.
     */
    public SimpleOpenPTP(LogStub logger) {
        super(logger);
    }

    @Override
    public void openConnection(String host, String guid, String name) throws IOException {
        super.openConnection(host, guid, name);
        mTransactionId = 0;
    }

    public void openSession() throws IOException {
        sendPTPCommand(mCommandSocket, mTransactionId, null, OutboundCommand.OpenSession, mSessionId);
        PtpResponse response = receivePtpResponse(mCommandSocket);

        // Ensure we got a proper command
        if (response.getResult() != ResponseCode.OK) {
            this.Log(LogStub.ERROR, "Failed to Open Session");
        }
    }

    public int doCommand(OutboundCommand action, OutputStream capture, int[] params) throws IOException {
        int tid = ++mTransactionId;
        if (capture != null) {
            addStreamShim(tid, capture);
        }
        sendPTPCommand(mCommandSocket, mTransactionId, null, action, params);
        return tid;
    }

    public Response getResponse() throws IOException {
        return receiveResponse(mCommandSocket);
    }

    public Response getEvent() throws IOException {
        return receiveResponse(mEventSocket);
    }

    public PtpResponse getPtpResponse(Response response) throws IOException {
        return receivePtpResponse(mCommandSocket, response);
    }

    public static ResponseParser getDefaultResponder(OutboundCommand action) {
        switch (action) {
            case GetStorageIDs:
            case GetObjectHandles:
                return new i32ArrayResponseParser();
            case GetObject:
            case GetThumb:
                return new RawDataResponseParser();
            case GetDeviceInfo:
                return new DeviceInfoResponseParser();
            case GetObjectInfo:
                return new ObjectInfoResponseParser();
            default:
                return new GenericResponseParser();
        }
    }


}