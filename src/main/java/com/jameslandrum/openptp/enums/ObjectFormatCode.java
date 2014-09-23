package com.jameslandrum.openptp.enums;

public enum ObjectFormatCode {
    UNDEFINED   (0x3000), // Undefined: Undefined non-image object
    ASSOCIATION (0x3001), // Association: Association (e.g. folder)
    SCRIPT      (0x3002), // Script: Device-model-specific script
    EXECUTABLE  (0x3003), // Executable: Device-model-specific binary executable
    TEXT        (0x3004), // Text: Text file
    HTML        (0x3005), // HTML: HyperText Markup Language file (text)
    DPOF        (0x3006), // DPOF: Digital Print Order Format file (text)
    AIFF        (0x3007), // AIFF: Audio clip
    WAV         (0x3008), // WAV: Audio clip
    MP3         (0x3009), // MP3: Audio clip
    AVI         (0x300A), // AVI: Video clip
    MPEG        (0x300B), // MPEG: Video clip
    ASF         (0x300C), // ASF: Microsoft Advanced Streaming Format (video)
    UNDEFINED_I (0x3800), // Undefined: Unknown image object
    EXIF_JPEG   (0x3801), // EXIF/JPEG: Exchangeable File Format, JEIDA standard
    TIFF_EP     (0x3802), // TIFF/EP: Tag Image File Format for Electronic Photography
    FLASHPIX    (0x3803), // FlashPix: Structured Storage Image Format
    BMP         (0x3804), // BMP: Microsoft Windows Bitmap file
    CIFF        (0x3805), // CIFF: Canon Camera Image File Format
    UNDEFINED_R (0x3806), // Undefined: Reserved
    GIF         (0x3807), // GIF: Graphics Interchange Format
    JFIF        (0x3808), // JFIF: JPEG File Interchange Format
    PCD         (0x3809), // PCD: PhotoCD Image Pac
    PICT        (0x380A), // PICT: QuickDraw Image Format
    PNG         (0x380B), // PNG: Portable Network Graphics
    RESERVED    (0x380C), // Reserved
    TIFF        (0x380D), // TIFF: Tag Image File Format
    TIFF_IT     (0x380E), // TIFF/IT: Tag Image File Format for Information Technology (graphic arts)
    JP2         (0x380F), // JP2: JPEG2000 Baseline File Format
    JPX         (0x3810); // JPX: JPEG2000 Extended File Format

    int value;
    ObjectFormatCode(int i) {
        value = i;
    }

    public static ObjectFormatCode fromInt(int i) {
        for (ObjectFormatCode formatCode : ObjectFormatCode.values()) {
            if (formatCode.value == i) return formatCode;
        }
        return UNDEFINED;
    }
}
