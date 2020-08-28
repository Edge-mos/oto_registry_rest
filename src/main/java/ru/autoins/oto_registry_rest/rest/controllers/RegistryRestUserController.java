package ru.autoins.oto_registry_rest.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.autoins.oto_registry_rest.rest.exeptions.WrongDateException;
import ru.autoins.oto_registry_rest.service.Registry;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/user")
public class RegistryRestUserController {

    protected final Registry service;

    private DateTimeFormatter formatter;

    @Value("${date.pattern}")
    private String datePattern;

    @Autowired
    public RegistryRestUserController(Registry service) {
        this.service = service;
    }

    @PostConstruct
    private void init() {
        this.formatter = DateTimeFormatter.ofPattern(this.datePattern);
    }


    @GetMapping("/registry/{regDate}")
    protected byte[] getRegistryBinary(@PathVariable String regDate) throws IOException {
        this.checkIncomingDate(regDate);
        return this.service.getZipData();
    }

    private void checkIncomingDate(String date) {
        try {
            LocalDate incomingDate = LocalDate.parse(date, this.formatter);

            if (incomingDate.isAfter(LocalDate.now())) {
                throw new WrongDateException("Incorrect Date. Date in the future!");
            }
        } catch (DateTimeParseException ex) {
            throw new WrongDateException("Can't parse date - date must be yyyyMMdd format! ".concat(ex.getMessage()) );
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    //    @GetMapping(value = "/registry_raw")  //, consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    public byte[] getRawByte() {                                           // test
//        final byte[] rawByteData = this.service.getRawByteData();
//        System.out.println("Raw: " + Arrays.toString(rawByteData));
//        System.out.println("Length: " + rawByteData.length);
//        return rawByteData;
//    }

    //    @GetMapping(value = "/bytes")
//    public ResponseEntity<Object> downloadBytes() {
//        final byte[] rawByteData = this.service.getRawByteData();
//        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(rawByteData));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "registry.xml"));
//        return this.getResponseEntity(headers, rawByteData.length, resource);
//    }
}
