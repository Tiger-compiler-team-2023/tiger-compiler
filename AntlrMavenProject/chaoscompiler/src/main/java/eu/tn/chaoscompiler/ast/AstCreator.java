package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.*;
import eu.tn.chaoscompiler.ChaosParser.*;
import eu.tn.chaoscompiler.ast.nodes.*;
import eu.tn.chaoscompiler.ast.nodes.declarations.*;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.*;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.*;
import eu.tn.chaoscompiler.ast.nodes.references.*;
import eu.tn.chaoscompiler.ast.nodes.terminals.*;
import eu.tn.chaoscompiler.ast.nodes.operators.*;
import eu.tn.chaoscompiler.errors.ChaosError;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Cette classe est une implémentation de {@link ChaosVisitor},
 * qui permet de créer un Ast
 */
public class AstCreator extends ChaosBaseVisitor<Ast> {

    // ----- Quelques fonctions pour factoriser un peu ce bazar
    public Ast getChildAst(int idx, ParserRuleContext ctx) {
        Ast node = ctx.getChild(idx).accept(this);
        if (node != null) {
            node.setligneEtColonne(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        return node;
    }

    /**
     * Retourne un {@link ListAccumulator} à partir :
     * - d'un nœud droit qui est soit null, soit un {@link ListAccumulator} existant,
     * - d'un nœud gauche qui sera ajouté en tête de la liste
     */
    @SuppressWarnings("unchecked")
    public <T extends Ast> ListAccumulator<T> getListNode(Ast gauche, Ast droite) {
        if (droite == null) { // Si tout à droite, on crée un accumulateur
            return new ListAccumulator<>((T) gauche);
        }
        // Sinon on modifie celui existant
        return ((ListAccumulator<T>) droite).addInHead((T) gauche);
    }

    public Ast customVisitAddOrMinus(ParserRuleContext ctx, int left_idx, int right_idx) {
        Ast value = getChildAst(left_idx, ctx);
        Ast suppMember = getChildAst(right_idx, ctx);
        if (suppMember == null) {
            return value;
        }
        if (ctx.getChild(right_idx) instanceof AddContext) {
            return new Addition(value, suppMember);
        }
        return new Soustraction(value, suppMember);
    }

    public Ast customVisitMultOrDiv(ParserRuleContext ctx, int left_idx, int right_idx) {
        Ast value = getChildAst(left_idx, ctx);
        Ast suppMember = getChildAst(right_idx, ctx);
        if (suppMember == null) {
            return value;
        }
        if (ctx.getChild(right_idx) instanceof MultContext) {
            return new Multiplication(value, suppMember);
        }
        return new Division(value, suppMember);
    }

    public Ast customVisitAnd(ParserRuleContext ctx, int left_idx, int right_idx) {
        Ast value = getChildAst(left_idx, ctx);
        Ast suppMember = getChildAst(right_idx, ctx);
        if (suppMember == null) {
            return value;
        }
        return new And(value, suppMember);
    }

    public Ast customVisitOr(ParserRuleContext ctx, int left_idx, int right_idx) {
        Ast value = getChildAst(left_idx, ctx);
        Ast suppMember = getChildAst(right_idx, ctx);
        if (suppMember == null) {
            return value;
        }
        return new Or(value, suppMember);
    }
    // -------------------------------

    @Override
    public Ast visitProgram(ChaosParser.ProgramContext ctx) {
        try {
            return new Program(getChildAst(0, ctx));
        } catch (Exception e) {
            e.printStackTrace();
            GestionnaireErreur.getInstance().addUnrecognisedError(
                    "Erreur durant la création de l'AST. Le programme est probablement mal formé" +
                    " mais le compilateur ne détecte pas l'erreur correctement.",
                    ChaosError.typeError.SYNTAX_ERROR);
            return null;
        }
    }

    @Override
    public Ast visitLoopOrCondition(ChaosParser.LoopOrConditionContext ctx) {
        return getChildAst(0, ctx); // Elimination d'un noeud unaire inutile
    }

    @Override
    public Ast visitExpBin(ChaosParser.ExpBinContext ctx) {
        // expValued affectOp
        Ast leftMember = getChildAst(0, ctx);
        Ast suppMember = getChildAst(1, ctx);

        if (suppMember != null) {
            var affectOp = (BinaryOperator) suppMember; // suppMember est une affectation
            affectOp.setLeft(leftMember); // placer la membre gauche
            return affectOp;
        }
        return leftMember;
    }

    @Override
    public Ast visitWhile(ChaosParser.WhileContext ctx) {
        Ast condExpr = getChildAst(1, ctx);
        Ast doExpr = getChildAst(3, ctx);
        return new While(condExpr, doExpr);
    }

    @Override
    public Ast visitFor(ChaosParser.ForContext ctx) {
        String str_Id = ctx.getChild(1).toString();
        Ast Id = new Id(str_Id);
        Ast startExpr = getChildAst(3, ctx);
        Ast endExpr = getChildAst(5, ctx);
        Ast doExpr = getChildAst(7, ctx);
        return new For(Id, startExpr, endExpr, doExpr);
    }

    @Override
    public Ast visitIf(ChaosParser.IfContext ctx) {
        Ast condExpr = getChildAst(1, ctx);
        Ast thenExpr = getChildAst(3, ctx);
        Ast elseExpr = getChildAst(4, ctx);

        return new IfThenElse(condExpr, thenExpr, elseExpr);
    }

    @Override
    public Ast visitElse(ChaosParser.ElseContext ctx) {
        return getChildAst(1, ctx);
    }

    @Override
    public Ast visitNoElse(ChaosParser.NoElseContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitExpValued(ChaosParser.ExpValuedContext ctx) {
        // expNoOr orOp
        return customVisitOr(ctx, 0, 1);
    }

    @Override
    public Ast visitExpNoOr(ChaosParser.ExpNoOrContext ctx) {
        // expNoAnd andOp
        return customVisitAnd(ctx, 0, 1);
    }

    @Override
    public Ast visitExpNoAnd(ChaosParser.ExpNoAndContext ctx) {
        // expNoComp compOp
        Ast leftMember = getChildAst(0, ctx);
        Ast suppMember = getChildAst(1, ctx);
        if (suppMember == null) {
            return leftMember;
        }
        return ((BinaryOperator) suppMember).setLeft(leftMember);
    }

    @Override
    public Ast visitExpNoComp(ChaosParser.ExpNoCompContext ctx) {
        // expNoAdd addOp
        return customVisitAddOrMinus(ctx, 0, 1);
    }

    @Override
    public Ast visitExpNoAdd(ChaosParser.ExpNoAddContext ctx) {
        // value multOp
        return customVisitMultOrDiv(ctx, 0, 1);
    }

    @Override
    public Ast visitAffect(ChaosParser.AffectContext ctx) {
        // ':=' expValued
        Ast value = getChildAst(1, ctx);
        return new Affect(null, value);
    }

    @Override
    public Ast visitNoAffect(ChaosParser.NoAffectContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitOr(ChaosParser.OrContext ctx) {
        // '|' expNoOr orOp
        return customVisitOr(ctx, 1, 2);
    }

    @Override
    public Ast visitNoOr(ChaosParser.NoOrContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitAnd(ChaosParser.AndContext ctx) {
        // '&' expNoAnd andOp
        return customVisitAnd(ctx, 1, 2);
    }

    @Override
    public Ast visitNoAnd(ChaosParser.NoAndContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitEquals(ChaosParser.EqualsContext ctx) {
        // '=' expNoComp
        Ast value = getChildAst(1, ctx);
        return new Equals(null, value);
    }

    @Override
    public Ast visitNotEquals(ChaosParser.NotEqualsContext ctx) {
        // '<>' expNoComp
        Ast value = getChildAst(1, ctx);
        return new NotEquals(null, value);
    }

    @Override
    public Ast visitInfOrEquals(ChaosParser.InfOrEqualsContext ctx) {
        // '<=' expNoComp
        Ast value = getChildAst(1, ctx);
        return new InferiorOrEquals(null, value);
    }

    @Override
    public Ast visitSupOrEquals(ChaosParser.SupOrEqualsContext ctx) {
        // '>=' expNoComp
        Ast value = getChildAst(1, ctx);
        return new SuperiorOrEquals(null, value);
    }

    @Override
    public Ast visitInfThan(ChaosParser.InfThanContext ctx) {

        // '<' expNoComp
        Ast value = getChildAst(1, ctx);
        return new Inferior(null, value);
    }

    @Override
    public Ast visitSupThan(ChaosParser.SupThanContext ctx) {
        // '>' expNoComp
        Ast value = getChildAst(1, ctx);
        return new Superior(null, value);
    }

    @Override
    public Ast visitNoComp(ChaosParser.NoCompContext ctx) {
        return null; // MOT VIDE donc null
    }

    //-----------------
    @Override
    public Ast visitAdd(ChaosParser.AddContext ctx) {
        // '+' expNoAdd addOp
        return customVisitAddOrMinus(ctx, 1, 2);
    }

    @Override
    public Ast visitMinus(ChaosParser.MinusContext ctx) {
        // '-' expNoAdd addOp
        return customVisitAddOrMinus(ctx, 1, 2);
    }
    // ---------------

    @Override
    public Ast visitNoAddMinus(ChaosParser.NoAddMinusContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitMult(ChaosParser.MultContext ctx) {
        // '*' value multOp
        return customVisitMultOrDiv(ctx, 1, 2);
    }

    @Override
    public Ast visitDivide(ChaosParser.DivideContext ctx) {
        // '/' value multOp
        return customVisitMultOrDiv(ctx, 1, 2);
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
        return accessAux(getChildAst(0, ctx), (IdRefContext) ctx.getChild(1));
    }

    // Fonction auxiliaire
    Ast accessAux(Ast id, IdRefContext irCtx) {

        if (irCtx instanceof FunctionCallContext) {
            // idRef : '(' expValuedOrIfListOpt ')'         #FunctionCall
            return new FunctionCall(id, getChildAst(1, irCtx));
        } else if (irCtx instanceof ArrayElementContext) {
            // idRef : '[' expValuedOrIf ']' arrayCreateOpt         #ArrayElement   (irCtx)
            //arrayCreateOpt : 'of' expValued                       #ArrayAssign    (acoCtx)
            //               |  idRef                               #ArrayAccess
            var acoCtx = (ChaosParser.ArrayCreateOptContext) irCtx.getChild(3);

            if (acoCtx instanceof ChaosParser.ArrayAssignContext) {
                return new ArrayAssign(id, getChildAst(1, irCtx), getChildAst(1, acoCtx));
            } else if (acoCtx instanceof ChaosParser.ArrayAccessContext) {
                var irTailCtx = (ChaosParser.IdRefContext) acoCtx.getChild(0);
                ArrayAccess aa = new ArrayAccess(id, getChildAst(1, irCtx));
                return accessAux(aa, irTailCtx);
            }
        } else if (irCtx instanceof RecordCreateContext) {
            // idRef : '{' fieldCreateOpt '}'           #RecordCreate
            /* fieldCreateOpt
             *      : fieldCreate                           #FieldCreateOptParent
             *      |    ;                                  #NoIdFieldCreate
             * fieldCreate
             *      : ID '=' expValued fieldCreateTail ;
             * fieldCreateTail
             *      : ',' fieldCreate                       #FieldCreateTailAdd
             *      |    ;                                  #NoFieldCreateTail
             */
            RecordCreate rc = new RecordCreate(id);

            if (irCtx.getChild(1) instanceof ChaosParser.FieldCreateOptParentContext) {
                var fcCtx = (ChaosParser.FieldCreateContext) irCtx.getChild(1).getChild(0);

                rc.addArg(new FieldCreate(getChildAst(0, fcCtx), getChildAst(2, fcCtx)));

                while (fcCtx.getChild(3) instanceof ChaosParser.FieldCreateTailAddContext) {
                    fcCtx = (ChaosParser.FieldCreateContext) fcCtx.getChild(3).getChild(1);
                    rc.addArg(new FieldCreate(getChildAst(0, fcCtx), getChildAst(2, fcCtx)));
                }
            }

            return rc;
        } else if (irCtx instanceof ChaosParser.StructFieldAccessContext) {
            // idRef : '.' ID idRef #StructFieldAccess
            RecordAccess ra = new RecordAccess(id, getChildAst(1, irCtx));
            var irTailCtx = (ChaosParser.IdRefContext) irCtx.getChild(2);
            return accessAux(ra, irTailCtx);
        }

        return id;
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

        // Dans les autres cas pas d'autre choix que de passer par un nouveau nœud
        return new Negation(tail);
    }

    @Override
    public Ast visitLet(ChaosParser.LetContext ctx) {
        // value : letExp
        return getChildAst(0, ctx); // élimination d'un nœud unaire inutile
    }

    @Override
    public Ast visitNegReference(ChaosParser.NegReferenceContext ctx) {
        // negationTail : ID idRef
        //gérée par la fonction auxiliare : accessAux qui gère tous les différents contexes de IdRef
        return accessAux(new Id(ctx.getChild(0).toString()), (ChaosParser.IdRefContext) ctx.getChild(1));
        //return getChildAst(0, ctx); // La négation est gérée par le nœud parent
    }

    @Override
    public Ast visitNegInteger(ChaosParser.NegIntegerContext ctx) {
        // negInteger : INT
        // Même chose que pour Integer avec '-' devant
        return new IntegerNode(Integer.parseInt(ctx.getChild(0).toString()));
    }

    @Override
    public Ast visitNegLet(ChaosParser.NegLetContext ctx) {
        // negationTail : letExp
        return getChildAst(0, ctx); // La négation est gérée par le nœud parent
    }

    @Override
    public Ast visitNegSequence(NegSequenceContext ctx) {
        // negationTail : '(' expSeq ')'
        //même comportement que visitSequence
        return commonVisitSequence((ExpSeqContext) ctx.getChild(1), ctx);
    }

    @Override
    public Ast visitSequence(ChaosParser.SequenceContext ctx) {
        // value : '(' expSeq ')'
        return commonVisitSequence((ExpSeqContext) ctx.getChild(1), ctx);
    }

    // Car visitNegSequence et visitSequence ont le même comportement
    private Ast commonVisitSequence(ExpSeqContext child, ParserRuleContext ctx) {
        Sequence res = new Sequence();
        var evl = child;
        res.addInstr(getChildAst(0, evl));
        while (evl.getChild(1) instanceof NextSeqElementContext) {
            evl = (ExpSeqContext) evl.getChild(1).getChild(1);
            res.addInstr(getChildAst(0, evl));
        }
        return res.setligneEtColonne(ctx.start.getLine(), ctx.start.getCharPositionInLine());

    }

    @Override
    public Ast visitFunctionCall(ChaosParser.FunctionCallContext ctx) {
        // idRef : '(' expValuedOrIfListOpt ')'
        return getChildAst(1, ctx);
    }

    @Override
    public Ast visitArrayElement(ChaosParser.ArrayElementContext ctx) {
        // idRef : '[' expValuedOrIf ']' arrayCreateOpt     #ArrayElement
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
        //arrayCreateOpt : 'of' expValued                        #ArrayAssign
        return null;
    }

    @Override
    public Ast visitArrayAccess(ChaosParser.ArrayAccessContext ctx) {
        // arrayCreateOpt :  idRef                                #ArrayAccess
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
        // letExp : 'let' declarationListOpt 'in' expSeqOpt 'end'
        Ast decList = getChildAst(1, ctx);
        Ast exprSeq = getChildAst(3, ctx);

        return new Let(decList, exprSeq);
    }

    @Override
    public Ast visitParameter(ChaosParser.ParameterContext ctx) {
        // expValuedOrIfListOpt : expValuedList

        ParameterList res = new ParameterList();
        var evl = (ChaosParser.ExpValuedListContext) ctx.getChild(0);

        res.addParameter(getChildAst(0, evl));
        while (evl.getChild(1) instanceof ChaosParser.NextParameterContext) {
            evl = (ChaosParser.ExpValuedListContext) evl.getChild(1).getChild(1);
            res.addParameter(getChildAst(0, evl));
        }
        return res;
    }

    @Override
    public Ast visitNoParameter(ChaosParser.NoParameterContext ctx) {
        // expValuedOrIfListOpt : /* mot vide */
        return new ParameterList(); // Liste vide
    }

    @Override
    public Ast visitExpValuedList(ChaosParser.ExpValuedListContext ctx) {
        // expValuedList : expValuedOrIf expValuedListTail
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitNextParameter(ChaosParser.NextParameterContext ctx) {
        //expValuedListTail : ',' expValuedList
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitEndParameters(ChaosParser.EndParametersContext ctx) {
        // expValuedListTail : /* mot vide */
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitIfValued(ChaosParser.IfValuedContext ctx) {
        // expValuedOrIf : 'if' expValued 'then' exp elseOpt
        Ast condExpr = getChildAst(1, ctx);
        Ast thenExpr = getChildAst(3, ctx);
        Ast elseExpr = getChildAst(4, ctx);

        return new IfThenElse(condExpr, thenExpr, elseExpr);
    }

    @Override
    public Ast visitExpValuedBis(ChaosParser.ExpValuedBisContext ctx) {
        // expValuedOrIf : expValued
        return getChildAst(0, ctx);
    }

    // ----------- Sequences ----------
    @Override
    public Ast visitNonEmptySequence(ChaosParser.NonEmptySequenceContext ctx) {
        // expSeqOpt : expSeq
        Sequence res = new Sequence();
        var evl = (ChaosParser.ExpSeqContext) ctx.getChild(0);

        res.addInstr(getChildAst(0, evl));
        while (evl.getChild(1) instanceof ChaosParser.NextSeqElementContext) {
            evl = (ChaosParser.ExpSeqContext) evl.getChild(1).getChild(1);
            res.addInstr(getChildAst(0, evl));
        }
        return res.setligneEtColonne(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public Ast visitEmptySequence(ChaosParser.EmptySequenceContext ctx) {
        // expSeqOpt : /* mot vide */
        return new Sequence(); // Séquence vide
    }

    @Override
    public Ast visitExpSeq(ChaosParser.ExpSeqContext ctx) {
        // expSeq : exp expSeqTail
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitNextSeqElement(ChaosParser.NextSeqElementContext ctx) {
        // expSeqTail : ';' expSeq
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitEndSequence(ChaosParser.EndSequenceContext ctx) {
        return null; // MOT VIDE donc null
    }

    // --------------------------------

    // ----------- Declaration ----------
    @Override
    public Ast visitNonEmptyDeclarationList(ChaosParser.NonEmptyDeclarationListContext ctx) {
        // declarationListOpt : declarationList
        DeclarationList res = new DeclarationList();

        var child = (ChaosParser.DeclarationListContext) ctx.getChild(0);
        res.addDeclaration((Declaration) getChildAst(0, child));

        var decTail = (ChaosParser.DecTailContext) child.getChild(1);

        while (decTail instanceof ChaosParser.NextDeclarationContext) {
            res.addDeclaration((Declaration) getChildAst(0, decTail));
            decTail = (ChaosParser.DecTailContext) decTail.getChild(1);
        }

        return res;
    }

    @Override
    public Ast visitEmptyDeclarationList(ChaosParser.EmptyDeclarationListContext ctx) {
        // declarationListOpt :  /* mot vide */
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitDeclarationList(ChaosParser.DeclarationListContext ctx) {
        // declarationList : declaration decTail
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitNextDeclaration(ChaosParser.NextDeclarationContext ctx) {
        // decTail : declaration decTail
        return null; // Inaccessible car traité dans son père
    }

    @Override
    public Ast visitDeclarationEnd(ChaosParser.DeclarationEndContext ctx) {
        // decTail : /* mot vide */
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitDeclarationVariable(ChaosParser.DeclarationVariableContext ctx) {
        // declaration : 'var' ID optType ':=' expValued
        Id varId = (Id) getChildAst(1, ctx);
        Id optType = (Id) getChildAst(2, ctx); // Marche parce qu'on peut cast null vers tous les types en java
        Ast value = getChildAst(4, ctx);

        return new VariableDeclaration(varId, optType, value);
    }

    @Override
    public Ast visitHasType(ChaosParser.HasTypeContext ctx) {
        // optType  : ':' ID
        return getChildAst(1, ctx);
    }

    @Override
    public Ast visitVoidType(ChaosParser.VoidTypeContext ctx) {
        return null; // MOT VIDE donc null
    }

    @Override
    public Ast visitDeclarationType(ChaosParser.DeclarationTypeContext ctx) {
        // declaration : 'type' ID '=' ty
        Id typeId = (Id) getChildAst(1, ctx);
        var ty = (Declaration) getChildAst(3, ctx);

        // dans les trois cas possibles (RenameType, array, record), on récupère un
        // objet type déclaration auquel il manque son id
        return ty.setObjectId(typeId);
    }

    @Override
    public Ast visitDeclarationFunction(ChaosParser.DeclarationFunctionContext ctx) {
        // declaration : 'function' ID '(' fieldDecList ')' optType '=' exp
        var functionId = (Id) getChildAst(1, ctx);
        var fields = (FieldDecList) getChildAst(3, ctx);
        var returnType = (Id) getChildAst(5, ctx);
        Ast content = getChildAst(7, ctx);

        return new FunctionDeclaration(functionId,
                (fields),
                returnType, content);
    }

    @Override
    public Ast visitFieldDecElement(ChaosParser.FieldDecElementContext ctx) {
        // fieldDecList : fieldDec fieldDecTail
        FieldDecList res = new FieldDecList();

        res.addFieldDec((FieldDeclaration) getChildAst(0, ctx));

        var evl = (ChaosParser.FieldDecTailContext) ctx.getChild(1);
        while (evl instanceof ChaosParser.NextFieldDecContext) {
            res.addFieldDec((FieldDeclaration) getChildAst(1, evl));
            evl = (ChaosParser.FieldDecTailContext) evl.getChild(2);
        }

        return res;
    }

    @Override
    public Ast visitEmptyFieldDecList(ChaosParser.EmptyFieldDecListContext ctx) {
        // fieldDecList : /* mot vide */
        return new FieldDecList(); // field dec list vide
    }

    @Override
    public Ast visitNextFieldDec(ChaosParser.NextFieldDecContext ctx) {
        // fieldDecTail : ',' fieldDec fieldDecTail
        return null; // innaccessible car traité dans le père
    }

    @Override
    public Ast visitEndFieldDecList(ChaosParser.EndFieldDecListContext ctx) {
        // fieldDecTail : /* mot vide */
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
        FieldDecList list = (FieldDecList) getChildAst(1, ctx);
        return new RecordTypeDeclaration(list);
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

    @Override
    public Ast visitArrTy(ChaosParser.ArrTyContext ctx) {
        // arrTy : 'array' 'of' ID
        return new ArrayTypeDeclaration((Id) getChildAst(2, ctx));
    }

    @Override
    public Ast visitTerminal(TerminalNode node) {
        // ID
        return new Id(node.getText());
    }
    // ----------------------------------
}
