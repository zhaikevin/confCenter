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

-- ----------------------------
-- Table structure for `conf_center_operation_log`
-- ----------------------------
CREATE TABLE `conf_center_operation_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '会员id',
  `type` tinyint(4) NOT NULL COMMENT '操作类型',
  `target_type` tinyint(4) NOT NULL COMMENT '操作对象类型',
  `target_id` bigint(20) NOT NULL COMMENT '操作对象数据id',
  `target_name` varchar(255) NOT NULL COMMENT '操作对象数据名称',
  `result` tinyint(4) NOT NULL COMMENT '操作结果',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `error_msg` varchar(1000) DEFAULT '' COMMENT '错误信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_USER_ID` (`user_id`) USING BTREE,
  KEY `INDEX_TARGET_TYPE` (`target_type`) USING BTREE,
  KEY `INDEX_TARGET_ID` (`target_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Table structure for `conf_center_menu`
-- ----------------------------
CREATE TABLE `conf_center_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点id',
  `path` varchar(255) NOT NULL COMMENT '路径',
  `icon` varchar(255) DEFAULT 'ios-grid-view' COMMENT '图标',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `component` varchar(255) DEFAULT 'Main' COMMENT '组件',
  `access` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：控制权限，1：不控制权限',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';