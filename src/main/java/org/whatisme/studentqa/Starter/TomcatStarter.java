package org.whatisme.studentqa.Starter;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class TomcatStarter {
    public void run() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        Context ctx = tomcat.addWebapp("", System.getProperty("user.dir") + "/src/main/webapp");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", System.getProperty("user.dir") + "/target/classes", "/"));
        ctx.setResources(resources);
        //
        tomcat.start();
        tomcat.getServer().await();
    }
}
