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
-- Table `mydb`.`ativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ativo` (
  `idAtivo` INT(11) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Tipo` VARCHAR(45) NOT NULL,
  `ValorCompra` VARCHAR(100) NOT NULL,
  `ValorVenda` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idAtivo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`portfolio` (
  `idPortfolio` INT(11) NOT NULL,
  PRIMARY KEY (`idPortfolio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`ativo_has_portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ativo_has_portfolio` (
  `Ativo_idAtivo` INT(11) NOT NULL,
  `Portfolio_idPortfolio` INT(11) NOT NULL,
  PRIMARY KEY (`Ativo_idAtivo`, `Portfolio_idPortfolio`),
  INDEX `fk_Ativo_has_Portfolio_Portfolio1_idx` (`Portfolio_idPortfolio` ASC),
  INDEX `fk_Ativo_has_Portfolio_Ativo_idx` (`Ativo_idAtivo` ASC),
  CONSTRAINT `fk_Ativo_has_Portfolio_Ativo`
    FOREIGN KEY (`Ativo_idAtivo`)
    REFERENCES `mydb`.`ativo` (`idAtivo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ativo_has_Portfolio_Portfolio1`
    FOREIGN KEY (`Portfolio_idPortfolio`)
    REFERENCES `mydb`.`portfolio` (`idPortfolio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`utilizador` (
  `idUtilizador` INT(11) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `DOB` VARCHAR(45) NOT NULL,
  `País` VARCHAR(45) NOT NULL,
  `Plafond` DECIMAL(25,5) NOT NULL,
  `Portfolio_idPortfolio` INT(11) NOT NULL,
  PRIMARY KEY (`idUtilizador`),
  INDEX `fk_Utilizador_Portfolio1_idx` (`Portfolio_idPortfolio` ASC),
  CONSTRAINT `fk_Utilizador_Portfolio1`
    FOREIGN KEY (`Portfolio_idPortfolio`)
    REFERENCES `mydb`.`portfolio` (`idPortfolio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`utilizador_has_ativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`utilizador_has_ativo` (
  `Utilizador_idUtilizador` INT(11) NOT NULL,
  `Ativo_idAtivo` INT(11) NOT NULL,
  `MontanteResultante` DECIMAL(25,5) NULL DEFAULT NULL,
  `Estado` VARCHAR(45) NOT NULL,
  `Montante` DECIMAL(25,5) NOT NULL,
  `LimiteLucro` DECIMAL(25,5) NULL DEFAULT NULL,
  `LimitePerda` DECIMAL(25,5) NULL DEFAULT NULL,
  PRIMARY KEY (`Utilizador_idUtilizador`, `Ativo_idAtivo`),
  INDEX `fk_Utilizador_has_Ativo_Ativo1_idx` (`Ativo_idAtivo` ASC),
  INDEX `fk_Utilizador_has_Ativo_Utilizador1_idx` (`Utilizador_idUtilizador` ASC),
  CONSTRAINT `fk_Utilizador_has_Ativo_Ativo1`
    FOREIGN KEY (`Ativo_idAtivo`)
    REFERENCES `mydb`.`ativo` (`idAtivo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Utilizador_has_Ativo_Utilizador1`
    FOREIGN KEY (`Utilizador_idUtilizador`)
    REFERENCES `mydb`.`utilizador` (`idUtilizador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`alerta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`alerta` (
  `idAlerta` INT NOT NULL,
  `Tipo` VARCHAR(45) NOT NULL,
  `Modalidade` VARCHAR(45) NOT NULL,
  `Montante` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAlerta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`utilizador_has_alerta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`utilizador_has_alerta` (
  `utilizador_idUtilizador` INT(11) NOT NULL,
  `alerta_idAlerta` INT NOT NULL,
  PRIMARY KEY (`utilizador_idUtilizador`, `alerta_idAlerta`),
  INDEX `fk_utilizador_has_alerta_alerta1_idx` (`alerta_idAlerta` ASC),
  INDEX `fk_utilizador_has_alerta_utilizador1_idx` (`utilizador_idUtilizador` ASC),
  CONSTRAINT `fk_utilizador_has_alerta_utilizador1`
    FOREIGN KEY (`utilizador_idUtilizador`)
    REFERENCES `mydb`.`utilizador` (`idUtilizador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_utilizador_has_alerta_alerta1`
    FOREIGN KEY (`alerta_idAlerta`)
    REFERENCES `mydb`.`alerta` (`idAlerta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
