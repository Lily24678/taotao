package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Model;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.JsonUtils;

/** 
 * @Description: taotao首页
 * @author nq  nianqiang@itcast.cn   
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;

	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_PAGE}")
	private Integer AD1_PAGE;
	@Value("${AD1_ROWS}")
	private Integer AD1_ROWS;

	
	@RequestMapping("index")
	public String index(Model model){
		List<TbContent> list = contentService.getContentList(AD1_CID, AD1_PAGE, AD1_ROWS);
		
		List<AD1Model> ad1Models = new ArrayList<>();
		for (TbContent tbContent : list) {
			AD1Model models = new AD1Model();
			models.setAlt(tbContent.getSubTitle());
			models.setHeight(AD1_HEIGHT);
			models.setHeightB(AD1_HEIGHT_B);
			models.setHref(tbContent.getUrl());
			models.setSrc(tbContent.getPic());
			models.setSrcB(tbContent.getPic2());
			models.setWidth(AD1_WIDTH);
			models.setWidthB(AD1_WIDTH_B);
			ad1Models.add(models);
		}
		
		model.addAttribute("ad1", JsonUtils.objectToJson(ad1Models));
		return "index";
	}
}
