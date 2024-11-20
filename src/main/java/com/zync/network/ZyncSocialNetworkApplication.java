package com.zync.network;

import com.zync.network.media.infrastructure.config.MediaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Modulithic
@EnableJpaAuditing
@EnableAsync
@EnableConfigurationProperties({MediaProperties.class})
public class ZyncSocialNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZyncSocialNetworkApplication.class, args);
    }
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Bean
//    CommandLineRunner commandLineRunner(){
//        return (String[] args) ->{
//            if (!roleRepository.existsByName(RoleName.ADMIN)) {
//                Role admin = new Role(ZID.fast(), RoleName.ADMIN, Set.of(Operator.CREATE, Operator.READ, Operator.DELETE, Operator.UPDATE));
//                admin = roleRepository.save(admin);
//
//            }
//            if (!roleRepository.existsByName(RoleName.USER)) {
//                Role user = new Role(ZID.fast(), RoleName.USER, Collections.emptySet());
//                user = roleRepository.save(user);
//            }
//
//            if (!accountRepository.existsByEmail("zync@zync.com")) {
//                Role ad = roleRepository.findByName(RoleName.ADMIN);
//                Role us = roleRepository.findByName(RoleName.USER);
//                Account admin = new Account(ZID.fast(), "zync@zync.com", passwordEncoder.encode("admin"), Set.of(ad, us), Locale.getDefault());
//                accountRepository.save(admin);
//            }
//
//        };
//    }
}
