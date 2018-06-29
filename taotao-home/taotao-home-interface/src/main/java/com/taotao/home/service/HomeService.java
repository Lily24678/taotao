package com.taotao.home.service;

import java.util.List;

import com.taotao.home.pojo.TbReciveAddress;
import com.taotao.utils.TaotaoResult;

public interface HomeService {
	
	public void save(TbReciveAddress reciveAddress);
	
	public List<TbReciveAddress> queryAllAddrByUserId(Long userId);
	
	public TaotaoResult delete(Long id);
	
	public TaotaoResult updateAddress(TbReciveAddress reciveAddress);
	
	public TaotaoResult changeIsdefault(Long id);

}
