package com.example.demoshop;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.demoshop.entity.Customer;
import com.example.demoshop.entity.OrderItem;
import com.example.demoshop.entity.Product;
import com.example.demoshop.entity.Shop;
import com.example.demoshop.repository.CustomerReposiory;
import com.example.demoshop.repository.OrderItemRepository;
import com.example.demoshop.repository.ProductRepository;
import com.example.demoshop.repository.ShopRepository;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("populator")
public class DemoShopFakeDataPopulator implements CommandLineRunner {

	private final CustomerReposiory customerReposiory;
	private final ShopRepository shopRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	public DemoShopFakeDataPopulator(CustomerReposiory customerReposiory, ShopRepository shopRepository,
			ProductRepository productRepository, OrderItemRepository orderItemRepository) {
		this.customerReposiory = customerReposiory;
		this.shopRepository = shopRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		log.info("begin populating fake data");

		long customersCount = customerReposiory.count();
		long shopsCount = shopRepository.count();
		long productsCount = productRepository.count();
		log.info("existed customers count: {}", customersCount);
		log.info("existed shops count: {}", shopsCount);
		log.info("existed products count: {}", productsCount);
		if (customersCount + shopsCount + productsCount > 0) {
			log.info("skip fake populating due to data already existed");
			return;
		}

		int totalFakeCustomers = 30;
		int totalFakeShops = 3;
		int totalFakeProducts = (int) Math.pow(2, 15);
		int totalOrderPerCustomer = 13;

		Faker faker = new Faker();

		log.info("populating {} fake customers", totalFakeCustomers);
		for (int i = 0; i < totalFakeCustomers; i++) {
			String name = faker.name().fullName();
			String email = faker.internet().emailAddress();
			LocalDate birthday = LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault());
			customerReposiory.save(new Customer(name, email, birthday));
		}

		log.info("populating {} fake shops", totalFakeShops);
		for (int i = 0; i < totalFakeShops; i++) {
			String name = faker.company().name();
			String address = faker.address().fullAddress();
			shopRepository.save(new Shop(name, address));
		}

		log.info("populating {} fake products", totalFakeProducts);
		List<Shop> shops = shopRepository.findAll();
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < totalFakeProducts; i++) {
			Shop shop = shops.get(i % shops.size());
			String name = faker.commerce().productName();
			String imageUrl = null;
			Double price = faker.number().randomDouble(2, 0, 100);
			products.add(new Product(name, imageUrl, price, shop));
		}
		productRepository.saveAll(products);

		log.info("populating fake order items");
		List<Customer> customers = customerReposiory.findAll();
		int productIndex = 0;
		for (Customer customer : customers) {
			List<OrderItem> orderedItems = new ArrayList<>();
			List<Product> orderedProducts = products.subList(productIndex, productIndex + totalOrderPerCustomer);
			for (Product product : orderedProducts) {
				OffsetDateTime orderedDate = OffsetDateTime.ofInstant(faker.date().past(720, TimeUnit.DAYS).toInstant(),
						ZoneId.systemDefault());
				orderedItems.add(new OrderItem(customer.getId(), product.getId(), orderedDate));
			}
			orderItemRepository.saveAll(orderedItems);
		}

		log.info("end populating fake data");
	}

}
