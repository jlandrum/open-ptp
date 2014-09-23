package com.jameslandrum.openptp.responders;

import com.jameslandrum.openptp.DataType;
import com.jameslandrum.openptp.PtpResponse;

import static com.jameslandrum.openptp.DataConversion.*;

public class PropertyResponseParser implements ResponseParser {
    private DataType mDataType;

    public PropertyResponseParser(DataType dataType) {
        mDataType = dataType;
    }

    @Override
    public Object[] respond(PtpResponse response) {
        switch (mDataType) {
            case UINT8:
                return new Object[] { (response.getData().getBytes(0,1)[0] & 0xff) };
            case UINT16:
                return new Object[] { i16l(response.getData().getBytes(0,2)) };
            case UINT32:
                return new Object[] { i32l(response.getData().getBytes(0,4)) };
            case RAW:
                return new Object[] { i32l(response.getData().getBytes()) };
        }
        return new Object[] { response };
    }
}
