-- 创建图书表
CREATE TABLE `book` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `name` varchar(100) NOT NULL COMMENT '图书名称',
  `number` int(11) NOT NULL COMMENT '馆藏数量',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='图书表'

-- 初始化图书数据
INSERT INTO `book` (`book_id`, `name`, `number`)
VALUES
    (1000, 'Java程序设计', 10),
    (1001, '数据结构', 10),
    (1002, '设计模式', 10),
    (1003, '编译原理', 10)

-- 创建预约图书表
CREATE TABLE `appointment` (
  `book_id` bigint(20) NOT NULL COMMENT '图书ID',
  `student_id` bigint(20) NOT NULL COMMENT '学号',
  `appoint_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预约时间' ,
  PRIMARY KEY (`book_id`, `student_id`),
  INDEX `idx_appoint_time` (`appoint_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约图书表'


CREATE TABLE `mygupiao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(100) NOT NULL COMMENT '微信username',
  `gupiao_code` varchar(100) NOT NULL COMMENT '股票编码',
  `gupiao_name` varchar(100) NOT NULL COMMENT '股票名称',
  `money` double(10,2) NOT NULL COMMENT '当时股价',
  `ctime` int(11) NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  INDEX `idx_username_gupiaocode` (`username`, `gupiao_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='自选股票表'


CREATE TABLE `gupiao_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mygupiao_id` bigint(20) NOT NULL COMMENT '股票表ID',
  `type` tinyint(4) NOT NULL COMMENT '操作类型',
  `content` varchar(250) NOT NULL COMMENT '内容',
  `money` double(10,2) NOT NULL COMMENT '当时股价',
  `ctime` int(11) NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  INDEX `idx_mygupiao_id` (`mygupiao_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='操作记录表'
