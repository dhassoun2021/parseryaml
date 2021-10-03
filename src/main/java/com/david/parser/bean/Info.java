package com.david.parser.bean;

import java.util.List;

public class Info {

    private String name;
    private String version;
    private String description;
    private Dependency dependencies;
    private DevDependency dev_dependencies;
   private List<String> transformers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Dependency getDependencies() {
        return dependencies;
    }

    public void setDependencies(Dependency dependencies) {
        this.dependencies = dependencies;
    }

    public DevDependency getDev_dependencies() {
        return dev_dependencies;
    }

    public void setDev_dependencies(DevDependency dev_dependencies) {
        this.dev_dependencies = dev_dependencies;
    }

    public List<String> getTransformers() {
        return transformers;
    }

    public void setTransformers(List<String> transformers) {
        this.transformers = transformers;
    }
}
