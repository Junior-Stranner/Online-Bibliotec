package com.br.biblioteca.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.biblioteca.Model.Usuario;
import com.br.biblioteca.Repository.UsuarioRepository;

@Controller
public class UsuarioController {

    List<Usuario> usuarios = new ArrayList<>();
    ArrayList<Usuario> usuCadastrados = new ArrayList<>();

    @Autowired
    UsuarioRepository repository;

    @GetMapping("/cadastroUsuario")
    public String cadastroUsuario() {
        return "cadastroUsuario";
    }

    @PostMapping("/cadastroUsuario")
    public String cadastro(Usuario usuario) {
        repository.save(usuario);
        return "redirect:/loginUsuario";
    }

    @GetMapping("/loginUsuario")
    public String loginUsuario() {
        return "loginUsuario";
    }

    @PostMapping("/verificaLogin")
    public String verificar(Usuario usuario) {
        usuarios = (ArrayList<Usuario>) repository.findAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getEmail().equalsIgnoreCase(usuario.getEmail())
                    && usuario1.getSenha().equalsIgnoreCase(usuario.getSenha())) {
                return "redirect:/listaLivro";
            }
        }
        return "redirect:/loginUsuario";
    }

    @GetMapping("/listaUsuario")
    public ModelAndView listaUsuario() {
        ModelAndView mv = new ModelAndView("listaUsuario");
        ArrayList<Usuario> usuarios = (ArrayList<Usuario>) repository.findAll();
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    @GetMapping("/excluirUsuario/{id}")
    public String excluir(@PathVariable("id") int id) {
        repository.deleteById(id);
        return "redirect:/listaUsuario";
    }

    @GetMapping("/editarUsuario/{id}")
    public ModelAndView editar(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("cadastroUsuario");
        Usuario usuario = repository.findById(id).get();
        mv.addObject("usuario", usuario);
        return mv;

    }
}