package com.mfh.crowd.funding.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mfh
 * @date 2019/12/14 23:02
 */
@Controller
public class PortalHandler {
    @RequestMapping("/index")
    public String showIndex() {
        return "index-page";
    }
}
