package com.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

@Component
public class MyOneZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * 1、pre：路由之前
     * 2、routing：路由之时
     * 3、post： 路由之后
     * 4、error：发送错误调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的顺序
     *
     * @return 过滤器的顺序
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 这里可以写逻辑判断，是否要过滤
     * 若返回值为 false，此过滤器将不会生效
     *
     * @return bool
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
     *
     * @return obj
     * @throws ZuulException e
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("这是过滤器优先顺序为 1...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("session");
        if(accessToken == null) {
            logger.warn("session is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("session is empty");
            }catch (Exception e){}

            return null;
        }
        // 设值，让下一个Filter看到上一个Filter的状态
        ctx.set("isSuccess", true);
        logger.info("ok");
        return null;
    }
}
