DROP DATABASE IF EXISTS `education`;

CREATE DATABASE `education` CHARACTER SET utf8;

USE `education`;

-- Table users
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `full_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(45) NOT NULL,
    `blocked` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE(`email`)
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- Table students
DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
    `id` INT NOT NULL,
    `group` INT NOT NULL,
    CONSTRAINT `fk_students_users1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- Table admin_operations
DROP TABLE IF EXISTS `admin_operations`;

CREATE TABLE `admin_operations` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `admin` INT NOT NULL,
    `operation` VARCHAR(45) NOT NULL,
    `user` INT NOT NULL,
    `date` DATETIME NOT NULL,
    `reason` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_admin_operations_users1` FOREIGN KEY (`admin`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_admin_operations_users2` FOREIGN KEY (`user`)  REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- Table academic_disciplines
DROP TABLE IF EXISTS `academic_disciplines`;

CREATE TABLE `academic_disciplines` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `abbreviation` VARCHAR(45) NOT NULL,
    `description` VARCHAR(255) NULL,
    `author` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`),
    UNIQUE (`abbreviation`),
    CONSTRAINT `fk_academic_discipline_users` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- Table educational_materials
DROP TABLE IF EXISTS `educational_materials`;

CREATE TABLE `educational_materials` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `academic_discipline` INT NOT NULL,
    `author` INT NOT NULL,
    `reviewer` INT NOT NULL,
    `review_status` VARCHAR(45) NOT NULL,
    `creation_date` DATETIME NOT NULL,
    `type` VARCHAR(45) NOT NULL,
    `review_finish_date` DATETIME NULL,
    `description` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_educational_materials_users1` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_educational_materials_academic_discipline1` FOREIGN KEY (`academic_discipline`) REFERENCES `academic_disciplines` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_educational_materials_users2` FOREIGN KEY (`reviewer`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- Table study_assignments
DROP TABLE IF EXISTS `study_assignments`;

CREATE TABLE `study_assignments` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `educational_material` INT NOT NULL,
    `student` INT NOT NULL,
    `review_status` VARCHAR(45) NOT NULL,
    `creation_date` DATETIME NOT NULL,
    `due_date` DATETIME NOT NULL,
    `grade` INT NULL,
    `description` VARCHAR(255) NULL,
    `date_of_change` DATETIME NULL,
    `teacher` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`),
    CONSTRAINT `fk_study_assignments_educational_materials1` FOREIGN KEY (`educational_material`) REFERENCES `educational_materials` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_study_assignments_users1` FOREIGN KEY (`student`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_study_assignments_users2` FOREIGN KEY (`teacher`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8;



