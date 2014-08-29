package com.jameslandrum.openptp;


import static com.jameslandrum.openptp.DataConversion.i32al;

public class ResponseParser {
    public static Object[] respond(PtpResponse response, OpenPTP.OutboundCommand command) {
        switch (command) {
            case GetStorageIDs:
            case GetObjectHandles:
                int[] list = i32al(response.getData().getBytes());
                return new Object[]{list};
            case GetObject:
            case GetThumb:
                return new Object[]{response.getData().getBytes()};
            default:
                return new Object[]{};
        }
    }
}
