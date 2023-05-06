// Generated from /home/christian/Università/Magistrale/Compilatori/Progetto23/SimpLanPlus2023/SimpLanPlus_2023/src/parser/SimpLanPlus.g4 by ANTLR 4.12.0
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpLanPlusParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpLanPlusVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(SimpLanPlusParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(SimpLanPlusParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(SimpLanPlusParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(SimpLanPlusParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SimpLanPlusParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStm(SimpLanPlusParser.StmContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(SimpLanPlusParser.ExpContext ctx);
}