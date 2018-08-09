package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建人:连磊
 * 日期: 2018/7/25. 11:09
 * 描述：过滤器
 */
@Component
public class LoginAccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(LoginAccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return getRequest();
    }

    public boolean getRequest(){
        RequestContext context = RequestContext.getCurrentContext();
        boolean result = false;
        HttpServletRequest request = context.getRequest();
        if (request.getRequestURI().equals("/oauth/login")) {
            String grantType = request.getParameter("result_type");
            if (grantType != null && !grantType.equals("result_data")) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        String[] split = request.getRequestURI().split("/");
        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            log.warn("access token is empty");
            /*ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);*/
            return "{'message':'当前用户登录错误','code':'401'}";
        }
        log.info("access token ok");
        return null;
    }
}
