ALTER TABLE `pinkpanther`.`shelters` 
ADD COLUMN `gender_restrictions` VARCHAR(255) NOT NULL DEFAULT 'anyone' AFTER `phone_number`,
ADD COLUMN `age_restrictions` VARCHAR(255) NOT NULL DEFAULT 'anyone' AFTER `gender_restrictions`,
ADD INDEX `idx_age_restrictions` (`age_restrictions` ASC),
ADD INDEX `idx_gender_restrictions` (`gender_restrictions` ASC);

