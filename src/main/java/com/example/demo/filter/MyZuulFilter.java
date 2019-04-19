package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人:连磊
 * 日期: 2019/4/18. 14:20
 * 描述：
 */
@Component
@Configuration
public class MyZuulFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyZuulFilter.class);

    @Autowired
    private ZuulProperties zuulProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {


        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        Object o = currentContext.get(FilterConstants.REQUEST_URI_KEY);
        String method = request.getMethod();
        System.out.println(method);
        String requestURI = request.getRequestURI();
        if ("user".equals(requestURI.split("/")[1])){
            return null;
        }
        /*String[] split = requestURI.split("/");
        String collect = Arrays.stream(split).skip(2).collect(Collectors.toList()).stream().map(s -> s.toString()).collect(Collectors.joining("/"));
        System.out.println(o);
        try {
            URI uri = new URI("/"+collect);
            currentContext.put(FilterConstants.REQUEST_URI_KEY, uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(requestURI);*/

        request.getParameterMap();
        Map<String, List<String>> requestQueryParams = currentContext.getRequestQueryParams();
        if (null == requestQueryParams){
            requestQueryParams = new HashMap<>();
        }
        List<String> paramList = new ArrayList<>();
        paramList.add("sdfa");
        requestQueryParams.put("userToken",paramList);
        currentContext.setRequestQueryParams(requestQueryParams);

        return null;
    }

    public  String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
