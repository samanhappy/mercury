/*
 * 创建并初始化数据库clickoo_main
 */
drop database  if exists clickoo_main; 
create database clickoo_main DEFAULT CHARACTER SET utf8 COLLATE utf8_bin; 

use clickoo_main;

drop table if exists clickoo_mail_account;

drop table if exists clickoo_mail_receive_server;

drop table if exists clickoo_mail_send_server;

drop table if exists clickoo_user;

drop table if exists clickoo_volume;

drop table if exists clickoo_volume_meta;

drop table if exists clickoo_yahoosnp_message;

drop table if exists clickoo_yahoosnp_ids;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for clickoo_sender_filter
-- ----------------------------
CREATE TABLE `clickoo_sender_filter` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `uid` bigint(10) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sign` int(1) NOT NULL, 
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_yahoosnp_message
-- 2000000
-- ----------------------------
CREATE TABLE `clickoo_yahoosnp_message` (
  `aid` bigint(10) PRIMARY KEY,
  `status` int(1) default NULL,
  `subId` bigint(10) default NULL,
  `userId` varchar(64) default NULL,
  `maxuuid` bigint(10) default 0,
  `backup` int(1) default 0
) ENGINE=InnoDB
PARTITION BY RANGE (aid) (PARTITION p0 VALUES LESS THAN (500000),
PARTITION p1 VALUES LESS THAN (500001) , PARTITION p2 VALUES LESS THAN (1000000) ,
PARTITION p3 VALUES LESS THAN (1000001) , PARTITION p4 VALUES LESS THAN (1500000) ,
PARTITION p5 VALUES LESS THAN (1500001) , PARTITION p6 VALUES LESS THAN (2000000) ,
PARTITION p7 VALUES LESS THAN MAXVALUE ); 

-- ----------------------------
-- Table structure for clickoo_yahoosnp_ids
-- ----------------------------
CREATE TABLE `clickoo_yahoosnp_ids` (
  `aid` bigint(10) NOT NULL,
  `uuid` varchar(256) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_mail_account
-- 2000000
-- ----------------------------
CREATE TABLE `clickoo_mail_account` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(128) NOT NULL,
  `status` int(1) default NULL,
  `in_cert` varchar(256) default NULL,
  `in_path` varchar(256) default NULL,
  `out_cert` varchar(256) default NULL,
  `outpath` varchar(256) default NULL,
  `failtime` int(1) default 0,
  `type` int(1) default 0,
  `maxuuid` varchar(256) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB
PARTITION BY RANGE (id) (PARTITION p0 VALUES LESS THAN (500000),
PARTITION p1 VALUES LESS THAN (500001) , PARTITION p2 VALUES LESS THAN (1000000) ,
PARTITION p3 VALUES LESS THAN (1000001) , PARTITION p4 VALUES LESS THAN (1500000) ,
PARTITION p5 VALUES LESS THAN (1500001) , PARTITION p6 VALUES LESS THAN (2000000) ,
PARTITION p7 VALUES LESS THAN MAXVALUE ); 

-- ----------------------------
-- Table structure for clickoo_mail_receive_server
-- ----------------------------
CREATE TABLE `clickoo_mail_receive_server` (
  `id` int(11) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(64) default NULL,
  `inpath` varchar(256) default NULL,
  `status` int(1) default NULL,
  `intervaltime` int(2) NOT NULL,
  `retrytime` int(1) NOT NULL,
  `connections` int(10) default 1000,
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_mail_send_server
-- ----------------------------
CREATE TABLE `clickoo_mail_send_server` (
  `id` int(11) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(64) default NULL,
  `outpath` varchar(256) default NULL,
  `status` int(1) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_user
-- 1000000
-- ----------------------------
CREATE TABLE `clickoo_user` (
  `uid` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `status` int(1) default NULL,
  `name` varchar(64) default NULL,
  `password` varchar(128) default NULL,
  `devicemodel` varchar(32) default NULL,
  `IMEI` varchar(64) default '' NOT NULL,
  `signature` varchar(1024) default NULL,
  `rolelevel` int(1) default 1 NOT NULL,
  `timedate` varchar(64) default NULL,
  `tsoffset` bigint(10) default 0,
  `sign` int(1) NOT NULL default 0, 
  `backup` int(1) default 0
) ENGINE=InnoDB
PARTITION BY RANGE (uid) (PARTITION p0 VALUES LESS THAN (500000),
PARTITION p1 VALUES LESS THAN (500001) , PARTITION p2 VALUES LESS THAN (1000000) ,
PARTITION p3 VALUES LESS THAN MAXVALUE ); 


-- ----------------------------
-- Table structure for clickoo_volume
-- ----------------------------
CREATE TABLE `clickoo_volume` (
  `id` int(10) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(64) default NULL,
  `path` varchar(128) default NULL,
  `type` tinyint(1) default NULL,
  `file_bits` int(10) default NULL,
  `space` int(10) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_volume_meta
-- ----------------------------
CREATE TABLE `clickoo_volume_meta` (
  `id` int(10) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(32) default NULL,
  `vid` varchar(32) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_user_account
-- 2000000
-- ----------------------------
CREATE TABLE `clickoo_user_account` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `uid` bigint(10) NOT NULL,
  `aid` bigint(10) NOT NULL,
  `status` int(2) default 0 NOT NULL,
  `registerDate` datetime default NULL,
  `validation` int(1) default 0,
  `offlineDate` datetime default null,
  `receivedNum` int(2) default 0,
  `backup` int(1) default 0
) ENGINE=InnoDB
PARTITION BY RANGE (id) (PARTITION p0 VALUES LESS THAN (500000),
PARTITION p1 VALUES LESS THAN (500001) , PARTITION p2 VALUES LESS THAN (1000000) ,
PARTITION p3 VALUES LESS THAN (1000001) , PARTITION p4 VALUES LESS THAN (1500000) ,
PARTITION p5 VALUES LESS THAN (1500001) , PARTITION p6 VALUES LESS THAN (2000000) ,
PARTITION p7 VALUES LESS THAN MAXVALUE ); 


-- ----------------------------
-- Table structure for clickoo_role
-- ----------------------------
drop table if exists clickoo_role;
CREATE TABLE `clickoo_role` (
  `id` int(10) NOT NULL PRIMARY KEY auto_increment,
  `title` varchar(64) default NULL,
  `remark` text,
  `function` varchar(512) default NULL,
  `priority` int(1) default 0,
  `backup` int(1) default 0
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for clickoo_folder
-- ----------------------------
CREATE TABLE `clickoo_folder` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(128) default NULL,
  `uid` bigint(10) NOT NULL,
  `aid` bigint(10) NOT NULL,
  `addDate` datetime default NULL,
  `receiveStatus` int(1) default NULL,
  `listStatus` int(1) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `clickoo_timer`;
CREATE TABLE `clickoo_timer` (
  `id` bigint(10) unsigned NOT NULL PRIMARY KEY auto_increment,
  `uid` bigint(10) unsigned NOT NULL,
  `aid` bigint(10) unsigned DEFAULT NULL,
  `weekdays` varchar(16) DEFAULT NULL,
  `starthour` tinyint(4) unsigned DEFAULT NULL,
  `endhour` tinyint(4) unsigned DEFAULT NULL,
  `startminute` tinyint(4) unsigned DEFAULT NULL,
  `endminute` tinyint(4) unsigned DEFAULT NULL,
  `timetype` tinyint(1) unsigned DEFAULT NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;


-- ----------------------------
-- Table structure for clickoo_message
-- 3000000
-- ----------------------------
CREATE TABLE `clickoo_message` (
  `id` bigint(10) NOT NULL auto_increment,
  `intime` datetime NOT NULL,
  `status` int(1) default NULL,
  `aid` bigint(10) default NULL,
  `uuid` varchar(128) collate utf8_bin default NULL,
  `foldername` varchar(128) collate utf8_bin default NULL,
  `sendtime` datetime default NULL,
  `emailFrom` varchar(512) collate utf8_bin default NULL,
  `emailTo` varchar(512) collate utf8_bin default NULL,
  `cc` varchar(512) collate utf8_bin default NULL,
  `bcc` varchar(512) collate utf8_bin default NULL,
  `subject` varchar(512) collate utf8_bin default NULL,
  `backup` int(1) default 0,
	PRIMARY KEY  (`aid`,`uuid`,`id`),
	key(`id`)
) ENGINE=InnoDB
PARTITION BY RANGE (id) (PARTITION p0 VALUES LESS THAN (500000),
PARTITION p1 VALUES LESS THAN (500001) , PARTITION p2 VALUES LESS THAN (1000000) ,
PARTITION p3 VALUES LESS THAN (1000001) , PARTITION p4 VALUES LESS THAN (1500000) ,
PARTITION p5 VALUES LESS THAN (1500001) , PARTITION p6 VALUES LESS THAN (2000000) ,
PARTITION p7 VALUES LESS THAN (2000001) , PARTITION p8 VALUES LESS THAN (2500000) ,
PARTITION p9 VALUES LESS THAN (2500001) , PARTITION p10 VALUES LESS THAN (3000000) ,
PARTITION p11 VALUES LESS THAN MAXVALUE ); 


DROP TABLE if EXISTS clickoo_send_message;
CREATE TABLE `clickoo_send_message` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `intime` datetime default NULL,
  `aid` bigint(10) default NULL,
  `uid` bigint(10) default NULL,
  `hid` bigint(10) default NULL,
  `status` int(1) default NULL,
  `volumeid` bigint(10) default NULL,
  `path` varchar(256) collate utf8_bin default NULL
) ENGINE=InnoDB;


CREATE TABLE `clickoo_send_failed_message` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `mid` bigint(10) default NULL,
  `oldmid` bigint(10) default NULL,
  `sendtype` int(1) default NULL,
  `qeuetype` VARCHAR(64) default NULL,
  `uid` bigint(10) default NULL,
  `hid` bigint(10) default NULL,
  `retrycount` int(1) default 0
) ENGINE=InnoDB;

CREATE TABLE `clickoo_message_data` (
  `id` BIGINT(10) NOT NULL PRIMARY KEY,
  `data` blob default NULL,
  `size` BIGINT UNSIGNED,
  `backup` int(1) default 0
)
ENGINE = InnoDB;

-- ----------------------------
-- Table structure for clickoo_message_attachement
-- 4000000
-- ----------------------------
CREATE TABLE `clickoo_message_attachement` (
  `id` BIGINT(10) UNSIGNED NOT NULL PRIMARY KEY auto_increment,
  `name` VARCHAR(512),
  `type` VARCHAR(64),
  `length` BIGINT(10) UNSIGNED,
  `path` VARCHAR(256),
  `parent` BIGINT(10) UNSIGNED,
  `mid` BIGINT(10) UNSIGNED,
  `volume_id` BIGINT(10) UNSIGNED,
  `backup` int(1) default 0
)ENGINE = InnoDB
PARTITION BY RANGE (id) (PARTITION p0 VALUES LESS THAN (500000),
PARTITION p1 VALUES LESS THAN (500001) , PARTITION p2 VALUES LESS THAN (1000000) ,
PARTITION p3 VALUES LESS THAN (1000001) , PARTITION p4 VALUES LESS THAN (1500000) ,
PARTITION p5 VALUES LESS THAN (1500001) , PARTITION p6 VALUES LESS THAN (2000000) ,
PARTITION p7 VALUES LESS THAN (2000001) , PARTITION p8 VALUES LESS THAN (2500000) ,
PARTITION p9 VALUES LESS THAN (2500001) , PARTITION p10 VALUES LESS THAN (3000000) ,
PARTITION p11 VALUES LESS THAN (3000001) , PARTITION p12 VALUES LESS THAN (3500000) ,
PARTITION p13 VALUES LESS THAN (3500001) , PARTITION p14 VALUES LESS THAN (4000000) ,
PARTITION p15 VALUES LESS THAN MAXVALUE ); 

-- ----------------------------
-- Table structure for clickoo_delete_message
-- ----------------------------
CREATE TABLE `clickoo_delete_message` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `accname` varchar(64) default NULL,
  `intime` datetime default NULL,
  `mid` bigint(10) NOT NULL,
  `uuid` varchar(256) default NULL,
  `backup` int(1) default 0
) ENGINE=InnoDB;


-- ----------------------------
-- Table structure for clickoo_task_factory
-- ----------------------------
CREATE TABLE `clickoo_task_factory` (
  `mctype` varchar(2) default NULL,
  `mckey` varchar(512) default NULL,
  `mcvalue` varchar(1024) default NULL,
   PRIMARY KEY  (`mckey`)
)ENGINE=MEMORY MAX_ROWS=1000;


DROP TABLE if EXISTS admin_user;
CREATE TABLE `admin_user` (
  `id` int(10) NOT NULL PRIMARY KEY auto_increment,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `authority` varchar(32) NOT NULL
) ENGINE=InnoDB;


-- ----------------------------
-- Table structure for clickoo_send_xml
-- ----------------------------
CREATE TABLE `clickoo_send_xml` (
  `id` bigint(10) NOT NULL PRIMARY KEY auto_increment,
  `sxkey` varchar(512) default NULL,
  `sxvalue` varchar(10240) default NULL,
  `sxdate` datetime default '0000-00-00 00:00:00' NOT NULL
)ENGINE=MEMORY MAX_ROWS=1000;
-- ----------------------------
-- Definition of views
-- ----------------------------
DROP VIEW IF EXISTS `selectuidandimeibyaid`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` 
SQL SECURITY DEFINER VIEW `selectuidandimeibyaid` 
AS select `ua`.`aid` AS `aid`,`u`.`uid` AS `uid`,`u`.`IMEI` AS `IMEI` 
from (`clickoo_user_account` `ua` 
join `clickoo_user` `u` 
on((`ua`.`uid` = `u`.`uid`)));

