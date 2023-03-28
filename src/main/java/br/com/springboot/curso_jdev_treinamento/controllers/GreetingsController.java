package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	@Autowired /*ID/CD ou CDI - Injeção de dependecia*/
	private UsuarioRepository usuarioRepository;
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    //@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@GetMapping("/mostrarnome/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring boot api:  " + name + "!";
    }
	
	@GetMapping("/olamundo/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {
		
		Usuario usuario= new Usuario();
		usuario.setNome(nome);
		usuarioRepository.save(usuario);
		
		return "Ola mundo "+ nome;
	}
	
	@GetMapping("listatodos")/*primeiro metodo de API*/
	@ResponseBody /*retorna os dados para o corpo da respota*/
	public ResponseEntity<List<Usuario>> listaUsuario(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK);/* retorna a lista em JSON*/
	}
	
	@PostMapping("salvar")
	@ResponseBody /*Descrição da resposta*/ 
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ /*Recebe os dados apra salvar*/
		Usuario user = usuarioRepository.save(usuario);
		return new  ResponseEntity<Usuario>(user,HttpStatus.CREATED);
	}
	
	@DeleteMapping("delete")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam Long id) {
		usuarioRepository.deleteById(id);
		
		return new ResponseEntity<String>("User deletado com sucesso",HttpStatus.OK);
	}
	
	@PutMapping("atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("id não foi informado",HttpStatus.OK);
		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		
		return new ResponseEntity<Usuario>(user,HttpStatus.OK);
	}
	
	@GetMapping("buscaruserid")
	@ResponseBody 
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name="id") Long id){
		Usuario usuario = usuarioRepository.findById(id).get();
		return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);/* retorna a lista em JSON*/
	}
	
	@GetMapping("buscarPorNome")
	@ResponseBody 
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name= "name") String name){
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario,HttpStatus.OK);/* retorna a lista em JSON*/
	}
}
