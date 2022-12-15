package org.whatisme.studentqa;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.Starter.TomcatStarter;

@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {
        new TomcatStarter().run();
    }
}
