package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ChaosBaseVisitor;
import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.ChaosVisitor;
import eu.tn.chaoscompiler.ChaosParser.FunctionCallContext;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
import eu.tn.chaoscompiler.ast.nodes.operators.Negation;
import eu.tn.chaoscompiler.ast.nodes.references.FunctionCall;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.operators.Addition;
import eu.tn.chaoscompiler.ast.nodes.terminals.StringNode;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Cette classe est une implémentation de {@link ChaosVisitor},
 * qui permet de créer un Ast
 */
public class AstCreator extends ChaosBaseVisitor<Ast> {
    //----- Quelques fonctions pour factoriser un peu ce bazar
    Ast getChildAst(int idx, ParserRuleContext ctx) {
        return new Program(ctx.getChild(idx).accept(this));
    }
    //-------------------------------


    @Override
    public Ast visitProgram(ChaosParser.ProgramContext ctx) {
        return new Program(getChildAst(0, ctx));
    }

    @Override
    public Ast visitLoopOrCondition(ChaosParser.LoopOrConditionContext ctx) {
        return null;
    }

    @Override
    public Ast visitExpBin(ChaosParser.ExpBinContext ctx) {
        return null;
    }

    @Override
    public Ast visitWhile(ChaosParser.WhileContext ctx) {
        return null;
    }

    @Override
    public Ast visitFor(ChaosParser.ForContext ctx) {
        return null;
    }

    @Override
    public Ast visitIf(ChaosParser.IfContext ctx) {
        return null;
    }

