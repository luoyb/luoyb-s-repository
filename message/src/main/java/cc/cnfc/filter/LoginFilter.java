package cc.cnfc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;

public class LoginFilter implements Filter {

	private String[] includePrefix; // 需要验证的URL地址前缀

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String path = filterConfig.getInitParameter("includePrefix");
		if (StringUtils.isNotBlank(path)) {
			includePrefix = path.split(",");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String url = req.getServletPath();
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute(Const.LOGIN_CUSTOMER);

		if (customer == null) { // 转向登录页面
			for (String includePath : includePrefix) {
				if (url.startsWith(includePath)) {
					resp.sendRedirect(this.getProjectUrl(req));
					return;
				}
			}
			chain.doFilter(req, resp);
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public String getProjectUrl(HttpServletRequest request) {
		StringBuilder projectUrl = new StringBuilder(request.getScheme());
		projectUrl.append("://").append(request.getServerName());
		int port = request.getServerPort();
		if (port != 80) {
			projectUrl.append(":").append(port);
		}
		projectUrl.append(request.getContextPath());
		return projectUrl.toString();
	}

}
