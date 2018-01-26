package br.com.consigaz.caminhao.testes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.consigaz.caminhao_sharedlib.modelo.*;
import utilitarios.restservice.CriaAtributosEstaticos;

public class Teste {

	public static void main(String[] args) {

		List<Integer> listaComFranquiaId = new ArrayList<Integer>();
		listaComFranquiaId.add(476);
		listaComFranquiaId.add(477);
		listaComFranquiaId.add(479);
		listaComFranquiaId.add(480);
		listaComFranquiaId.add(481);
		listaComFranquiaId.add(483);
		listaComFranquiaId.add(487);
		listaComFranquiaId.add(489);
		listaComFranquiaId.add(495);
		listaComFranquiaId.add(496);
		listaComFranquiaId.add(501);
		listaComFranquiaId.add(502);
		listaComFranquiaId.add(504);
		listaComFranquiaId.add(505);
		listaComFranquiaId.add(507);
		listaComFranquiaId.add(508);
		listaComFranquiaId.add(511);
		listaComFranquiaId.add(512);
		listaComFranquiaId.add(515);
		listaComFranquiaId.add(516);
		listaComFranquiaId.add(519);
		listaComFranquiaId.add(520);
		listaComFranquiaId.add(521);
		listaComFranquiaId.add(523);
		listaComFranquiaId.add(534);
		listaComFranquiaId.add(536);
		listaComFranquiaId.add(538);
		listaComFranquiaId.add(542);
		listaComFranquiaId.add(543);
		listaComFranquiaId.add(546);

		String caminho = "c:/temp/tabelas.txt";

		File file = new File(caminho);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			int autoIncrement = 1;

			for (int franquiaId : listaComFranquiaId) {

				int avaliacaoId = 1;
				while (avaliacaoId < 37) {

					String texto = "update vistoria_franquia.franquia_avaliacao set id=" + autoIncrement
							+ " where avaliacao_id =" + avaliacaoId + " and franquia_id =" + franquiaId + "; \n";

					writer.write(texto);

					avaliacaoId++;
					autoIncrement++;
				}
			}

			writer.flush();
			writer.close();

			System.out.println("Gerou no caminho: " + caminho);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
