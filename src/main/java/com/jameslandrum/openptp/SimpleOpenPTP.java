package com.jameslandrum.openptp;

import java.io.IOException;

/**
 * SimpleOpenPTP.java
 * Adds functions to OpenPTP to simplify use.
 * (C) 2014 James Landrum
 */

public class SimpleOpenPTP extends OpenPTP {
    /**
     * Creates an instance of the SimpleOpenPTP
     * @param logger The logger to use.
     */
    public SimpleOpenPTP(LogStub logger) {
        super(logger);
    }

    public void openSession() throws IOException {
        sendPTPCommand(mCommandSocket, mTransactionId, null, OutboundCommand.OpenSession, mSessionId);
        Response response = receiveResponse(mCommandSocket); // TODO: Ensure opening
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
