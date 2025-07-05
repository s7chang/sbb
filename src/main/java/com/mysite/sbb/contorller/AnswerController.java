package com.mysite.sbb.contorller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    // 답변 등록
    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model, @PathVariable("id") Integer id, @Valid Answer answerForm, BindingResult bindingResult, Principal principal) {
        
        Question question = this.questionService.getQuestion(id);
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        UserEntity author = this.userService.getUser(principal.getName());

        Answer answer = this.answerService.create(question, answerForm.getContent(), author);
        return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId());
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(Model model, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        Question question = answer.getQuestion();
        model.addAttribute("question", question);
        model.addAttribute("answerForm", answer);
        return "question_detail";
    }

    // 답변 수정
    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(Model model, @Valid Answer answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        
        Answer answer = this.answerService.getAnswer(id);
        Question question = answer.getQuestion();

        // 유효성 검사
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            // 유효성 검사 오류 시에도 수정 모드 유지를 위해 ID 설정
            answerForm.setId(id);
            model.addAttribute("answerForm", answerForm);
            // BindingResult를 모델에 추가
            model.addAttribute("org.springframework.validation.BindingResult.answerForm", bindingResult);
            return "question_detail";
        }
        
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s", question.getId(), id);
    }

    // 질문 삭제
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    // 답변 추천
    @GetMapping("/vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String vote(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Answer answer = this.answerService.getAnswer(id);
        UserEntity user = this.userService.getUser(principal.getName());
        
        // 자기 자신의 답변은 추천할 수 없음
        if (answer.getAuthor().equals(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "본인이 작성한 답변은 추천할 수 없습니다.");
            return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), id);
        }
        
        this.answerService.vote(answer, user);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), id);
    }
}
