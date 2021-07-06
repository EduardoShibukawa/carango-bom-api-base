DELETE FROM car;
DELETE FROM brand;

ALTER SEQUENCE brand_id_seq RESTART WITH 1;
ALTER SEQUENCE car_id_seq RESTART WITH 1;

INSERT INTO brand (name)
  VALUES ('Audi');

INSERT INTO brand (name)
  VALUES ('Fiat');

INSERT INTO brand (name)
  VALUES ('Ford');

INSERT INTO car (brand_id, model, year, value)
  VALUES (1, 'A5', 2021, 300000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (1, 'E-TRON', 2021, 600000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (2, 'Strada', 2016, 70000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (2, 'Uno', 2016, 45000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (2, 'Argo', 2018, 65000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (2, 'EcoSport', 2016, 70000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (2, 'Ka', 2016, 50000);

INSERT INTO car (brand_id, model, year, value)
  VALUES (3, 'New Fiesta Hatch', 2018, 55000);

