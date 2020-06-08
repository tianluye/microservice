package com.tly.design.pattern.visitor;

import com.tly.design.pattern.visitor.master.Circle;
import com.tly.design.pattern.visitor.master.Rectangle;

/**
 * @author tianluye
 *
 * 定义访问者
 */
public interface IVisitor {

    /**
     * 计算面积
     *
     * @param rectangle 被访问者
     */
    void visitor(Rectangle rectangle);

    /**
     * 计算圆形面积
     *
     * @param circle 被访问者
     */
    void visitor(Circle circle);

}
