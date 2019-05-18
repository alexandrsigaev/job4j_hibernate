CREATE TABLE IF NOT EXISTS person
(
  person_id        serial PRIMARY KEY  NOT NULL,
  login            varchar(100) UNIQUE NOT NULL,
  password         VARCHAR(100)        NOT NULL,
  first_name       VARCHAR(100)        NOT NULL,
  email            VARCHAR(100)        NOT NULL,
  phone_number     VARCHAR(20)            NOT NULL,
  create_user_date DATE                NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS car_color
(
  color_id   serial PRIMARY KEY NOT NULL,
  color_name VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS car
(
  car_id       SERIAL PRIMARY KEY NOT NULL,
  car_year     INTEGER            NOT NULL,
  make         VARCHAR(100)       NOT NULL,
  model        VARCHAR(100)       NOT NULL,
  modification VARCHAR(255),
  drive_train   VARCHAR(100),
  transmission VARCHAR(100),
  engine       VARCHAR(100),
  mileage      INTEGER            NOT NULL,
  doors        INTEGER,
  color_id     INTEGER            NOT NULL,
  vin_number   VARCHAR(200),
  FOREIGN KEY (color_id) REFERENCES car_color (color_id)
);

CREATE TABLE IF NOT EXISTS car_item
(
  car_item_id      SERIAL PRIMARY KEY NOT NULL,
  id_person        INTEGER            NOT NULL,
  id_car           INTEGER UNIQUE     NOT NULL,
  price            INTEGER            NOT NULL,
  address_locality VARCHAR(255)       NOT NULL,
  description      TEXT,
  create_item      TIMESTAMP          NOT NULL DEFAULT now(),
  sold             BOOLEAN            NOT NULL,
  FOREIGN KEY (id_person) REFERENCES person (person_id),
  FOREIGN KEY (id_car) REFERENCES car (car_id)
);


CREATE TABLE IF NOT EXISTS car_image
(
  image_id    SERIAL PRIMARY KEY NOT NULL,
  id_car_item INTEGER            NOT NULL,
  image_path  VARCHAR(255)       NOT NULL,
  FOREIGN KEY (id_car_item) REFERENCES car_item (car_item_id)
);

