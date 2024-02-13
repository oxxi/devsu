--
-- Host: localhost    Database: devsu

/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET SQL_NOTES=0 */;

DROP TABLE IF EXISTS cliente;
CREATE TABLE `cliente` (
  `contrasena` varchar(255) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKkpvkbjg32bso6riqge70hwcel` FOREIGN KEY (`id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS cuentas;
CREATE TABLE `cuentas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` bit(1) DEFAULT NULL,
  `numero_cuenta` varchar(255) DEFAULT NULL,
  `saldo_inicial` decimal(38,2) DEFAULT NULL,
  `tipo_cuenta` varchar(255) DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1oja24lefkoqt6p42vo7pb32c` (`cliente_id`),
  CONSTRAINT `FK1oja24lefkoqt6p42vo7pb32c` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS movimientos;
CREATE TABLE `movimientos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `saldo` decimal(38,2) DEFAULT NULL,
  `tipo_movimiento` varchar(255) DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `cuenta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4moe88hxuohcysas5h70mdc09` (`cuenta_id`),
  CONSTRAINT `FK4moe88hxuohcysas5h70mdc09` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS persona;
CREATE TABLE `persona` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  `identificacion` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;