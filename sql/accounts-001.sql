CREATE TABLE `accounts` (
      `id` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
      `type` varchar(255) NOT NULL,
      `username` varchar(255) NOT NULL,
      `password` varchar(255) NOT NULL,
      `name` varchar(255) NOT NULL,
      `email` varchar(255) NOT NULL,
      `account_state` varchar(255) NOT NULL DEFAULT 'active',
      `shelter_id` int unsigned NULL,
      UNIQUE KEY `username` (`username`),
      KEY `shelter_id` (`shelter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
