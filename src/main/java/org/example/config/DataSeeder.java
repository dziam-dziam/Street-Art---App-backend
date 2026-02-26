package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.entities.*;
import org.example.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_MAIL = "damianzmudzinski3@gmail.com";
    private static final String ADMIN_PASSWORD = "Admin12345!";

    @Override
    @Transactional
    public void run(String... args) {
        City poznan = seedCity();
        seedDistricts(poznan, List.of("Jeżyce", "Stare Miasto","Nowe Miasto",
                "Grunwald", "Wilda", "Łazarz","Ławica","Rataje",
                "Winogrady","Ostrów Tumski","Sołacz","Podolany",
                "Umultowo","Ogrody","Antoninek","Chartowo",
                "Żegrze","Wola","Piątkowo","Łacina","Miłostowo",
                "Świerczewo","Malta","Junikowo","Naramowice","Dębiec",
                "Górczyn","Strzeszyn"));
        seedAdminUser();
    }

    private City seedCity() {
        return cityRepository.findByCityName("Poznań")
                .orElseGet(() -> {
                    City city = new City();
                    city.setCityName("Poznań");
                    return cityRepository.save(city);
                });
    }

    private void seedDistricts(City city, List<String> names) {
        for (String districtName : names) {

            if (districtRepository.findByDistrictName(districtName).isEmpty()) {
                District district = new District();
                district.setDistrictName(districtName);
                district.setDistrictCity(city);
                districtRepository.save(district);
            }
        }
    }

    private void seedAdminUser() {
        if (appUserRepository.findByAppUserEmail(ADMIN_MAIL).isPresent()) {
            return;
        }

        AppUser appUser = new AppUser();
        appUser.setAppUserEmail(ADMIN_MAIL);
        appUser.setAppUserName("Admin");
        appUser.setAppUserPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        appUserRepository.save(appUser);
    }

}
