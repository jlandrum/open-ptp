package com.jameslandrum.openptp.responders;


import com.jameslandrum.openptp.PtpResponse;

public interface ResponseParser {
    public Object[] respond(PtpResponse response);
}
