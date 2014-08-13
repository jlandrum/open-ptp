package com.jameslandrum.openptp;

import java.io.IOException;
import java.util.Arrays;

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

    public void initiateCapture() throws IOException {
        sendPTPCommand(mCommandSocket, mTransactionId, null, OutboundCommand.InitiateCapture, 0, 0);
        Response response = receiveResponse(mCommandSocket); // TODO: Ensure Capture
    }

    public void ping() throws IOException {
        sendPTPCommand(mCommandSocket, mTransactionId, null, OutboundCommand.GetDeviceInfo, 0, 0);
        Response response = receiveResponse(mCommandSocket); // TODO: Absorb the ping.
    }
}
