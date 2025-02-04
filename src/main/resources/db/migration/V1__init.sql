CREATE TABLE `t_inventory`
(
  `id` bigint(28) NOT NULL AUTO_INCREMENT,
    `sku_code` VARCHAR(255) DEFAULT NULL,
    `quantity` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);