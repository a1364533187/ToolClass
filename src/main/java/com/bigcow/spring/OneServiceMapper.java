package com.bigcow.spring;

public interface OneServiceMapper {

    @Su("select * from `one_service_admin_user` where id=#{id}")
    int selectById(int id);
}
