-- ----------------------------
-- Table structure for `conf_center_project`
-- ----------------------------
CREATE TABLE `conf_center_project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `project_name` varchar(255) NOT NULL COMMENT '项目名',
  `project_desc` varchar(255) DEFAULT NULL COMMENT '项目描述',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8 COMMENT='配置中心项目表';

-- ----------------------------
-- Table structure for `conf_center_user`
-- ----------------------------
CREATE TABLE `conf_center_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `type` tinyint(4) NOT NULL COMMENT '账户类型，1：管理员，2：普通用户',
  `status` tinyint(4) NOT NULL COMMENT '状态，1：有效，0：无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新建时间',
  `mail` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8 COMMENT='用户表';
