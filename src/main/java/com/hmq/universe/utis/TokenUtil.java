package com.hmq.universe.utis;

import com.hmq.universe.model.vo.ITokenVO;
import com.hmq.universe.model.vo.TokenVO;

public class TokenUtil {
	
	public static ITokenVO<String> getTokenVO(){
		return new TokenVO();
	}
}
