DELIMITER ;;
DROP PROCEDURE if EXISTS `INSERTTESTDATA`;
CREATE PROCEDURE `INSERTTESTDATA`()
BEGIN
	
    DECLARE i,user_i,account_i,user_account_i,rolelevel INT default 0;
    DECLARE in_path,outpath,name,in_cert varchar(128);
      
    -- 插入10万数据到用户表  
    SET rolelevel = 5;
    SET user_i=1;
    WHILE user_i<100001 DO
      	INSERT INTO clickoo_user(uid,status,name,password,devicemodel,IMEI,rolelevel,timedate) 
      		VALUES(user_i,0,user_i,user_i,'260,320',user_i,rolelevel,now());
    	SET user_i = user_i+1;
   	END WHILE;
     
    -- 插入2万数据到帐号表
    SET account_i=1;
    SET in_path = '{"protocoltype":"pop","host":"10.101.101.221","receivePort":"110","type":"","compositor":"0"}';
    SET outpath = '{"smtpServer":"10.101.101.221","type":"","sendPort":"25"}';
    WHILE account_i<20001 DO
    	SET name  = CONCAT('test', LPAD(account_i,5,"0"),'@clickooinc.local');
   		SET in_cert = CONCAT('{"loginID":"',name,'","pwd":"QU1UdGVzdDEyMw==","alias":""}');
    	INSERT INTO clickoo_mail_account(id,name,status,in_cert,in_path,out_cert,outpath,type) 
    		VALUES(account_i,name,0,in_cert,in_path,in_cert,outpath,0);
   		SET account_i = account_i+1;
    END WHILE;
    
	-- 插入2万数据到关系表
    SET user_account_i=1;
    WHILE user_account_i<20001 DO
   		INSERT INTO clickoo_user_account(id,uid,aid,status,registerDate) values(user_account_i,user_account_i,user_account_i,2,now());
      	SET user_account_i = user_account_i+1;
    END WHILE;

    -- 插入发送接收邮件服务器配置
    INSERT INTO `clickoo_mail_receive_server` (`id`,`name`,`inpath`,`status`,`intervaltime`,`retrytime`,`backup`) 
    	VALUES (0, 'clickooinc.local', '{"protocoltype":"pop","host":"10.101.101.221","receivePort":"110","type":""}', '0', '5', '0', 0);
	INSERT INTO `clickoo_mail_send_server` 
		VALUES (0, 'clickooinc.local','{"smtpServer":"10.101.101.221","type":"","sendPort":"25"}', '0', 0);
	
END;;
DELIMITER ;