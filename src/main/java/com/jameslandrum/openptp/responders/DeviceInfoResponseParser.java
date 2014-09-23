package com.jameslandrum.openptp.responders;

import com.jameslandrum.openptp.DataType;
import com.jameslandrum.openptp.PtpResponse;
import com.jameslandrum.openptp.ResponseRunner;

public class DeviceInfoResponseParser implements ResponseParser {
    @Override
    public Object[] respond(PtpResponse response) {
        ResponseRunner runner = new ResponseRunner(response,
                DataType.UINT16,
                DataType.UINT32,
                DataType.UINT16,
                DataType.STRING,
                DataType.UINT16,
                DataType.UINT32_ARRAY,
                DataType.UINT32_ARRAY,
                DataType.UINT32_ARRAY,
                DataType.UINT32_ARRAY,
                DataType.UINT32_ARRAY,
                DataType.STRING,
                DataType.STRING,
                DataType.STRING,
                DataType.STRING
        );
        return runner.createObjectArray();
    }
}
