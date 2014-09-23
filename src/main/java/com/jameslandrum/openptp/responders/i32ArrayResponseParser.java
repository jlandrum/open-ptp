package com.jameslandrum.openptp.responders;

import com.jameslandrum.openptp.PtpResponse;

import static com.jameslandrum.openptp.DataConversion.i32al;

public class i32ArrayResponseParser implements ResponseParser {
    @Override
    public Object[] respond(PtpResponse response) {
        int[] list = i32al(response.getData().getBytes());
        return new Object[]{list};
    }
}