-- ----------------------------
-- Definition of indexes
-- ----------------------------
CREATE INDEX account_index ON clickoo_mail_account(name);
-- CREATE INDEX user_index ON clickoo_user(name,password,IMEI,rolelevel);
CREATE INDEX relation_index ON clickoo_user_account(uid,aid,status);
CREATE INDEX message_index ON clickoo_message(aid,intime,uuid);
CREATE INDEX attachment_index ON clickoo_message_attachement(mid);

-- ----------------------------
-- Trigger structure for insert message
-- ----------------------------
DELIMITER ;;
drop trigger if EXISTS tri_message_bi;
create trigger tri_message_bi before insert on clickoo_message
for each row begin
declare countNumber int default 0;
select count(1) into countNumber from clickoo_message where aid =
new.aid and uuid = new.uuid;
if countNumber > 0 then
SELECT ERROR_DUP_MESSAGE INTO countNumber;
end if;
end;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for getMailBoxConfig
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `getMailBoxConfig`;
CREATE DEFINER=`root`@`%` PROCEDURE `getMailBoxConfig`(IN in_uuid VARCHAR(128),
	IN in_name VARCHAR(128),
	IN in_suffix VARCHAR(128),
	IN in_second_suffix VARCHAR(128))
BEGIN
	DECLARE out_state INT DEFAULT 0;
	DECLARE out_inpath VARCHAR(256) DEFAULT NULL;
	DECLARE out_outpath VARCHAR(256) DEFAULT NULL;
	DECLARE out_uid LONG DEFAULT 0;
	
	DECLARE v_aid INT DEFAULT 0;

	SELECT inpath INTO out_inpath FROM clickoo_mail_receive_server WHERE name = in_suffix AND status = 0;
	SELECT outpath INTO out_outpath FROM clickoo_mail_send_server WHERE name = in_suffix AND status = 0;
	
	IF out_inpath IS NULL OR out_outpath IS NULL THEN
		SELECT inpath INTO out_inpath FROM clickoo_mail_receive_server WHERE name = in_second_suffix AND status = 0;
		SELECT outpath INTO out_outpath FROM clickoo_mail_send_server WHERE name = in_second_suffix AND status = 0;
	END IF;
	
	IF out_inpath IS NULL OR out_outpath IS NULL THEN
		SET out_state = 1;
	ELSE
		IF in_uuid IS NOT NULL THEN
			SELECT uid INTO out_uid FROM clickoo_user where name = in_uuid;
			IF out_uid IS NULL OR out_uid = 0 THEN
				SET out_inpath = NULL;
				SET out_outpath = NULL;
				SET out_state = 2;
			-- remove user-account relation validation because client has done this work
			-- ELSE
				-- SELECT id INTO v_aid FROM clickoo_mail_account WHERE name = in_name;
				-- IF v_aid IS NOT NULL AND v_aid != 0 THEN
					-- IF EXISTS(SELECT 1 FROM clickoo_user_account WHERE uid = out_uid AND aid = v_aid) THEN
						-- SET out_inpath = NULL;
						-- SET out_outpath = NULL;
						-- SET out_state = 3;
					-- END IF;
				-- END IF;
			END IF;
		END IF;
		
	END IF;
	SELECT out_state, out_inpath, out_outpath, out_uid;
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addAccount
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `addAccount`;
CREATE DEFINER=`root`@`%` PROCEDURE `addAccount`(IN in_name VARCHAR(128),
	IN in_inpath VARCHAR(256),
	IN in_incert VARCHAR(256),
	IN in_outpath VARCHAR(256),
	IN in_outcert VARCHAR(256),
	IN in_uid LONG, 
	IN in_type INT,
	IN in_newmailbox BOOL,
	IN in_suffix VARCHAR(50))
