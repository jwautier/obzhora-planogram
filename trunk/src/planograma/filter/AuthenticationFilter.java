package planograma.filter;

import planograma.constant.SessionConst;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.12
 * Time: 5:28
 * To change this template use File | Settings | File Templates.
 */
@WebFilter({"/servlet/*"})
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			if (((HttpServletRequest) request).getSession(true).getAttribute(SessionConst.SESSION_USER) != null) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	@Override
	public void destroy() {
	}
}
