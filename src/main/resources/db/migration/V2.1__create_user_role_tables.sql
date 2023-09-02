-- DROP SEQUENCE IF EXISTS user_id_seq;
-- DROP SEQUENCE IF EXISTS role_id_seq;

--CREATE SEQUENCE user_id_seq START WITH 1;
--CREATE SEQUENCE role_id_seq START WITH 1;


--DROP TABLE IF EXISTS users CASCADE;
/*-- DROP TABLE IF EXISTS roles CASCADE;*/

--
-- Table structure for table `users`
--

CREATE TABLE users (
    id                      BIGSERIAL NOT NULL,
    first_name              VARCHAR(30),
    last_name               VARCHAR(30),
    email                   VARCHAR(50) not null unique,
    username                VARCHAR(30) not null unique,
    password                VARCHAR(64),
    secret_key              VARCHAR(512),
    phone                   VARCHAR(255) DEFAULT NULL,
    enabled                 bit(1) NOT NULL,
    checking_account_id     bigint DEFAULT NULL,
    savings_account_id      bigint DEFAULT NULL
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

--
-- Table structure for table `roles`
--

CREATE TABLE roles (
    id                   BIGSERIAL NOT NULL,
    name                 VARCHAR(30) not null unique,
    allowed_resource     VARCHAR(200),
    allowed_read         BOOLEAN not null default FALSE ,
    allowed_create       BOOLEAN not null default FALSE,
    allowed_update       BOOLEAN not null default FALSE,
    allowed_delete       BOOLEAN not null default FALSE
);

ALTER TABLE roles ADD CONSTRAINT role_pk PRIMARY KEY ( id );

--
-- Table structure for table `users_roles`
--

CREATE TABLE users_roles (
    user_id    BIGINT NOT NULL,
    role_id    BIGINT NOT NULL
);

ALTER TABLE users_roles
    ADD CONSTRAINT users_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id );

ALTER TABLE users_roles
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES roles ( id );

--
-- Table structure for table `appointment`
--

CREATE TABLE appointment (
    id                  BIGSERIAL NOT NULL,
    confirmed           bit(1) NOT NULL,
    date                date DEFAULT NULL,
    description         varchar(255) DEFAULT NULL,
    location            varchar(255) DEFAULT NULL,
    user_id             bigint DEFAULT NULL
    );

ALTER TABLE appointment ADD CONSTRAINT appointment_pk PRIMARY KEY ( id );
ALTER TABLE appointment ADD CONSTRAINT appointment_fk FOREIGN KEY ( user_id ) REFERENCES users ( id );

--
-- Table structure for table `checking_account`
--

CREATE TABLE checking_account (
   id                   BIGSERIAL NOT NULL,
   account_balance      NUMERIC(19,2) DEFAULT NULL,
   account_number       CHAR(11) NOT NULL
);

ALTER TABLE checking_account ADD CONSTRAINT checking_account_pk PRIMARY KEY ( id );

--
-- Table structure for table `checking_transaction`
--

CREATE TABLE checking_transaction (
   id                       BIGSERIAL NOT NULL,
   amount                   float NOT NULL,
   available_balance        DECIMAL(19,2) DEFAULT NULL,
   date                     date DEFAULT NULL,
   description              varchar(255) DEFAULT NULL,
   status                   varchar(255) DEFAULT NULL,
   type                     varchar(255) DEFAULT NULL,
   checking_account_id      BIGINT DEFAULT NULL
);

ALTER TABLE checking_transaction ADD CONSTRAINT checking_transaction_pk PRIMARY KEY ( id );
ALTER TABLE checking_transaction ADD CONSTRAINT checking_transaction_fk FOREIGN KEY ( checking_account_id )
    REFERENCES checking_account ( id );

--
-- Table structure for table `savings_account`
--

CREATE TABLE savings_account (
    id                       BIGSERIAL NOT NULL,
    account_balance          DECIMAL(19,2) DEFAULT NULL,
    account_number           CHAR(11) NOT NULL
);

ALTER TABLE savings_account ADD CONSTRAINT savings_account_pk PRIMARY KEY ( id );

--
-- Table structure for table `savings_transaction`
--

CREATE TABLE savings_transaction (
    id                      BIGSERIAL NOT NULL,
    amount                  FLOAT NOT NULL,
    available_balance       DECIMAL(19,2) DEFAULT NULL,
    date                    date DEFAULT NULL,
    description             varchar(255) DEFAULT NULL,
    status                  varchar(255) DEFAULT NULL,
    type                    varchar(255) DEFAULT NULL,
    savings_account_id      bigint DEFAULT NULL
) ;
ALTER TABLE savings_transaction ADD CONSTRAINT savings_transaction_pk PRIMARY KEY ( id );

--
-- Table structure for table `recipient`
--

CREATE TABLE recipient (
    id                      BIGSERIAL NOT NULL,
    account_number          varchar(255) DEFAULT NULL,
    description             varchar(255) DEFAULT NULL,
    email                   varchar(255) DEFAULT NULL,
    name                    varchar(255) DEFAULT NULL,
    phone                   varchar(255) DEFAULT NULL,
    user_id                 bigint DEFAULT NULL
    );
ALTER TABLE recipient ADD CONSTRAINT recipient_pk PRIMARY KEY ( id );
ALTER TABLE recipient ADD CONSTRAINT recipient_fk FOREIGN KEY ( user_id )
    REFERENCES users ( id );


ALTER TABLE users ADD CONSTRAINT users_checking_fk FOREIGN KEY ( checking_account_id )
    REFERENCES checking_account ( id );
ALTER TABLE users ADD CONSTRAINT users_savings_fk FOREIGN KEY ( savings_account_id )
    REFERENCES savings_account ( id );

ALTER TABLE savings_transaction ADD CONSTRAINT savings_transaction_fk FOREIGN KEY ( savings_account_id )
    REFERENCES savings_account ( id );



