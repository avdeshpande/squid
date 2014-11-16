# Users schema
 
# --- !Ups
CREATE TABLE users
(
  id UUID PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  email      VARCHAR(255) NOT NULL,
  phone      VARCHAR(16),
  gender     VARCHAR(1),
  image_url  VARCHAR(255),
  created    BIGINT       NOT NULL,
  updated    BIGINT       NOT NULL
);

CREATE TABLE automobiles
(
  id UUID PRIMARY KEY,
  userid UUID REFERENCES users(id),
  title        VARCHAR(255) NOT NULL,
  description  VARCHAR(1024),
  make         VARCHAR(128) NOT NULL,
  model        VARCHAR(128) NOT NULL,
  year         INT          NOT NULL,
  transmission VARCHAR(16)  NOT NULL,
  fuel         VARCHAR(16)  NOT NULL,
  title_status VARCHAR(16)  NOT NULL,
  price        NUMERIC      NOT NULL,
  color        VARCHAR(16),
  odometer     BIGINT,
  vin          VARCHAR(16),
  size         VARCHAR(16),
  bodytype     VARCHAR(16),
  drive        VARCHAR(16),
  engine       VARCHAR(16),
  created      BIGINT       NOT NULL,
  updated      BIGINT       NOT NULL
);

CREATE TABLE automobile_contents
(
  id UUID PRIMARY KEY,
  automobileid UUID REFERENCES automobiles(id),
  filename VARCHAR(255) NOT NULL,
  created  BIGINT       NOT NULL,
  updated  BIGINT       NOT NULL
);


# --- !Downs
DROP TABLE users;
DROP TABLE automobiles;
DROP TABLE automobile_contents;