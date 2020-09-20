CREATE SEQUENCE seq_profile INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE seq_role INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE seq_user INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;
CREATE SEQUENCE seq_user_admin INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1;

CREATE TABLE tb_profile (id_profile numeric(19, 0) NOT NULL, nm_profile varchar(255) NOT NULL UNIQUE, ds_profile varchar(255), PRIMARY KEY (id_profile));
CREATE TABLE tb_profile_role (id_profile numeric(19, 0) NOT NULL, id_role numeric(19, 0) NOT NULL, PRIMARY KEY (id_profile, id_role));
CREATE TABLE tb_role (id_role numeric(19, 0) NOT NULL, nm_role varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id_role));
CREATE TABLE tb_user (id_user numeric(19, 0) NOT NULL, id_profile numeric(19, 0) NOT NULL, dt_create timestamp NOT NULL, ds_email varchar(255) NOT NULL UNIQUE, ds_password varchar(255), nu_cel varchar(20) NOT NULL, nm_user varchar(255) NOT NULL, dt_last_login timestamp, ds_token varchar(500), dt_expiry_token timestamp, is_email_verified bool NOT NULL, PRIMARY KEY (id_user));
CREATE TABLE tb_user_admin (id_user_admin numeric(19, 0) NOT NULL, id_profile numeric(19, 0) NOT NULL, dt_create timestamp NOT NULL, ds_email varchar(255) NOT NULL UNIQUE, ds_password varchar(255), nu_cel varchar(20) NOT NULL, nm_user varchar(255) NOT NULL, dt_last_login timestamp, ds_token varchar(500), dt_expiry_token timestamp, is_email_verified bool NOT NULL, PRIMARY KEY (id_user_admin));


ALTER TABLE tb_user_admin ADD CONSTRAINT FKtb_user_ad725170 FOREIGN KEY (id_profile) REFERENCES tb_profile (id_profile);
ALTER TABLE tb_user ADD CONSTRAINT FKtb_user747850 FOREIGN KEY (id_profile) REFERENCES tb_profile (id_profile);
ALTER TABLE tb_profile_role ADD CONSTRAINT FKtb_profile589574 FOREIGN KEY (id_profile) REFERENCES tb_profile (id_profile);
ALTER TABLE tb_profile_role ADD CONSTRAINT FKtb_profile238820 FOREIGN KEY (id_role) REFERENCES tb_role (id_role);
