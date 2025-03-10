package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Product product1 = new Product(1L, "Laptop", "Electronics", new BigDecimal("999.99"));
        Product product2 = new Product(2L, "Smartphone", "Electronics", new BigDecimal("499.99"));
        Product product3 = new Product(3L, "Coffee Maker", "Home Appliances", new BigDecimal("79.99"));
        Product product4 = new Product(4L, "Headphones", "Electronics", new BigDecimal("199.99"));
        Product product5 = new Product(5L, "Blender", "Home Appliances", new BigDecimal("49.99"));
        Product product6 = new Product(6L, "Java Programming", "Books", new BigDecimal("120.00"));
        Product product7 = new Product(7L, "Python Programming", "Books", new BigDecimal("150.00"));
        Product product8 = new Product(8L, "Children's Storybook", "Children's products", new BigDecimal("80.00"));
        Product product9 = new Product(9L, "Toy Car", "Toys", new BigDecimal("15.00"));
        Product product10 = new Product(10L, "Doll", "Toys", new BigDecimal("25.00"));
        Product product11 = new Product(11L, "Science Fiction Novel", "Books", new BigDecimal("200.00"));
        Product product12 = new Product(12L, "Kids Puzzle", "Children's products", new BigDecimal("30.00"));
        Product product13 = new Product(13L, "Cooking Book", "Books", new BigDecimal("110.00"));
        Product product14 = new Product(14L, "Action Figure", "Toys", new BigDecimal("50.00"));

        Set<Product> productsForAlice = new HashSet<>(Arrays.asList(product1, product6));
        Set<Product> productsForBob = new HashSet<>(Arrays.asList(product2, product7));
        Set<Product> productsForCharlie = new HashSet<>(Arrays.asList(product9, product10));
        Set<Product> productsForDavid = new HashSet<>(Arrays.asList(product12, product8));
        Set<Product> productsForEve = new HashSet<>(Arrays.asList(product5, product13));

        Order order1 = new Order(1L, LocalDate.now(), LocalDate.now().plusDays(3), "Shipped", productsForAlice);
        Order order2 = new Order(2L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(2), "Delivered", productsForBob);
        Order order3 = new Order(3L, LocalDate.now().minusDays(5), LocalDate.now().plusDays(1), "Pending", productsForCharlie);
        Order order4 = new Order(4L, LocalDate.of(2021, 2, 15), LocalDate.of(2021, 2, 20), "Delivered", productsForDavid);
        Order order5 = new Order(5L, LocalDate.of(2021, 3, 14), LocalDate.of(2021, 3, 18), "Shipped", productsForEve);

        List<Customer> customers = new ArrayList<>(Arrays.asList(
                new Customer(1L, "Alice", 1L, new HashSet<>(Arrays.asList(order1))),
                new Customer(2L, "Bob", 2L, new HashSet<>(Arrays.asList(order2))),
                new Customer(3L, "Charlie", 1L, new HashSet<>(Arrays.asList(order3))),
                new Customer(4L, "David", 2L, new HashSet<>(Arrays.asList(order4))),
                new Customer(5L, "Eve", 1L, new HashSet<>(Arrays.asList(order5)))
        ));

    }

    public static Set<Product> doTask1(Set<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory()) && product.getPrice().compareTo(new BigDecimal("100")) > 0)
                .collect(Collectors.toSet());
    }

    public static Set<Order> doTask2(Set<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> "Children's products".equals(product.getCategory())))
                .collect(Collectors.toSet());
    }

    public static Map<List<Product>, BigDecimal> doTask3(List<Customer> customers) {
        List<Product> toysWithDiscount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Toys".equals(product.getCategory()))
                .map(product -> {
                    product.setPrice(product.getPrice().multiply(new BigDecimal("0.9")));
                    return product;
                })
                .collect(Collectors.toList());

        BigDecimal totalPrice = toysWithDiscount.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<List<Product>, BigDecimal> result = new HashMap<>();
        result.put(toysWithDiscount, totalPrice);
        return result;
    }

    public static List<Product> doTask4(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> customer.getLevel() == 2)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1))
                        && !order.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Product> doTask5(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory()))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .collect(Collectors.toList());
    }

    public static Set<Order> doTask6(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toSet());
    }

    public static List<Order> doTask7(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("Order ID: " + order.getId()))
                .collect(Collectors.toList());
    }

    public static BigDecimal doTask8(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> !order.getOrderDate().isBefore(LocalDate.of(2021, 2,1))
                        && order.getOrderDate().isBefore(LocalDate.of(2021, 3, 1)))
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static double doTask9(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 14)))
                .map(order -> order.getProducts().stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0.0);
    }

    public static ProductStatistics doTask10(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory()))
                .map(Product::getPrice)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        prices -> {
                            BigDecimal sum = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                            BigDecimal average = BigDecimal.valueOf(prices.stream()
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .average()
                                    .orElse(0.0));
                            BigDecimal max = prices.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                            BigDecimal min = prices.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                            long count = prices.size();
                            return new ProductStatistics(sum, average, max, min, count);
                        }));
    }

    public static Map<Long, Integer> doTask11(List<Customer> customers) {
        return customers.stream()
                .collect(Collectors.toMap(
                        Customer::getId,
                        customer -> customer.getOrders().stream()
                                .flatMap(order -> order.getProducts().stream())
                                .collect(Collectors.toList())
                                .size()
                ));
    }

    public static Map<Customer, Set<Order>> doTask12(Set<Customer> customers) {
        return customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> new HashSet<>(customer.getOrders())
                ));
    }

    public static Map<Order, Double> doTask13(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getProducts().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .doubleValue()
                ));
    }

    public static Map<String, List<String>> doTask14(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())
                ));
    }

    public static Map<String, Product> doTask15(Set<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)),
                                optional -> optional.orElse(null)
                        )
                ));
    }
}
