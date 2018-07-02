-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Portfolio` (
  `idPortfolio` INT NOT NULL,
  PRIMARY KEY (`idPortfolio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utilizador` (
  `idUtilizador` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `DOB` VARCHAR(45) NOT NULL,
  `País` VARCHAR(45) NOT NULL,
  `Plafond` DECIMAL(25,5) NOT NULL,
  `Portfolio_idPortfolio` INT NOT NULL,
  PRIMARY KEY (`idUtilizador`),
  INDEX `fk_Utilizador_Portfolio1_idx` (`Portfolio_idPortfolio` ASC),
  CONSTRAINT `fk_Utilizador_Portfolio1`
    FOREIGN KEY (`Portfolio_idPortfolio`)
    REFERENCES `mydb`.`Portfolio` (`idPortfolio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ativo` (
  `idAtivo` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Tipo` VARCHAR(45) NOT NULL,
  `ValorCompra` VARCHAR(100) NOT NULL,
  `ValorVenda` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idAtivo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ativo_has_Portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ativo_has_Portfolio` (
  `Ativo_idAtivo` INT NOT NULL,
  `Portfolio_idPortfolio` INT NOT NULL,
  PRIMARY KEY (`Ativo_idAtivo`, `Portfolio_idPortfolio`),
  INDEX `fk_Ativo_has_Portfolio_Portfolio1_idx` (`Portfolio_idPortfolio` ASC),
  INDEX `fk_Ativo_has_Portfolio_Ativo_idx` (`Ativo_idAtivo` ASC),
  CONSTRAINT `fk_Ativo_has_Portfolio_Ativo`
    FOREIGN KEY (`Ativo_idAtivo`)
    REFERENCES `mydb`.`Ativo` (`idAtivo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ativo_has_Portfolio_Portfolio1`
    FOREIGN KEY (`Portfolio_idPortfolio`)
    REFERENCES `mydb`.`Portfolio` (`idPortfolio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Utilizador_has_Ativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utilizador_has_Ativo` (
  `Utilizador_idUtilizador` INT NOT NULL,
  `Ativo_idAtivo` INT NOT NULL,
  `MontanteResultante` DECIMAL(25,5) NULL,
  `Estado` VARCHAR(45) NOT NULL,
  `Montante` DECIMAL(25,5) NOT NULL,
  `LimiteLucro` DECIMAL(25,5) NULL,
  `LimitePerda` DECIMAL(25,5) NULL,
  PRIMARY KEY (`Utilizador_idUtilizador`, `Ativo_idAtivo`),
  INDEX `fk_Utilizador_has_Ativo_Ativo1_idx` (`Ativo_idAtivo` ASC),
  INDEX `fk_Utilizador_has_Ativo_Utilizador1_idx` (`Utilizador_idUtilizador` ASC),
  CONSTRAINT `fk_Utilizador_has_Ativo_Utilizador1`
    FOREIGN KEY (`Utilizador_idUtilizador`)
    REFERENCES `mydb`.`Utilizador` (`idUtilizador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Utilizador_has_Ativo_Ativo1`
    FOREIGN KEY (`Ativo_idAtivo`)
    REFERENCES `mydb`.`Ativo` (`idAtivo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
