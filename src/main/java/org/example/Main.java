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
        List<Product> childrenProd = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Children's products".equals(product.getCategory()))
                .toList();
        //childrenProd.forEach(product -> System.out.println(product));

        //получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов.
        BigDecimal totalWithDiscount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Toys".equals(product.getCategory()))
                .map(product -> product.getPrice().multiply(new BigDecimal("0.9")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // System.out.println(totalWithDiscount);

        //получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        List<Product> cheapestBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> "Books".equals(product.getCategory()))
                .sorted(Comparator.comparing(Product::getPrice))
                .distinct()
                .limit(2)
                .toList();

        //  cheapestBooks.forEach(product -> System.out.println(product));

        //получите 3 самых последних сделанных заказа.
        List<Order> orderList3 = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .distinct()
                .limit(3)
                .toList();
        // orderList3.forEach(order -> System.out.println(order));

        //получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов
        List<Product> productsFromMarch15Orders = orders.stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("Order ID: " + order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toList());
        // Выводим список продуктов
        productsFromMarch15Orders.forEach(product -> System.out.println(product));

        BigDecimal productsFromFeb = orders.stream()
                .filter(order ->
                        !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) &&
                                order.getOrderDate().isBefore(LocalDate.of(2021, 3, 1)))
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(productsFromFeb);

        //Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        OptionalDouble averagePayment = orders.stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 14))) // Фильтруем по дате
                .map(order -> order.getProducts().stream() // Для каждого заказа берем продукты
                        .map(Product::getPrice) // Извлекаем цену каждого продукта
                        .reduce(BigDecimal.ZERO, BigDecimal::add)) // Суммируем цены продуктов
                .mapToDouble(BigDecimal::doubleValue)
                .average();// Преобразуем BigDecimal в double
        System.out.println("Средний платёж: " + averagePayment);

        // Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        //продуктов категории "Книги"
        ProductStatistics stats = products.stream()
                .filter(product -> "Books".equals(product.getCategory())) // Фильтруем по категории "Books"
                .map(Product::getPrice) // Извлекаем цену каждого продукта
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), // Сначала собираем все цены в список
                        prices -> {
                            BigDecimal sum = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add); // Сумма
                            BigDecimal average = BigDecimal.valueOf(prices.stream()
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .average()
                                    .orElse(0.0)); // Среднее
                            BigDecimal max = prices.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO); // Максимум
                            BigDecimal min = prices.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO); // Минимум
                            long count = prices.size(); // Количество
                            // System.out.println(new ProductStatistics(sum, average, min, max, count));
                            return new ProductStatistics(sum, average, max, min, count); // Возвращаем объект статистики
                        }));

        //Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        Map<Long, Integer> orderProductCountMap = orders.stream()
                .collect(Collectors.toMap(Order::getId, order -> order.getProducts().size()));

        orderProductCountMap.forEach((orderId, productCount) ->
                System.out.println("Order ID: " + orderId + ", Count: " + productCount)
        );

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


        //Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Order, Double> orderTotalMap = orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getProducts().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .doubleValue()
                ));

        //результат
        orderTotalMap.forEach((order, totalPrice) -> {
            System.out.println("Order id: " + order.getId() + ", Total: " + totalPrice);
        });

        //Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        Map<String, List<String>> productInCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory, // ключ - категория
                        Collectors.mapping(Product::getName, Collectors.toList()) // список названий продуктов в категории
                ));

        //Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        Map<String, Product> mostExpensiveProductByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory, // группировка по категории
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)),
                                optional -> optional.orElse(null)
                        )
                ));
    }
}
//
