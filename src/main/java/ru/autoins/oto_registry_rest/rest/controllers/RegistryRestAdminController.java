package ru.autoins.oto_registry_rest.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.autoins.oto_registry_rest.service.Registry;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class RegistryRestAdminController extends RegistryRestUserController {

    @Autowired
    public RegistryRestAdminController(Registry service) {
        super(service);
    }

    @Override
    @GetMapping("/registry/{regDate}")
    protected byte[] getRegistryBinary(@PathVariable String regDate) throws IOException {
        return super.getRegistryBinary(regDate);
    }

    @GetMapping(value = "/gzip")
    public ResponseEntity<Object> downloadCompressedBytes() throws IOException {
        final byte[] compressedData = this.service.getCompressedData();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(compressedData));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "registry.xml.zip"));
        return this.getResponseEntity(headers, compressedData.length, resource);
    }

    @GetMapping(value = "/zip")
    public ResponseEntity<Object> downloadZippedBytes() throws IOException {
        final byte[] zippedData = this.service.getZipData();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(zippedData));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "registry.zip"));
        return this.getResponseEntity(headers, zippedData.length, resource);
    }

    private ResponseEntity<Object> getResponseEntity(HttpHeaders headers, int contentLength, InputStreamResource resource) {
        return ResponseEntity
                .ok().headers(headers)
                .contentLength(contentLength)
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
    }
}
