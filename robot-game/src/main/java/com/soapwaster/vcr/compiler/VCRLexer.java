// Generated from VCR.g4 by ANTLR 4.4
package com.soapwaster.vcr.compiler;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VCRLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, OR=2, AND=3, EQ=4, NEQ=5, GT=6, LT=7, GTEQ=8, LTEQ=9, PLUS=10, 
		MINUS=11, MULT=12, DIV=13, MOD=14, NOT=15, ASSIGN=16, OPAR=17, CPAR=18, 
		OBRACE=19, CBRACE=20, TRUE=21, FALSE=22, NIL=23, IF=24, ELSE=25, WHILE=26, 
		ID=27, INT=28, STRING=29, COMMENT=30, SPACE=31, OTHER=32;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '"
	};
	public static final String[] ruleNames = {
		"T__0", "OR", "AND", "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", "PLUS", 
		"MINUS", "MULT", "DIV", "MOD", "NOT", "ASSIGN", "OPAR", "CPAR", "OBRACE", 
		"CBRACE", "TRUE", "FALSE", "NIL", "IF", "ELSE", "WHILE", "ID", "INT", 
		"STRING", "COMMENT", "SPACE", "OTHER"
	};


	public VCRLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "VCR.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\"\u00b4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3"+
		"\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\34\3\34\7\34\u0091\n\34\f\34\16\34\u0094\13\34\3\35\6\35\u0097\n\35"+
		"\r\35\16\35\u0098\3\36\3\36\3\36\3\36\7\36\u009f\n\36\f\36\16\36\u00a2"+
		"\13\36\3\36\3\36\3\37\3\37\7\37\u00a8\n\37\f\37\16\37\u00ab\13\37\3\37"+
		"\3\37\3 \3 \3 \3 \3!\3!\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36;\37= ?!A\"\3\2\b\5\2C\\aac|\6\2\62;C\\aac|\3"+
		"\2\62;\5\2\f\f\17\17$$\4\2\f\f\17\17\5\2\13\f\17\17\"\"\u00b8\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\3C\3\2\2\2\5E\3\2\2\2\7H\3\2\2\2\tK\3"+
		"\2\2\2\13N\3\2\2\2\rQ\3\2\2\2\17S\3\2\2\2\21U\3\2\2\2\23X\3\2\2\2\25["+
		"\3\2\2\2\27]\3\2\2\2\31_\3\2\2\2\33a\3\2\2\2\35c\3\2\2\2\37e\3\2\2\2!"+
		"g\3\2\2\2#i\3\2\2\2%k\3\2\2\2\'m\3\2\2\2)o\3\2\2\2+q\3\2\2\2-v\3\2\2\2"+
		"/|\3\2\2\2\61\u0080\3\2\2\2\63\u0083\3\2\2\2\65\u0088\3\2\2\2\67\u008e"+
		"\3\2\2\29\u0096\3\2\2\2;\u009a\3\2\2\2=\u00a5\3\2\2\2?\u00ae\3\2\2\2A"+
		"\u00b2\3\2\2\2CD\7.\2\2D\4\3\2\2\2EF\7~\2\2FG\7~\2\2G\6\3\2\2\2HI\7(\2"+
		"\2IJ\7(\2\2J\b\3\2\2\2KL\7?\2\2LM\7?\2\2M\n\3\2\2\2NO\7#\2\2OP\7?\2\2"+
		"P\f\3\2\2\2QR\7@\2\2R\16\3\2\2\2ST\7>\2\2T\20\3\2\2\2UV\7@\2\2VW\7?\2"+
		"\2W\22\3\2\2\2XY\7>\2\2YZ\7?\2\2Z\24\3\2\2\2[\\\7-\2\2\\\26\3\2\2\2]^"+
		"\7/\2\2^\30\3\2\2\2_`\7,\2\2`\32\3\2\2\2ab\7\61\2\2b\34\3\2\2\2cd\7\'"+
		"\2\2d\36\3\2\2\2ef\7#\2\2f \3\2\2\2gh\7?\2\2h\"\3\2\2\2ij\7*\2\2j$\3\2"+
		"\2\2kl\7+\2\2l&\3\2\2\2mn\7}\2\2n(\3\2\2\2op\7\177\2\2p*\3\2\2\2qr\7v"+
		"\2\2rs\7t\2\2st\7w\2\2tu\7g\2\2u,\3\2\2\2vw\7h\2\2wx\7c\2\2xy\7n\2\2y"+
		"z\7u\2\2z{\7g\2\2{.\3\2\2\2|}\7p\2\2}~\7k\2\2~\177\7n\2\2\177\60\3\2\2"+
		"\2\u0080\u0081\7k\2\2\u0081\u0082\7h\2\2\u0082\62\3\2\2\2\u0083\u0084"+
		"\7g\2\2\u0084\u0085\7n\2\2\u0085\u0086\7u\2\2\u0086\u0087\7g\2\2\u0087"+
		"\64\3\2\2\2\u0088\u0089\7y\2\2\u0089\u008a\7j\2\2\u008a\u008b\7k\2\2\u008b"+
		"\u008c\7n\2\2\u008c\u008d\7g\2\2\u008d\66\3\2\2\2\u008e\u0092\t\2\2\2"+
		"\u008f\u0091\t\3\2\2\u0090\u008f\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u00938\3\2\2\2\u0094\u0092\3\2\2\2\u0095"+
		"\u0097\t\4\2\2\u0096\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3\2"+
		"\2\2\u0098\u0099\3\2\2\2\u0099:\3\2\2\2\u009a\u00a0\7$\2\2\u009b\u009f"+
		"\n\5\2\2\u009c\u009d\7$\2\2\u009d\u009f\7$\2\2\u009e\u009b\3\2\2\2\u009e"+
		"\u009c\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2"+
		"\2\2\u00a1\u00a3\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a4\7$\2\2\u00a4"+
		"<\3\2\2\2\u00a5\u00a9\7%\2\2\u00a6\u00a8\n\6\2\2\u00a7\u00a6\3\2\2\2\u00a8"+
		"\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\3\2"+
		"\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00ad\b\37\2\2\u00ad>\3\2\2\2\u00ae\u00af"+
		"\t\7\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\b \2\2\u00b1@\3\2\2\2\u00b2\u00b3"+
		"\13\2\2\2\u00b3B\3\2\2\2\b\2\u0092\u0098\u009e\u00a0\u00a9\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}