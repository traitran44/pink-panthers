ALTER TABLE `pinkpanther`.`shelters` 
DROP COLUMN `single_occupied`,
DROP COLUMN `family_occupied`,
CHANGE COLUMN `family_capacity` `update_capacity` INT(11) NOT NULL DEFAULT 300 ,
CHANGE COLUMN `single_capacity` `occupancy` INT(11) NOT NULL DEFAULT 0 ;
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='264', `occupancy`='0' WHERE `id`='1';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='140', `occupancy`='0' WHERE `id`='2';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='450', `occupancy`='0' WHERE `id`='3';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='92', `occupancy`='0' WHERE `id`='4';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='112', `occupancy`='0' WHERE `id`='6';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='80', `occupancy`='0' WHERE `id`='8';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='300', `occupancy`='0' WHERE `id`='13';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='12', `occupancy`='0' WHERE `id`='12';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='330', `occupancy`='0' WHERE `id`='11';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='22', `occupancy`='0' WHERE `id`='10';
UPDATE `pinkpanther`.`shelters` SET `update_capacity`='300' WHERE `id`='9';
-- update the accounts table
ALTER TABLE `pinkpanther`.`accounts` 
DROP COLUMN `is_veteran`,
DROP COLUMN `has_children`,
CHANGE COLUMN `has_family` `family_members` INT NOT NULL DEFAULT 0 ,
ADD COLUMN `restriction_match` VARCHAR(255) NOT NULL DEFAULT 'none' AFTER `family_members`;

