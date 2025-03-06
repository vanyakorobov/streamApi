package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Set<Product> products = new HashSet<>(Arrays.asList(
                new Product(1L, "Laptop", "Electronics", new BigDecimal("999.99")),
                new Product(2L, "Smartphone", "Electronics", new BigDecimal("499.99")),
                new Product(3L, "Coffee Maker", "Home Appliances", new BigDecimal("79.99")),
                new Product(4L, "Headphones", "Electronics", new BigDecimal("199.99")),
                new Product(5L, "Blender", "Home Appliances", new BigDecimal("49.99")),
                new Product(6L, "Java Programming", "Books", new BigDecimal("120.00")),
                new Product(7L, "Python Programming", "Books", new BigDecimal("150.00")),
                new Product(8L, "Children's Storybook", "Children's products", new BigDecimal("80.00")),
                new Product(9L, "Toy Car", "Toys", new BigDecimal("15.00")),
                new Product(10L, "Doll", "Toys", new BigDecimal("25.00")),
                new Product(11L, "Science Fiction Novel", "Books", new BigDecimal("200.00")),
                new Product(12L, "Kids Puzzle", "Children's products", new BigDecimal("30.00")),
                new Product(13L, "Cooking Book", "Books", new BigDecimal("110.00")),
                new Product(14L, "Action Figure", "Toys", new BigDecimal("50.00"))
        ));

        // Инициализация заказов
        Set<Order> orders = new HashSet<>(Arrays.asList(
                new Order(1L, LocalDate.now(), LocalDate.now().plusDays(3), "Shipped", Set.of(
                        new Product(1L, "Laptop", "Electronics", new BigDecimal("999.99")),
                        new Product(2L, "Smartphone", "Electronics", new BigDecimal("499.99"))
                )),
                new Order(2L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(2), "Delivered", Set.of(
                        new Product(3L, "Coffee Maker", "Home Appliances", new BigDecimal("79.99")),
                        new Product(4L, "Headphones", "Electronics", new BigDecimal("199.99"))
                )),
                new Order(3L, LocalDate.now().minusDays(5), LocalDate.now().plusDays(1), "Pending", Set.of(
                        new Product(5L, "Blender", "Home Appliances", new BigDecimal("49.99")),
                        new Product(6L, "Java Programming", "Books", new BigDecimal("20.00"))
                )),
                new Order(4L, LocalDate.of(2021, 2, 15), LocalDate.of(2021, 2, 20), "Delivered", Set.of(
                        new Product(8L, "Children's Storybook", "Children's products", new BigDecimal("80.00")),
                        new Product(9L, "Toy Car", "Toys", new BigDecimal("15.00"))
                )),
                new Order(5L, LocalDate.of(2021, 3, 14), LocalDate.of(2021, 3, 18), "Shipped", Set.of(
                        new Product(10L, "Doll", "Toys", new BigDecimal("25.00")),
                        new Product(12L, "Kids Puzzle", "Children's products", new BigDecimal("30.00"))
                )),
                new Order(6L, LocalDate.of(2021, 3, 15), LocalDate.of(2021, 3, 20), "Delivered", Set.of(
                        new Product(14L, "Action Figure", "Toys", new BigDecimal("50.00")),
                        new Product(11L, "Science Fiction Novel", "Books", new BigDecimal("200.00"))
                )),
                new Order(7L, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 5), "Delivered", Set.of(
                        new Product(7L, "Python Programming", "Books", new BigDecimal("150.00")),
                        new Product(13L, "Cooking Book", "Books", new BigDecimal("110.00"))
                )),
                new Order(8L, LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 5), "Pending", Set.of(
                        new Product(6L, "Java Programming", "Books", new BigDecimal("120.00")),
                        new Product(9L, "Toy Car", "Toys", new BigDecimal("15.00"))
                ))
        ));


        // Инициализация покупателей
        List<Customer> customers = new ArrayList<>(Arrays.asList(
                new Customer(1L, "Alice", 1L, orders),
                new Customer(2L, "Bob", 2L, orders),
                new Customer(3L, "Charlie", 1L, new HashSet<>()), // Без заказов
                new Customer(4L, "David", 2L, orders),
                new Customer(5L, "Eve", 1L, new HashSet<>()) // Без заказов
        ));

