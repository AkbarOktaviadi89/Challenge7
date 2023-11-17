package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Enum.MerchantStatus;
import com.binarfud.challenge7.Model.Merchant;
import com.binarfud.challenge7.Repository.MerchantRepository;
import com.binarfud.challenge7.Service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public List<Merchant> getAllMerchant() {
        try {
            List<Merchant> merchants = merchantRepository.findAll();
            log.info("Retrieved all merchants.");
            return merchants;
        } catch (Exception e) {
            log.error("Error while retrieving all merchants: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve merchants.", e);
        }
    }

    @Override
    public Optional<Merchant> findById(String id) {
        try {
            log.info("Retrieving Merchant by ID: {}", id);
            return merchantRepository.findById(id);
        } catch (Exception e) {
            log.error("Error while retrieving Merchant by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve Merchant by ID.", e);
        }
    }


    @Override
    public void deleteMerchantById(String merchantCode) {
        try {
            merchantRepository.deleteById(merchantCode);
            log.info("Deleted merchant with code: {}", merchantCode);
        } catch (Exception e) {
            log.error("Error while deleting merchant: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete merchant.", e);
        }
    }

    @Override
    public List<Merchant> getMerchantByStatus(MerchantStatus merchantStatus) {
        try {
            log.info("Retrieving Merchants by status: {}", merchantStatus);
            List<Merchant> merchantList = merchantRepository.findMerchantByStatus(merchantStatus);
            return merchantList;
        } catch (Exception e) {
            log.error("Error while retrieving merchants by status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve merchants by status.", e);
        }
    }

    @Override
    public void updateStatusMerchant(Merchant merchant) {
        try {
            log.info("Updating Merchant with code: {}", merchant.getMerchantCode());
            Optional<Merchant> existingMerchant = merchantRepository.findById(merchant.getMerchantCode());

            if (existingMerchant.isPresent()) {
                merchantRepository.save(merchant);
                log.info("Updated Merchant with code: {}", merchant.getMerchantCode());
            } else {
                log.error("Merchant with code {} not found. Update failed.", merchant.getMerchantCode());
                throw new RuntimeException("Merchant not found for update.");
            }
        } catch (Exception e) {
            log.error("Error while updating merchant: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update merchant.", e);
        }
    }

    @Override
    public Boolean addNewMerchant(Merchant merchant) {
        try {
            if (merchant != null) {
                log.info("Saving new Merchant...");
                merchantRepository.save(merchant);
                return true;
            } else {
                log.error("Failed to save new Merchant: merchant is null");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new merchant: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add a new merchant.", e);
        }
    }
}

