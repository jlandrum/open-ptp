package com.jameslandrum.openptp.responders;

import com.jameslandrum.openptp.PtpResponse;

public class RawDataResponseParser implements ResponseParser {
    @Override
    public Object[] respond(PtpResponse response) {
        return new Object[]{response.getData().getBytes()};
    }
}
