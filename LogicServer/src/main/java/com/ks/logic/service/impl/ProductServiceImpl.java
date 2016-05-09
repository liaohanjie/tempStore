package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.ProductService;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.shop.Product;
import com.ks.model.shop.ProductItem;
import com.ks.model.shop.ProductRecord;
import com.ks.model.user.User;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.shop.ProductBuyCountVO;
import com.ks.protocol.vo.shop.ProductItemVO;
import com.ks.protocol.vo.shop.ProductVO;

public class ProductServiceImpl extends BaseService implements ProductService {

	@Override
    public void addProduct(Product entity) {
	    // TODO Auto-generated method stub
    }

	@Override
    public void updateProduct(Product entity) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void deleteProduct(int id) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public List<ProductVO> queryProductByClassId(int classId) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public ProductVO findProductByTypeId(int typeId) {
		Product entity = productDAO.findProductByTypeId(typeId);
		if(entity == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		List<ProductItem> listEntity = productItemDAO.queryProductItemByProductId(entity.getId());
		if(listEntity == null || listEntity.isEmpty()){
			throw new GameException(GameException.CODE_商品物品项配置为空, "");
		}
		
		ProductVO vo = createProductVO(entity);
		vo.setListProductItem(productItemService.createProductItemVOList(listEntity));
	    return vo;
    }

	@Override
    public UserAwardVO buyProduct(int userId, int typeId) {
		User user = userService.getExistUser(userId);
		ProductVO vo = findProductByTypeId(typeId);
		
		UserStat userStat = userStatDAO.queryUserStat(userId); 
		if(userStat == null){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		// 验证购买次数限制
		List<ProductBuyCountVO> productBuyCountVOList = createProductBuyCountVOList(userStat.getProductDayMark());
		if(vo.getBuyCount() > 0){
			ProductBuyCountVO pbcVo = getProductBuyCountVO(productBuyCountVOList, vo.getTypeId());
			if(pbcVo != null && pbcVo.getBuyCount() >= vo.getBuyCount()) {
				throw new GameException(GameException.CODE_超出购买次数, "shop");
			}
		}
		
		UserAwardVO userAwardVo;
		// 扣去魂钻，记录购买次数
		if(user.getCurrency() >= vo.getPrice()){
			userService.decrementCurrency(user, vo.getPrice(), LoggerType.TYPE_商城购买, vo.getId() + "");
			
			List<Goods> listGoods = createGoodsList(vo.getListProductItem());
			userAwardVo = goodsService.getUserAwardVO(listGoods, user, LoggerType.TYPE_商城购买, vo.getId() + "");
			
			// 记录购买次数
			String strProductMark = createProductBuyCountVOString(productBuyCountVOList, vo);
			userStat.setProductDayMark(strProductMark);
			UserStatOpt opt = new UserStatOpt();
			opt.productDayMark = SQLOpt.EQUAL;
			userStatDAO.updateUserStat(opt, userStat);
			
			// 记录购买日志
			ProductRecord entity = new ProductRecord();
			entity.setProductId(vo.getId());
			entity.setProductNum(1);
			entity.setUserId(userId);
			entity.setCreateTime(new Date());
			productRecordDAO.add(entity);
		} else {
			throw new GameException(GameException.CODE_魂钻不够, "shop");
		}
	    return userAwardVo;
    }
	
	@Override
	public ProductVO createProductVO(Product entity){
		ProductVO vo = MessageFactory.getMessage(ProductVO.class);
		vo.setId(entity.getId());
		vo.setClassId(entity.getClassId());
		vo.setTypeId(entity.getTypeId());
		vo.setBuyCount(entity.getBuyCount());
		vo.setProductName(entity.getProductName());
		vo.setProductDesc(entity.getProductDesc());
		vo.setPrice(entity.getPrice());
		vo.setGold(entity.getGold());
		vo.setNum(entity.getNum());
		return vo;
	}

	@Override
    public List<ProductVO> createProductVOList(List<Product> list) {
		List<ProductVO> listVo = new ArrayList<ProductVO>();
		for(Product entity : list) {
			listVo.add(createProductVO(entity));
	    }
	    return listVo;
    }
	
	
	public List<Goods> createGoodsList(List<ProductItemVO> list){
		List<Goods> listGoods = new ArrayList<>();
		for(ProductItemVO vo : list){
			if(vo.getRate() >= Math.random()){
				listGoods.add(Goods.create(vo.getGoodsId(), vo.getGoodsType(), vo.getGoodsNum(), vo.getGoodsLevel()));
			}
		}
		return listGoods;
	}
	
//	private int[] getLogIds(List<Goods> list){
//		int[] logIds = new int[list.size()];
//		for(int i = 0; i < list.size(); i++){
//			Goods goods = list.get(i);
//			switch (goods.getType()) {
//			case Goods.TYPE_GOLD :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_SOUL :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_PROP :
//			case Goods.TYPE_STUFF :
//			case Goods.TYPE_EQUIPMENT :
//			case Goods.TYPE_BOX :
//			case Goods.TYPE_MONSTER :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_CURRENCY :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_EXP :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_FRIENDLY_POINT :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_BAK_PROP :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			case Goods.TYPE_STAMINA :
//				logIds[i] = LoggerType.TYPE_商城购买;
//				break;
//			default :
//				throw new GameException(GameException.CODE_物品类型不存在, "");
//			}
//		}
//		return logIds;
//	}
	
	@Override
	public List<ProductBuyCountVO> createProductBuyCountVOList(String string){
		List<ProductBuyCountVO> list = new ArrayList<ProductBuyCountVO>();
		if(string == null || string.trim().equals("")) {
			return list;
		}
		
		String[] a1 = string.split(",");
		int[][] arr = new int[a1.length][2];
		
		for(int i = 0; i< arr.length; i++) {
			ProductBuyCountVO vo = MessageFactory.getMessage(ProductBuyCountVO.class);
			String[] pc = a1[i].split("_");
			vo.setTypeId(Integer.parseInt(pc[0]));
			//vo.setMaxBuyCount(Integer.parseInt(pc[1]));
			vo.setBuyCount(Integer.parseInt(pc[1]));
			list.add(vo);
		}
		return list;
	}
	
	@Override
	public String createProductBuyCountVOString(List<ProductBuyCountVO> list, ProductVO productVO){
		if(list == null || list.isEmpty()) {
			return productVO.getTypeId() + "_1";
		}
		
		String result = "";
		ProductBuyCountVO pbcVO = getProductBuyCountVO(list, productVO.getTypeId());
		if(pbcVO == null) {
			ProductBuyCountVO vo = MessageFactory.getMessage(ProductBuyCountVO.class);
			vo.setTypeId(productVO.getTypeId());
			vo.setBuyCount(1);
			//vo.initProductBuyCountVO(0, productVO.getTypeId(), 1, productVO.getBuyCount());
			list.add(vo);
		} else {
			pbcVO.setBuyCount(pbcVO.getBuyCount() + 1);
		}
		
		for(ProductBuyCountVO vo : list) {
			//result = result + vo.getTypeId() + "_" + vo.getMaxBuyCount() + "_" + vo.getBuyCount() + ",";
			result = result + vo.getTypeId() + "_" + vo.getBuyCount() + ",";
		}
		
		return result.substring(0, result.length() - 1);
	}
	
	/**
	 * 查找 ProductBuyCountVO 对象
	 * @param list
	 * @param productTypeId
	 * @return
	 */
	private ProductBuyCountVO getProductBuyCountVO(List<ProductBuyCountVO> list, int productTypeId){
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(ProductBuyCountVO vo : list) {
			if(vo.getTypeId() == productTypeId) {
				return vo;
			}
		}
		return null;
	}
}
