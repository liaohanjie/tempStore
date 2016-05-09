package com.ks.logic.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserTeamService;
import com.ks.logic.test.BaseTestCase;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.user.UserTeamVO;

/**
 * 
 * @author ks
 */
public class UserTeamTestCase extends BaseTestCase {
	
	private static final UserTeamService service = ServiceFactory.getService(UserTeamService.class);
	
	@Test
	public void testUpdateUserTeam(){
		List<UserTeamVO> teams = new ArrayList<>();
		UserTeamVO vo = MessageFactory.getMessage(UserTeamVO.class);
		vo.setCap((byte)1);
		vo.setTeamId((byte)1);
		vo.setPos(new ArrayList<Long>());
		vo.getPos().add(491l);
		vo.getPos().add(492l);
		vo.getPos().add(493l);
		vo.getPos().add(0L);
		vo.getPos().add(0L);
		teams.add(vo);
	
	}
}
