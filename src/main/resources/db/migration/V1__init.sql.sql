

DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`authority`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `authority` VALUES ('ADMIN');
INSERT INTO `authority` VALUES ('USER');


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `user` VALUES ('b0df0230-115d-473d-bff2-45225aa27ccd','felipe@felipe','felipe','felipe');


DROP TABLE IF EXISTS `user_authority`;

CREATE TABLE `user_authority` (
  `user_id` varchar(255) NOT NULL,
  `authority_name` varchar(255) NOT NULL,
  KEY `FK6ktglpl5mjosa283rvken2py5` (`authority_name`),
  KEY `FKpqlsjpkybgos9w2svcri7j8xy` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `user_authority` VALUES ('b0df0230-115d-473d-bff2-45225aa27ccd','USER');