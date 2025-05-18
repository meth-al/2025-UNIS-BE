package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        // URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션

        log.info("id=" + id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);

        /*
        * findById() 는 데이터베이스에 해당 id가 존재하지 않을 수도 있기 때문 아래 내용 사용
        *
        * 1. Optional<Article> articleEntity = articleRepository.findById(id);
        * null이 될 수도 있는 값을 안전하게 감싸는 Optional을 그대로 받는 것
        * 이후에는 isPresent(), get(), orElse() 등을 사용해 처리
        * 명시적으로 null 체크를 하게 되어 NPE 방지에 유리
        *
        * 2. Article articleEntity = articleRepository.findById(id).orElse(null);
        * Optional에서 값이 존재하면 꺼내고, 없으면 null을 반환
        * Optional의 철학(명시적 null 대체)에는 맞지 않음
        * */

        return "articles/show";
    }

    @GetMapping("/articles/index")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = (List<Article>)articleRepository.findAll();
        // Iterable<Article> articleEntityList = articleRepository.findAll()


        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit") //뷰 변수 {{}} vs. 컨트롤러 URL 변수 {}
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        // 1. DTO를 엔티티 변환
        Article articleEntity = form.toEntity();
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존데이터 갱신
        if(target != null) {
            articleRepository.save(articleEntity);
        }
        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, Model model, RedirectAttributes rttr) {
        log.info("삭제요청이 들어왔습니다!");
        // 1. 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString()); // 타겟 잘 들어왔는지 확인
        // 2. 대상 엔티티 삭제
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles/index";
    }
}

