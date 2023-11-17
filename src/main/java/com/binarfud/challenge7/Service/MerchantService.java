package com.binarfud.challenge7.Service;


import com.binarfud.challenge7.Enum.MerchantStatus;
import com.binarfud.challenge7.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MerchantService {

    List<Merchant> getAllMerchant();

    List<Merchant> getMerchantByStatus(MerchantStatus merchantStatus);
    Boolean addNewMerchant(Merchant merchant);

    Optional<Merchant> findById(String id);

    void deleteMerchantById(String merchant);

    void updateStatusMerchant(Merchant merchant);


//    Page<Merchant> getMerchantPaged(int page);

}
