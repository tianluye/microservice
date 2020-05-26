package com.spring.cloud.feign.tly;

import java.util.Arrays;
import java.util.Objects;
import org.springframework.cloud.context.named.NamedContextFactory.Specification;

/**
 * @author tian.luye
 */
public class ProxyClientSpecification implements Specification {

    private String name;
    private Class<?>[] configuration;

    ProxyClientSpecification() {
    }

    ProxyClientSpecification(String name, Class<?>[] configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Class<?>[] getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Class<?>[] configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ProxyClientSpecification that = (ProxyClientSpecification)o;
            return Objects.equals(this.name, that.name) && Arrays.equals(this.configuration, that.configuration);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.configuration});
    }

    @Override
    public String toString() {
        return "ProxyClientSpecification{" + "name='" + this.name + "', " + "configuration=" + Arrays.toString(this.configuration) + "}";
    }

}
