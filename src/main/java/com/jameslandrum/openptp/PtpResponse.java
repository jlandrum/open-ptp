package com.jameslandrum.openptp;

import java.util.ArrayList;

import static com.jameslandrum.openptp.DataConversion.*;

public class PtpResponse extends Response {
    private int mResult;
    private int mTransactionId;
    private ByteArray mData;
    private ArrayList<Integer> mParams;

    public PtpResponse(Response cloneof, ByteArray data) {
        super(cloneof);

        mParams = new ArrayList<>();
        mData = data;

        mResult = i16l(cloneof.getPayload().getBytes(0, 2));
        mTransactionId = i32l(cloneof.getPayload().getBytes(2,6));

        int i = 6;
        while (i < cloneof.getPayload().size()) {
            mParams.add(i32l(cloneof.getPayload().getBytes(i, i + 4)));
            i+=4;
        }
    }

    public OpenPTP.ResponseCode getResult() {
        return OpenPTP.ResponseCode.fromInt(mResult);
    }

    public ByteArray getData() {
        return mData;
    }

    public Integer getTransactionId() {
        return mTransactionId;
    }
}
