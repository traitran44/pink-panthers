ALTER TABLE `pinkpanther`.`accounts`
DROP COLUMN `is_veteran`,
DROP COLUMN `has_children`,
CHANGE COLUMN `has_family` `family_members` INT NOT NULL DEFAULT 0 ,
ADD COLUMN `restriction_match` VARCHAR(255) NOT NULL DEFAULT 'none' AFTER `famil
y_members`;


