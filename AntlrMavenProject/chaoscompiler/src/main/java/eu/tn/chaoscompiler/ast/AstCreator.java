package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ChaosBaseVisitor;
import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.ChaosVisitor;
import eu.tn.chaoscompiler.ChaosParser.ArrayAssignContext;
import eu.tn.chaoscompiler.ChaosParser.ArrayElementContext;
import eu.tn.chaoscompiler.ChaosParser.FieldCreateOptParentContext;
import eu.tn.chaoscompiler.ChaosParser.FunctionCallContext;
import eu.tn.chaoscompiler.ChaosParser.NoIdTailContext;
import eu.tn.chaoscompiler.ChaosParser.RecordCreateContext;
import eu.tn.chaoscompiler.ChaosParser.StructFieldAccessContext;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
import eu.tn.chaoscompiler.ast.nodes.declarations.ArrayTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.NoRecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.TypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.declarations.RecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.operators.Negation;
import eu.tn.chaoscompiler.ast.nodes.references.ArrayAccess;
import eu.tn.chaoscompiler.ast.nodes.references.ArrayAssign;
import eu.tn.chaoscompiler.ast.nodes.references.FieldCreate;
import eu.tn.chaoscompiler.ast.nodes.references.FieldDeclaration;
import eu.tn.chaoscompiler.ast.nodes.references.FunctionCall;
import eu.tn.chaoscompiler.ast.nodes.references.ParameterList;
import eu.tn.chaoscompiler.ast.nodes.references.RecordAccess;
import eu.tn.chaoscompiler.ast.nodes.references.RecordCreate;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.operators.Addition;
import eu.tn.chaoscompiler.ast.nodes.terminals.StringNode;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Cette classe est une implémentation de {@link ChaosVisitor},
 * qui permet de créer un Ast
 */
public class AstCreator extends ChaosBaseVisitor<Ast> {
    // ----- Quelques fonctions pour factoriser un peu ce bazar
    Ast getChildAst(int idx, ParserRuleContext ctx) {
        return new Program(ctx.getChild(idx).accept(this));
    }
    // -------------------------------

    @Override
    public Ast visitProgram(ChaosParser.ProgramContext ctx) {
        // return new Program(getChildAst(0, ctx));
        return getChildAst(0, ctx);
    }

    @Override
    public Ast visitLoopOrCondition(ChaosParser.LoopOrConditionContext ctx) {
        return ctx.getChild(0).accept(this); // Elimination d'un noeud unaire inutile
    }

    @Override
    public Ast visitExpBin(ChaosParser.ExpBinContext ctx) {
        return null;
    }

    @Override
    public Ast visitWhile(ChaosParser.WhileContext ctx) {
        Ast condExpr = ctx.getChild(1).accept(this);
        Ast doExpr = ctx.getChild(3).accept(this);
        return new While(condExpr, doExpr);
    }

    @Override
    public Ast visitFor(ChaosParser.ForContext ctx) {
        String str_Id = ctx.getChild(1).toString();
        Ast Id = new Id(str_Id);
        Ast startExpr = ctx.getChild(3).accept(this);
        Ast endExpr = ctx.getChild(5).accept(this);
        Ast doExpr = ctx.getChild(7).accept(this);
        return new For(Id, startExpr, endExpr, doExpr);

    }

    @Override
    public Ast visitIf(ChaosParser.IfContext ctx) {
        Ast condExpr = ctx.getChild(1).accept(this);
        Ast thenExpr = ctx.getChild(3).accept(this);
        Ast elseExpr = ctx.getChild(4).accept(this);

        return new IfThenElse(condExpr, thenExpr, elseExpr);

    }

