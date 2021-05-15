package com.test.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 无法使用@Order来控制过滤器的先后顺序
 * 1、可以通过类名，但要限制类名
 * 2、FilterRegistrationBean类的来设置
 */
@Slf4j
public class TokenFilter1 implements Filter {

    /**
     * 白名单的集合
     */
    private Set<String> excludePaths = new HashSet<String>();

    /**
     * 校验token的有效性的url
     */
    private String videoServerIp;

    private String tokenIpPort ;

    public static Boolean dogResult = null;

    public TokenFilter1(String videoServerIp) {
        this.videoServerIp = videoServerIp;
        this.tokenIpPort = "http://" + videoServerIp + ":8294";
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        log.info("过滤器初始化:{}", config);
        String checkToken = config.getInitParameter("checkTokenIpPort").trim();
        if (StringUtils.isNotEmpty(tokenIpPort)) {
            tokenIpPort = checkToken;
        }
        String excludeAction = config.getInitParameter("excludeAction").trim();
        Optional.ofNullable(excludeAction).ifPresent(actions -> {
            String[] paths = actions.split(";");
            for (String path : paths) {
                if (StringUtils.isNotEmpty(path)) {
                    excludePaths.add(path);
                }
            }
        });
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getServletPath().toLowerCase();
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type,X-Requested-With");
        response.setHeader("Access-Control-Max-Age", "3600");
        dogEncryptDog(response);
        // 白名单直接放行(下载文件直接放行)
        for (String excludePath : excludePaths) {
			if (uri.trim().startsWith(excludePath)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
        if ( uri.contains("swagger-") || uri.contains("/v2/api-docs") || uri.contains("download")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (StringUtils.isEmpty(uri)) {
            return;
        }
        //token不可以为空
        String token = request.getHeader("X-Subject-Token");
        if (StringUtils.isBlank(token)) {
            // TODO 设置返回格式
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 409);
            map.put("message", "token is null");
            //HttpResponseUtils.writeJson(response, 409, map);
            return;
        }

        // 校验token的有效性
//        boolean tokenValidate = TokenUtil.checkToken(token, tokenIpPort);
        boolean tokenValidate = true;

        if (tokenValidate) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // TODO 设置返回格式
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 409);
            map.put("message", "token is inValidate");
            //HttpResponseUtils.writeJson(response, 409, map);
            return;
        }

    }

    @Override
    public void destroy() {

    }

    public void dogEncryptDog(HttpServletResponse response) {
        //每次请求都进行加密狗校验
       /* BaseResponse baseResponse = null;
        RestRequest restRequest = new RestRequest.Builder().method("GET")
            .action("/encryptDogService/dogInfo/function")
            .content(null)
            .build();
        try {
            baseResponse = JerseyClientUtil.call("http://"  + videoServerIp +":8314", restRequest, null);
            if (null != baseResponse) {
                if (baseResponse.getStatusCode() == 506 || baseResponse.getStatusCode() == 507) {
                    dogReturn(response);
                }
                //返回成功
                if (baseResponse.getSuccess()) {
                    if (!parseFunction(baseResponse.getResult())) {
                        //返回失败
                        dogReturn(response);
                    }
                } else {
                    //返回失败
                    dogReturn(response);
                }
            } else {
                //返回失败
                dogReturn(response);
            }
        } catch (Exception e) {
            //返回失败
            dogReturn(response);
        }*/
    }

    /**
     * 加密狗失败返回
     *
     * @param response
     */
    public void dogReturn(HttpServletResponse response) {
        dogResult = false;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 507);
        map.put("message", "encryptDog is not found");
//        HttpResponseUtils.writeJson(response, 507, map);
        return;
    }

    /**
     * 解析加密狗，查看是否包含有main服务
     *
     * @return
     */
    public boolean parseFunction(String dogResult) {
        boolean result = false;
        JSONArray jsonArray = JSONObject.parseArray(dogResult);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                //判断有机场子服务
                if ("subSys_airportTerminal".equals(jsonArray.getJSONObject(i).get("type")) && Boolean.valueOf(jsonArray.getJSONObject(i).get("flag") + "") == true) {
                    result = true;
                }
            }
        }
        return result;
    }

}
