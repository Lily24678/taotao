package com.taotao.sso.service;

import com.taotao.pojo.TbUser;
import com.taotao.utils.TaotaoResult;

/** 
 * @Description: 用户模块接口
 * @author nq  nianqiang@itcast.cn   
 */
public interface UserService {
	public TaotaoResult checkUserInfo(String param,Integer type);
	public TaotaoResult createUser(TbUser user);
	public TaotaoResult login(String username, String password);
	public TaotaoResult logout(String token);
	public TaotaoResult getUserByToken(String token);
}
