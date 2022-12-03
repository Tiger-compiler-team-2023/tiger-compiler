package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.operators.Addition;
import eu.tn.chaoscompiler.ast.nodes.operators.BinariesOperators;
import eu.tn.chaoscompiler.ast.nodes.operators.Negation;
import eu.tn.chaoscompiler.ast.nodes.references.FunctionCall;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;

/**
 * Interface représentant un visiteur de l'AST
 *
 * @param <T>
 */
public interface AstVisitor<T> {
    public T visit(Id node);

    public T visit(Program node);
    public T visit(Addition node);
    public T visit(Negation node);
    public T visit(IntegerNode node);
    
    public T visit(FunctionCall node);
}
