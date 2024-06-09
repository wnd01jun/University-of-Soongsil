package network.freeTopic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import network.freeTopic.consts.SessionConst;
import network.freeTopic.domain.Member;
import network.freeTopic.form.LoginForm;
import network.freeTopic.form.RegisterForm;
import network.freeTopic.security.Login;
import network.freeTopic.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@Login Member member,
                        @ModelAttribute("loginForm") LoginForm form ,Model model){
        if(member != null){
            return "/home";
        }
        return "/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") @Validated LoginForm form
            , BindingResult bindingResult
            , HttpServletRequest request){
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(bindingResult.hasErrors()){
            return "/loginForm";
        }
        if(loginMember == null){
            bindingResult.reject("password", null, null);
            return "/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";

    }

    @GetMapping("/register")
    public String register(@ModelAttribute("registerForm") RegisterForm form, Model model){
        return "/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("registerForm") @Validated RegisterForm form
            , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/register";
        }
        Member member = loginService.register(form);
        if(member == null){
            bindingResult.reject("duplicate");
            return "/register";
        }
        return "redirect:/";
    }
}
