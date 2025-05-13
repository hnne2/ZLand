package com.gr.zland.сontroller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebAppController {
    @GetMapping("/webapp")
    fun serveWebApp(): String {
        return "webapp/index"
    }
}
