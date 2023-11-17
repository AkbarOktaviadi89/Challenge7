package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Controller.MerchantController;
import com.binarfud.challenge7.Enum.MerchantStatus;
import com.binarfud.challenge7.Model.Merchant;
import com.binarfud.challenge7.Service.MerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MerchantControllerTest {

    @InjectMocks
    private MerchantController merchantController;

    @Mock
    private MerchantService merchantService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMerchantByStatusAll() {
        MerchantStatus merchantStatus = null;
        List<Merchant> merchants = new ArrayList<>();
        merchants.add(new Merchant());

        when(merchantService.getAllMerchant()).thenReturn(merchants);

        ResponseEntity<List<Merchant>> response = merchantController.getMerchantByStatus(merchantStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMerchantByStatusFiltered() {
        MerchantStatus merchantStatus = MerchantStatus.OPEN;
        List<Merchant> merchants = new ArrayList<>();
        merchants.add(new Merchant());

        when(merchantService.getMerchantByStatus(merchantStatus)).thenReturn(merchants);

        ResponseEntity<List<Merchant>> response = merchantController.getMerchantByStatus(merchantStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMerchantByStatusEmpty() {
        MerchantStatus merchantStatus = null;
        List<Merchant> merchants = new ArrayList<>();

        when(merchantService.getAllMerchant()).thenReturn(merchants);

        ResponseEntity<List<Merchant>> response = merchantController.getMerchantByStatus(merchantStatus);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testCreateMerchant() {
        Merchant merchant = new Merchant();
        when(merchantService.addNewMerchant(merchant)).thenReturn(true);

        ResponseEntity<Merchant> response = merchantController.createMerchant(merchant);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateMerchantError() {
        Merchant merchant = new Merchant();
        when(merchantService.addNewMerchant(merchant)).thenReturn(false);

        ResponseEntity<Merchant> response = merchantController.createMerchant(merchant);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateStatusMerchant() {
        String id = "1";
        Merchant merchant = new Merchant();
        Optional<Merchant> merchantData = Optional.of(new Merchant());

        when(merchantService.findById(id)).thenReturn(merchantData);

        ResponseEntity<Merchant> response = merchantController.updateStatusMerchant(id, merchant);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateStatusMerchantNotFound() {
        String id = "1";
        Merchant merchant = new Merchant();
        Optional<Merchant> merchantData = Optional.empty();

        when(merchantService.findById(id)).thenReturn(merchantData);

        ResponseEntity<Merchant> response = merchantController.updateStatusMerchant(id, merchant);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateStatusMerchantError() {
        String id = "1";
        Merchant merchant = new Merchant();
        Optional<Merchant> merchantData = Optional.of(new Merchant());

        doThrow(new RuntimeException("Simulated error")).when(merchantService).updateStatusMerchant(any(Merchant.class));

        when(merchantService.findById(id)).thenReturn(merchantData);

        ResponseEntity<Merchant> response = merchantController.updateStatusMerchant(id, merchant);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testDeleteMerchant() {
        String id = "1";
        Optional<Merchant> merchantData = Optional.of(new Merchant());

        when(merchantService.findById(id)).thenReturn(merchantData);

        ResponseEntity<HttpStatus> response = merchantController.deleteMerchant(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteMerchantNotFound() {
        String id = "1";
        Optional<Merchant> merchantData = Optional.empty();

        when(merchantService.findById(id)).thenReturn(merchantData);

        ResponseEntity<HttpStatus> response = merchantController.deleteMerchant(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteMerchantError() {
        String id = "1";
        Optional<Merchant> merchantData = Optional.of(new Merchant());

        when(merchantService.findById(id)).thenReturn(merchantData);
        doThrow(new RuntimeException()).when(merchantService).deleteMerchantById(id);

        ResponseEntity<HttpStatus> response = merchantController.deleteMerchant(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
