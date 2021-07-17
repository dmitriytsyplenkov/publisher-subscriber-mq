package com.dts.subscriber.service;

import com.dts.subscriber.model.Purchase;
import com.dts.subscriber.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceJPAImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceJPAImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }
}
