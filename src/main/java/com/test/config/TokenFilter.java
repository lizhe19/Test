//package com.test.config;
//
//import cn.hutool.http.HttpStatus;
//import com.alibaba.fastjson.JSON;
//import com.dahuatech.videoservices.bean.BaseResponse;
//import com.dahuatech.videoservices.common.ResponseEnm;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//
//@Component
//public class TokenFilter implements Filter {
//
//    @Value("${service.token.header}")
//    public String TOKEN_HEADER;
//
//    @Value("${service.token.value}")
//    public String TOKEN_VALUE;
//
//    @Value("${service.token.enable}")
//    public boolean enable;
//
//    //销毁时
//    @Override
//    public void destroy() {
//    }
//
//    //逻辑代码
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        if(enable){//启用服务鉴权
//            HttpServletRequest req = (HttpServletRequest) request;
//            HttpServletResponse rep = (HttpServletResponse) response;
//            String token = req.getHeader(TOKEN_HEADER);
//            if(StringUtils.isBlank(token) || !StringUtils.equals(token,TOKEN_VALUE)){ //鉴权缺失或错误
//                BaseResponse<String> resultBean = new BaseResponse<String>(ResponseEnm.Foridden.getResult());
//                resultBean.setSuccess(false);
//                resultBean.setResult("缺少token信息或鉴权信息错误，鉴权失败");
//                rep.setHeader("Content-Type", "application/json;charset=UTF-8");
//                rep.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
//
//                //鉴权失败
//                PrintWriter writer = null;
//                OutputStreamWriter osw = null;
//                try {
//                    osw = new OutputStreamWriter(response.getOutputStream(),
//                            "UTF-8");
//                    writer = new PrintWriter(osw, true);
//                    String jsonStr = JSON.toJSONString(resultBean);
//                    writer.write(jsonStr);
//                    writer.flush();
//                    writer.close();
//                    osw.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (null != writer) {
//                        writer.close();
//                    }
//                    if (null != osw) {
//                        osw.close();
//                    }
//                }
//                return;
//
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    //加载前
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//    }
//
//}