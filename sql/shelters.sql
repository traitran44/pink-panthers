CREATE TABLE `pinkpanther`.`shelters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `shelter_name` VARCHAR(255) CHARACTER SET 'utf8' NOT NULL,
  `capacity` VARCHAR(255) NULL DEFAULT '',
  `restrictions` VARCHAR(255) NULL DEFAULT '',
  `longitude` DOUBLE NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `special_notes` VARCHAR(255) NULL DEFAULT '',
  `phone_number` VARCHAR(255) NULL DEFAULT '',
  PRIMARY KEY (`id`),
  INDEX `idx_shelter_name` (`shelter_name` ASC));