    @Override
    public Ast visitElse(ChaosParser.ElseContext ctx) {
        return ctx.getChild(1).accept(this);
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
        /*
         * idRef
         * : '(' expValuedOrIfListOpt ')' #FunctionCall
         * | '[' expValuedOrIf ']' arrayCreateOpt #ArrayElement
         * | '{' fieldCreateOpt '}' #RecordCreate
         * | '.' ID idRef #StructFieldAccess
         * | * mot vide * #NoIdTail
         * ;
         */
        if (getChildAst(1, ctx) /* droit */ instanceof FunctionCallContext) {

            return new FunctionCall(getChildAst(0, ctx),
                    getChildAst(1, ctx));

        } else if (ctx.getChild(1) instanceof ChaosParser.ArrayElementContext) {

            ChaosParser.ArrayElementContext ae = (ChaosParser.ArrayElementContext) ctx.getChild(1);
            // '[' expValuedOrIf ']' arrayCreateOpt #ArrayElement
            if (ae.getChild(3) instanceof ChaosParser.ArrayAssignContext) {
                // On crée un tableau #ArrayAssign
                return new ArrayAssign( // syntaxe: type [nombre] of element
                        getChildAst(0, ctx), // type
                        getChildAst(1, ae), // nombre
                        getChildAst(1, (ChaosParser.ArrayAssignContext) ae.getChild(3))); // element
            } else {

                return accessAux(ctx);
            }

        } else if (getChildAst(1, ctx) /* droit */ instanceof RecordCreateContext) {

            ChaosParser.RecordCreateContext rc = (ChaosParser.RecordCreateContext) ctx.getChild(1);
            RecordCreate res = new RecordCreate();

            if (rc.getChild(1) instanceof ChaosParser.NoIdFieldCreateContext) {
                // Enregistrement vide
                return res;
            } else {
                // Enregistrement plein
                ChaosParser.FieldCreateOptParentContext fc = (ChaosParser.FieldCreateOptParentContext) rc.getChild(1);
                res.addArg((Ast) new FieldCreate(getChildAst(0, fc), getChildAst(2, fc)));
                while (fc.getChild(3) instanceof ChaosParser.FieldCreateTailAddContext) {
                    fc = (ChaosParser.FieldCreateOptParentContext) fc.getChild(3).getChild(1);
                    res.addArg((Ast) new FieldCreate(getChildAst(0, fc), getChildAst(2, fc)));
                }
                return res;
            }

        } else if (getChildAst(1, ctx) /* droit */ instanceof StructFieldAccessContext) {

            return accessAux(ctx);

        } else if (getChildAst(1, ctx) == null) {

            return getChildAst(0, ctx) /* gauche */;

        } else {

            return null; // Erreur

        }

    }