// Получите список продуктов из категории "Books" с ценой более 100.
        List<Product> booksWithPriceOver100 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory()) && product.getPrice().compareTo(new BigDecimal("100")) > 0)
                .distinct()
                .toList();
        // booksWithPriceOver100.forEach(product -> System.out.println(product));

        // Получите список заказов с продуктами из категории "Children's products".
        List<Order> ordersChildrenProd = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> "Children's products".equals(product.getCategory())))
                .toList();


        //Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        Map<Customer, List<Order>> customerOrdersMap = customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> new ArrayList<>(customer.getOrders())
                ));

        //результат
        customerOrdersMap.forEach((customer, ordersList) -> {
            System.out.println("Customer: " + customer.getName());
            ordersList.forEach(order ->
                    System.out.println("  Order id: " + order.getId() + ", Статус: " + order.getStatus())
            );
        });

    }

    //Получите Map<String, Product> → самый дорогой продукт по каждой категории.
    public Set<CategoryMostExpensiveProduct> getMostExpensiveProductByCategorySet(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)), // Самый дорогой продукт
                                optional -> optional.orElse(null) // Вытягиваем сам продукт
                        )
                ))
                .entrySet().stream()
                .map(entry -> new CategoryMostExpensiveProduct(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()); // Собираем в Set объектов CategoryMostExpensiveProduct
    }

    public Map<String, List<Product>> getToysWithDiscount(List<Customer> customers) {

        List<Product> toysWithDiscount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Toys".equals(product.getCategory()))
                .map(product -> {
                    product.setPrice(product.getPrice().multiply(new BigDecimal("0.9")));
                    return product;
                })
                .collect(Collectors.toList());

        return Map.of("ToysWithDiscount", toysWithDiscount);
    }

    //получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
    public List<Product> getProductsOrderedByLevel2CustomersBetweenDates(List<Customer> customers, LocalDate startDate, LocalDate endDate) {

        return customers.stream()
                .filter(customer -> customer.getLevel() == 2)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1))
                        && !order.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toList());
    }

    //получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
    //список их продуктов
    public List<Order> getOrdersFromMarch15(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("Order ID: " + order.getId()))
                .collect(Collectors.toList());
    }

    // Получение суммы цен продуктов из заказовв феврале 2021
    public BigDecimal getProductsFromFeb(List<Order> orders) {
        return orders.stream()
                .filter(order -> !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) &&
                        order.getOrderDate().isBefore(LocalDate.of(2021, 3, 1)))
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //получите 3 самых последних сделанных заказа.
    public Set<Order> getLast3Orders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toSet());
    }

    public Set<Order> getOrdersFromMarch14(List<Order> orders) {
        // Фильтруем по дате и собираем в Set
        return orders.stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 14)))
                .collect(Collectors.toSet());
    }

    public OptionalDouble getAveragePaymentOnMarch14(List<Order> orders) {
        //средний платеж по заказаи
        return orders.stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 14))) // Фильтруем по дате
                .map(order -> order.getProducts().stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .mapToDouble(BigDecimal::doubleValue)
                .average();
    }

    // Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
    //продуктов категории "Книги"
    public ProductStatistics getBookStatistics(Set<Product> products) {

        return products.stream()
                .filter(product -> "Books".equals(product.getCategory()))
                .map(Product::getPrice)
                .collect(Collectors.collectingAndThen(
                        Collectors.toSet(),
                        prices -> {
                            BigDecimal sum = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add); // Сумма
                            BigDecimal average = BigDecimal.valueOf(prices.stream()
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .average()
                                    .orElse(0.0)); // Среднее
                            BigDecimal max = prices.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                            BigDecimal min = prices.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                            long count = prices.size(); // Количество
                            return new ProductStatistics(sum, average, max, min, count);
                        }));
    }

    //Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
    public Set<String> getOrderProductCounts(Set<Order> orders) {
        return orders.stream()
                .map(order -> "Order id: " + order.getId() + ", Count: " + order.getProducts().size())
                .collect(Collectors.toSet());
    }

    //Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
    public Set<String> getOrderTotalSet(List<Order> orders) {
        return orders.stream()
                .map(order -> "Order ID: " + order.getId() + ", Total: " +
                        order.getProducts().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .doubleValue())
                .collect(Collectors.toSet());
    }

    //Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
    public Set<String> getProductCategorySet(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> "Category: " + entry.getKey() + ", Products: " + entry.getValue())
                .collect(Collectors.toSet()); // Сборка в Set строк
    }

}
