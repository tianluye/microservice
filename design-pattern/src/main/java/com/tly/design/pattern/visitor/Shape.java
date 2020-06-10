package com.tly.design.pattern.visitor;

public interface Shape {

    /**
     * 被访问者向外（访问者）提供访问的接口
     *
     * @param visitor 访问者
     */
    void visitor(IVisitor visitor);

}
