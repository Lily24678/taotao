package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.EasyUITreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.portal.service.ContentCategoryService;
import com.taotao.utils.TaotaoResult;
/** 
 * @Description: 内容管理service
 * @author nq  nianqiang@itcast.cn   
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> nodes = new ArrayList<>();
		
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			
			nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 添加或者是更新当前分类
	 * 如果是新添加的 则 id为null
	 */
	public TaotaoResult addOrUpdateContentCategory(TbContentCategory category) {
		
		if (null!=category && category.getId()!=null){
			// 更新操作
			category.setUpdated(new Date());
			category.setSortOrder(category.getId().intValue());
			contentCategoryMapper.updateByPrimaryKeySelective(category);
			return TaotaoResult.ok();
		}else {
			// 添加操作 {parentId:,name:}
			category.setCreated(new Date());
			// 排序
			category.setSortOrder(1);
			// 可选值:1(正常),2(删除)
			category.setStatus(1);
			// 新添加的节点都是叶子节点
			category.setIsParent(false);
			category.setUpdated(new Date());
			contentCategoryMapper.insert(category);
			// 3、判断父节点的isparent是否为true，不是true需要改为true。
			TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
			if (null!=parentNode && !parentNode.getIsParent()){
				parentNode.setIsParent(true);
				contentCategoryMapper.updateByPrimaryKeySelective(parentNode);
			}
			
			return TaotaoResult.ok(category);
		}
	}

	/**
	 * 删除分类节点
	 */
	public TaotaoResult deleteContentCategory(Long id) {
		// 1、根据ID查询当前的节点
		TbContentCategory node = contentCategoryMapper.selectByPrimaryKey(id);
		// 2、判断当前节点是否为 父节点，是：递归删除子节点
		if (node!=null && node.getIsParent()){
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			// 查询所有节点的父id是当前的节点的id的集合
			criteria.andParentIdEqualTo(id);
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			for (TbContentCategory tbContentCategory : list) {
				deleteContentCategory(tbContentCategory.getId());
			}
		}
		// 否：删除当前的节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		// 3、根据当前的节点获得当前的父节点，查询当前节点的父节点是否还有子节点
		Long parentId = node.getParentId();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		// 没有：修改当前节点的isParent为false		
		if (list!=null && list.size()==0){
			TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
			contentCategory.setIsParent(false);
			contentCategory.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}
		return TaotaoResult.ok();
	}

}
