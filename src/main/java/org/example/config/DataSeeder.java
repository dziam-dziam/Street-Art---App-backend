package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.entities.*;
import org.example.mappers.LocationMapper;
import org.example.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArtPieceRepository artPieceRepository;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;



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
        seedDevUsersAndArtPieces(poznan);
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
    private void seedDevUsersAndArtPieces(City poznan) {
        // --- Users (2) ---
        AppUser user1 = seedUserIfMissing(
                "user1@streetapp.dev",
                "User One",
                "Poland",
                Set.of("Polish", "English"),
                poznan,
                "Jeżyce"
        );

        AppUser user2 = seedUserIfMissing(
                "user2@streetapp.dev",
                "User Two",
                "Germany",
                Set.of("German", "English"),
                poznan,
                "Stare Miasto"
        );

        // --- ArtPieces (2) ---
        District jez = mustGetDistrict("Jeżyce");
        District sm = mustGetDistrict("Stare Miasto");

        seedArtPieceWithLocationIfMissing(
                "Jeżyce Fox",
                "Dąbrowskiego 23",
                "Front wall",
                "Fox mural on a corner building.",
                Set.of("English"),
                Set.of(ArtPieceTypes.MURAL),
                Set.of(ArtPieceStyles.ANTI_CONSUMERISM),
                user1,
                jez
        );

        seedArtPieceWithLocationIfMissing(
                "Old Town Sticker Wall",
                "Wrocławska 12",
                "Near entrance",
                "Sticker cluster near the doorway.",
                Set.of("Polish", "English"),
                Set.of(ArtPieceTypes.STICKER),
                Set.of(ArtPieceStyles.ACTIVISM),
                user2,
                sm
        );

    }

    private AppUser seedUserIfMissing(
            String email,
            String name,
            String nationality,
            Set<String> languages,
            City city,
            String liveDistrictName
    ) {
        Optional<AppUser> existing = appUserRepository.findByAppUserEmail(email);
        if (existing.isPresent()) {
            return existing.get();
        }

        District liveDistrict = mustGetDistrict(liveDistrictName);

        AppUser u = new AppUser();
        u.setAppUserEmail(email);
        u.setAppUserName(name);
        u.setAppUserNationality(nationality);
        u.setAppUserLanguagesSpoken(languages);
        u.setAppUserCity(city);
        u.setAppUserLiveInDistrict(liveDistrict);

        // Dev-only password (zmień jak chcesz)
        u.setAppUserPassword(passwordEncoder.encode("Test12345!"));

        return appUserRepository.save(u);
    }

    private District mustGetDistrict(String districtName) {
        return districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new IllegalStateException("District not found in seeder: " + districtName));
    }
    private void seedArtPieceWithLocationIfMissing(
            String name,
            String address,
            String position,
            String description,
            Set<String> textLanguages,
            Set<ArtPieceTypes> types,
            Set<ArtPieceStyles> styles,
            AppUser owner,
            District district
    ) {
        boolean exists = artPieceRepository.findAll().stream().anyMatch(a ->
                name.equals(a.getArtPieceName()) && address.equals(a.getArtPieceAddress())
        );
        if (exists) return;

        // 1) Geocoding + round + reuse existing location if same lat/lng
        Location loc = locationMapper.mapAddressToLocationEntity(address, "Poznań");
        loc = locationRepository.save(loc);

        // 2) Create artpiece
        ArtPiece ap = new ArtPiece();
        ap.setArtPieceName(name);
        ap.setArtPieceAddress(address);
        ap.setArtPieceContainsText(true);
        ap.setArtPiecePosition(position);
        ap.setArtPieceUserDescription(description);

        ap.setArtPieceTextLanguages(textLanguages);
        ap.setArtPieceTypes(types);
        ap.setArtPieceStyles(styles);

        ap.setArtPieceAppUserWhoAddedIt(owner);
        ap.setArtPieceDistrict(district);
        ap.setArtPieceLocation(loc);

        // 3) Mirror AddArtPieceService relationship wiring
        loc.addArtPiece(ap);
        loc.setLocationDistrict(district);
        district.addArtPiece(ap);

        artPieceRepository.save(ap);
    }


}
