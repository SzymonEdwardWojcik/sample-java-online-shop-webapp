CREATE TABLE IF NOT EXISTS `mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_tag` TEXT NOT NULL
);
INSERT INTO `mentor` VALUES ('mentor','2017.1');