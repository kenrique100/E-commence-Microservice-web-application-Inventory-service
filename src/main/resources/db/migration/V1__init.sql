CREATE TABLE IF NOT EXISTS `t_inventory`
(
  `id` BIGINT(28) NOT NULL AUTO_INCREMENT,
    `sku_code` VARCHAR(255) NOT NULL,
    `quantity` INT NOT NULL,
    PRIMARY KEY (`id`)
);