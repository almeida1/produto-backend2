package com.fatec.sigvs.ti_model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sigvs.model.Catalogo;
import com.fatec.sigvs.model.Imagem;
import com.fatec.sigvs.model.Produto;
import com.fatec.sigvs.service.IImagemRepository;
import com.fatec.sigvs.service.IProdutoRepository;
import com.fatec.sigvs.service.ProdutoServico;

@SpringBootTest
class Req02ConsultarCatalogoTest {
	@Autowired
	ProdutoServico servico;
	@Autowired
	IImagemRepository imagemRepository;
	@Autowired
	IProdutoRepository repository;
	
	public void setup() {
		byte[] arquivo1 = null;
		repository.deleteAll();
		Produto produto1 = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", "maquina de lavar",
				"51.66", "12");
		repository.save(produto1);
		Path path = Paths.get("c:/temp/produto1.jpg");
		try {
			InputStream file = Files.newInputStream(path);
			arquivo1 = file.readAllBytes();
		} catch (Exception e) {
			System.out.println("erro no acesso ao arquivo");
		}
		Imagem imagem = new Imagem();
		imagem.setId(produto1.getId()); // associa o id do produto ao id da imagem
		imagem.setNome("produto1.jpg");
		imagem.setCaminho("imagens/" + imagem.getNome());
		imagem.setArquivo(arquivo1);

		imagemRepository.save(imagem);
	}

	@Test
	void ct01_quando_catalogo_tem_produto_e_imagem_cadastrada_retorna_maior_ou_igual_a_um() {
		setup();
		List<Catalogo> lista = servico.consultaCatalogo();
		for (Catalogo c : lista) {
            System.out.println("imagem -id => " + c.getId() + "-" + c.getQuantidadeNoEstoque());
        }
		assertTrue(lista.size()>= 1);
	}

}
