package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Enum.MerchantStatus;
import com.binarfud.challenge7.Model.Merchant;
import com.binarfud.challenge7.Service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/merchants")
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Merchants Available")
    @GetMapping(value = "/get-merchants")
    public ResponseEntity<List<Merchant>> getMerchantByStatus(@RequestParam(required = false) MerchantStatus merchantStatus) {
        try {
            List<Merchant> merchants = new ArrayList<>();
            if (merchantStatus == null) {
                merchantService.getAllMerchant().forEach(merchants::add);
            } else {
                merchantService.getMerchantByStatus(merchantStatus).forEach(merchants::add);
            }

            if (merchants.isEmpty()) {
                return new ResponseEntity<>(new ArrayList<Merchant>(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(merchants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new Merchants")
    @PostMapping(value = "/create-merchants", produces = "application/json")
    public ResponseEntity<Merchant> createMerchant(@RequestBody Merchant merchant) {
        try {
            Merchant _merchant = new Merchant(merchant.getMerchantName(), merchant.getMerchantLocation(), merchant.getOpen());
            if (merchantService.addNewMerchant(_merchant)) {
                return new ResponseEntity<>(_merchant, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured("ROLE_MERCHANT")
    @Operation(summary = "Edit Status Merchants by Id")
    @PutMapping("/edit-merchants/{id}")
    public ResponseEntity<Merchant> updateStatusMerchant(@PathVariable("id") String id, @RequestBody Merchant merchant) {
        Optional<Merchant> merchantData = merchantService.findById(id);
        try {
            if (merchantData.isPresent()) {
                Merchant _merchant = merchantData.get();
                _merchant.setOpen(merchant.getOpen());

                if (merchant.getMerchantName() != null) {
                    _merchant.setMerchantName(merchant.getMerchantName());
                }
                if (merchant.getMerchantLocation() != null) {
                    _merchant.setMerchantLocation(merchant.getMerchantLocation());
                }

                merchantService.updateStatusMerchant(_merchant);
                return new ResponseEntity<>(_merchant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Merchant By Id")
    @DeleteMapping("/delete-merchants/{id}")
    public ResponseEntity<HttpStatus> deleteMerchant(@PathVariable("id") String id) {
        try {
            Optional <Merchant> merchantData = merchantService.findById(id);
            if (merchantData.isPresent()) {
                merchantService.deleteMerchantById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
