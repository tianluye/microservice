package com.tly.design.pattern.visitor;

import com.tly.design.pattern.visitor.master.Circle;
import com.tly.design.pattern.visitor.master.Rectangle;

/**
 * @author tianluye
 *
 * 定义抽象访问者
 */
public interface IVisitor {

    /**
     * 访问长方形
     *
     * @param rectangle 被访问者
     */
    void visitor(Rectangle rectangle);

    /**
     * 访问圆
     *
     * @param circle 被访问者
     */
    void visitor(Circle circle);

}
