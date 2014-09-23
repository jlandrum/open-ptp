package com.jameslandrum.openptp.responders;

import com.jameslandrum.openptp.DataType;
import com.jameslandrum.openptp.PtpResponse;
import com.jameslandrum.openptp.ResponseRunner;

public class ObjectInfoResponseParser implements ResponseParser {
    @Override
    public Object[] respond(PtpResponse response) {
        final byte[] data = response.getData().getBytes();
        ResponseRunner runner = new ResponseRunner(response,
                DataType.UINT32,    // StorageID
                DataType.UINT16,    // ObjectFormat
                DataType.UINT16,    // ProtectionStatus
                DataType.UINT32,    // ObjectCompressedSize
                DataType.UINT16,    // ThumbFormat
                DataType.UINT32,    // ThumbCompressedSize
                DataType.UINT32,    // ThumbPixWidth
                DataType.UINT32,    // ThumbPixHeight
                DataType.UINT32,    // ImagePixWidth
                DataType.UINT32,    // ImagePixHeight
                DataType.UINT32,    // ImageBitDepth
                DataType.UINT32,    // ParentObject
                DataType.UINT16,    // AssociationType
                DataType.UINT32,    // AssociationDesc
                DataType.UINT32,    // SequenceNumber
                DataType.STRING,    // Filename
                DataType.STRING,    // CaptureDate
                DataType.STRING,    // ModificationDate
                DataType.STRING    // Keywords
            );
        return runner.createObjectArray();
    }
}
