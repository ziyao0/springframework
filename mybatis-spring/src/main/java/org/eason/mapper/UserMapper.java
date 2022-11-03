package org.eason.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author Eason
 * @date 2022/11/3
 */
public interface UserMapper {

    @Select("select 'user'")
    String getAll();
}