BEGIN
	DECLARE v_aid LONG DEFAULT 0;
	DECLARE v_rid LONG DEFAULT 0;
	DECLARE v_id LONG DEFAULT 0;

	SELECT id INTO v_aid FROM clickoo_mail_account WHERE name = in_name;
	IF v_aid IS NULL OR v_aid = 0 THEN
		-- CALL getNextId('accountid', v_aid);
		INSERT INTO clickoo_mail_account(id, name, status, in_cert, in_path, out_cert, outpath, type)
			VALUES (0, in_name, 2, in_incert, in_inpath, in_outcert, in_outpath, in_type);
		SELECT LAST_INSERT_ID() INTO v_aid;
	ELSE
		UPDATE clickoo_mail_account SET in_cert = in_incert, in_path = in_inpath, out_cert = in_outcert, outpath = in_outpath
			WHERE id = v_aid;
	END IF;

	SELECT id INTO v_rid FROM clickoo_user_account WHERE uid = in_uid AND aid = v_aid;
	IF v_rid IS NULL OR v_rid = 0 THEN
		-- CALL getNextId('uarelationid', v_rid);
		IF in_uid = 0 THEN
			INSERT INTO clickoo_user_account(id, uid, aid, status, registerDate, cuki, validation, lastgettime)
				VALUES (0, in_uid, v_aid, 1, now(), 'imap-idle', 1, now());
		ELSE
			INSERT INTO clickoo_user_account(id, uid, aid, status, registerDate)
				VALUES (0, in_uid, v_aid, 1, now());
		END IF;
	ELSE
		IF in_uid = 0 THEN
			UPDATE clickoo_user_account SET lastgettime = now() WHERE aid = v_aid AND uid = 0;
		END IF;
	END IF;

	IF in_newmailbox = TRUE AND NOT EXISTS(SELECT 1 FROM clickoo_mail_receive_server WHERE name = in_suffix) THEN
		-- CALL getNextId('receive_server', v_id);
		INSERT INTO clickoo_mail_receive_server(id, name, inpath, status, intervaltime, retrytime)
			VALUES(0, in_suffix,in_inpath,0,5,0);

		-- CALL getNextId('sendserver', v_id);
		INSERT INTO clickoo_mail_send_server(id, name, outpath, status)
			VALUES(0, in_suffix, in_outpath, 0);
	END IF;
	
	select v_aid;
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for removeAccount
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `removeAccount`;
CREATE DEFINER=`root`@`%` PROCEDURE `removeAccount`(IN in_uid LONG, 
	IN in_aid LONG)
