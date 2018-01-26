package cc.cnfc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.cnfc.dto.LoginReqDto;

@Controller
@RequestMapping(value = "/main")
public class MainController {

	/**
	 * 跳转到登陆页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toLogin.do")
	public String toLogin() {
		return "login";
	}

	/**
	 * 登陆验证
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public ModelAndView login(LoginReqDto reqDto) {
		ModelAndView mv = new ModelAndView();
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(reqDto.getUsername(), reqDto.getPassword());
			subject.login(token);
		} catch (Exception e) {
			mv.addObject("errorMsg", "账户或密码不对");
			mv.setViewName("login");
			return mv;
		}
		mv.setViewName("redirect:/main/toMain.do");
		return mv;
	}

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toMain.do")
	public String toMain() {
		return "wrap_index";
	}

}
