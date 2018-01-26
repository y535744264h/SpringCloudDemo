package com.yanhao.server2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class test {
    @RequestMapping("sayHi")
    public String seaHi(HttpSession session){
        session.setAttribute("test","看看Session是否是共共享的");
        return "hi My is Server2";
    }
}