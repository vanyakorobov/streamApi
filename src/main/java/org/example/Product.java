package org.example;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {  // продукты

    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;

    public Product(Long id, String name, String category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }
public BigDecimal setPrice(BigDecimal price) {
        return price;
}
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Проверка на идентичность
        if (!(o instanceof Product)) return false; // Проверка на тип
        Product product = (Product) o; // Приведение типа
        return Objects.equals(id, product.id); // Сравнение по id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Генерация hashCode по id
    }
}
//