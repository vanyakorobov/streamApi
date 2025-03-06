package org.example;

public class CategoryMostExpensiveProduct {
    private final String category;
    private final Product product;

    public CategoryMostExpensiveProduct(String category, Product product) {
        this.category = category;
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Category: " + category + ", Most Expensive Product: " + (product != null ? product.getName() : "None");
    }
}
