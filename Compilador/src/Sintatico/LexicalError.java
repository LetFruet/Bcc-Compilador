package Sintatico;
public class LexicalError extends AnalysisError {
	
	private Token tokenEncontrado;
	
    public LexicalError(String msg, int position, Token tokenEncontrado) {
        super(msg, position);
        this.tokenEncontrado = tokenEncontrado;
    }

    public LexicalError(String msg) {
        super(msg);
    }
    
    public Token getToken() {
    	return tokenEncontrado;
    }
}