
----------------- ROLES USER ------------------------------------
INSERT INTO tb_role		values(1, 'ROLE_USER_FIND_ALL');
INSERT INTO tb_role		values(2, 'ROLE_USER_FIND');
INSERT INTO tb_role		values(3, 'ROLE_USER_SAVE');
INSERT INTO tb_role		values(4, 'ROLE_USER_EDIT');
INSERT INTO tb_role		values(5, 'ROLE_USER_DELETE');
INSERT INTO tb_role		values(6, 'ROLE_USER_PASSWORD_EDIT');


----------------- ROLES ADMIN ------------------------------------
INSERT INTO tb_role		values(7,  'ROLE_USER_ADMIN_FIND_ALL');
INSERT INTO tb_role		values(8,  'ROLE_USER_ADMIN_FIND');
INSERT INTO tb_role		values(9,  'ROLE_USER_ADMIN_SAVE');
INSERT INTO tb_role		values(10, 'ROLE_USER_ADMIN_EDIT');
INSERT INTO tb_role		values(11, 'ROLE_USER_ADMIN_DELETE');
INSERT INTO tb_role		values(12, 'ROLE_USER_ADMIN_PASSWORD_EDIT');

SELECT setval('seq_role', 12);

----------------- PROFILE ------------------------------------
INSERT INTO tb_profile 	values(1, 'ADMIN', 'ADMIN');
INSERT INTO tb_profile 	values(2, 'USER', 'USER');

SELECT setval('seq_profile', 2);

----------------- PROFILE X ROLE ------------------------------------

-- PROFILE ADMIN
INSERT INTO tb_profile_role values(1, 1);
INSERT INTO tb_profile_role values(1, 2);
INSERT INTO tb_profile_role values(1, 3);
INSERT INTO tb_profile_role values(1, 4);
INSERT INTO tb_profile_role values(1, 5);
INSERT INTO tb_profile_role values(1, 6);

INSERT INTO tb_profile_role values(1, 7);
INSERT INTO tb_profile_role values(1, 8);
INSERT INTO tb_profile_role values(1, 9);
INSERT INTO tb_profile_role values(1, 10);
INSERT INTO tb_profile_role values(1, 11);
INSERT INTO tb_profile_role values(1, 12);

-- PROFILE USER
INSERT INTO tb_profile_role values(2, 1);
INSERT INTO tb_profile_role values(2, 2);
INSERT INTO tb_profile_role values(2, 3);
INSERT INTO tb_profile_role values(2, 4);
INSERT INTO tb_profile_role values(2, 6);

----------------- DEFAULT INSERTS ------------------------------------
INSERT INTO tb_user_admin(id_user_admin, id_profile, dt_create, ds_email, ds_password, nu_ddd_cel, nu_cel, nm_user, dt_last_login, ds_token, dt_expiry_token, is_email_verified)
	VALUES (
	1,
	1,
    current_date,
	'emaileventosdahora@gmail.com',
	'$2a$10$1MxXxcJc9DG40ToCQMPh3.AHerddtnyeVDkOptMPqK08Xo1RCGHNi',
	'61',
	'999999999',
	'Admin',
    current_date,
	null,
	null,
	true);

SELECT setval('seq_user_admin', 1);

