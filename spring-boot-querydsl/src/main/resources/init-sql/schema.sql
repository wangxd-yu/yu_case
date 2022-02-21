-- 部门表
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`
(
    `id`   bigint NOT NULL,
    `sort` tinyint     DEFAULT NULL COMMENT '排序',
    `name` varchar(32) DEFAULT NULL COMMENT '名称',
    PRIMARY KEY `pk_id`(`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='部门表';

-- 用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`       bigint NOT NULL COMMENT 'ID',
    `dept_id`  bigint     DEFAULT NULL COMMENT '部门id',
    `username` varchar(32) DEFAULT NULL COMMENT '用户名',
    `name`     varchar(32) DEFAULT NULL COMMENT '姓名',
    PRIMARY KEY `pk_id`(`id`) USING BTREE,
    INDEX `idx_dept_id`(`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';