    // Fonction auxiliaire
    Ast accessAux(ParserRuleContext ctx) {
        ParserRuleContext c = ctx; // Copy pour parcours en profondeur
        Ast res = getChildAst(0, ctx); // Init avec l'id

        c = /* idref */ (ParserRuleContext) c.getChild(1);
        while (c != null) {
            if (c instanceof StructFieldAccessContext) { // Lecture d'un enregistrement
                res = new RecordAccess(res, getChildAst(1, c));
                c = /* idref */ (ParserRuleContext) c.getChild(3);
            } else if (c instanceof ArrayElementContext) { // Lecture d'un tableau
                res = new ArrayAccess(res, getChildAst(1, c));
                c = /* arrayCreateOpt */ (ParserRuleContext) c.getChild(3);
                if (c instanceof FieldCreateOptParentContext) {
                    c = null; // Erreur
                } else {
                    c = /* idref */ (ParserRuleContext) c.getChild(0);
                }
            } else if (c instanceof NoIdTailContext) { // On a fini le parcours
                c = null;
            }
        }

        return res;
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
        return ctx.getChild(0).accept(this); // élimination d'un noeud unaire inutile
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
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitArrayElement(ChaosParser.ArrayElementContext ctx) {
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitRecordCreate(ChaosParser.RecordCreateContext ctx) {
        return null; // Inaccessible car traité dans son père
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
        Ast declarationList = ctx.getChild(1).accept(this);
        Ast exprSeq = ctx.getChild(3).accept(this);
        return new Let(declarationList, exprSeq);

    }

    @Override
    public Ast visitParameter(ChaosParser.ParameterContext ctx) {
        ParameterList res = new ParameterList();

        ChaosParser.ExpValuedListContext evl = (ChaosParser.ExpValuedListContext) ctx.getChild(0);

        res.addParameter(getChildAst(0, evl));
        while (evl.getChild(1) instanceof ChaosParser.NextParameterContext) {
            evl = (ChaosParser.ExpValuedListContext) evl.getChild(1).getChild(1);
            res.addParameter(getChildAst(0, evl));
        }

        return (Ast) res;
    }

    @Override
    public Ast visitNoParameter(ChaosParser.NoParameterContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitExpValuedList(ChaosParser.ExpValuedListContext ctx) {
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitNextParameter(ChaosParser.NextParameterContext ctx) {
        return null; // Inaccessible car traité dans son père
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

    // ----------- Sequences ----------
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
        // expSeqTail : ';' expSeq
        return getChildAst(1, ctx);
    }

    @Override
    public Ast visitEndSequence(ChaosParser.EndSequenceContext ctx) {
        return null; // MOT VIDE donc null
    }
    // --------------------------------

    // ----------- Declaration ----------
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
        // declaration : 'type' ID '=' ty
        Id typeId = (Id) getChildAst(1, ctx);
        TypeDeclaration ty = (TypeDeclaration) getChildAst(3, ctx);

        // dans les trois cas possibles (RenameType, array, record), on récupère un
        // objet
        // type déclaration auquel il manque son typeId
        ty.typeId = typeId;
        return ty;
    }

    @Override
    public Ast visitDeclarationFunction(ChaosParser.DeclarationFunctionContext ctx) {
        return null;
    }

    @Override
    public Ast visitFieldDecElement(ChaosParser.FieldDecElementContext ctx) {
        // fieldDecList : fieldDec fieldDecTail
        Ast gauche = getChildAst(0, ctx);
        Ast droite = getChildAst(1, ctx);

        if (droite == null) {
            // On initialise l'objet pour la déclaration du record si le noeud
            // actuel est le champ le plus à droite
            RecordTypeDeclaration recordTypeDeclaration = new RecordTypeDeclaration();
            return recordTypeDeclaration.addFieldInHead((FieldDeclaration) gauche);
        }
        // Dans le cas contraire, on modifie la déclaration existante pour
        // à ajouter le nœud actuel et le retourner
        return ((RecordTypeDeclaration) droite).addFieldInHead((FieldDeclaration) gauche);
    }

    @Override
    public Ast visitEmptyFieldDecList(ChaosParser.EmptyFieldDecListContext ctx) {
        // MOT VIDE
        return new RecordTypeDeclaration(); // Crée une déclaration de record vide
    }

    @Override
    public Ast visitNextFieldDec(ChaosParser.NextFieldDecContext ctx) {
        // fieldDecTail : ',' fieldDec fieldDecTail
        Ast gauche = getChildAst(1, ctx);
        Ast droite = getChildAst(2, ctx);

        if (droite == null) {
            // On initialise l'objet pour la déclaration du record si le noeud
            // actuel est le champ le plus à droite
            RecordTypeDeclaration recordTypeDeclaration = new RecordTypeDeclaration();
            return recordTypeDeclaration.addFieldInHead((FieldDeclaration) gauche);
        }
        // Dans le cas contraire, on modifie la déclaration existante pour
        // à ajouter le nœud actuel et le retourner
        return ((RecordTypeDeclaration) droite).addFieldInHead((FieldDeclaration) gauche);
    }

    @Override
    public Ast visitEndFieldDecList(ChaosParser.EndFieldDecListContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitFieldDec(ChaosParser.FieldDecContext ctx) {
        // fieldDec : ID ':' ID
        return new FieldDeclaration((Id) getChildAst(0, ctx), (Id) getChildAst(2, ctx));
    }

    @Override
    public Ast visitRecTy(ChaosParser.RecTyContext ctx) {
        // recTy : '{' fieldDecList '}'
        return getChildAst(1, ctx);
    }

    @Override
    public Ast visitRecord(ChaosParser.RecordContext ctx) {
        // ty : recTy
        return getChildAst(0, ctx);
    }

    @Override
    public Ast visitArray(ChaosParser.ArrayContext ctx) {
        // ty : arrTy
        return getChildAst(0, ctx);
    }

    @Override
    public Ast visitRenameType(ChaosParser.RenameTypeContext ctx) {
        // ty : id
        return new NoRecordTypeDeclaration((Id) getChildAst(0, ctx));
    }
    // ----------------------------------

    @Override
    public Ast visitArrTy(ChaosParser.ArrTyContext ctx) {
        // arrTy : 'array' 'of' ID
        return new ArrayTypeDeclaration((Id) getChildAst(2, ctx));
    }

    @Override
    public Ast visitHasType(ChaosParser.HasTypeContext ctx) {
        return null;
    }

    @Override
    public Ast visitVoidType(ChaosParser.VoidTypeContext ctx) {
        return null; // MOT VIDE donc null
    }

}
