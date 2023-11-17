package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Controller.InvoiceController;
import com.binarfud.challenge7.Controller.InvoiceController.InvoiceResponse;
import com.binarfud.challenge7.Service.InvoiceService;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private InvoiceService invoiceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGenerateInvoice() throws JRException, FileNotFoundException {
        String username = "testuser";
        byte[] invoiceData = "Invoice Data".getBytes();

        when(invoiceService.generateInvoice(username)).thenReturn(invoiceData);

        ResponseEntity<InvoiceResponse> response = invoiceController.generateInvoice(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(invoiceData, response.getBody().getInvoiceData());
    }

    @Test
    void testGenerateInvoiceInvalidUsername() {
        String username = null;

        ResponseEntity<InvoiceResponse> response = invoiceController.generateInvoice(username);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid username.", response.getBody().getErrorMessage());
    }

    @Test
    void testGenerateInvoiceError() throws JRException, FileNotFoundException {
        String username = "testuser";

        when(invoiceService.generateInvoice(username)).thenThrow(new JRException("Report error"));

        ResponseEntity<InvoiceResponse> response = invoiceController.generateInvoice(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error generating invoice: Report error", response.getBody().getErrorMessage());
    }
}
