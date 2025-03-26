package ru.restful.filescanner.service;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class SignatureScanService {

    private static final Set<String> knownSignatures = new HashSet<>(Arrays.asList(
            "1C90D538AB7720AD58FAAF2429934374E05A7BDF7B6DB6DA394162F339088F89",
            "signature"
    ));

    public boolean isFileSafe(byte[] fileContent) {
        String fileHash = calculateHash(fileContent).toUpperCase();
        System.out.println("file hash: " + fileHash);
        return !knownSignatures.contains(fileHash);
    }

    private String calculateHash(byte[] fileContent) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileContent);
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}

