package com.maximase.document_converter_service.controller.impl;

import com.maximase.document_converter_service.service.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConversionControllerImpl {

    private final ConversionService conversionService;

    @PostMapping(
            value = "/convert",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convert(
            @RequestPart("file") MultipartFile file,
            Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        byte[] pdf = conversionService.convertToPdf(file, auth);

        String filename = file.getOriginalFilename() != null
                ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                : "converted.pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(pdf);
    }

    /**
     * Конвертирует файл DOCX в PDF
     *
     * @param file документ в формате DOCX
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/docx",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convertDocxToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "DOCX");
        }
    }

    /**
     * Конвертирует файл DOC в PDF
     *
     * @param file документ в формате DOC
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/doc",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convertDocToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "DOC");
        }
    }

    /**
     * Конвертирует файл ODT в PDF
     *
     * @param file документ в формате ODT
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/odt",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convertOdtToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "ODT");
        }
    }

    /**
     * Конвертирует файл RTF в PDF
     *
     * @param file документ в формате RTF
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/rtf",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convertRtfToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "RTF");
        }
    }

    /**
     * Конвертирует файл XLSX в PDF
     *
     * @param file документ в формате XLSX
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/xlsx",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convertXlsxToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "XLSX");
        }
    }

    /**
     * Конвертирует файл PPTX в PDF
     *
     * @param file документ в формате PPTX
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/pptx",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )

    public ResponseEntity<byte[]> convertPptxToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "PPTX");
        }
    }

    /**
     * Конвертирует файл ODP в PDF
     *
     * @param file документ в формате ODP
     * @return ResponseEntity с PDF файлом
     */
    @PostMapping(
            value = "/convert/odp",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> convertOdpToPdf(
            @RequestPart("file") MultipartFile file, Authentication auth) {
        ResponseEntity<byte[]> emptyCheck = checkEmptyFile(file);
        if (emptyCheck != null) return emptyCheck;
        try {
            byte[] pdf = conversionService.convertToPdf(file, auth);
            String filename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("\\.[^.]+$", "") + ".pdf"
                    : "converted.pdf";
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return handleConversionError(e, "ODP");
        }
    }

    /**
     * Обрабатывает исключения, возникающие при конвертации документа,
     * и формирует корректный HTTP-ответ с сообщением об ошибке в формате JSON.
     *
     * @param e        исключение, возникшее при конвертации
     * @param fileType строка, описывающая тип обрабатываемого файла (например, "DOCX", "XLSX")
     * @return ResponseEntity с телом ошибки в виде JSON и соответствующим HTTP-статусом:
     * - 400 Bad Request при ошибке типа IllegalArgumentException (например, неверный формат файла)
     * - 500 Internal Server Error для всех остальных исключений
     */
    private ResponseEntity<byte[]> handleConversionError(Exception e, String fileType) {
        if (e instanceof IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid file format", "message", "The provided file is not a valid " + fileType + " document.").toString().getBytes());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error", "message", "An error occurred while processing the " + fileType + " file. Please try again.").toString().getBytes());
        }
    }

    /**
     * Проверка, что файл не пустой.
     * Если файл пустой, возвращает ResponseEntity с ошибкой,
     * иначе — возвращает null.
     */
    private ResponseEntity<byte[]> checkEmptyFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            String message = "Ошибка: загруженный файл пустой или отсутствует.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(message.getBytes());
        }
        return null;
    }
}
