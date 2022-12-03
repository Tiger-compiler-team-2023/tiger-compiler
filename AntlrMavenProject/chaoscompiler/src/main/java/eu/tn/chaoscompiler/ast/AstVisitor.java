package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.operators.Addition;
import eu.tn.chaoscompiler.ast.nodes.operators.BinariesOperators;
import eu.tn.chaoscompiler.ast.nodes.operators.Negation;
import eu.tn.chaoscompiler.ast.nodes.references.ArrayAssign;
import eu.tn.chaoscompiler.ast.nodes.references.FieldCreate;
import eu.tn.chaoscompiler.ast.nodes.references.FunctionCall;
import eu.tn.chaoscompiler.ast.nodes.references.ParameterList;
import eu.tn.chaoscompiler.ast.nodes.references.RecordCreate;
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

    public T visit(ParameterList node);

    public T visit(ArrayAssign node);

    public T visit(RecordCreate node);

    public T visit(FieldCreate node);

    T visit(For forExpr);

    T visit(While whileExpr);

    T visit(IfThenElse ifThenElseExpr);

    T visit(Let letExpr);
}
