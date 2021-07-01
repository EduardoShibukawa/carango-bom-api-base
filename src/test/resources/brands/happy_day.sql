DELETE FROM brand;

ALTER SEQUENCE brand_id_seq RESTART WITH 1;

INSERT INTO brand (name)
  VALUES('Audi');

INSERT INTO brand (name)
  VALUES('Fiat');

INSERT INTO brand (name)
  VALUES('Ford');