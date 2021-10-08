package com.david.yamlparser.bean;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return name.equals(info.name) && version.equals(info.version) && description.equals(info.description) && dependencies.equals(info.dependencies) && dev_dependencies.equals(info.dev_dependencies) && transformers.equals(info.transformers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version, description, dependencies, dev_dependencies, transformers);
    }
}
