// Generated from C:/Courses/compiler2/grammers/cssParser.g4 by ANTLR 4.13.1
package grammers;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class cssParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENBRACE=1, CLOSEBRACE=2, ID_SELECTOR=3, CLASS_SELECTOR=4, TAG_SELECTOR=5, 
		PROPERTY=6, STRING_CSS=7, NUMBER=8, COLOR=9, SEMICOLON_CSS=10, COLON_CSS=11, 
		COMMA_CSS=12, WS=13, COMMENT=14;
	public static final int
		RULE_stylesheet = 0, RULE_ruleset = 1, RULE_selector = 2, RULE_properties = 3, 
		RULE_value = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"stylesheet", "ruleset", "selector", "properties", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", null, null, null, null, null, null, null, "';'", 
			"':'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "OPENBRACE", "CLOSEBRACE", "ID_SELECTOR", "CLASS_SELECTOR", "TAG_SELECTOR", 
			"PROPERTY", "STRING_CSS", "NUMBER", "COLOR", "SEMICOLON_CSS", "COLON_CSS", 
			"COMMA_CSS", "WS", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "cssParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public cssParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StylesheetContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(cssParser.EOF, 0); }
		public List<RulesetContext> ruleset() {
			return getRuleContexts(RulesetContext.class);
		}
		public RulesetContext ruleset(int i) {
			return getRuleContext(RulesetContext.class,i);
		}
		public StylesheetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stylesheet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).enterStylesheet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).exitStylesheet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cssParserVisitor ) return ((cssParserVisitor<? extends T>)visitor).visitStylesheet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StylesheetContext stylesheet() throws RecognitionException {
		StylesheetContext _localctx = new StylesheetContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_stylesheet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 58L) != 0)) {
				{
				{
				setState(10);
				ruleset();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(16);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RulesetContext extends ParserRuleContext {
		public TerminalNode OPENBRACE() { return getToken(cssParser.OPENBRACE, 0); }
		public TerminalNode CLOSEBRACE() { return getToken(cssParser.CLOSEBRACE, 0); }
		public SelectorContext selector() {
			return getRuleContext(SelectorContext.class,0);
		}
		public List<PropertiesContext> properties() {
			return getRuleContexts(PropertiesContext.class);
		}
		public PropertiesContext properties(int i) {
			return getRuleContext(PropertiesContext.class,i);
		}
		public RulesetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).enterRuleset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).exitRuleset(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cssParserVisitor ) return ((cssParserVisitor<? extends T>)visitor).visitRuleset(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesetContext ruleset() throws RecognitionException {
		RulesetContext _localctx = new RulesetContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_ruleset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) {
				{
				setState(18);
				selector();
				}
			}

			setState(21);
			match(OPENBRACE);
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PROPERTY) {
				{
				{
				setState(22);
				properties();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(28);
			match(CLOSEBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorContext extends ParserRuleContext {
		public TerminalNode ID_SELECTOR() { return getToken(cssParser.ID_SELECTOR, 0); }
		public TerminalNode CLASS_SELECTOR() { return getToken(cssParser.CLASS_SELECTOR, 0); }
		public TerminalNode TAG_SELECTOR() { return getToken(cssParser.TAG_SELECTOR, 0); }
		public SelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).enterSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).exitSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cssParserVisitor ) return ((cssParserVisitor<? extends T>)visitor).visitSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorContext selector() throws RecognitionException {
		SelectorContext _localctx = new SelectorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_selector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertiesContext extends ParserRuleContext {
		public TerminalNode PROPERTY() { return getToken(cssParser.PROPERTY, 0); }
		public TerminalNode COLON_CSS() { return getToken(cssParser.COLON_CSS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode SEMICOLON_CSS() { return getToken(cssParser.SEMICOLON_CSS, 0); }
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).enterProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).exitProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cssParserVisitor ) return ((cssParserVisitor<? extends T>)visitor).visitProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_properties);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(PROPERTY);
			setState(33);
			match(COLON_CSS);
			setState(34);
			value();
			setState(35);
			match(SEMICOLON_CSS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING_CSS() { return getToken(cssParser.STRING_CSS, 0); }
		public TerminalNode NUMBER() { return getToken(cssParser.NUMBER, 0); }
		public TerminalNode COLOR() { return getToken(cssParser.COLOR, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof cssParserListener ) ((cssParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cssParserVisitor ) return ((cssParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 896L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000e(\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0005\u0000\f\b\u0000\n\u0000\f\u0000\u000f\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0003\u0001\u0014\b\u0001\u0001\u0001\u0001\u0001"+
		"\u0005\u0001\u0018\b\u0001\n\u0001\f\u0001\u001b\t\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0000\u0000\u0005"+
		"\u0000\u0002\u0004\u0006\b\u0000\u0002\u0001\u0000\u0003\u0005\u0001\u0000"+
		"\u0007\t%\u0000\r\u0001\u0000\u0000\u0000\u0002\u0013\u0001\u0000\u0000"+
		"\u0000\u0004\u001e\u0001\u0000\u0000\u0000\u0006 \u0001\u0000\u0000\u0000"+
		"\b%\u0001\u0000\u0000\u0000\n\f\u0003\u0002\u0001\u0000\u000b\n\u0001"+
		"\u0000\u0000\u0000\f\u000f\u0001\u0000\u0000\u0000\r\u000b\u0001\u0000"+
		"\u0000\u0000\r\u000e\u0001\u0000\u0000\u0000\u000e\u0010\u0001\u0000\u0000"+
		"\u0000\u000f\r\u0001\u0000\u0000\u0000\u0010\u0011\u0005\u0000\u0000\u0001"+
		"\u0011\u0001\u0001\u0000\u0000\u0000\u0012\u0014\u0003\u0004\u0002\u0000"+
		"\u0013\u0012\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000"+
		"\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0019\u0005\u0001\u0000\u0000"+
		"\u0016\u0018\u0003\u0006\u0003\u0000\u0017\u0016\u0001\u0000\u0000\u0000"+
		"\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000"+
		"\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u001c\u0001\u0000\u0000\u0000"+
		"\u001b\u0019\u0001\u0000\u0000\u0000\u001c\u001d\u0005\u0002\u0000\u0000"+
		"\u001d\u0003\u0001\u0000\u0000\u0000\u001e\u001f\u0007\u0000\u0000\u0000"+
		"\u001f\u0005\u0001\u0000\u0000\u0000 !\u0005\u0006\u0000\u0000!\"\u0005"+
		"\u000b\u0000\u0000\"#\u0003\b\u0004\u0000#$\u0005\n\u0000\u0000$\u0007"+
		"\u0001\u0000\u0000\u0000%&\u0007\u0001\u0000\u0000&\t\u0001\u0000\u0000"+
		"\u0000\u0003\r\u0013\u0019";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}