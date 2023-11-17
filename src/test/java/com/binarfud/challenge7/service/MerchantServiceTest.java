package com.binarfud.challenge7.service;

import com.binarfud.challenge7.Enum.MerchantStatus;
import com.binarfud.challenge7.Model.Merchant;
import com.binarfud.challenge7.Repository.MerchantRepository;
import com.binarfud.challenge7.Service.Impl.MerchantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MerchantServiceTest {

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Mock
    private MerchantRepository merchantRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllMerchant() {
        List<Merchant> merchants = new ArrayList<>();
        when(merchantRepository.findAll()).thenReturn(merchants);

        List<Merchant> result = merchantService.getAllMerchant();

        assertEquals(merchants, result);
        verify(merchantRepository).findAll();
    }

    @Test
    public void testFindById() {
        String merchantId = "123";
        Merchant merchant = new Merchant();
        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(merchant));

        Optional<Merchant> result = merchantService.findById(merchantId);

        assertTrue(result.isPresent());
        assertEquals(merchant, result.get());
        verify(merchantRepository).findById(merchantId);
    }

    @Test
    public void testDeleteMerchantById() {
        String merchantCode = "123";
        doNothing().when(merchantRepository).deleteById(merchantCode);

        merchantService.deleteMerchantById(merchantCode);

        verify(merchantRepository).deleteById(merchantCode);
    }

    @Test
    public void testGetMerchantByStatus() {
        MerchantStatus merchantStatus = MerchantStatus.OPEN;
        List<Merchant> merchantList = new ArrayList<>();
        when(merchantRepository.findMerchantByStatus(merchantStatus)).thenReturn(merchantList);

        List<Merchant> result = merchantService.getMerchantByStatus(merchantStatus);

        assertEquals(merchantList, result);
        verify(merchantRepository).findMerchantByStatus(merchantStatus);
    }

    @Test
    public void testUpdateStatusMerchant() {
        String merchantCode = "123";
        Merchant merchant = new Merchant();
        when(merchantRepository.findById(merchantCode)).thenReturn(Optional.of(merchant));
        doNothing().when(merchantRepository).save(merchant);

        merchantService.updateStatusMerchant(merchant);

        verify(merchantRepository).findById(merchantCode);
        verify(merchantRepository).save(merchant);
    }

    @Test
    public void testAddNewMerchant() {
        Merchant merchant = new Merchant();
        when(merchantRepository.save(merchant)).thenReturn(merchant);

        boolean result = merchantService.addNewMerchant(merchant);

        assertTrue(result);
        verify(merchantRepository).save(merchant);
    }
}
