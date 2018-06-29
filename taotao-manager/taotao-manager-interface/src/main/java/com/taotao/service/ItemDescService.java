package com.taotao.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.utils.TaotaoResult;

/** 
 * @Description: 商品描述service
 * @author nq  nianqiang@itcast.cn   
 */
public interface ItemDescService {
	TaotaoResult getItemDescById(Long id);
	TbItemDesc findItemDescById(Long id);
}
