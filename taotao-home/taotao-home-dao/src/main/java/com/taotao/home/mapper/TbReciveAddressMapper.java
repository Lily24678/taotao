package com.taotao.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.taotao.home.pojo.TbReciveAddress;
import com.taotao.home.pojo.TbReciveAddressExample;

public interface TbReciveAddressMapper {
    int countByExample(TbReciveAddressExample example);

    int deleteByExample(TbReciveAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbReciveAddress record);

    int insertSelective(TbReciveAddress record);

    List<TbReciveAddress> selectByExample(TbReciveAddressExample example);

    TbReciveAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbReciveAddress record, @Param("example") TbReciveAddressExample example);

    int updateByExample(@Param("record") TbReciveAddress record, @Param("example") TbReciveAddressExample example);

    int updateByPrimaryKeySelective(TbReciveAddress record);

    int updateByPrimaryKey(TbReciveAddress record);
}