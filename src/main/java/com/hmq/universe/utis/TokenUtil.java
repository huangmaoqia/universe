package com.hmq.universe.utis;

import com.hmq.universe.model.ITokenVO;
import com.hmq.universe.model.TokenVO;

public class TokenUtil {
	
	public static ITokenVO<String> getTokenVO(){
		return new TokenVO();
	}
}
