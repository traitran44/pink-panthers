ALTER TABLE `pinkpanther`.`shelters` 
ADD COLUMN `family_capacity` INT NOT NULL DEFAULT 0 AFTER `age_restrictions`,
ADD COLUMN `single_capacity` INT NOT NULL DEFAULT 200 AFTER `family_capacity`;
-- update the value for capacity per fam and per single
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='264' WHERE `id`='1';
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='140' WHERE `id`='2';
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='450' WHERE `id`='3';
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='92' WHERE `id`='4';
UPDATE `pinkpanther`.`shelters` SET `family_capacity`='40', `single_capacity`='0' WHERE `id`='5';
UPDATE `pinkpanther`.`shelters` SET `family_capacity`='32', `single_capacity`='80' WHERE `id`='6';
UPDATE `pinkpanther`.`shelters` SET `family_capacity`='76', `single_capacity`='0' WHERE `id`='7';
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='80' WHERE `id`='8';
UPDATE `pinkpanther`.`shelters` SET `family_capacity`='200', `single_capacity`='0' WHERE `id`='9';
UPDATE `pinkpanther`.`shelters` SET `family_capacity`='0', `single_capacity`='22' WHERE `id`='10';
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='330' WHERE `id`='11';
UPDATE `pinkpanther`.`shelters` SET `single_capacity`='12' WHERE `id`='12';
-- add new columns for occupied
ALTER TABLE `pinkpanther`.`shelters` 
ADD COLUMN `family_occupied` INT NOT NULL DEFAULT 0 AFTER `single_capacity`,
ADD COLUMN `single_occupied` INT NOT NULL DEFAULT 0 AFTER `family_occupied`;

