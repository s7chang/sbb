package com.mysite.sbb.contorller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.domain.Answer;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.service.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question") 
public class QuestionController {

    private final QuestionService questionService;

    // 질문 목록 조회
    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();

        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    // 질문 상세 조회
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, Answer answer) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 질문 등록 폼
    @GetMapping("/create")
    public String questionCreate(Question question) {
        return "question_form";
    }

    // 질문 등록 처리
    @PostMapping("/create")
    public String questionCreate(@Valid Question question, BindingResult bindingResult) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(question.getSubject(), question.getContent());
        return "redirect:/question/list";
    }
}
