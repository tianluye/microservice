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
public class MyZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * 1、pre：路由之前
     * 2、route：路由之时
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
     * 按照升序进行执行过滤器的方法，若过滤器的 run方法抛出异常，那么后续的过滤器将不再执行
     * 否则将会按照顺序依次执行
     *
     * @return 过滤器的顺序
     */
    @Override
    public int filterOrder() {
        return 3;
    }

    /**
     * 这里可以写逻辑判断，是否要过滤
     * 若返回值为 false，此过滤器将不会生效
     *
     * 如果前一个过滤器的结果为true，则说明上一个过滤器成功了，需要进入当前的过滤，
     * 如果前一个过滤器的结果为false，则说明上一个过滤器没有成功，则无需进行下面的过滤动作了，直接跳过后面的所有过滤器并返回结果
     *
     * @return bool
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");
    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
     *
     * @return obj
     * @throws ZuulException e
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("这是过滤器优先顺序为 3...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            logger.warn("token is empty");
            // 令zuul过滤该请求，不对其进行路由
            ctx.setSendZuulResponse(false);
            // 设置了其返回的错误码
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write(" token is empty");
            }catch (Exception e){}

            return null;
        }
        logger.info("ok");
        return null;
    }
}