    @Override
    public Ast visitElse(ChaosParser.ElseContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoElse(ChaosParser.NoElseContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitExpValued(ChaosParser.ExpValuedContext ctx) {
        return null;
    }

    @Override
    public Ast visitExpNoOr(ChaosParser.ExpNoOrContext ctx) {
        return null;
    }

    @Override
    public Ast visitExpNoAnd(ChaosParser.ExpNoAndContext ctx) {
        return null;
    }

    @Override
    public Ast visitExpNoComp(ChaosParser.ExpNoCompContext ctx) {
        // expNoComp : expNoAdd addOp
        Ast gauche = getChildAst(0, ctx);
        Ast droit = getChildAst(1, ctx);

        // Si l'opérateur à droite est null on skip le nœud
        if (droit == null) {
            return gauche;
        }
        if (ctx.getChild(1) instanceof ChaosParser.AddContext) {
            return new Addition(gauche, droit);
        }
        // TODO SOUSTRACTION
        return null;
    }

    @Override
    public Ast visitExpNoAdd(ChaosParser.ExpNoAddContext ctx) {
        return null;
    }

    @Override
    public Ast visitAffect(ChaosParser.AffectContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoAffect(ChaosParser.NoAffectContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitOr(ChaosParser.OrContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoOr(ChaosParser.NoOrContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitAnd(ChaosParser.AndContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoAnd(ChaosParser.NoAndContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitEquals(ChaosParser.EqualsContext ctx) {
        return null;
    }

    @Override
    public Ast visitNotEquals(ChaosParser.NotEqualsContext ctx) {
        return null;
    }

    @Override
    public Ast visitInfOrEquals(ChaosParser.InfOrEqualsContext ctx) {
        return null;
    }

    @Override
    public Ast visitSupOrEquals(ChaosParser.SupOrEqualsContext ctx) {
        return null;
    }

    @Override
    public Ast visitInfThan(ChaosParser.InfThanContext ctx) {
        return null;
    }

    @Override
    public Ast visitSupThan(ChaosParser.SupThanContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoComp(ChaosParser.NoCompContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitAdd(ChaosParser.AddContext ctx) {
        // '+' expNoAdd addOp
        Ast memberToAdd = getChildAst(1, ctx);
        Ast suppMember = getChildAst(2, ctx);
        if (suppMember == null) {
            return memberToAdd;
        }
        if (ctx.getChild(2) instanceof ChaosParser.AddContext) {
            return new Addition(memberToAdd, suppMember);
        }
        // TODO SOUSTRACTION
        return null;
    }

    @Override
    public Ast visitMinus(ChaosParser.MinusContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoAddMinus(ChaosParser.NoAddMinusContext ctx) {
        return null;
    }

    @Override
    public Ast visitMult(ChaosParser.MultContext ctx) {
        return null;
    }

    @Override
    public Ast visitDivide(ChaosParser.DivideContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoMultDiv(ChaosParser.NoMultDivContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitReference(ChaosParser.ReferenceContext ctx) {
        // value -> ID idRef
        Ast gauche = getChildAst(0, ctx);
        Ast droit = getChildAst(1, ctx);

        if (droit == null) { // id tout seul
            return gauche;
        } else {
            /*
            idRef
                : '(' expValuedOrIfListOpt ')'              #FunctionCall
                | '[' expValuedOrIf ']' arrayCreateOpt      #ArrayElement
                | '{' fieldCreateOpt '}'                #RecordCreate
                | '.' ID idRef                          #StructFieldAccess
                | * mot vide *                        #NoIdTail
                ;
             */
            if (droit instanceof FunctionCallContext) {
                return new FunctionCall(gauche, droit);
            } else {
                return null; // TODO
            }
        }
    }

    @Override
    public Ast visitString(ChaosParser.StringContext ctx) {
        // Value : STR.
        // Il faut enlever les guillemets au début et à la fin
        String str = ctx.getChild(0).toString();
        return new StringNode(str.substring(1, str.length() - 1));
    }

    @Override
    public Ast visitInteger(ChaosParser.IntegerContext ctx) {
        return new IntegerNode(Integer.parseInt(ctx.getChild(0).toString()));
    }

    @Override
    public Ast visitNegation(ChaosParser.NegationContext ctx) {
        // '-' negationTail
        Ast tail = getChildAst(1, ctx);
        if (tail instanceof IntegerNode) {
            return tail; // le nœud fils gère la négation
        }
        // Dans les autres cas pas d'autre choix que de passer par un nouveau nœud
        return new Negation(tail);
    }

    @Override
    public Ast visitLet(ChaosParser.LetContext ctx) {
        return null;
    }

    @Override
    public Ast visitSequence(ChaosParser.SequenceContext ctx) {
        // value : '(' expSeq ')'
        return getChildAst(1, ctx); // on retourne la séquence sous-jacente
    }

    @Override
    public Ast visitNegReference(ChaosParser.NegReferenceContext ctx) {
        // negationTail : ID idRef
        return getChildAst(0, ctx); // La négation est gérée par le nœud parent
    }

    @Override
    public Ast visitNegInteger(ChaosParser.NegIntegerContext ctx) {
        // negInteger : INT
        // Même chose que pour Integer avec '-' devant
        return new IntegerNode(-Integer.parseInt(ctx.getChild(0).toString()));
    }

    @Override
    public Ast visitNegLet(ChaosParser.NegLetContext ctx) {
        // negationTail : letExp
        return getChildAst(0, ctx); // La négation est gérée par le nœud parent
    }

    @Override
    public Ast visitNegSequence(ChaosParser.NegSequenceContext ctx) {
        // negationTail : '(' expSeq ')'
        return getChildAst(1, ctx); // La négation est gérée par le nœud parent
    }

    @Override
    public Ast visitFunctionCall(ChaosParser.FunctionCallContext ctx) {
        return null;
    }

    @Override
    public Ast visitArrayElement(ChaosParser.ArrayElementContext ctx) {
        return null;
    }

    @Override
    public Ast visitRecordCreate(ChaosParser.RecordCreateContext ctx) {
        return null;
    }

    @Override
    public Ast visitStructFieldAccess(ChaosParser.StructFieldAccessContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoIdTail(ChaosParser.NoIdTailContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitArrayAssign(ChaosParser.ArrayAssignContext ctx) {
        return null;
    }

    @Override
    public Ast visitArrayAccess(ChaosParser.ArrayAccessContext ctx) {
        return null;
    }

    @Override
    public Ast visitFieldCreateOptParent(ChaosParser.FieldCreateOptParentContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoIdFieldCreate(ChaosParser.NoIdFieldCreateContext ctx) {
        return null; // MOT VIDE donc null

    }

    @Override
    public Ast visitFieldCreate(ChaosParser.FieldCreateContext ctx) {
        return null;
    }

    @Override
    public Ast visitFieldCreateTailAdd(ChaosParser.FieldCreateTailAddContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoFieldCreateTail(ChaosParser.NoFieldCreateTailContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitLetExp(ChaosParser.LetExpContext ctx) {
        return null;
    }

    @Override
    public Ast visitParameter(ChaosParser.ParameterContext ctx) {
        return null;
    }

    @Override
    public Ast visitNoParameter(ChaosParser.NoParameterContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitExpValuedList(ChaosParser.ExpValuedListContext ctx) {
        return null;
    }

    @Override
    public Ast visitNextParameter(ChaosParser.NextParameterContext ctx) {
        return null;
    }

    @Override
    public Ast visitEndParameters(ChaosParser.EndParametersContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitIfValued(ChaosParser.IfValuedContext ctx) {
        return null;
    }

    @Override
    public Ast visitExpValuedBis(ChaosParser.ExpValuedBisContext ctx) {
        return null;
    }

    // ----------- Sequences  ----------
    @Override
    public Ast visitNonEmptySequence(ChaosParser.NonEmptySequenceContext ctx) {
        // expSeqOpt : expSeq
        return getChildAst(0, ctx); // on retourne la séquence sous-jacente
    }

    @Override
    public Ast visitEmptySequence(ChaosParser.EmptySequenceContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitExpSeq(ChaosParser.ExpSeqContext ctx) {
        // expSeq : exp expSeqTail
        Ast gauche = getChildAst(0, ctx);
        Ast droite = getChildAst(1, ctx);
        if (droite == null) {
            // sequence avec un seul élément
            return new Sequence(gauche);
        }
        // Sinon on sait que le fils droit est une séquence, donc on
        // va la modifier pour ajouter le nœud gauche en tête et la retourner
        return ((Sequence) droite).addInHead(gauche);
    }

    @Override
    public Ast visitNextSeqElement(ChaosParser.NextSeqElementContext ctx) {
        //expSeqTail : ';' expSeq
        return getChildAst(1, ctx);
    }

    @Override
    public Ast visitEndSequence(ChaosParser.EndSequenceContext ctx) {
        return null; // MOT VIDE donc null
    }
    // --------------------------------

    @Override
    public Ast visitNonEmptyDeclarationList(ChaosParser.NonEmptyDeclarationListContext ctx) {
        return null;
    }

    @Override
    public Ast visitEmptyDeclarationList(ChaosParser.EmptyDeclarationListContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitDeclarationList(ChaosParser.DeclarationListContext ctx) {
        return null;
    }

    @Override
    public Ast visitNextDeclaration(ChaosParser.NextDeclarationContext ctx) {
        return null;
    }

    @Override
    public Ast visitDeclarationEnd(ChaosParser.DeclarationEndContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitDeclarationVariable(ChaosParser.DeclarationVariableContext ctx) {
        return null;
    }

    @Override
    public Ast visitDeclarationType(ChaosParser.DeclarationTypeContext ctx) {
        return null;
    }

    @Override
    public Ast visitDeclarationFunction(ChaosParser.DeclarationFunctionContext ctx) {
        return null;
    }

    @Override
    public Ast visitRenameType(ChaosParser.RenameTypeContext ctx) {
        return null;
    }

    @Override
    public Ast visitArray(ChaosParser.ArrayContext ctx) {
        return null;
    }

    @Override
    public Ast visitRecord(ChaosParser.RecordContext ctx) {
        return null;
    }

    @Override
    public Ast visitArrTy(ChaosParser.ArrTyContext ctx) {
        return null;
    }

    @Override
    public Ast visitRecTy(ChaosParser.RecTyContext ctx) {
        return null;
    }

    @Override
    public Ast visitHasType(ChaosParser.HasTypeContext ctx) {
        return null;
    }

    @Override
    public Ast visitVoidType(ChaosParser.VoidTypeContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitFieldDecElement(ChaosParser.FieldDecElementContext ctx) {
        return null;
    }

    @Override
    public Ast visitEmptyFieldDecList(ChaosParser.EmptyFieldDecListContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitNextFieldDec(ChaosParser.NextFieldDecContext ctx) {
        return null;
    }

    @Override
    public Ast visitEndFieldDecList(ChaosParser.EndFieldDecListContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitFieldDec(ChaosParser.FieldDecContext ctx) {
        return null;
    }
}

