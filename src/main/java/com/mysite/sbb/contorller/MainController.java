package com.mysite.sbb.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	// 디폴트 경로
	@GetMapping("/")
    public String root() {
        return "redirect:/question/list";  // 홈 화면으로 이동
    }

	@GetMapping("/sbb")
	@ResponseBody
	public String index() {
		return "안녕하세요 sbb에 오신 것을 환영합니다.";
	}

}
