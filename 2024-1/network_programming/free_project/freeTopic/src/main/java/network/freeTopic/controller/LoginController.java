package network.freeTopic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/")
    public String index(@Login Member member, Model model){
        log.info("in controller Member : {}", member);
        if(member == null){
            return "/main";
        }
        model.addAttribute("member", member);
        return "/loginMain";
    }

    @PostMapping("/")
    public String index2(@Login Member member, Model model){
        log.info("test");
        return "/";
    }

    @GetMapping("/login")
    public String login(@Login Member member,
                        @ModelAttribute("loginForm") LoginForm loginForm ,Model model){
        if(member != null){
            return "/loginMain";
        }
        log.info("pass");
        return "/member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") @Validated LoginForm loginForm
            , BindingResult bindingResult
            , HttpServletRequest request){
        log.info("pass");
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult.getAllErrors());
            return "/member/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("loginMember = {}", loginMember);

        if(loginMember == null || loginMember.getStudentId() == null){

            bindingResult.reject("password", null, null);
            log.info("errors = {}", bindingResult.getAllErrors());
            return "/member/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";

    }

    @GetMapping("/sign-up")
    public String register(@ModelAttribute("form") RegisterForm form, Model model){
        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerPost(@ModelAttribute("form") @Validated RegisterForm form
            , BindingResult bindingResult){
        log.info("errors = {}", bindingResult.getAllErrors());
        if(bindingResult.hasErrors()){
            return "/member/sign-up";
        }
        Member member = loginService.register(form);
        if(member == null){
            bindingResult.reject("duplicate");
            return "/member/sign-up";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
        session.invalidate();
        }
        return "/main";

    }
}
