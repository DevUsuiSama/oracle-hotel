-- MySQL Script generated by MySQL Workbench
-- dom 10 sep 2023 23:15:44
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hotelchallenge
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `hotelchallenge` ;

-- -----------------------------------------------------
-- Schema hotelchallenge
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotelchallenge` DEFAULT CHARACTER SET utf8 ;
USE `hotelchallenge` ;

-- -----------------------------------------------------
-- Table `hotelchallenge`.`reserva`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotelchallenge`.`reserva` ;

CREATE TABLE IF NOT EXISTS `hotelchallenge`.`reserva` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha_de_entrada` DATE NOT NULL,
  `fecha_de_salida` DATE NOT NULL,
  `valor_de_reserva` DOUBLE NOT NULL,
  `forma_de_pago` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotelchallenge`.`huesped`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotelchallenge`.`huesped` ;

CREATE TABLE IF NOT EXISTS `hotelchallenge`.`huesped` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `apellido` VARCHAR(50) NOT NULL,
  `fecha_de_nacimiento` DATE NOT NULL,
  `nacionalidad` INT NOT NULL,
  `telefono` BIGINT(10) NOT NULL,
  `numero_de_reserva` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
