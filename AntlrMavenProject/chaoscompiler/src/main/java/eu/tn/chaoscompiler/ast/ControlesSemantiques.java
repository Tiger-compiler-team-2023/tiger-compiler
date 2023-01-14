package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
import eu.tn.chaoscompiler.ast.nodes.declarations.FunctionDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.VariableDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.ArrayTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.NoRecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.RecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.operators.*;
import eu.tn.chaoscompiler.ast.nodes.references.*;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;
import eu.tn.chaoscompiler.ast.nodes.terminals.StringNode;
import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;

import static eu.tn.chaoscompiler.errors.Errors.*;

@NoArgsConstructor
public class ControlesSemantiques implements AstVisitor<Type> {
    // Couleurs pour l'affichage
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BOLD = "\033[1;31m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static String lastId = "";

    private TDScontroller tdsController;
    private GestionnaireErreur err = GestionnaireErreur.getInstance();

    public boolean checkIfTypeExist(String type, Ast node) {
        if (!tdsController.existsType(type)) {
            err.addSemanticError(node, Errors.UNDECLARED_TYPE, type);
            return false;
        }
        return true;
    }

    /**
     * Fonctions auxiliaires pour vérfier si le break existe en dehors des boucles
     */
    public boolean checkIfBreakExistsAsId(Id id) {
        if (id.identifier.equals("break")) {
            err.addSemanticError(id, Errors.UNAUTHORIZED_BREAK);
            return true;
        }
        return false;
    }

