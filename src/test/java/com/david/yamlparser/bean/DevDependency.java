package com.david.yamlparser.bean;

import java.util.Objects;

public class DevDependency {

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevDependency that = (DevDependency) o;
        return test.equals(that.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(test);
    }
}
