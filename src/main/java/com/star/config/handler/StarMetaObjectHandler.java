package com.star.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author liuxing
 */

@Component
public class StarMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject,"create_time", LocalDateTime.class,LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"update_time",LocalDateTime.class,LocalDateTime.now());
    }
}
