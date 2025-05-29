package com.maximase.document_converter_service.service;


import com.maximase.document_converter_service.entity.ConversionLog;
import com.maximase.document_converter_service.entity.User;
import com.maximase.document_converter_service.repository.ConversionLogRepository;
import com.maximase.document_converter_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ConversionService {
    private final UserRepository userRepo;
    private final ConversionLogRepository logRepo;

    public byte[] convertToPdf(MultipartFile file, Authentication auth) {

        User user = userRepo.findByUsername(auth.getName())
                .orElseThrow();

        String original = file.getOriginalFilename();
        String baseName = original != null
                ? original.replaceAll("\\.[^.]+$", "")
                : "document";


        Path workDir;
        try {
            workDir = Files.createTempDirectory("conv-");
        } catch (IOException e) {
            throw new RuntimeException("Cannot create temp dir", e);
        }


        Path input = workDir.resolve(original);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, input, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            cleanup(workDir);
            throw new RuntimeException("Failed to save temp file", e);
        }


        ConversionLog log = new ConversionLog();
        log.setUser(user);
        log.setSourceFilename(original);
        log.setSourceMimeType(file.getContentType());
        log.setConvertedTo("PDF");
        log.setStatus("PENDING");
        log = logRepo.save(log);

        Path output = workDir.resolve(baseName + ".pdf");
        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "soffice",
                    "--headless",
                    "--convert-to", "pdf",
                    input.toString(),
                    "--outdir", workDir.toString()
            );
            pb.redirectErrorStream(true);
            Process p = pb.start();
            int exit = p.waitFor();
            if (exit != 0) {
                String err = new String(p.getInputStream().readAllBytes());
                throw new RuntimeException("LibreOffice failed: " + err);
            }


            byte[] pdf = Files.readAllBytes(output);


            log.setStatus("SUCCESS");
            logRepo.save(log);

            return pdf;

        } catch (Exception ex) {
            log.setStatus("FAIL");
            logRepo.save(log);
            throw new RuntimeException("Conversion failed", ex);

        } finally {
            cleanup(workDir);
        }
    }

    private void cleanup(Path dir) {
        try {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException ignore) {
                        }
                    });
        } catch (IOException ignore) {
        }
    }
}