BEGIN
	DECLARE out_type INT DEFAULT -1;
	DECLARE out_status INT DEFAULT 0;
	DECLARE out_incert VARCHAR(256) DEFAULT NULL;

	DECLARE v_id LONG DEFAULT 0;

	SELECT id INTO v_id FROM clickoo_user_account WHERE uid = in_uid AND aid = in_aid;
	IF v_id IS NOT NULL AND v_id != 0 THEN
		IF NOT EXISTS (SELECT 1 FROM clickoo_user_account WHERE aid = in_aid AND uid != in_uid) THEN
			SELECT type, in_cert INTO out_type, out_incert FROM clickoo_mail_account WHERE id = in_aid;
		ELSE
			IF NOT EXISTS (SELECT 1 from clickoo_user_account WHERE aid = in_aid AND uid != in_uid AND (status = 0 OR status = 1)) THEN
				-- send offline message for this
				SELECT type, in_cert INTO out_type, out_incert FROM clickoo_mail_account WHERE id = in_aid;
				SET out_status = 1;
			END IF;
		END IF;

		DELETE FROM clickoo_user_account WHERE id = v_id;
		DELETE FROM clickoo_timer WHERE uid = in_uid AND aid = in_aid;
		DELETE FROM clickoo_folder WHERE uid = in_uid AND aid = in_aid;
		UPDATE clickoo_message set status=1 WHERE aid = in_aid;
		DELETE FROM clickoo_mail_account WHERE id = in_aid; 
	END IF;
	SELECT out_type AS type, out_incert AS inCert, out_status AS status;
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SPLIT_SUB_STR0
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `SPLIT_SUB_STR0`;
CREATE DEFINER=`root`@`%` PROCEDURE `SPLIT_SUB_STR0`(inout str TEXT ,delimiter VARCHAR(1), out split VARCHAR(100))
BEGIN
DECLARE SUB_STR_LENGTH INT;

SET SUB_STR_LENGTH = length(SUBSTRING_INDEX(str,delimiter,1));

SET split = substring(str, 1, SUB_STR_LENGTH);

SET str = substring(str, SUB_STR_LENGTH + 2 );

#SELECT SUB_STR_LENGTH, SUB_STR_SPLIT0, STR;

END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for SetOnline
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `SetOnline`;
CREATE DEFINER=`root`@`%` PROCEDURE `SetOnline`(
	IN in_uid LONG,
	IN in_status INT,
	IN in_aids VARCHAR(1024))
	
BEGIN
DECLARE v_aid VARCHAR(50) DEFAULT NULL;
DECLARE v_type INT(1) DEFAULT 0;
DECLARE v_status INT(1) DEFAULT 0;
DECLARE v_incert VARCHAR(256) DEFAULT NULL;

DECLARE v_sign INT(1) DEFAULT 0;
DECLARE cur CURSOR FOR SELECT aid, status FROM clickoo_user_account WHERE uid = in_uid;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_sign = 1;

CREATE TEMPORARY TABLE `tmp_account` (
	`id` BIGINT(10) DEFAULT NULL,
	`type` INT(1) DEFAULT -1,
	`inCert` varchar(256) default NULL
)ENGINE=InnoDB;

IF in_aids IS NULL THEN
	OPEN cur;
	WHILE v_sign != 1 DO
		FETCH cur into v_aid, v_status;
		IF v_sign <> 1 THEN
			IF (v_status | in_status) != in_status THEN
				UPDATE clickoo_user_account SET status = (status & in_status) WHERE uid =  in_uid AND aid = v_aid;
				IF v_status > 1 AND (v_status & in_status) < 2 AND NOT EXISTS(SELECT 1 FROM clickoo_user_account WHERE aid = v_aid AND uid != in_uid AND status < 2) THEN
					SELECT type, in_cert INTO v_type, v_incert FROM clickoo_mail_account WHERE id = v_aid;
					INSERT INTO tmp_account(id, type, inCert) VALUES (v_aid, v_type, v_incert);
				END IF;
			END IF;
		END IF;
	END WHILE;
	CLOSE cur;
ELSE
	REPEAT
		CALL SPLIT_SUB_STR0(in_aids, ',', v_aid);
		SELECT status INTO v_status FROM clickoo_user_account WHERE uid =  in_uid AND aid = v_aid;
			IF (v_status | in_status) != in_status THEN
				UPDATE clickoo_user_account SET status = (status & in_status) WHERE uid =  in_uid AND aid = v_aid;
				IF v_status > 1 AND (v_status & in_status) < 2 AND NOT EXISTS(SELECT 1 FROM clickoo_user_account WHERE aid = v_aid AND uid != in_uid AND status < 2) THEN
					SELECT type, in_cert INTO v_type, v_incert FROM clickoo_mail_account WHERE id = v_aid;
					INSERT INTO tmp_account(id, type, inCert) VALUES (v_aid, v_type, v_incert);
				END IF;
			END IF;
	UNTIL in_aids = ''
	END REPEAT;
END IF;

SELECT * from tmp_account;
DROP TEMPORARY TABLE IF EXISTS tmp_account;
END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for SetOffline
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `SetOffline`;
CREATE DEFINER=`root`@`%` PROCEDURE `SetOffline`(
	IN in_uid LONG,
	IN in_status INT,
	IN in_aids VARCHAR(1024))
BEGIN

DECLARE v_aid VARCHAR(50) DEFAULT '';
DECLARE v_type INT(1) DEFAULT 0;
DECLARE v_status INT(1) DEFAULT -1;

DECLARE v_sign INT(1) DEFAULT 0;
DECLARE cur CURSOR FOR
	SELECT aid, status FROM clickoo_user_account WHERE uid = in_uid;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_sign = 1;

CREATE TEMPORARY TABLE `tmp_account` (
	`id` BIGINT(10) DEFAULT NULL,
	`type` INT(1) DEFAULT -1
)ENGINE=InnoDB;

IF in_aids IS NULL THEN
	OPEN cur;
	WHILE v_sign != 1 DO
		FETCH cur into v_aid, v_status;
		IF v_sign <> 1 THEN
			IF (v_status & in_status) != in_status THEN
				UPDATE clickoo_user_account SET status = (status | in_status) WHERE uid =  in_uid AND aid = v_aid;
				UPDATE clickoo_user_account SET offlineDate = NOW() WHERE uid =  in_uid AND aid = v_aid AND offlineDate IS NULL;
				IF v_status < 2 AND (v_status | in_status) > 1 AND NOT EXISTS(SELECT 1 FROM clickoo_user_account WHERE aid = v_aid AND uid != in_uid AND status < 2) THEN
					SELECT type INTO v_type FROM clickoo_mail_account WHERE id = v_aid;
					INSERT INTO tmp_account(id, type) VALUES (v_aid, v_type);
				END IF;
			END IF;
		END IF;
	END WHILE;
	CLOSE cur;
