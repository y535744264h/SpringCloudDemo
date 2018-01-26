package com.yanhao.server1;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class test {
    @RequestMapping("sayHi")
    public String seaHi(HttpSession session){
        System.out.println(session.getAttribute("test")+"========");
        return "hi My is Server1";
    }
}
