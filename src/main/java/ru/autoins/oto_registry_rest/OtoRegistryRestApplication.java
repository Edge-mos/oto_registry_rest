package ru.autoins.oto_registry_rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.autoins.oto_registry_rest.dao.RegistryProvider;
import ru.autoins.oto_registry_rest.security.models.Role;
import ru.autoins.oto_registry_rest.security.models.User;
import ru.autoins.oto_registry_rest.security.security_dao.RoleRepository;
import ru.autoins.oto_registry_rest.security.security_dao.UserRepository;
import ru.autoins.oto_registry_rest.security.security_utils.SecurityUser;
import ru.autoins.oto_registry_rest.service.Registry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

@SpringBootApplication
public class OtoRegistryRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtoRegistryRestApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(RegistryProvider provider, Registry registryService, UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {

            // запись файла
//            final byte[] bytes = provider.getStringFromDB().getBytes();
//
//            String storageFolder = "C:\\Users\\yamnikovva\\Desktop\\Source\\";
//
//            final File destination = Paths.get(storageFolder.concat("compressall.xml.zip")).toFile();
//
//            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination));
//                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bos);
//            ) {
//                gzipOutputStream.write(bytes);
//                gzipOutputStream.finish();
//            }
//
//            final byte[] zippedBytes = Files.readAllBytes(Paths.get(storageFolder.concat("compressall.xml.zip")));
//            System.out.println("zipped: " + zippedBytes.length);

            //-----------------------------------------------------------------------------------------------------

//            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytes.length);
//            GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
//            zipStream.write(bytes);
//            zipStream.close();
//            byteStream.close();
//
//            byte[] compressedData = byteStream.toByteArray();
//
//            System.err.println("Final: " +  compressedData.length);
            //-----------------------------------------------------------------------------------------------------

//            final byte[] compressedData = registryService.getCompressedData();
//            System.err.println("compressed size kb: " + compressedData.length / 1024);
//
//            final byte[] zipData = registryService.getZipData();
//            System.err.println("zipData size kb: " + zipData.length / 1024);





//            final Optional<User> admin = userRepository.getUserByName("WTF");
//            final User user = admin.get();
//
//            SecurityUser securityUser = new SecurityUser(user);
//
//            final Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
//
//            authorities.forEach(grantedAuthority -> System.out.println(grantedAuthority.getAuthority()));


//            final Role admin = roleRepository.getRoleByName("ADMIN");
//            System.err.println("permissions: " +  admin.getListOfPermissions());
//
//            Role user = new Role();
//            user.setRoleName("USER");
//
//            user.setPermissionsById(1, 2);
//
//            roleRepository.persistRole(user);


//            User admin = new User();
//            admin.setStatusById(1);
//            admin.setUserName("WTF");
//            admin.setPassword("FUCK");
//
//            Role adminRole = new Role();
//            adminRole.setRoleName("ADMIN");
//            adminRole.setPermissionsById(1, 2);
//
//            admin.setListOfRoles(Set.of(adminRole));
//            adminRole.setListOfUsers(Set.of(admin));
//
//
//
//            userRepository.persistUser(admin);

            //MASTER BRANCH
            //Eugene JWT branch

        };
    }

}
