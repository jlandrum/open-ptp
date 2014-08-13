package com.jameslandrum.openptp;

import java.util.ArrayList;

import static com.jameslandrum.openptp.DataConversion.*;

public class PtpResponse extends Response {
    private int mResult;
    private int mTransactionId;
    private ByteArray mPayload;
    private ArrayList<Integer> mParams;

    public PtpResponse() {
        throw new RuntimeException("Cannot create a PtpResponse without initial data.");
    }

    public PtpResponse(ByteArray payload, ByteArray data) {
        mPayload = data;

        mParams = new ArrayList<Integer>();

        mResult = i16l(payload.getBytes(0, 2));
        mTransactionId = i32l(payload.getBytes(2,6));

        int i = 6;
        while (i < payload.size()) {
            mParams.add(i32l(payload.getBytes(i,i+=4)));
        }
    }

    public OpenPTP.ResponseCode getResult() {
        return OpenPTP.ResponseCode.fromInt(mResult);
    }
}
