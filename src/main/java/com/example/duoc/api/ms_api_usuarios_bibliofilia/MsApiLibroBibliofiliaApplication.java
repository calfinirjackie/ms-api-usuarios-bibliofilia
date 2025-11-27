package com.example.duoc.api.ms_api_usuarios_bibliofilia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
public class MsApiLibroBibliofiliaApplication {

    public static void main(String[] args) {
        String walletPath = Paths.get("").toAbsolutePath()
                .resolve("wallet")
                .toString();
        System.setProperty("oracle.net.tns_admin", walletPath);
        SpringApplication.run(MsApiLibroBibliofiliaApplication.class, args);
    }

}
