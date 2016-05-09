package com.ks.logic.test.service;

import org.junit.Test;
import com.ks.logic.service.UserService;
import com.ks.logic.test.BaseTestCase;

public class InitRankCase extends BaseTestCase {
	
	
	private UserService serive= getService(UserService.class);

	@Test
	public void testChapterUp() {

	}
	
	
	/**
	 * 复制到一个serviece，增加写功能呢
	 */
//	public void test() {
//		List<User> findAllUser = userDAO.findAllUser();
//		for (User user : findAllUser) {
//			if(user.getLevel() > 5){
//				int dungeonId = userChapterDAO.queryUserChapterLimit(user.getUserId(), Chapter.CHAPTER_ID_副本_START);
//				if(dungeonId > 0){
//					userDAO.updateChapterRank(user.getUserId(), dungeonId);
//					
//					UserCap userCap = getUserCap(user.getUserId());
//					if(userCap == null){
//						continue;
//					}
//					userCap.setCurrChapterId(dungeonId);
//					userTeamDAO.updateUserCapCache(userCap);
//				}
//			}
//		}
//	}
	
}
