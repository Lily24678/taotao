package com.taotao.home.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.home.mapper.TbReciveAddressMapper;
import com.taotao.home.pojo.TbReciveAddress;
import com.taotao.home.pojo.TbReciveAddressExample;
import com.taotao.home.service.HomeService;
import com.taotao.utils.TaotaoResult;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private TbReciveAddressMapper mapper;

	@Override
	public void save(TbReciveAddress reciveAddress) {
		reciveAddress.setIsdefault((byte) 0);
		mapper.insert(reciveAddress);
	}

	@Override
	public List<TbReciveAddress> queryAllAddrByUserId(Long userId) {
		TbReciveAddressExample example = new TbReciveAddressExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return mapper.selectByExample(example);
	}

	@Override
	public TaotaoResult delete(Long id) {
		mapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateAddress(TbReciveAddress reciveAddress) {
		mapper.updateByPrimaryKeySelective(reciveAddress);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult changeIsdefault(Long id) {
		TbReciveAddressExample example = new TbReciveAddressExample();
		example.createCriteria().andIsdefaultEqualTo((byte) 1);
		List<TbReciveAddress> list = mapper.selectByExample(example);
		if (list.size() > 0 && list != null) {
			TbReciveAddress address = list.get(0);
			address.setIsdefault((byte) 0);
			mapper.updateByPrimaryKeySelective(address);
		}
		TbReciveAddress reciveAddress = new TbReciveAddress();
		reciveAddress.setId(id);
		reciveAddress.setIsdefault((byte) 1);
		mapper.updateByPrimaryKeySelective(reciveAddress);
		return TaotaoResult.ok();
	}

}
