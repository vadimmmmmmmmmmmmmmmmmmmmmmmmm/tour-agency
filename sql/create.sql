-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tour_agency
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tour_agency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tour_agency` DEFAULT CHARACTER SET utf8 ;
USE `tour_agency` ;

-- -----------------------------------------------------
-- Table `tour_agency`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`city_name_localisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`city_name_localisation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city_id` INT NOT NULL,
  `language` VARCHAR(2) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_city_name_localisation_city1_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `fk_city_name_localisation_city1`
    FOREIGN KEY (`city_id`)
    REFERENCES `tour_agency`.`city` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 75
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`hotel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`hotel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `star_rating` INT NOT NULL DEFAULT '3',
  `hotel_city_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `hotel_city_id_idx` (`hotel_city_id` ASC) VISIBLE,
  CONSTRAINT `hotel_city`
    FOREIGN KEY (`hotel_city_id`)
    REFERENCES `tour_agency`.`city` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`hotel_name_localisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`hotel_name_localisation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hotel_id` INT NOT NULL,
  `language` VARCHAR(2) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_hotel_name_localisation_hotel1_idx` (`hotel_id` ASC) VISIBLE,
  CONSTRAINT `fk_hotel_name_localisation_hotel1`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `tour_agency`.`hotel` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`tour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`tour` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `on_fire` TINYINT NOT NULL DEFAULT '0',
  `image_file` VARCHAR(100) NOT NULL DEFAULT 'no_tour_image.jpeg',
  `type` VARCHAR(45) NOT NULL,
  `ticket_price` DECIMAL(10,2) NOT NULL,
  `ticket_count` INT NOT NULL,
  `supported_languages` VARCHAR(45) NOT NULL,
  `departure_takeoff_date` DATE NOT NULL,
  `departure_takeoff_time` TIME NOT NULL,
  `return_takeoff_date` DATE NOT NULL,
  `return_takeoff_time` TIME NOT NULL,
  `city_id` INT NOT NULL,
  `hotel_id` INT NULL DEFAULT NULL,
  `discount_amount` INT NOT NULL DEFAULT '0',
  `discount_per` INT NOT NULL DEFAULT '0',
  `discount_max` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_tour_city1_idx` (`city_id` ASC) VISIBLE,
  INDEX `fk_tour_hotel1_idx` (`hotel_id` ASC) VISIBLE,
  CONSTRAINT `fk_tour_city1`
    FOREIGN KEY (`city_id`)
    REFERENCES `tour_agency`.`city` (`id`),
  CONSTRAINT `fk_tour_hotel1`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `tour_agency`.`hotel` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` CHAR(60) NOT NULL,
  `password` CHAR(20) NOT NULL,
  `name` CHAR(20) NOT NULL,
  `surname` CHAR(20) NOT NULL,
  `status` TINYINT NOT NULL DEFAULT '1',
  `language` VARCHAR(2) NOT NULL DEFAULT 'ua',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`orders` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `tour_id` INT NOT NULL,
  `ticket_count` INT NOT NULL DEFAULT '1',
  `state` VARCHAR(45) NOT NULL DEFAULT 'unconfirmed',
  `total_price` DECIMAL(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_booking_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_booking_tour1_idx` (`tour_id` ASC) VISIBLE,
  CONSTRAINT `fk_booking_tour1`
    FOREIGN KEY (`tour_id`)
    REFERENCES `tour_agency`.`tour` (`id`),
  CONSTRAINT `fk_booking_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tour_agency`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 43
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tour_agency`.`tour_description_localisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`tour_description_localisation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description_tour_id` INT NOT NULL,
  `language` VARCHAR(2) NOT NULL,
  `description_text` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `description_tour_id_idx` (`description_tour_id` ASC) VISIBLE,
  CONSTRAINT `description_tour_id`
    FOREIGN KEY (`description_tour_id`)
    REFERENCES `tour_agency`.`tour` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tour_agency`.`tour_title_localisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tour_agency`.`tour_title_localisation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title_tour_id` INT NOT NULL,
  `language` VARCHAR(2) NOT NULL,
  `title_text` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `title_tour_id_idx` (`title_tour_id` ASC) VISIBLE,
  CONSTRAINT `title_tour_id`
    FOREIGN KEY (`title_tour_id`)
    REFERENCES `tour_agency`.`tour` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
