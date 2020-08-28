package ru.autoins.oto_registry_rest.service;

import java.io.IOException;

public interface Registry {

    byte[] getCompressedData() throws IOException;
    byte[] getRawByteData();
    byte[] getZipData() throws IOException;
}
