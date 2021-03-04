-- Init table users
DELETE FROM `users`;
COMMIT;

INSERT INTO `users` (full_name, email, password, role, blocked) VALUES ("Journey Tyler", "ysudiptodebn@manchesterlandsurveying.com", "1234", "ADMIN", 0);
INSERT INTO `users` (full_name, email, password, role, blocked) VALUES ("Cassandra Monroe", "qjamal.chafik.75t@gmailwe.com", "qwerty", "PROFESSOR", 0);
INSERT INTO `users` (full_name, email, password, role, blocked) VALUES ("Calvin Jensen", "1yassinebelaatiq@chediak-higashi.org", "4321", "TEACHER", 1);
INSERT INTO `users` (full_name, email, password, role, blocked) VALUES ("Denise Rivers", "ydat@lihdaf.com", "abcd", "STUDENT", 0);
INSERT INTO `users` (full_name, email, password, role, blocked) VALUES ("Serena Flowers", "zakra@maechic.com", "dcba", "TEACHER", 0);
COMMIT;

-- Init table students
DELETE FROM `students`;
COMMIT;

INSERT INTO `students` (id, group) VALUES (4, 722403);
COMMIT;

-- Init table admin_operations
DELETE FROM `admin_operations`;
COMMIT;

INSERT INTO `admin_operations` (admin, operation, user, date, reason) VALUES (1, "CREATE", 2, '2020-12-18 13:10:00', NULL);
INSERT INTO `admin_operations` (admin, operation, user, date, reason) VALUES (1, "CREATE", 3, '2020-12-18 13:11:00', NULL);
INSERT INTO `admin_operations` (admin, operation, user, date, reason) VALUES (1, "CREATE", 5, '2020-12-18 13:12:00', NULL);
INSERT INTO `admin_operations` (admin, operation, user, date, reason) VALUES (1, "BLOCK", 3, '2020-12-18 13:13:00', NULL);
COMMIT;

-- Table academic_disciplines
DELETE FROM `academic_disciplines`;
COMMIT;

INSERT INTO `academic_disciplines` (name, abbreviation, description, author) VALUES ("Computer Information Systems", "INFS", NULL, 2);
INSERT INTO `academic_disciplines` (name, abbreviation, description, author) VALUES ("Foundations of Education", "FOED", NULL, 2);
INSERT INTO `academic_disciplines` (name, abbreviation, description, author) VALUES ("Environmental Science and Technology", "EST", NULL, 2);
COMMIT;

-- Table educational_materials
DELETE FROM `educational_materials`;
COMMIT;

INSERT INTO `educational_materials` (name, academic_discipline, author, reviewer, review_status, creation_date, type) VALUES ("Foundations of Information Systems", 1, 3, 2, "Ready for review", '2020-12-18 13:11:30', "Workshop");
INSERT INTO `educational_materials` (name, academic_discipline, author, reviewer, review_status, creation_date, type) VALUES ("Foundations of Information Systems", 1, 5, 2, "Reviewed", '2020-12-18 13:18:00', "Test");
INSERT INTO `educational_materials` (name, academic_discipline, author, reviewer, review_status, creation_date, type) VALUES ("Foundations of Information Systems", 1, 5, 2, "Back to rework", '2020-12-18 13:19:14', "Exam");
COMMIT;

-- Table study_assignments
DELETE FROM `study_assignments`;
COMMIT;

INSERT INTO `study_assignments` (name, educational_material, student, review_status, creation_date, due_date, teacher) VALUES ("Homework", 2, 4, "Open", '2020-12-28 12:10:00', '2020-12-28 12:50:00', 5);
COMMIT;