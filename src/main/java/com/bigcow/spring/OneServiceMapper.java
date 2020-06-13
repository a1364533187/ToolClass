package com.bigcow.spring;

@Su
public interface OneServiceMapper {

    @Select("select * from `one_service_admin_user` where id=#{id}")
    int selectById(int id);
}
