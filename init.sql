use isd;

insert into admin (username, password, role) values ('subadmin', '$2a$10$LBFIAhF4RP0l7QOjrlPtg.9Mvs70PhQYtPtdzpvSgZ8GbjHSngaq2', 'ADMIN');
insert into kid (name, nickname, date_of_birth) values ('Bùi Quốc Khang', 'Johnny', '2020-01-01');
insert into teacher (name, username, password, role) values ('Nguyễn Thị Thanh Hương', 'huongntt', '$2a$10$FqoHoCSZWazUJaZ8Wzrm1uamSHaizKbzJx9q8euvNoHhiLn.oEq12', 'TEACHER');
insert into parent (full_name, username, password, role) values ('Nguyễn Văn Anh', 'anhnv', '$2a$10$YLMoEGnFdwpgG/8qmRN14.0wPfFFOzVL.ccomC9RZeU6TF7aKnRhm', 'PARENT');