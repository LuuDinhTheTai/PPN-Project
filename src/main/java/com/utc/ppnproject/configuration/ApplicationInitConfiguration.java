package com.utc.ppnproject.configuration;


import com.utc.ppnproject.constant.Constant;
import com.utc.ppnproject.entity.Account;
import com.utc.ppnproject.entity.Product;
import com.utc.ppnproject.entity.Role;
import com.utc.ppnproject.repository.AccountRepository;
import com.utc.ppnproject.repository.ProductRepository;
import com.utc.ppnproject.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfiguration {
  
  private final PasswordEncoder passwordEncoder;
  
  @NonFinal
  private static final String ADMIN_USERNAME = "admin";
  @NonFinal
  private static final String ADMIN_PASSWORD = "admin";
  
  @Bean
  @ConditionalOnProperty(
          prefix = "spring",
          value = "datasource.driver-class-name",
          havingValue = "com.mysql.cj.jdbc.Driver")
  ApplicationRunner applicationRunner(AccountRepository accountRepository,
                                      RoleRepository roleRepository, ProductRepository productRepository) {
    return args -> {
      log.info("Application initializing...");
      if (!roleRepository.existsByName(Constant.ROLE_ADMIN)) {
        log.info("(create) role: " + Constant.ROLE_ADMIN);
        roleRepository.save(Role.builder()
                                    .name(Constant.ROLE_ADMIN)
                                    .build());
        
      }
      
      if (!roleRepository.existsByName(Constant.ROLE_USER)) {
        log.info("(create) role: " + Constant.ROLE_USER);
        roleRepository.save(Role.builder()
                                    .name(Constant.ROLE_USER)
                                    .build());
      }
      
      if (accountRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
        log.info("(create) admin account");
        var roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Constant.ROLE_ADMIN));
        
        Account account = Account.builder()
                                  .username(ADMIN_USERNAME)
                                  .password(passwordEncoder.encode(ADMIN_PASSWORD))
                                  .roles(roles)
                                  .build();
        
        accountRepository.save(account);
      }
      
      if (productRepository.findByName(Constant.WATER_PRODUCT).isEmpty()) {
        log.info("(create) water product");
        Product product = new Product();
        product.setName(Constant.WATER_PRODUCT);
        product.setPrice(10000);
        product.setQty(100);
        productRepository.save(product);
      }
    };
  }
}