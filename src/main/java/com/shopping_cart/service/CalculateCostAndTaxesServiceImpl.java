package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.Category;
import com.shopping_cart.domain.CheckOutProduct;
import com.shopping_cart.domain.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CalculateCostAndTaxesServiceImpl implements CalculateCostAndTaxesService {

    @Override
    public void calculateTotalCostAndTaxesForOrderedProduct(CheckOutProduct checkOutProduct, Product product) {
        double costPrice = checkOutProduct.getUnitsOrdered() * product.getPrice();
        double taxes = calculateTaxesForOrderedProduct(costPrice, product.getCategories());
        checkOutProduct.setApplicableTaxes(taxes);
        checkOutProduct.setTotalCost(costPrice + taxes);
    }

    @Override
    public double calculateTaxesForOrderedProduct(double totalCost, List<Category> categoryList) {
        return categoryList.stream()
                .mapToDouble(category -> category.getTaxValue() * (totalCost / 100)).sum();
    }

    @Override
    public void updateTotalCostAndTaxesForAllOrderedProducts(BillDetails billDetails, CheckOutProduct checkOutProduct) {
        billDetails.setTotalCost(checkOutProduct.getTotalCost() + billDetails.getTotalCost());
        billDetails.setTotalSalesTax(checkOutProduct.getApplicableTaxes() + billDetails.getTotalSalesTax());
    }
}
