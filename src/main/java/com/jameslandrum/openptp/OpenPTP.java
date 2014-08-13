package com.jameslandrum.openptp;

import java.io.IOException;
import java.net.Socket;

import static com.jameslandrum.openptp.DataConversion.*;

/**
 * OpenPTP - Open PTP Communications Library
 * (C) 2014 James Landrum
 */

public class OpenPTP {
    Socket mCommandSocket;
    Socket mEventSocket;
    int mTransactionId;

    // Remote info
    int mSessionId;
    String mRemoteGuid;
    String mRemoteName;

    LogStub mLogger;

    public enum OutboundCommand {
        GetDeviceInfo(0x1001),
        OpenSession(0x1002),
        CloseSession(0x1003),
        GetStorageIDs(0x1004),
        GetStorageInfo(0x1005),
        GetNumObjects(0x1006),
        GetObjectHandles(0x1007),
        GetObjectInfo(0x1008),
        GetObject(0x1009),
        GetThumb(0x100A),
        DeleteObject(0x100B),
        InitiateCapture(0x100E),
        GetDevicePropDesc(0x1014),
        GetDevicePropValue(0x1015),
        SetDevicePropValue(0x1016),
        UnknownCommand(0x1022);

        byte[] value;

        OutboundCommand(int command) {
            value = i16l(command);
        }
    }

    public enum ResponseCode {
        Undefined(0x2000),
        OK(0x2001);

        byte[] value;

        ResponseCode(int command) {
            value = i16l(command);
        }
    }

    public OpenPTP(LogStub logger) {
        if (logger == null) {
            //Shim to allow null messaging
            mLogger = new LogStub() {@Override public void log(int level, String message) {}};
        } else {
            mLogger = logger;
        }
    }

    public void openConnection(String host, String guid, String name) throws IOException {
        try {
            mLogger.log(LogStub.INFO, "Opening connection to: " + host + ":15740");
            mCommandSocket = new Socket(host, 15740);

            // Create our connect packet and send it
            byte[] payload = ByteArray.compound(
                    hexstr(guid.replace("-","")),
                    str16lz(name),
                    i32l(1));
            sendCommand(mCommandSocket, 1, payload);

            // Wait for a response
            Response response = receiveResponse(mCommandSocket);
            mSessionId = i16l(response.getPayload().getBytes(0,4));
            mRemoteGuid = GUIDFromBytes(response.getPayload().getBytes(4, 16));
            mRemoteName = str16lz(response.getPayload().getBytes(20,-4));

            // Do the same for the event socket
            mEventSocket = new Socket(host, 15740);

            sendCommand(mEventSocket, 3, i32l(mSessionId));
            receiveResponse(mEventSocket);

            mTransactionId = 0;

            mLogger.log(LogStub.INFO, "Opened connection to: " + mRemoteGuid + " as " + mRemoteName);
        } catch (IOException e) {
            Log(LogStub.ERROR, "Failed to open connection to " + host + " on port 15740.");
            closeConnection();
            throw e;
        }
    }

    public void closeConnection() {
        try {
            mEventSocket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            mCommandSocket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        mEventSocket = null;
        mCommandSocket = null;
    }

    public boolean isConnected() {
        return (mEventSocket!=null && mCommandSocket!=null) && (!mEventSocket.isClosed() && !mCommandSocket.isClosed());
    }

    public synchronized void sendPTPCommand(Socket socket, int mTransactionId, byte[] payload, OutboundCommand command, int ... arg) throws IOException {
        byte[][] arg32 = new byte[arg.length][4];
        for (int i = 0; i < arg.length; i++) {
            arg32[i] = i32l(arg[i]);
        }

        byte[] cmd_payload = ByteArray.compound(
                i32l(1),
                command.value,
                i32(mTransactionId),
                ByteArray.compound(arg32)
        );

        sendCommand(socket, 6, cmd_payload);

        // TODO: Add data pack support
    }

    public synchronized void sendCommand(Socket socket, int commandId, byte[] payload) throws IOException {
        ByteArray data = new ByteArray();
        data.add(i32l(payload.length + 8),
                 i32l(commandId),
                 payload);
        Log(LogStub.INFO, "Sending Command: " + commandId + " Payload: " + data);
        socket.getOutputStream().write(data.getBytes());
    }

    protected synchronized Response receiveResponse(Socket socket) throws IOException {
        byte[] buffer = new byte[4];

        Response response = new Response();
        ByteArray payload = new ByteArray();

        // Read our size
        socket.getInputStream().read(buffer, 0, 4);
        response.setLength(i32l(buffer));
        payload.add(buffer);

        // Response has no command.
        if (response.getLength() < 8) {
            return response;
        }

        // Get the command
        socket.getInputStream().read(buffer, 0, 4);
        response.setCommand(i32l(buffer));
        payload.add(buffer);

        // Get the payload
        int responsePayload = response.getLength() - 8;
        buffer = new byte[responsePayload];
        socket.getInputStream().read(buffer, 0, responsePayload);

        response.setPayload(buffer);
        payload.add(buffer);

        Log(LogStub.INFO, response.toString());

        return response;
    }

    private void Log(int level, String message) {
        if (mLogger != null) {
            mLogger.log(level, message);
        }
    }

    private String GUIDFromBytes(byte[] in) {
        String guid = hexstr(in);
        return  guid.substring(0,8) + "-" +
                guid.substring(8,12) + "-" +
                guid.substring(12,16) + "-" +
                guid.substring(16,20) + "-" +
                guid.substring(20);
    }
}
