package com.mysite.sbb.contorller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysite.sbb.domain.Answer;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.domain.UserEntity;
import com.mysite.sbb.service.AnswerService;
import com.mysite.sbb.service.QuestionService;
import com.mysite.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question") 
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    // 질문 목록 조회
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    // 질문 상세 조회
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, Answer answer, 
                        @RequestParam(value = "answerId", required = false) Integer answerId,
                        @RequestParam(value = "error", required = false) String error) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        
        // answerId가 있으면 해당 답변을 answerForm으로 설정
        if (answerId != null) {
            Answer answerForm = this.answerService.getAnswer(answerId);
            model.addAttribute("answerForm", answerForm);
            
            // error 파라미터가 있으면 유효성 검사 오류 상태로 설정
            if ("true".equals(error)) {
                model.addAttribute("hasErrors", true);
            }
        }
        
        return "question_detail";
    }

    // 질문 등록 폼
    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Question question) {
        return "question_form";
    }

    // 질문 등록 처리
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@Valid Question question, BindingResult bindingResult, Principal principal) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        UserEntity author = this.userService.getUser(principal.getName());
        this.questionService.create(question.getSubject(), question.getContent(), author);
        return "redirect:/";
    }

    // 질문 수정
    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(Model model, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        model.addAttribute("question", question);
        return "question_form";
    }

    // 질문 수정 처리
    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@Valid Question questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        
        Question question = this.questionService.getQuestion(id); // 기존 질문 조회
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    // 질문 삭제
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    // 질문 추천
    @GetMapping("/vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String vote(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Question question = this.questionService.getQuestion(id);
        UserEntity user = this.userService.getUser(principal.getName());
        
        // 자기 자신의 질문은 추천할 수 없음
        if (question.getAuthor().equals(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "본인이 작성한 질문은 추천할 수 없습니다.");
            return String.format("redirect:/question/detail/%s", id);
        }
        
        this.questionService.vote(question, user);
        return String.format("redirect:/question/detail/%s", id);
    }
}
