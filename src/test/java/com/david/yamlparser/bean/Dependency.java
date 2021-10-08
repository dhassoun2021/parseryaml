package com.david.yamlparser.bean;

import java.util.Objects;

public class Dependency {

    private String args;
    private String browser;
    private String geo;
    private String shelf;
    private String shelf_web_socket;
    private String shelf_static;
    private String xml_rpc;
    private String google_maps;
    private String dart_to_js_script_rewriter;

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getShelf_web_socket() {
        return shelf_web_socket;
    }

    public void setShelf_web_socket(String shelf_web_socket) {
        this.shelf_web_socket = shelf_web_socket;
    }

    public String getShelf_static() {
        return shelf_static;
    }

    public void setShelf_static(String shelf_static) {
        this.shelf_static = shelf_static;
    }

    public String getXml_rpc() {
        return xml_rpc;
    }

    public void setXml_rpc(String xml_rpc) {
        this.xml_rpc = xml_rpc;
    }

    public String getGoogle_maps() {
        return google_maps;
    }

    public void setGoogle_maps(String google_maps) {
        this.google_maps = google_maps;
    }

    public String getDart_to_js_script_rewriter() {
        return dart_to_js_script_rewriter;
    }

    public void setDart_to_js_script_rewriter(String dart_to_js_script_rewriter) {
        this.dart_to_js_script_rewriter = dart_to_js_script_rewriter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dependency that = (Dependency) o;
        return args.equals(that.args) && browser.equals(that.browser) && geo.equals(that.geo) && shelf.equals(that.shelf) && shelf_web_socket.equals(that.shelf_web_socket) && shelf_static.equals(that.shelf_static) && xml_rpc.equals(that.xml_rpc) && google_maps.equals(that.google_maps) && dart_to_js_script_rewriter.equals(that.dart_to_js_script_rewriter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(args, browser, geo, shelf, shelf_web_socket, shelf_static, xml_rpc, google_maps, dart_to_js_script_rewriter);
    }
}
