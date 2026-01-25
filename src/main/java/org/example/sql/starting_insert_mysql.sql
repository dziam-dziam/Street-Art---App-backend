USE `streetart`;

INSERT INTO city (city_name, city_residents_count)
VALUES ('Poznań', 135000);

INSERT INTO district (
  district_name,
  district_zip_code,
  district_art_pieces_count,
  district_residents_count,
  city_id
)
VALUES
('Jeżyce', '60-500', 0, 22002, (SELECT id FROM city WHERE city_name = 'Poznań')),
('Łazarz', '60-700', 0, 30281, (SELECT id FROM city WHERE city_name = 'Poznań')),
('Grunwald', '60-101', 0, 40040, (SELECT id FROM city WHERE city_name = 'Poznań')),
('Stare Miasto', '61-772', 0, 25082, (SELECT id FROM city WHERE city_name = 'Poznań')),
('Wilda', '60-501', 0, 26290, (SELECT id FROM city WHERE city_name = 'Poznań'));