ELSE
	REPEAT
		CALL SPLIT_SUB_STR0(in_aids, ',', v_aid);
		SELECT status INTO v_status FROM clickoo_user_account WHERE uid =  in_uid AND aid = v_aid;
		IF v_status IS NOT NULL AND v_status != -1 AND (v_status & in_status) != in_status THEN
			UPDATE clickoo_user_account SET status = (status | in_status) WHERE uid =  in_uid AND aid = v_aid;
			UPDATE clickoo_user_account SET offlineDate = NOW() WHERE uid =  in_uid AND aid = v_aid AND offlineDate IS NULL;
			IF v_status < 2 AND (v_status | in_status) > 1 AND NOT EXISTS(SELECT 1 FROM clickoo_user_account WHERE aid = v_aid AND uid != in_uid AND status < 2) THEN
				SELECT type INTO v_type FROM clickoo_mail_account WHERE id = v_aid;
				INSERT INTO tmp_account(id, type) VALUES (v_aid, v_type);
			END IF;
		END IF;
	UNTIL in_aids = ''
	END REPEAT;
END IF;


SELECT * from tmp_account;
DROP TEMPORARY TABLE IF EXISTS tmp_account;
END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for SetOfflineForUsers
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `SetOfflineForUsers`;
CREATE DEFINER=`root`@`%` PROCEDURE `SetOfflineForUsers`(
	IN in_uids LONGTEXT,
	IN in_status INT)
BEGIN

DECLARE v_aid VARCHAR(100) DEFAULT '';
DECLARE v_uid_str VARCHAR(100) DEFAULT '';
DECLARE v_uid LONG DEFAULT 0;
DECLARE v_type INT(1) DEFAULT 0;
DECLARE v_status INT(1) DEFAULT 0;
DECLARE v_sign INT(1) DEFAULT 0;


DECLARE cur CURSOR FOR
	SELECT aid, status FROM clickoo_user_account WHERE uid = v_uid;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_sign = 1;

CREATE TEMPORARY TABLE `tmp_account` (
	`id` BIGINT(10) DEFAULT NULL,
	`type` INT(1) DEFAULT -1
)ENGINE=InnoDB;
	
REPEAT
	CALL SPLIT_SUB_STR0(in_uids, ',', v_uid_str);
	SET v_uid = v_uid_str;
	-- set state to 1 for auto offline user
	UPDATE clickoo_user SET status = 1 WHERE uid = v_uid;
	SET v_sign = 0;
	OPEN cur;
	WHILE v_sign != 1 DO
		FETCH cur into v_aid, v_status;
		IF v_sign <> 1 THEN
			IF (v_status & in_status) != in_status THEN
				UPDATE clickoo_user_account SET status = (status | in_status) WHERE uid = v_uid AND aid = v_aid;
				UPDATE clickoo_user_account SET offlineDate = NOW() WHERE uid = v_uid AND aid = v_aid AND offlineDate IS NULL;
				IF v_status < 2 AND (v_status | in_status) > 1 AND NOT EXISTS(SELECT 1 FROM clickoo_user_account WHERE aid = v_aid AND uid != v_uid AND status < 2) THEN
					SELECT type INTO v_type FROM clickoo_mail_account WHERE id = v_aid;
					INSERT INTO tmp_account(id, type) VALUES (v_aid, v_type);
				END IF;
			END IF;
		END IF;
	END WHILE;
	CLOSE cur;
UNTIL in_uids = ''
END REPEAT;

SELECT * from tmp_account;
DROP TEMPORARY TABLE IF EXISTS tmp_account;
END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for SETCAGCONFIG
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `SETCAGCONFIG`;
CREATE DEFINER=`root`@`%` PROCEDURE `SETCAGCONFIG`(
	IN in_names VARCHAR(1000),
	IN in_validations VARCHAR(100),
	IN in_uid VARCHAR(100),
	IN in_cukis VARCHAR(1000))
BEGIN

DECLARE v_name VARCHAR(128) DEFAULT '';

DECLARE v_validation VARCHAR(8) DEFAULT '';

DECLARE v_cuki VARCHAR(64) DEFAULT '';

DECLARE v_aid LONG DEFAULT 0;

DECLARE v_rid LONG DEFAULT 0;

DECLARE v_type INT DEFAULT 0;

DECLARE v_incert VARCHAR(256) DEFAULT '';

CREATE TEMPORARY TABLE `tmpaccount` (
	`id` bigint(10) DEFAULT NULL,
	`inCert` varchar(256) DEFAULT NULL,
	`type` int(1) default 0
)ENGINE=InnoDB;

REPEAT

	CALL SPLIT_SUB_STR0(in_names, ',', v_name);
	CALL SPLIT_SUB_STR0(in_validations, ',', v_validation);
	CALL SPLIT_SUB_STR0(in_cukis, ',', v_cuki);

	SELECT id, type, in_cert INTO v_aid, v_type, v_incert FROM clickoo_mail_account WHERE name = v_name;

	IF v_aid IS NOT NULL AND v_aid != 0 THEN
		IF v_validation = '1'  THEN
			insert into tmpaccount(id, type, inCert) values (v_aid, v_type, v_incert);
		END IF;

		IF v_validation = '2' THEN
			-- delete relation for validation = 2
			DELETE FROM clickoo_user_account WHERE uid = in_uid AND aid = v_aid;
		ELSE
			SELECT id INTO v_rid FROM clickoo_user_account WHERE uid = in_uid AND aid = v_aid;
			IF v_rid IS NULL OR v_rid = 0 THEN
				-- CALL getNextID('uarelationid', v_rid);
				INSERT INTO clickoo_user_account(id, uid, aid, validation, cuki) VALUES (0, in_uid, v_aid, v_validation, v_cuki);
			ElSE
				UPDATE clickoo_user_account SET validation = v_validation, cuki = v_cuki WHERE id = v_rid;
			END IF;
		END IF;
	END IF;
	-- do nothing for invalid mail account

UNTIL in_names = ''
END REPEAT;

SELECT * from tmpaccount;
DROP TEMPORARY TABLE IF EXISTS tmpaccount;

