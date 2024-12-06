package Sintatico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants {

	public Semantico(File arquivoFonte) {
		this.arquivoFonte = arquivoFonte;
	}

	String operador_relacional = "";
	String codigo_objeto = "";
	Stack<String> pilha_tipos = new Stack<String>();
	Stack<String> pilha_rotulos = new Stack<String>();
	List<String> lista_identificadores = new ArrayList<String>();
	Map<String, Simbolo> tabela_simbolos = new HashMap<String, Simbolo>();
	private File arquivoFonte;
	private int contadorRotulos = 0;

	public void setArquivoFonte(File arquivoFonte) {
		this.arquivoFonte = arquivoFonte;
	}

	public void executeAction(int action, Token token) throws SemanticError {
		System.out.println("Ação #" + action + ", Token: " + token); // dps podemos excluir, usando só para conferir...
		// a ordem está bagunçada mas deixa assim, pq essa é a ordem que esta no arquivo
		// de explicação
		// da professora, então fica mais facil para ver se está certo
		switch (action) {
		case 100:
			acao_semantica100();
			break;
		case 101:
			acao_semantica101();
			break;
		case 128:
			acao_semantica128(token);
			break;
		case 129:
			acao_semantica129(token);
			break;
		case 130:
			acao_semantica130(token);
			break;
		case 118:
			acao_semantica118(token);
			break;
		case 119:
			acao_semantica119(token);
			break;
		case 131:
			acao_semantica131();
			break;
		case 123:
			acao_semantica123();
			break;
		case 124:
			acao_semantica124();
			break;
		case 125:
			acao_semantica125();
			break;
		case 126:
			acao_semantica126();
			break;
		case 121:
			acao_semantica121(token);
			break;
		case 122:
			acao_semantica122();
			break;
		case 120:
			acao_semantica120();
			break;
		case 116:
			acao_semantica116();
			break;
		case 117:
			acao_semantica117();
			break;
		case 127:
			acao_semantica127(token);
			break;
		case 108:
			acao_semantica108();
			break;
		case 107:
			acao_semantica107();
			break;
		case 104:
			acao_semantica104(token);
			break;
		case 102:
			acao_semantica102(token);
			break;
		case 103:
			acao_semantica103(token);
			break;
		case 106:
			acao_semantica106(token);
			break;
		case 105:
			acao_semantica105(token);
			break;
		case 109:
			acao_semantica109();
			break;
		case 110:
			acao_semantica110();
			break;
		case 111:
			acao_semantica111();
			break;
		case 112:
			acao_semantica112();
			break;
		case 113:
			acao_semantica113();
			break;
		case 114:
			acao_semantica114();
			break;
		case 115:
			acao_semantica115();
			break;
		default:
			throw new IllegalArgumentException("Ação não implementada");
		}
	}

	public void acao_semantica100() {
		codigo_objeto += ".assembly extern mscorlib {} \n" + ".assembly _codigo_objeto{} \n"
				+ ".module _codigo_objeto.exe \n" + ".class public UNICA{ \n"
				+ ".method static public void _principal() { \n" + ".entrypoint \n";
	}

	public void acao_semantica101() {
		codigo_objeto += "ret \n " + "} \n" + "}";
		gerarArquivoObjeto(codigo_objeto);
	}

	public void acao_semantica128(Token token) {
		pilha_tipos.push("int64");
		codigo_objeto += "ldc.i8 " + token.getLexeme() + " \n" + "conv.r8 \n";
	}

	public void acao_semantica129(Token token) {
		pilha_tipos.push("float64");
		codigo_objeto += "ldc.r8 " + token.getLexeme() + " \n";
	}

	public void acao_semantica130(Token token) {
		pilha_tipos.push("string");
		codigo_objeto += "ldstr " + token.getLexeme() + " \n";
	}

	public void acao_semantica118(Token token) {
		pilha_tipos.push("bool");
		codigo_objeto += "ldc.i4.1 \n";
	}

	public void acao_semantica119(Token token) {
		pilha_tipos.push("bool");
		codigo_objeto += "ldc.i4.0 \n";
	}

	public void acao_semantica131() {
		pilha_tipos.pop();
		codigo_objeto += "ldc.i8 -1 \n" + "conv.r8" + "mul \n";

	}

	public void acao_semantica123() {
		String tipo1 = pilha_tipos.pop();
		String tipo2 = pilha_tipos.pop();
		if (tipo1.equals("int64") && tipo2.equals("int64")) {
			pilha_tipos.push("int64");
		} else {
			pilha_tipos.push("float64");
		}

		codigo_objeto += "add \n";
	}

	public void acao_semantica124() {
		String tipo1 = pilha_tipos.pop();
		String tipo2 = pilha_tipos.pop();
		if (tipo1.equals("int64") && tipo2.equals("int64")) {
			pilha_tipos.push("int64");
		} else {
			pilha_tipos.push("float64");
		}

		codigo_objeto += "sub \n";
	}

	public void acao_semantica125() {
		String tipo1 = pilha_tipos.pop();
		String tipo2 = pilha_tipos.pop();
		if (tipo1.equals("int64") && tipo2.equals("int64")) {
			pilha_tipos.push("int64");
		} else {
			pilha_tipos.push("float64");
		}

		codigo_objeto += "mul \n";
	}

	public void acao_semantica126() {
		pilha_tipos.pop();
		pilha_tipos.pop();

		pilha_tipos.push("float64");

		codigo_objeto += "div \n";
	}

	public void acao_semantica121(Token token) {
		operador_relacional = token.getLexeme();
	}

	public void acao_semantica122() throws SemanticError {
		String tipo1 = pilha_tipos.pop();
		String tipo2 = pilha_tipos.pop();

		if (!tipo1.equals(tipo2)) {
			throw new SemanticError("Tipos incompatíveis: " + tipo1 + " e " + tipo2);
		}

		pilha_tipos.push("bool");

		switch (operador_relacional) {
		case "==":
			codigo_objeto += "ceq \n";
			break;
		case "!=":
			codigo_objeto += "ceq \n" + "ldc.i4.0 \n" + "ceq \n";
			break;
		case "<":
			codigo_objeto += "clt \n";
			break;
		case ">":
			codigo_objeto += "cgt \n";
			break;
		default:
			throw new SemanticError("Operador relacional inválido: " + operador_relacional);
		}
	}

	public void acao_semantica120() {
		codigo_objeto += "ldc.i4.1 xor \n";
	}

	public void acao_semantica116() {
		pilha_tipos.pop();
		pilha_tipos.pop();

		pilha_tipos.push("bool");

		codigo_objeto += "and \n";
	}

	public void acao_semantica117() {
		pilha_tipos.pop();
		pilha_tipos.pop();

		pilha_tipos.push("bool");

		codigo_objeto += "or \n";
	}

	public void acao_semantica127(Token token) throws SemanticError {
		if (tabela_simbolos.containsKey(token.getLexeme())) {
			String tipo = "";
			if (token.getLexeme().startsWith("i_")) {
				tipo = "int64";
				pilha_tipos.push("int64");
			} else if (token.getLexeme().startsWith("f_")) {
				tipo = "float64";
				pilha_tipos.push("float64");
			} else if (token.getLexeme().startsWith("s_")) {
				tipo = "string";
				pilha_tipos.push("string");
			} else if (token.getLexeme().startsWith("b_")) {
				tipo = "bool";
				pilha_tipos.push("bool");
			}

			pilha_tipos.add(tipo);
			codigo_objeto += "ldloc " + token.getLexeme() + " \n";

			if (tipo.equals("int64")) {
				codigo_objeto += "conv.r8 \n";
			}

		} else {
			throw new SemanticError(token.getLexeme() + " não declarado", token.getPosition());
		}
	}

	public void acao_semantica108() {
		String tipo = pilha_tipos.pop();

		if (tipo.equals("int64")) {
			codigo_objeto += "conv.i8 \n";
		}

		codigo_objeto += "call void [mscorlib]System.Console::Write(" + tipo + ") \n";
	}

	public void acao_semantica107() {
		String pularLinha = "ldstr \"\\n\"";

		codigo_objeto += pularLinha + "\ncall void [mscorlib]System.Console::Write(string) \n";
	}

	public void acao_semantica104(Token token) {
		lista_identificadores.add(token.getLexeme());
	}

	public void acao_semantica102(Token token) throws SemanticError {
		for (String id : lista_identificadores) {
			if (tabela_simbolos.containsKey(id)) {
				throw new SemanticError(id + " já declarado", token.getPosition());
			}

			String tipo = "";
			if (token.getLexeme().startsWith("i_")) {
				tipo = "int64";
				pilha_tipos.push("int64");
			} else if (token.getLexeme().startsWith("f_")) {
				tipo = "float64";
				pilha_tipos.push("float64");
			} else if (token.getLexeme().startsWith("s_")) {
				tipo = "string";
				pilha_tipos.push("string");
			} else if (token.getLexeme().startsWith("b_")) {
				tipo = "bool";
				pilha_tipos.push("bool");
			}

			tabela_simbolos.put(id, new Simbolo(tipo));
			codigo_objeto += ".locals (" + tipo + " " + id + ") \n";
		}

		lista_identificadores.clear();
	}

	public void acao_semantica103(Token token) throws SemanticError {
		String tipo = pilha_tipos.pop();

		if (tipo.equals("int64")) {
			codigo_objeto += "conv.i8 \n";
		}

		int qtdeDup = lista_identificadores.size() - 1;
		for (int i = 0; i < qtdeDup; i++) {
			codigo_objeto += "dup \n";
		}

		for (String id : lista_identificadores) {
			if (tabela_simbolos.containsKey(id)) {
				codigo_objeto += "stloc " + id + " \n";
			} else {
				throw new SemanticError(id + " não declarado", token.getPosition());
			}
		}

		lista_identificadores.clear();
	}

	public void acao_semantica106(Token token) {
		pilha_tipos.pop();
		codigo_objeto += "ldstr " + token.getLexeme() + "\ncall void [mscorlib]System.Console::Write(string) \n";
	}

	public void acao_semantica105(Token token) throws SemanticError {
		if (!tabela_simbolos.containsKey(token.getLexeme())) {
			throw new SemanticError(token.getLexeme() + " não declarado", token.getPosition());
		}

		String tipo = tabela_simbolos.get(token.getLexeme()).getTipo();
		switch (tipo) {
		case "int64":
			codigo_objeto += "call string [mscorlib]System.Console::ReadLine() \n"
					+ "call int64 [mscorlib]System.Int64::Parse(string) \n";
			break;
		case "float64":
			codigo_objeto += "call string [mscorlib]System.Console::ReadLine() \n"
					+ "call float64 [mscorlib]System.Double::Parse(string) \n";
			break;
		case "string":
			codigo_objeto += "call string [mscorlib]System.Console::ReadLine() \n";
			break;
		case "bool":
			codigo_objeto += "call string [mscorlib]System.Console::ReadLine() \n"
					+ "call int32 [mscorlib]System.Int32::Parse(string) \n" + "ldc.i4.0 ceq \n";
			break;
		}
		codigo_objeto += "stloc " + token.getLexeme() + "\n";
	}

	public void acao_semantica109() {
		contadorRotulos++;
		String novoRotulo1 = "label" + contadorRotulos;
		contadorRotulos++;
		String novoRotulo2 = "label" + contadorRotulos;
		pilha_rotulos.push(novoRotulo1);
		pilha_rotulos.push(novoRotulo2);

		codigo_objeto += "brfalse " + novoRotulo2 + " \n";
	}

	public void acao_semantica110() {
		String rotuloFim = pilha_rotulos.pop();
		String rotuloAtual = pilha_rotulos.pop();

		codigo_objeto += "br " + rotuloAtual + " \n";
		codigo_objeto += rotuloFim + ": \n";
		pilha_rotulos.push(rotuloAtual);
	}

	public void acao_semantica111() {
		String rotuloFim = pilha_rotulos.pop();
		codigo_objeto += rotuloFim + ": \n";
	}

	public void acao_semantica112() {
		contadorRotulos++;
		String novoRotulo = "label" + contadorRotulos;

		codigo_objeto += "brfalse " + novoRotulo + " \n";

		pilha_rotulos.push(novoRotulo);
	}

	public void acao_semantica113() {
		contadorRotulos++;
		String novoRotulo = "label" + contadorRotulos;

		codigo_objeto += novoRotulo + ": \n";
		pilha_rotulos.push(novoRotulo);
	}

	public void acao_semantica114() {
		String rotuloInicio = pilha_rotulos.pop();
		codigo_objeto += "brtrue " + rotuloInicio + " \n";
	}

	public void acao_semantica115() {
		String rotuloInicio = pilha_rotulos.pop();
		codigo_objeto += "brfalse " + rotuloInicio + " \n";
	}

	public void gerarArquivoObjeto(String codigo_objeto) {
		if (arquivoFonte == null) {
			throw new IllegalArgumentException("Erro: Nenhum arquivo fonte foi especificado.");
		}

		try {
			String nomeOriginal = arquivoFonte.getName();

			String nomeNovo = nomeOriginal.substring(0, nomeOriginal.lastIndexOf('.')) + ".il";

			File arquivoGerado = new File(arquivoFonte.getParent(), nomeNovo);

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoGerado))) {
				bw.write(codigo_objeto);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Erro ao gerar arquivo objeto: " + e.getMessage());
		}
	}
}