package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.entities.City;
import org.example.entities.District;
import org.example.entities.AppUser;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    public void run(String... args) {
        City poznan = seedCity();
        seedDistricts(poznan, List.of("Jeżyce", "Stare Miasto", "Grunwald", "Wilda", "Łazarz"));
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