END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for EMAILLIST
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `EMAILLIST`;
CREATE PROCEDURE `EMAILLIST`(IN param1 VARCHAR(128),IN param2 VARCHAR(128),IN param3 DATETIME,IN param4 VARCHAR(128))
BEGIN
	DECLARE userid,sign,caid,cstatus VARCHAR(128) default null;
    DECLARE renum,srenum,limitnum INT default 0;
    DECLARE rdate,odate,receiveTime,receiveOldTime,rangeTime,nowTime DATETIME;
	DECLARE rs CURSOR FOR select aid,status,registerDate,offlineDate,DATE_SUB(registerDate,INTERVAL param4 DAY),receivedNum from clickoo_user_account
		where uid=param1 and (status < 2);
    DECLARE CONTINUE HANDLER FOR NOT FOUND
        SET sign = 1;

	SET userid = param1;
    SET limitnum = param2;
    SET receiveTime = param3;
	CREATE TEMPORARY TABLE `tmpmsg` (`id` bigint(10) NOT NULL,`intime` datetime default NULL,
        `status` int(1) default NULL,`aid` bigint(10) default NULL,`uuid` varchar(256) default NULL,`sendtime` datetime default NULL
        ) ENGINE=InnoDB;
        
         OPEN rs;
         loop_label:LOOP
                 FETCH rs into caid,cstatus,rdate,odate,rangeTime,renum;
                 IF sign=1 THEN
                      LEAVE loop_label;
                 END IF;
                 IF odate is not null THEN
                       SET  receiveTime = odate;
                 END IF;
                 IF receiveTime is null THEN
                      SET receiveTime = '0000-00-00 00:00:00';
                 END IF;
                 
                 SET receiveOldTime = receiveTime;
                 
                 IF cstatus=1  THEN
                 	 IF renum = 0 THEN
                 	 	SET receiveOldTime = '0000-00-00 00:00:00';
                 	 END IF;
                     SET limitnum = limitnum - renum;
                     SET @oldmail=CONCAT('INSERT INTO tmpmsg(id,intime,status,aid,uuid,sendtime)
                               select id,intime,status,aid,uuid,sendtime from clickoo_message where status != 2 and aid=\'',caid,'\' and sendtime<\'',rdate,'\' and intime>\'',receiveOldTime,'\' order by intime desc limit  ',limitnum);
                     PREPARE pre1 from @oldmail;
                     EXECUTE pre1;
                     select count(id) into srenum from tmpmsg where aid = caid;
                     SET srenum = srenum +renum;
                     update clickoo_user_account set receivedNum = srenum where uid=userid and aid= caid;
                     SET limitnum = limitnum +renum;
                     select DATE_SUB(now(),INTERVAL 6 HOUR) into nowTime;
                     IF srenum >=  limitnum or nowTime>rdate THEN
                          UPDATE clickoo_user_account set status=0 where uid=userid and aid= caid;
                     END IF;

                 END IF;

                 INSERT INTO tmpmsg(id,intime,status,aid,uuid,sendtime)
                        select id,intime,status,aid,uuid,sendtime from clickoo_message where aid=caid and intime>receiveTime and sendtime>rdate and status!=2 limit 10;
				 
                 -- update timedate for user
                 update clickoo_user set timedate = receiveTime where uid = userid;
                        
                 IF odate is not null THEN
				       update clickoo_user_account set offlineDate = null where uid=userid and aid= caid;
				 END IF;
				 SET receiveTime = param3;
         END LOOP;
         CLOSE rs;
         select id,intime,status,aid,uuid,sendtime from tmpmsg order by intime asc;
      DROP TEMPORARY TABLE IF EXISTS tmpmsg;
	END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for EMAILLIST
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `MESSAGECLEAN`;
CREATE PROCEDURE `MESSAGECLEAN`(IN accountid VARCHAR(128),IN lastTime VARCHAR(128),IN maxEmailNum INT,IN validdatemax INT)
BEGIN
        DECLARE msgcount INT;
        DECLARE AUNUMBER INT;
        DECLARE CURRENTTIME DATETIME;
        DECLARE DAYCOUNT INT;
        DECLARE COMMONNUMBER INT;

        SELECT COUNT(*) INTO msgcount FROM clickoo_message WHERE aid = accountid;
        SELECT COUNT(*) INTO AUNUMBER FROM clickoo_user_account WHERE UID = 0 AND AID = accountid;
        SELECT COUNT(*) INTO COMMONNUMBER FROM clickoo_user_account WHERE UID != 0 AND AID = accountid;

        IF AUNUMBER = 0 AND COMMONNUMBER = 0 THEN
           SELECT id,name,type,length,path,mid,volume_id,parent from clickoo_message_attachement where mid in(select id from clickoo_message where aid = accountid and status != 2);
           DELETE FROM  clickoo_message WHERE AID = accountid;
           DELETE FROM  clickoo_message_attachement WHERE mid IN (SELECT id FROM clickoo_message WHERE AID = accountid);
           DELETE FROM  clickoo_message_data WHERE id IN (SELECT id FROM clickoo_message WHERE AID = accountid);
        END IF;

        IF AUNUMBER = 1 THEN
           SELECT LASTGETTIME INTO CURRENTTIME FROM clickoo_user_account WHERE UID = 0 AND AID = accountid;
           SELECT id,name,type,length,path,mid,volume_id,parent from clickoo_message_attachement where mid in(select id from clickoo_message where aid = accountid and status != 2);

           SET DAYCOUNT = TO_DAYS(NOW()) - TO_DAYS(CURRENTTIME);
               IF(DAYCOUNT > VALIDDATEMAX) THEN
                     DELETE FROM  clickoo_message WHERE AID = accountid;
                     DELETE FROM  clickoo_message_attachement WHERE mid IN (SELECT id FROM clickoo_message WHERE AID = accountid);
                     DELETE FROM  clickoo_message_data WHERE id IN (SELECT id FROM clickoo_message WHERE AID = accountid);
               END IF;
        END IF;

        IF COMMONNUMBER > 0 THEN
           SELECT COUNT(*) INTO msgcount FROM clickoo_message WHERE aid = accountid;
               IF (msgcount - maxEmailNum) > 0 THEN
                   DROP TABLE IF EXISTS tmpmsg;
                   CREATE TABLE `tmpmsg` (`id` BIGINT(10) NOT NULL,`intime` DATETIME DEFAULT NULL) ENGINE=InnoDB;
                   SET @oldmail=CONCAT('INSERT INTO tmpmsg(id,intime)
                   SELECT id,intime FROM clickoo_message WHERE aid=\'',accountid,' ORDER BY id \' LIMIT ',msgcount - maxEmailNum);
                   PREPARE pre1 FROM @oldmail;
                   EXECUTE pre1;

                   DELETE FROM tmpmsg WHERE intime > lastTime;

                   SELECT id,name,type,length,path,mid,volume_id,parent FROM clickoo_message_attachement WHERE mid IN (SELECT id FROM tmpmsg);

                   /*select msgcount,maxEmailNum;
                   select * from tmpmsg;*/

                   DELETE FROM  clickoo_message WHERE id IN (SELECT id FROM tmpmsg);
                   DELETE FROM  clickoo_message_attachement WHERE mid IN (SELECT id FROM tmpmsg);
                   DELETE FROM  clickoo_message_data WHERE id IN (SELECT id FROM tmpmsg);
                   DROP TABLE IF EXISTS tmpmsg;
               END IF;
        END IF;
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for Select account to receive mail
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `SENDMESSAGECLEAN`;
CREATE PROCEDURE `SENDMESSAGECLEAN`(IN lastTime VARCHAR(128),IN accountid VARCHAR(128))
BEGIN
    SELECT ID,INTIME,AID,VOLUMEID,PATH FROM clickoo_send_message WHERE AID = accountid and INTIME < lastTime;
    DELETE FROM clickoo_send_message WHERE AID = accountid and INTIME < lastTime;
