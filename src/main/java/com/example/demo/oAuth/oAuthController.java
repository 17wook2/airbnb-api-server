package com.example.demo.oAuth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class oAuthController {

    @GetMapping()
    public ModelAndView socialLogin(ModelAndView mav){
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/{provider}")
    public ModelAndView redirectPage(ModelAndView mav , @RequestParam String code, @PathVariable String provider){
        mav.addObject("code",code);
        mav.setViewName("redirect");
        return mav;
    }
}