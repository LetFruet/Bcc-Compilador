package Sintatico;
public class SyntaticError extends AnalysisError {
	
	private Token tokenEncontrado;

	public SyntaticError(String msg, int position, Token tokenEncontrado) {
		super(msg, position);
		this.tokenEncontrado = tokenEncontrado;
	}

	public SyntaticError(String msg) {
		super(msg);
	}

	public Token getToken() {
		return tokenEncontrado;
	}
}