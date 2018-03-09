ALTER TABLE `pinkpanther`.`accounts` 
ADD COLUMN `user_gender` VARCHAR(255) NULL AFTER `shelter_id`,
ADD COLUMN `user_age` INT(10) NULL AFTER `user_gender`,
ADD COLUMN `has_family` TINYINT NOT NULL DEFAULT 0 AFTER `user_age`,
ADD COLUMN `has_children` TINYINT NOT NULL DEFAULT 0 AFTER `has_family`,
ADD COLUMN `is_veteran` TINYINT NOT NULL DEFAULT 0 AFTER `has_children`;

