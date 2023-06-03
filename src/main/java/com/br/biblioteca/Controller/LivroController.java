package com.br.biblioteca.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.biblioteca.Model.Livro;
import com.br.biblioteca.Repository.LivroRepository;

@Controller
public class LivroController {

    List<Livro> livros = new ArrayList<>();

    @Autowired
    LivroRepository repository;

    @GetMapping("/homeLivro")
    public String homeLivro() {
        return "homeLivro";
    }

    @PostMapping("/homeLivro")
    public String cadastroLivro(Livro livro) {
        repository.save(livro);
        return "redirect:/listaLivro";
    }

    @GetMapping("/listaLivro")
    public ModelAndView listaLivro() {
        ModelAndView mv = new ModelAndView("listaLivro");
        ArrayList<Livro> livros = new ArrayList<>();
        livros = (ArrayList<Livro>) repository.findAll();
        mv.addObject("livros", livros);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable("id") int id) {
        repository.deleteById(id);
        return "redirect:/listaLivro";
    }

    @GetMapping("/alugar/{id}")
    public String alugarLivro(@PathVariable("id") int id) {
        Livro livro = repository.findById(id).get();
        livro.setStatus(true);
        repository.save(livro);
        return "redirect:/listaLivro";
    }

    @GetMapping("/devolver/{id}")
    public String devolverLivro(@PathVariable("id") int id) {
        Livro livro = repository.findById(id).get();
        livro.setStatus(false);
        repository.save(livro);
        return "redirect:/listaLivro";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLivro(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("homeLivro");
       Livro livro = repository.findById(id).get();
        mv.addObject("livro", livro);
        return mv;
    }

}