    public boolean checkIfBreakExistsInBinaryOp(BinaryOperator bop) {
        if (bop.leftValue instanceof Id) {
            if (checkIfBreakExistsAsId((Id) bop.leftValue)) {
                return true;
            }
        } else if (bop.rightValue instanceof Id) {
            if (checkIfBreakExistsAsId((Id) bop.rightValue)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfBreakExistsInDecList(DeclarationList decList) {
        // Vérifier si l'instruction de type Id
        for (Ast expression : decList.list) {
            if (expression instanceof Id) {
                if (((Id) expression).identifier.equals("break")) {
                    err.addSemanticError(decList, Errors.UNAUTHORIZED_BREAK);
                    return true;
                }
            } else if (expression instanceof BinaryOperator) {
                if (checkIfBreakExistsInBinaryOp((BinaryOperator) expression)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfBreakExistsInExprSeq(Sequence sequence) {
        // Vérifier si l'instruction de type Id
        for (Ast expression : sequence.instructions) {
            if (expression instanceof Id) {
                if (((Id) expression).identifier.equals("break")) {
                    err.addSemanticError(expression, Errors.UNAUTHORIZED_BREAK);
                    return true;
                }
            } else if (expression instanceof BinaryOperator) {
                if (checkIfBreakExistsInBinaryOp((BinaryOperator) expression)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfBreakExistsInIfThenElse(Ast field) {
        // the field is eighter a condExpr, thenExpr or ElseExpr
        if (field instanceof Id) {
            if (((Id) field).identifier.equals("break")) {
                err.addSemanticError(field, Errors.UNAUTHORIZED_BREAK);
                return true;
            }
        }
        if (field instanceof Sequence) {
            if (checkIfBreakExistsInExprSeq((Sequence) field)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDefinedId(Ast node) {
        if (node instanceof Id id) {
            if (id.accept(this) == null) {
                err.addSemanticError(node, UNDECLARED_VARIABLE, id.identifier);
                return false ;
            }
        }
        return true ;
    }

    // --------------------------------------------
    // | VISITEURS |
    // --------------------------------------------

    @Override
    public Void visit(Program node) {
        tdsController = new TDScontroller();

        try {
            node.expression.accept(this);
        } catch (Exception e) {
            e.printStackTrace();
            err.addSemanticError(node, Errors.UNRECOGNISED_ERROR,
                    "Erreur durant la construction de la table des symboles. Le programme est probablement mal formé" +
                            " mais le compilateur ne détecte pas l'erreur correctement.");
        }
        return null;
    }

    @Override
    public Type visit(Let letExpr) {
        tdsController.down();
        letExpr.decList.accept(this);
        // Vérifier le cas d'utilisation de break dans DecList
        if (letExpr.decList != null) {
            checkIfBreakExistsInDecList((DeclarationList) letExpr.decList);
        }
        Type typeSequence = letExpr.exprSeq.accept(this);
        // Vérifier le cas d'utilisation de break dans Sequence
        if (letExpr.exprSeq != null) {
            checkIfBreakExistsInExprSeq((Sequence) letExpr.exprSeq);
        }
        tdsController.up();
        // "letExp: If the body is empty, the type is void, otherwise, the type is that
        // of the last body expression"
        return typeSequence;
    }

    @Override
    public Type visit(Sequence node) {
        tdsController.down();
        // Applique la fonction visit sur chaque élément de la séquence et retourne le
        // type de la dernière expression
        // Si la séquence est vide, retourne le type void
        Type typeSeq = node.instructions.stream()
                .map(instr -> instr.accept(this))
                .reduce((a, b) -> b)
                .orElse(Type.VOID_TYPE);
        tdsController.up();
        return typeSeq;
    }

    @Override
    public Type visit(FunctionDeclaration node) {

        boolean correct = true;

        // verifier que la variable n'existe pas deja
        if (tdsController.existsLocalVariable(node.objectId.identifier)) {
            err.addSemanticError(node, Errors.ALREADY_DECLARED, node.objectId.identifier);
            correct = false;
        }

        tdsController.down();

        // verifier validité type retour
        Type retour;
        if (node.returnType != null) {
            String returnStr = node.returnType.identifier;

            correct = checkIfTypeExist(returnStr, node);

            if (correct) {
                retour = tdsController.getTypeOfId(node, returnStr);
            } else {
                retour = Type.VOID_TYPE;
            }

        } else {
            retour = Type.VOID_TYPE;
        }

        FunctionType fType = new FunctionType(node.objectId.identifier, retour);

        // Ajout des paramètres à la TDS après verif
        FieldDecList fdl = node.fields;
        for (FieldDeclaration fdl_e : fdl.list) {
            Type t = tdsController.findType(fdl_e.baseType.identifier);
            if (t == null) {
                err.addSemanticError(node, PARAMETER_TYPE_NO_DECLARED, fdl_e.baseType.identifier, fdl_e.fieldId.identifier);
                correct = false;
            } else {
                fType.addIn(t);
            }
            if (tdsController.existsLocalVariable(fdl_e.fieldId.identifier)) {
                err.addSemanticError(node, Errors.ID_ALREADY_USED, fdl_e.fieldId.identifier);
                correct = false;
            }
            tdsController.add(new Value(t, fdl_e.fieldId.identifier));
        }

        // verifier type de retour coherent avec contenu de la fonction
        Type content = node.content.accept(this);
        if (!retour.equals(content)) {
            err.addSemanticError(node, Errors.INCOMPATIBLE_FUNCTION_TYPE, retour.getId(), node.objectId.identifier, content.getId());
            correct = false;
        }
        tdsController.up();

        if (correct) {
            tdsController.add(new Value(fType, node.objectId.identifier));
        }
        // vérifier si le break est utilisé dans la Function Declaration
        // Vérifier si le nom de la fonction n'est pas celui d'un mot clé
        if (node.objectId != null) {
            if (node.objectId.identifier.equals("break")) {
                err.addSemanticError(node.objectId, Errors.UNAUTHORIZED_BREAK);
            }
        }

        // Vérifier si break est dans le corps de la fonction
        if (node.content != null) {
            if (node.content instanceof Sequence) {
                checkIfBreakExistsInExprSeq((Sequence) node.content);
            } else if (node.content instanceof DeclarationList) {
                checkIfBreakExistsInExprSeq((Sequence) node.content);
            } else if (node.content instanceof Id) {
                checkIfBreakExistsAsId((Id) node.content);
            }
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(VariableDeclaration node) {
        boolean correct = true;

        if (tdsController.existsLocalVariable(node.objectId.identifier)) {
            err.addSemanticError(node, Errors.ALREADY_DECLARED, node.objectId.identifier);
            correct = false;
        }

        Type typeValue = node.value.accept(this);
        if (typeValue == null) {
            err.addSemanticError(node, Errors.NO_VALUE_ERROR, node.objectId.identifier);
            return Type.VOID_TYPE;
        }
        Type typeToCreate = null;

        if (node.typeId != null) {
            checkIfTypeExist(node.typeId.identifier, node);
            Type declaredType = tdsController.getTypeOfId(node, node.typeId.identifier);
            if (!typeValue.equals(declaredType)) {
                err.addSemanticError(node, Errors.BAD_AFFECTATION_TYPE, typeValue.getId(), node.typeId.identifier);
                correct = false;
            }
            // Si le type est explicite on s'en sert pour créer la variable
            typeToCreate = declaredType;
        } else if (correct) {
            // Sinon on s'adapte au type implicite
            typeToCreate = typeValue;
        }

        if (typeToCreate instanceof ArrayType arrType && checkIfTypeExist(arrType.elementsType.getId(), node)) {
            // Les tests sémantiques sont déjà faits par l'accept sur value
            tdsController.add(new ArrayValue(arrType, node.objectId.identifier));
        } else if (typeToCreate instanceof RecordType recType && checkIfTypeExist(recType.getId(), node)) {
            tdsController.add(new Value(recType, node.objectId.identifier));
        } else {
            tdsController.add(new Value(typeToCreate, node.objectId.identifier));
        }


        // Vérifier si le nom de la variable n'est pas break
        if (node.objectId != null) {
            if (node.objectId.identifier.equals("break")) {
                err.addSemanticError(node.objectId, Errors.UNAUTHORIZED_BREAK);
            }
        }
        if (node.value != null) {
            if (node.value instanceof Id) {
                if (((Id) node.value).identifier.equals("break")) {
                    err.addSemanticError(node.objectId, Errors.UNAUTHORIZED_BREAK);
                }
            }
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(ArrayTypeDeclaration node) {
        boolean correct = true;

        // verifier que le type n'existe pas deja
        if (tdsController.existsLocalType(node.objectId.identifier)) {
            err.addSemanticError(node, Errors.ALREADY_DECLARED_TYPE, node.objectId.identifier);
            correct = false;
        }

        if (node.baseTypeId == null) {
            err.addSemanticError(node, Errors.UNDECLARED_TYPE, "du tableau ");
        } else if (checkIfTypeExist(node.baseTypeId.identifier, node) && correct) {
            Type elementType = tdsController.getTypeOfId(node, node.baseTypeId.identifier);
            tdsController.add(new ArrayType(node.objectId.identifier, elementType));
        }
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(NoRecordTypeDeclaration node) {

        // verifier que le type n'existe pas deja
        if (tdsController.existsLocalType(node.objectId.identifier)) {
            err.addSemanticError(node, Errors.ALREADY_DECLARED_TYPE, node.objectId.identifier);
            // verifier que le type de base existe
            checkIfTypeExist(node.baseTypeId.identifier, node);
        } else {
            // verifier que le type de base existe
            if (checkIfTypeExist(node.baseTypeId.identifier, node)) {
                tdsController.add(new TypeRename(node.objectId.identifier,
                        tdsController.getTypeOfId(node, node.baseTypeId.identifier)));
            }
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(RecordTypeDeclaration node) {
        boolean correct = true;

        // on vérifie que le type n'existe pas déjà
        if (tdsController.existsLocalType(node.objectId.identifier)) {
            err.addSemanticError(node, Errors.ALREADY_DECLARED_TYPE, node.objectId.identifier);
            correct = false;
        }

        RecordType rType = new RecordType(node.objectId.identifier);
        // on vérifie que les types des attributs existent
        for (FieldDeclaration fd : node.fields.list) {
            if (checkIfTypeExist(fd.baseType.identifier, node)) {
                rType.addAttribut(new Value(tdsController.getTypeOfId(node, fd.baseType.identifier), fd.fieldId.identifier));
            } else {
                correct = false;
            }
        }
        tdsController.add(rType);
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(For forExpr) {

        boolean continuer = true;

        tdsController.down();

        // Definir l'indice de la boucle comme INCR_TYPE
        tdsController.add(new Value(Type.INCR_TYPE, ((Id) forExpr.id).identifier));

        // Vérifier l'expression de départ est de type entier
        Type type_start_for = forExpr.startExpr.accept(this);
        if (!type_start_for.equals(Type.INT_TYPE)) {
            err.addSemanticError(forExpr.startExpr, LOOP_TYPE,
                    "le départ de l'indice de  la boucle for est de type " +ANSI_BLUE+ type_start_for.getId()
                            +ANSI_RESET+ ". Or, il doit avoir un type " + ANSI_BLUE + " entier" + ANSI_RESET);
        }

        // Vérifier que l'expression de la fin doit être entière
        Type type_end_for = forExpr.endExpr.accept(this);
        if (!type_end_for.equals(Type.INT_TYPE)) {
            err.addSemanticError(forExpr.endExpr, LOOP_TYPE, "la fin de la boucle for est de type "+ANSI_BLUE
                    + type_end_for.getId()+ANSI_RESET + ". Or, il doit avoir un type " + ANSI_BLUE + " entier" + ANSI_RESET);
        }

        // Vérifier que le contenu de la boucle est de type void
        Type typedoexpr = forExpr.doExpr.accept(this);
        if (!typedoexpr.equals(Type.VOID_TYPE)) {
            err.addSemanticError(forExpr.doExpr, LOOP_TYPE,
                    "l'expression à l'intérieur de for est de type " +ANSI_BLUE+ typedoexpr.getId()+ANSI_RESET
                            + ". Or, elle doit avoir un type " + ANSI_BLUE + " void" + ANSI_RESET);
        }

        /*
         * Le contenu de la boucle for peut être une expression simple ou
         * une sequence.
         */

        /*if (!(forExpr.doExpr instanceof Sequence)) {
            Ast instruction = forExpr.doExpr;
            if (instruction instanceof Affect) {
                if (continuer) {
                    // Vérifier si la partie gauche de l'instruction est un Id
                    if (((Affect) instruction).leftValue instanceof Id) {
                        String id_left_value = ((Id) ((Affect) instruction).leftValue).identifier;
                        // Vérifier si la valeur d'id est égale à l'indice de la boucle
                        Value v = tdsController.getVariableOfId(forExpr.doExpr, id_left_value);
                        if (v != null && v.getType() == Type.INCR_TYPE) {
                        err.addSemanticError(forExpr.doExpr, LOOP_COUNTER_AFFECT, id_left_value);
                        // Pour afficher l'erreur une seule fois lorsque l'indice de la boucle est
                        // assigné plusieurs fois dans la séquence
                        continuer = false;
                    }
                    }
                }
            }
        } else {
            Sequence sequence = (Sequence) forExpr.doExpr;
            for (Ast instruction : sequence.instructions) {
                // Vérifier s'il y a une instruction d'affectation
                if (instruction instanceof Affect) {
                    if (continuer) {
                        // Vérifier si la partie gauche de l'instruction est un Id
                        if (((Affect) instruction).leftValue instanceof Id) {
                            String id_left_value = ((Id) ((Affect) instruction).leftValue).identifier;
                            // Vérifier si la valeur d'id est égale à l'indice de la boucle
                            Value v = tdsController.getVariableOfId(forExpr.doExpr, id_left_value);
                            if (v != null && v.getType() == Type.INCR_TYPE) {
                            err.addSemanticError(forExpr.doExpr, LOOP_COUNTER_AFFECT, id_left_value);
                            // Pour afficher l'erreur une seule fois lorsque l'indice de la boucle est
                            // assigné plusieurs fois dans la séquence
                            continuer = false;
                        }
                        }
                    }
                }
            }
        }*/
        
        tdsController.up();

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(IfThenElse ifThenElseExpr) {
        boolean continuer = true;
        tdsController.down();
        Type conditionType = ifThenElseExpr.condExpr.accept(this);
        if (conditionType != null) {
            // Vérifier si le type de condition est entier
            if (!conditionType.equals(Type.INT_TYPE)) {
                err.addSemanticError(ifThenElseExpr.condExpr, Errors.INT_EXPECTED, conditionType.getId());
            }
        }

        // Vérifier si le champs condition ne contient pas break
        // la condExpr est toujours non nul, sinon une exception sera levée
        if (checkIfBreakExistsInIfThenElse(ifThenElseExpr.condExpr)) {
            continuer = false;
        }

        Type thenType = ifThenElseExpr.thenExpr.accept(this);

        // thenExp est toujours non nulle sinon on aura un exception levée
        if (continuer) {
            if (checkIfBreakExistsInIfThenElse(ifThenElseExpr.thenExpr)) {
                continuer = false;
            }
        }
        if (ifThenElseExpr.elseExpr != null) {
            Type elseType = ifThenElseExpr.elseExpr.accept(this);
            // voir si else block contient break
            if (ifThenElseExpr.elseExpr != null && continuer) {
                checkIfBreakExistsInIfThenElse(ifThenElseExpr.elseExpr);
            }
            if (thenType != null) {
                // Vérifier si thenExpr et ElseExpr ont le même type
                if (!(thenType.equals(elseType))) {
                    err.addSemanticError(ifThenElseExpr.condExpr, Errors.INCOMPATIBLES_THEN_ELSE, thenType.getId(), elseType.getId());
                }
            }
            tdsController.up();
            return thenType;
        } else {
            // Vérifier si le type de l'expression
            if (!thenType.equals(Type.VOID_TYPE)) {
                err.addSemanticError(ifThenElseExpr.condExpr, Errors.THEN_WITH_TYPE, thenType.getId());
            }
            tdsController.up();
            return Type.VOID_TYPE;
        }
    }

    @Override
    public Type visit(While whileExpr) {
        tdsController.down();
        Type whilecond = whileExpr.condExpr.accept(this);
        // Vérifier que la condition est de type entier
        if (!whilecond.equals(Type.INT_TYPE)) {
            err.addSemanticError(whileExpr.condExpr, Errors.INT_EXPECTED, whilecond.getId());
        }
        // vérifier que l'expression à l'intérieur de block while est de type void
        Type typedoexpr = whileExpr.doExpr.accept(this);
        if (!typedoexpr.equals(Type.VOID_TYPE)) {
            err.addSemanticError(whileExpr.condExpr, Errors.NO_VOID_WHILE, typedoexpr.getId());
        }
        tdsController.up();
        // While renvoie un type void
        return typedoexpr;
    }

    @Override
    public Type visit(Affect node) {
        // Concernant la syntaxe membre de gauche...
        if (node.leftValue instanceof Id leftId) {

            // ... on vérifie que si c'est un id, il est déclaré...
            if (!tdsController.existsVar(leftId.identifier)) {
                err.addSemanticError(leftId, Errors.UNDECLARED_VARIABLE, leftId.identifier);
            }

            // ... sinon, on vérifie que c'est bien un accès à un tableau ou un champ de record.
        } else if (!((node.leftValue instanceof ArrayAccess) || (node.leftValue instanceof RecordAccess))) {
            err.addSemanticError(node, Errors.INVALID_LVALUE);
        }

        // verifier qu'aucun des deux membres soient des variables indefinies
        if (checkDefinedId(node.leftValue)) {

            Type type1 = node.leftValue.accept(this);

            if (type1.isIncr()) {
                err.addSemanticError(node, CANT_AFFECT_TO_FOR_INDEX) ;
            }

            if (checkDefinedId(node.rightValue)) {

                Type type2 = node.rightValue.accept(this);

                if ((type1 == Type.VOID_TYPE) || (type2 == Type.VOID_TYPE)
                        || (!type1.equals(type2))) {

                    // La plupart des erreurs sont traitées dans les accepts, mais il reste celle-ci
                    err.addSemanticError(node, Errors.BAD_AFFECT_TYPE, type2.getId(), type1.getId());
                }
            }

        }

        return Type.VOID_TYPE;
    }

    public Type equalOrInequal(BinaryOperator node) {
        if (checkDefinedId(node.leftValue) && checkDefinedId(node.rightValue)){
            Type type1 = node.leftValue.accept(this);
            Type type2 = node.rightValue.accept(this);

            if (!(type1.equals(type2))) {
                err.addSemanticError(node, Errors.INCOMPARABLE_TYPES, type1.getId(), type2.getId());
            }
        }
        // le resultat est un booleen representé par un int
        return Type.INT_TYPE;
    }

    @Override
    public Type visit(NotEquals node) {
        return equalOrInequal(node);
    }

    @Override
    public Type visit(Equals node) {
        return equalOrInequal(node);
    }

    @Override
    public Type visit(Negation node) {
        Type entree = node.negationTail.accept(this);
        if (entree != Type.INT_TYPE) {
            err.addSemanticError(node, Errors.BAD_NEGATION_TYPE, (entree == null) ? "null" : entree.getId());
        }
        return Type.INT_TYPE;
    }

    public Type operation(BinaryOperator node, String operation) {

        if (checkDefinedId(node.leftValue) && checkDefinedId(node.rightValue)){

            Type type1 = node.leftValue.accept(this);
            Type type2 = node.rightValue.accept(this);

            if (!(type1.equals(Type.INT_TYPE)) || !(type2.equals(Type.INT_TYPE))) {
                err.addSemanticError(node, Errors.BAD_OPERATION_TYPE, operation, type1.getId(), type2.getId());
            }
        }
        return Type.INT_TYPE;
    }

    @Override
    public Type visit(Multiplication node) {
        return operation(node, "une multiplication");
    }

    @Override
    public Type visit(Soustraction node) {
        return operation(node, "une soustraction");
    }

    @Override
    public Type visit(Addition node) {
        return operation(node, "une addition");
    }

    @Override
    public Type visit(Division node) {
        if (node.rightValue instanceof IntegerNode) {
            if (((IntegerNode) node.rightValue).value == 0) {
                err.addSemanticError(node, Errors.ZERO_DIV);
            }
        }

        return operation(node, "une division");
    }

    @Override
    public Type visit(And node) {
        return operation(node, "un et logique");
    }

    @Override
    public Type visit(Or node) {
        return operation(node, "un ou logique");
    }

    public Type comparison(BinaryOperator node) {

        if (checkDefinedId(node.rightValue) && checkDefinedId(node.leftValue)) {
            Type type1 = node.leftValue.accept(this);
            Type type2 = node.rightValue.accept(this);

            if (!((type1.equals(Type.INT_TYPE) && type2.equals(Type.INT_TYPE))
                    || (type1.equals(Type.STRING_TYPE) && type2.equals(Type.STRING_TYPE)))) {
                err.addSemanticError(node, Errors.INCOMPARABLE_TYPES, type1.getId(), type2.getId());
            }
        }
        // le resultat est un booleen represente par un int
        return Type.INT_TYPE;
    }

    @Override
    public Type visit(Superior node) {
        return comparison(node);
    }

    @Override
    public Type visit(SuperiorOrEquals node) {
        return comparison(node);
    }

    @Override
    public Type visit(Inferior node) {
        return comparison(node);
    }

    @Override
    public Type visit(InferiorOrEquals node) {
        return comparison(node);
    }

    @Override
    public Type visit(ArrayAccess node) {
        // verifier que l'exp designe bien un tableau
        Type tableau = node.exp.accept(this);
        Type tabElem;
        if (!(tableau instanceof ArrayType)) {
            err.addSemanticError(node, Errors.ARRAY_TYPE,
                    "Impossible d'appliquer l'opérateur [] sur une valeur de type " + tableau.getId());
            tabElem = Type.VOID_TYPE;
        } else {
            tabElem = ((ArrayType) tableau).getType();
        }

        // verfier que l'index est un entier
        Type index = node.index.accept(this);
        if (!index.equals(Type.INT_TYPE)) {
            err.addSemanticError(node, Errors.INT_EXPECTED, index.getId());
        }

        // Retourner le type des elem du tableau
        return tabElem;
    }

    @Override
    public Type visit(ArrayAssign node) {
        Type arrayType = Type.VOID_TYPE;

        // verifier que le type est bien un type d'array
        if (checkIfTypeExist(((Id) node.type).identifier, node)) {
            arrayType = tdsController.findType(((Id) node.type).identifier);

            if (arrayType instanceof ArrayType at) {

                // verifier valeur d'initialisation est conforme au type de l'array
                Type contentType = node.element.accept(this);
                if (!(contentType.equals(at.elementsType))) {
                    err.addSemanticError(node ,BAD_ARRAY_INIT_VALUE, at.elementsType.getId(), contentType.getId());
                }

            } else {
                err.addSemanticError(node, Errors.ARRAY_TYPE,
                        String.format("Le type"+ANSI_BLUE+" %s"+ANSI_RESET+" ne correspond pas à un array", arrayType.getId()));
                arrayType = Type.VOID_TYPE;
            }
        }

        // verifier taille est entiere
        Type nbElemType = node.nombreDElements.accept(this);
        if (!nbElemType.equals(Type.INT_TYPE)) {
            err.addSemanticError(node, Errors.INT_EXPECTED, nbElemType.getId());
        }

        // renvoyer le type de l'array
        return arrayType;
    }

    @Override
    public Type visit(FieldCreate node) {
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(FieldDeclaration node) {
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(FieldDecList node) {
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(FunctionCall node) {
        // verifier que l'id est l'id d'une fonction qui existe et recuperer son type de
        Type func = node.id.accept(this);
        if (func == null) {
            err.addSemanticError(node, Errors.UNDECLARED_FUNCTION, new String(lastId));
            return Type.VOID_TYPE;
        }
        
        
        Type retour;
        if (!(func instanceof FunctionType)) {
            err.addSemanticError(node, Errors.NO_FUNCTION_TYPE, ((Id) node.id).identifier);
            return Type.VOID_TYPE;
        } else {
            retour = ((FunctionType) func).outType;
        }

        // verifier le nombre d'arguments
        int nbArgsCall = ((ParameterList) node.argList).parameters.size();
        int nbArgsExpected = ((FunctionType) func).inTypes.size();
        if (nbArgsCall != nbArgsExpected) {
            err.addSemanticError(node, Errors.BAD_ARGUMENT_NUMBER, nbArgsCall + "", nbArgsExpected + "");
        }

        // verifier le type des arguments
        for (int i = 0; (i < nbArgsCall) && (i < nbArgsExpected); i++) {
            Type argType = ((ParameterList) node.argList).parameters.get(i).accept(this);
            Type argTypeExpected = ((FunctionType) func).inTypes.get(i);
            if (!argType.equals(argTypeExpected)) {
                err.addSemanticError(node, Errors.BAD_ARGUMENT_TYPE, argType.getId(),i + "", argTypeExpected.getId());
            }
        }

        // renvoyer le type de retour de la fonction
        return retour;
    }

    @Override
    public Type visit(DeclarationList node) {
        node.list.forEach(declaration -> declaration.accept(this));
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(ParameterList node) {
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(RecordAccess node) {
        String recordName = ((Id) node.exp).identifier;

        // On vérifie que le membre gauche est une variable connue...
        if (!tdsController.existsVar(recordName)) {
            err.addSemanticError(node, Errors.UNDECLARED_VARIABLE, recordName);
        } else {

            // ...puis, qu'il est bien de type record...
            Type leftType = tdsController.getVariableOfId(recordName).getType();
            if (!(leftType instanceof RecordType recordType)) {
                err.addSemanticError(node, Errors.NO_RECORD_TYPE, leftType.getId(), leftType.getType().getId());
            } else {

                // ...et enfin que l'attribut existe.
                Value fieldValue = recordType.getAttribut(((Id) node.index).identifier);
                if (fieldValue == null) {
                    err.addSemanticError(node, Errors.INEXISTING_FIELD, ((Id) node.index).identifier, recordType.getId(),recordType.getType().getId());
                } else {
                    // On termine en retournant le type de l'attribut
                    return fieldValue.getType();
                }
            }
        }
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(RecordCreate node) {
        // on vérifie que la variable existe...
        if (!tdsController.existsType(((Id) node.idObject).identifier)) {
            err.addSemanticError(node, Errors.UNDECLARED_TYPE, ((Id) node.idObject).identifier);
        } else {

            // ... Puis, que son type soit record.
            Type varType = tdsController.getTypeOfId(node, ((Id) node.idObject).identifier);
            if (!(varType instanceof RecordType recordType)) {
                err.addSemanticError(node, Errors.NO_RECORD_TYPE, varType.getId());
            } else {

                // Pour chaque champ du record...
                ArrayList<Value> fieldsPresents = new ArrayList<>();
                for (Ast currentField : node.args) {

                    // ...on vérifie que l'attribut existe...
                    Value fieldValue = recordType.getAttribut(((Id) ((FieldCreate) currentField).id).identifier);
                    if (fieldValue == null) {
                        err.addSemanticError(node, Errors.INEXISTING_FIELD,
                                ((Id) ((FieldCreate) currentField).id).identifier, recordType.getId(),recordType.getType().getId());
                    } else {

                        // ... puis que la valeur affectée est du bon type.
                        Type expType = ((FieldCreate) currentField).expr.accept(this);
                        fieldsPresents.add(fieldValue);
                        if (!(expType.equals(fieldValue.getType()))) {
                            err.addSemanticError(node, Errors.BAD_ARGUMENT_TYPE,
                                    expType.getId(),((Id) ((FieldCreate) currentField).id).identifier ,fieldValue.getType().getId());
                        }
                    }
                }

                // Maintenant qu'on a la liste de champs, on vérifie qu'ils sont tous différents...
                fieldsPresents.stream().distinct().forEach(field -> {
                    if (Collections.frequency(fieldsPresents, field) > 1) {
                        err.addSemanticError(node, Errors.DUPLICATE_FIELD, field.getId(), recordType.getId(),recordType.getType().getId());
                    }
                });

                // ... et sont tous présents.
                recordType.getAttributs().forEach(field -> {
                    if (!fieldsPresents.contains(field)) {
                        err.addSemanticError(node, Errors.MISSING_FIELD, field.getId(), recordType.getId(), recordType.getType().getId());
                    }
                });
                return varType;
            }
        }
        // Et s'il y a encore une erreur ici, suivre les consignes du pictogramme suivant:    (╯°□°)╯︵ ┻━┻
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(Id node) {
        // si le nœud contient le mot clé break, il retourne un type void
        lastId = node.identifier;
        if (node.identifier.equals("break")) {
            return Type.VOID_TYPE;
        }

        // sinon
        Variable v = tdsController.getVariableOfId(node, node.identifier);
        if (v != null)
            return v.getType();
        return null;
    }

    @Override
    public Type visit(IntegerNode node) {
        // Par construction ce nœud est un int valide
        return Type.INT_TYPE;
    }

    @Override
    public Type visit(StringNode node) {
        // Par construction ce nœud est un string valide
        return Type.STRING_TYPE;
    }
}
