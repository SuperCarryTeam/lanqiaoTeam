package org.lanqiao.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@WebFilter(filterName = "WordFilter",urlPatterns = "/*")
public class WordFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final HttpServletRequest req = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if("getParameter".equals(method.getName())){
                    String method1 = request.getMethod();

                    if("get".equalsIgnoreCase(method1)){
                        String value = (String) method.invoke(request, args);//request.getParameter()
                        value = new String(value.getBytes("iso-8859-1"),"utf-8");
                        return value;
                    }else if("post".equalsIgnoreCase(method1)){
                        request.setCharacterEncoding("utf-8");
                    }
                }
                return method.invoke(request,args);
            }
        });

        filterChain.doFilter(req,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
