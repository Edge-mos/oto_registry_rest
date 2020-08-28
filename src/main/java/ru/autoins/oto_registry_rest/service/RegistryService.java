package ru.autoins.oto_registry_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.autoins.oto_registry_rest.dao.DbAccess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.ToDoubleBiFunction;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class RegistryService implements Registry{

    private final DbAccess provider;

    @Autowired
    public RegistryService(DbAccess provider) {
        this.provider = provider;
    }


    @Override
    public byte[] getCompressedData() throws IOException {
        final byte[] bytesFromDb = provider.getXmlStringFromDB().getBytes();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytesFromDb.length);

        try (GZIPOutputStream zipStream = new GZIPOutputStream(byteStream)) {
            zipStream.write(bytesFromDb);
            byteStream.close();
        }
        return byteStream.toByteArray();
    }

    @Override
    public byte[] getZipData() throws IOException {
        final byte[] bytesFromDb = provider.getXmlStringFromDB().getBytes();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytesFromDb.length);

        try(ZipOutputStream zipOutputStream = new ZipOutputStream(byteStream)) {
            zipOutputStream.putNextEntry(new ZipEntry("registry.xml"));
            zipOutputStream.write(bytesFromDb);
            byteStream.close();
        }
        return byteStream.toByteArray();
    }

    @Override
    public byte[] getRawByteData() {                  // test
        return this.provider.getXmlStringFromDB().getBytes();
    }
}
