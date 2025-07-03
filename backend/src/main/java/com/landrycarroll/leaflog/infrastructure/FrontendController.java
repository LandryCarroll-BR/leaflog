package com.landrycarroll.leaflog.infrastructure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping(value = {"/{path:^(?!api|static|favicon\\.ico|index\\.html).*}", "/"})
    public String redirect() {
        return "forward:/index.html";
    }
}