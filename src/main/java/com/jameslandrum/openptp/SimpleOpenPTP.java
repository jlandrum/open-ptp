package com.jameslandrum.openptp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.jameslandrum.openptp.DataConversion.*;

/**
 * SimpleOpenPTP.java
 * Adds functions to OpenPTP to simplify use.
 * (C) 2014 James Landrum
 */

public class SimpleOpenPTP extends OpenPTP {
    int mTransactionId;

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

    public PtpResponse getPtpResponse(Response response) throws IOException {
        return receivePtpResponse(mCommandSocket, response);
    }
}