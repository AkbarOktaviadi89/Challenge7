package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Service.InvoiceService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Transactional
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Secured("ROLE_CUSTOMER")
    @GetMapping(value = "/generate-invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InvoiceResponse> generateInvoice(@RequestHeader("username") String username) {
        try {
            if (username != null) {
                byte[] invoiceData = invoiceService.generateInvoice(username);
                return ResponseEntity.ok(new InvoiceResponse(invoiceData));
            } else {
                return ResponseEntity.badRequest().body(new InvoiceResponse("Invalid username."));
            }
        } catch (JRException | FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new InvoiceResponse("Error generating invoice: " + e.getMessage()));
        }
    }

    public static class InvoiceResponse {
        private byte[] invoiceData;
        private String errorMessage;

        public InvoiceResponse(byte[] invoiceData) {
            this.invoiceData = invoiceData;
        }

        public InvoiceResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public byte[] getInvoiceData() {
            return invoiceData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
