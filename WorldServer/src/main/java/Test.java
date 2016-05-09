import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.ks.model.check.SimpleFightModel;
import com.ks.model.dungeon.Monster;
import com.ks.model.skill.SocialSkill;
import com.ks.model.soul.Soul;
import com.ks.util.DateUtil;


public class Test {
	public static volatile int  value = 1;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("USER_LEVEL_RANK".hashCode()%16);
	}

	
	/**
	 * 计算攻击伤害
	 * @param simpleFightModel
	 * @param monster
	 * @param crit
	 * @return
	 */
	private static int calculationDamage(SimpleFightModel simpleFightModel, Monster monster, boolean crit){
		
		int damge = 0;
        float percentage = 1;
        float atk = (float)simpleFightModel.getAttack();
        float def = (float)monster.getDef();
        int restraint = RestraintType(simpleFightModel.getSoul().getSoulEle(), monster.getEle());
        if (restraint == 1)
        {
            percentage = 1.5f;
        }
        else if (restraint == 2)
        {
            percentage = 0.5f;
        }
        damge = (int)((30*atk/def) * percentage);
        if (damge <= 0){
        	 damge = 0;
        }
           
        damge = damge + 20;
        
        if(crit){
        	damge = (int) (damge * 1.25);
        }
        
        return damge;
		
	}
	
	
	   /// <summary>
    /// 属性相克
    /// </summary>
    /// <param namelbl="type1"></param>
    /// <param namelbl="type2"></param>
    /// <returns>0=不相克 1=克制 2=被克</returns>
    public static int RestraintType(int type1, int type2)
    {
        switch (type1)
        {
            case 1:
                if (type2 == 2)
                {
                    return 1;
                }
                else if (type2 == 4)
                {
                    return 2;
                }
                break;
            case 2:
                if (type2 == 3)
                {
                    return 1;
                }
                else if (type2 == 1)
                {
                    return 2;
                }
                break;
            case 3:
                if (type2 == 4)
                {
                    return 1;
                }
                else if (type2 == 2)
                {
                    return 2;
                }
                break;
            case 4:
                if (type2 == 1)
                {
                    return 1;
                }
                else if (type2 == 3)
                {
                    return 2;
                }
                break;
            case 5:
                if (type2 == 6)
                {
                    return 1;
                }
                break;
            case 6:
                if (type2 == 5)
                {
                    return 1;
                }
                break;
        }
        return 0;
    } 
}