END;;
DELIMITER ;

-- ----------------------------
-- Trigger structure for insert message
-- ----------------------------
-- ----------------------------
-- DELIMITER ;;
-- drop trigger if EXISTS tri_message_bi;
-- create trigger tri_message_bi before insert on clickoo_message
-- for each row begin
-- declare countNumber int default 0;
-- select count(1) into countNumber from clickoo_message where aid =
-- new.aid and uuid = new.uuid;
-- if countNumber > 0 then
-- SELECT ERROR_DUP_MESSAGE INTO countNumber;
-- end if;
-- end;;
-- DELIMITER ;
-- ----------------------------
-- ----------------------------
-- Procedure structure for Select account to receive mail
-- ----------------------------
DELIMITER ;;
DROP PROCEDURE if EXISTS `RECEIVEEMAILACCOUNT`;
CREATE PROCEDURE `RECEIVEEMAILACCOUNT`(IN param1 VARCHAR(128))
BEGIN
	DROP TEMPORARY TABLE IF EXISTS tmpreceiveaccount;
	CREATE TEMPORARY TABLE `tmpreceiveaccount` (`id` bigint(10) NOT NULL, `name` varchar(128) default NULL,
 	 `status` int(1) default NULL,`in_cert` varchar(256) default NULL,`in_path` varchar(256) default NULL,
  	 `out_cert` varchar(256) default NULL, `outpath` varchar(256) default NULL,`failtime` int(11) default 0,
  	 `type` int(1) default 0,`maxuuid` varchar(256) NOT NULL default 0,
  	 `registrationDate` datetime default null,`devicemodel` varchar(32) default NULL,`recentMessageDate` datetime default null,
  	 `sign` int(1) default 0,`uid` bigint(10) NOT NULL,`function` varchar(512) default NULL,
  	  PRIMARY KEY  (`id`)) ENGINE=InnoDB;
  	  insert into tmpreceiveaccount(id,name,status,in_cert,in_path,out_cert,outpath,maxuuid,registrationDate,devicemodel,failtime,type,sign,uid,function)
      select a.id ,a.name,a.status,in_cert as inCert,in_path as inPath,out_cert as outCert,
      	outpath as outPath,a.maxuuid,min(ua.registerDate) as registrationDate,max(devicemodel) as
		deviceModel,failtime,type,u.sign,u.uid,r.function
		from clickoo_mail_account a
		left join clickoo_user_account ua on a.id=ua.aid
		left join clickoo_user u on ua.uid = u.uid
		left join clickoo_role r on u.rolelevel = r.priority 
		where a.id= param1 group by ua.aid;
	  update tmpreceiveaccount  set recentMessageDate = (select max(sendtime) from clickoo_message where aid = param1);
	select id,name,status,in_cert as inCert,in_path as inPath,out_cert as outCert,
      	outpath as outPath,maxuuid,registrationDate,devicemodel,failtime,type,sign,uid,recentMessageDate,function from tmpreceiveaccount;
	 DROP TEMPORARY TABLE IF EXISTS tmpreceiveaccount;
	END;;
DELIMITER ;
-- ----------------------------
-- Records 
-- ----------------------------
-- INSERT INTO `clickoo_user` VALUES ('0', '0', 'idle', '', '260,240', '', '', 2, '0000-00-00 00:00:00', '0', 0);
-- UPDATE clickoo_user SET uid = 0;

INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('1', 'archermind.com', '{\"host\":\"mail.archermind.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\",\"compositor\":\"0\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('2', '163.com', '{\"host\":\"imap.163.com\",\"type\":\"\",\"protocoltype\":\"imap\",\"receivePort\":\"143\"}', '0', 5, '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('3', 'gmail.com', '{\"host\":\"pop.gmail.com\",\"type\":\"SSL\",\"protocoltype\":\"pop\",\"receivePort\":\"995\",\"supportalluid\":\"0\",\"compositor\":\"0\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('4', 'qq.com', '{\"host\":\"pop.qq.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('5', 'clickoo.com', '{\"host\":\"mail.clickoo.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\",\"compositor\":\"0\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('6', 'live.*', '{\"host\":\"pop3.live.com\",\"type\":\"SSL\",\"protocoltype\":\"pop\",\"receivePort\":\"995\",\"compositor\":\"0\"}', '0', '5', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('7', 'hotmail.*', '{\"host\":\"pop3.live.com\",\"type\":\"SSL\",\"protocoltype\":\"pop\",\"receivePort\":\"995\",\"supportalluid\":\"0\",\"compositor\":\"0\"}', '0', '5', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('8', 'msn.*', '{\"host\":\"pop3.live.com\",\"type\":\"SSL\",\"protocoltype\":\"pop\",\"receivePort\":\"995\",\"supportalluid\":\"0\",\"compositor\":\"0\"}', '0', '5', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('9', 'yahoo.*', '{\"host\":\"124.108.115.241,212.82.96.94,212.82.111.223,imap.mail.yahoo.com\",\"type\":\"SSL\",\"protocoltype\":\"imap\",\"receivePort\":\"993\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('10', 'ymail.*', '{\"host\":\"124.108.115.241,212.82.96.94,212.82.111.223,imap.mail.yahoo.com\",\"type\":\"SSL\",\"protocoltype\":\"imap\",\"receivePort\":\"993\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('11', 'rocketmail.*', '{\"host\":\"124.108.115.241,212.82.96.94,212.82.111.223,imap.mail.yahoo.com\",\"type\":\"SSL\",\"protocoltype\":\"imap\",\"receivePort\":\"993\"}', '0', '3', '0', 0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('12','126.com','{\"protocoltype\":\"pop\",\"host\":\"pop.126.com\",\"receivePort\":\"110\",\"type\":\"\"}',0,5,0,0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('13','sina.com','{\"protocoltype\":\"pop\",\"host\":\"pop3.sina.com\",\"receivePort\":\"110\",\"type\":\"\"}',0,5,0,0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('14','sohu.com','{\"protocoltype\":\"pop\",\"host\":\"pop3.sohu.com\",\"receivePort\":\"110\",\"type\":\"\"}',0,5,0,0);
INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) VALUES ('15','yeah.net','{\"protocoltype\":\"pop\",\"host\":\"pop.yeah.net\",\"receivePort\":\"110\",\"type\":\"\"}',0,5,0,0);

INSERT INTO `clickoo_mail_send_server` VALUES ('1', 'archermind.com', '{\"smtpServer\":\"mail.archermind.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('2', '163.com', '{\"smtpServer\":\"smtp.163.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('3', 'gmail.com', '{\"smtpServer\":\"smtp.gmail.com\",\"type\":\"SSL\",\"sendPort\":\"465\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('4', 'qq.com', '{\"smtpServer\":\"smtp.qq.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('5', 'clickoo.com', '{\"smtpServer\":\"mail.clickoo.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('6', 'live.*', '{\"smtpServer\":\"smtp.live.com\",\"type\":\"\",\"sendPort\":\"587\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('7', 'hotmail.*', '{\"smtpServer\":\"smtp.live.com\",\"type\":\"\",\"sendPort\":\"587\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('8', 'msn.*', '{\"smtpServer\":\"smtp.live.com\",\"type\":\"\",\"sendPort\":\"587\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('9', 'yahoo.*', '{\"smtpServer\":\"smtp.mail.yahoo.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('10', 'ymail.*', '{\"smtpServer\":\"smtp.mail.yahoo.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES ('11', 'rocketmail.*', '{\"smtpServer\":\"smtp.mail.yahoo.com\",\"type\":\"\",\"sendPort\":\"25\"}', '0', 0);
INSERT INTO `clickoo_mail_send_server` VALUES (12,'126.com','{\"protocoltype\":\"smtp\",\"host\":\"smtp.126.com\",\"sendPort\":\"25\",\"type\":\"\"}',0,0);
INSERT INTO `clickoo_mail_send_server` VALUES (13,'sina.com','{\"protocoltype\":\"smtp\",\"host\":\"smtp.sina.com\",\"sendPort\":\"25\",\"type\":\"\"}',0,0);
INSERT INTO `clickoo_mail_send_server` VALUES (14,'sohu.com','{\"protocoltype\":\"smtp\",\"host\":\"smtp.sohu.com\",\"sendPort\":\"25\",\"type\":\"\"}',0,0);
INSERT INTO `clickoo_mail_send_server` VALUES (15,'yeah.net','{\"protocoltype\":\"smtp\",\"host\":\"smtp.yeah.net\",\"sendPort\":\"25\",\"type\":\"\"}',0,0);


INSERT INTO `clickoo_volume` VALUES ('1', 'Locationone', '/home/clickoomail/NFS', '0', 100, '1024', 0);
INSERT INTO `clickoo_volume` VALUES ('2', 'second', '/home/clickoomail/NFS', '1',100 ,'2048', 0);
INSERT INTO `clickoo_volume_meta` VALUES ('1', 'CURRENT_MESSAGE_VOLUME', '1', 0);
INSERT INTO `clickoo_volume_meta` VALUES ('2', 'SECONDARY_MESSAGE_VOLUME', '2', 0);
INSERT INTO `clickoo_volume_meta` VALUES ('3', 'INDEX_VOLUME', '1', 0);

INSERT INTO `clickoo_role` VALUES ('1', 'Disable', '', '{\"accountNumber\":\"0\",\"storageOption\":\"NO\",\"latestEmailNumber\":\"0\",\"retrievalEmailInterval\":\"LOW\",\"encryptionOption\":\"NO\",\"synchronizeOption\":\"NO\",\"saveOriginalAttachmentOption\":\"NO\",\"useMailAccountOption\":\00000000\",\"scheduledPushOption\":\"NO\",\"allowAttachmentNumber\":\"NO\",\"autoCleanupPeriod\":\"0\"}',1, 0);
INSERT INTO `clickoo_role` VALUES ('2', 'Free', '', '{\"accountNumber\":\"5\",\"storageOption\":\"NO\",\"latestEmailNumber\":\"30\",\"retrievalEmailInterval\":\"LOW\",\"encryptionOption\":\"NO\",\"synchronizeOption\":\"NO\",\"saveOriginalAttachmentOption\":\"NO\",\"useMailAccountOption\":\"11110000\",\"scheduledPushOption\":\"NO\",\"allowAttachmentNumber\":\"1\",\"autoCleanupPeriod\":\"7\"}',2, 0);
INSERT INTO `clickoo_role` VALUES ('3', 'Consumer', '', '{\"accountNumber\":\"2\",\"storageOption\":\"NO\",\"latestEmailNumber\":\"30\",\"retrievalEmailInterval\":\"MED\",\"encryptionOption\":\"NO\",\"synchronizeOption\":\"NO\",\"saveOriginalAttachmentOption\":\"NO\",\"useMailAccountOption\":\"11110000\",\"scheduledPushOption\":\"NO\",\"allowAttachmentNumber\":\"1\",\"autoCleanupPeriod\":\"14\"}',3, 0);
INSERT INTO `clickoo_role` VALUES ('4', 'Consumer +', '', '{\"accountNumber\":\"3\",\"storageOption\":\"NO\",\"latestEmailNumber\":\"30\",\"retrievalEmailInterval\":\"MED\",\"encryptionOption\":\"NO\",\"synchronizeOption\":\"YES\",\"saveOriginalAttachmentOption\":\"YES\",\"useMailAccountOption\":\"11110000\",\"scheduledPushOption\":\"YES\",\"allowAttachmentNumber\":\"2\",\"autoCleanupPeriod\":\"28\"}',4, 0);
INSERT INTO `clickoo_role` VALUES ('5', 'Premium', '', '{\"accountNumber\":\"10\",\"storageOption\":\"YES\",\"latestEmailNumber\":\"50\",\"retrievalEmailInterval\":\"ASAP\",\"encryptionOption\":\"YES\",\"synchronizeOption\":\"YES\",\"saveOriginalAttachmentOption\":\"YES\",\"useMailAccountOption\":\"11111000\",\"scheduledPushOption\":\"YES\",\"allowAttachmentNumber\":\"10\",\"autoCleanupPeriod\":\"NEVER\"}',5, 0);
INSERT INTO `clickoo_role` VALUES ('6', 'PLMN', '', '{\"accountNumber\":\"10\",\"storageOption\":\"YES\",\"latestEmailNumber\":\"OD\",\"retrievalEmailInterval\":\"ASAP\",\"encryptionOption\":\"OD\",\"synchronizeOption\":\"YES\",\"saveOriginalAttachmentOption\":\"OD\",\"useMailAccountOption\":\"22222222\",\"scheduledPushOption\":\"YES\",\"allowAttachmentNumber\":\"OD\",\"autoCleanupPeriod\":\"NEVER\"}',6, 0);
