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
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class ControlesSemantiques implements AstVisitor<Type> {
    private TDScontroller tdsController;

    public void checkIfTypeExist(String type, Ast node) {
        if (!tdsController.exists(type)) {
            GestionnaireErreur.getInstance().addSemanticError(node, "Le type " + type + " n'est pas déclaré");
        }
    }

    // --------------------------------------------
    // |             VISITEURS                    |
    // --------------------------------------------

    @Override
    public Void visit(Program node) {
        tdsController = new TDScontroller();
        tdsController.add(Type.INT_TYPE);
        tdsController.add(Type.STRING_TYPE);
        tdsController.add(Type.VOID_TYPE);
        node.expression.accept(this);
        return null;
    }

    @Override
    public Type visit(Let letExpr) {
        tdsController.down();
        letExpr.decList.accept(this);
        Type typeSequence = letExpr.exprSeq.accept(this);
        tdsController.up();
        // "letExp: If the body is empty, the type is void, otherwise, the type is that of the last body expression"
        return typeSequence;
    }

    @Override
    public Type visit(Sequence node) {
        tdsController.down();
        // Applique la fonction visit sur chaque élément de la séquence et retourne le type de la dernière expression
        // Si la séquence est vide, retourne le type void
        Type typeSeq =  node.instructions.stream().map(instr -> instr.accept(this)).reduce((a, b) -> b).orElse(Type.VOID_TYPE);
        tdsController.up();
        return typeSeq;
    }

    @Override
    public Type visit(FunctionDeclaration node) {
        return null;
    }

    @Override
    public Type visit(VariableDeclaration node) {
        Type typeValue = node.value.accept(this);;
        if(node.typeId != null){
            checkIfTypeExist(node.typeId.identifier, node);
            if (!typeValue.getId().equals(node.typeId.identifier)) {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        String.format("Une valeur de type %s ne peut pas être affectée à une variable de type %s", typeValue, node.typeId.identifier));
            }
        }
        tdsController.add(new Value(typeValue, node.objectId.identifier));

        // Une déclaration n'a pas de type
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(ArrayTypeDeclaration node) {
        return null;
    }

    @Override
    public Type visit(NoRecordTypeDeclaration node) {
        return null;
    }

    @Override
    public Type visit(RecordTypeDeclaration node) {
        return null;
    }

    @Override
    public Type visit(For forExpr) {
        return null;
    }

    @Override
    public Type visit(IfThenElse ifThenElseExpr) {
        tdsController.down();
        Type conditionType=ifThenElseExpr.condExpr.accept(this);
        //Vérifier si le type de condition est entier
        if(!conditionType.equals(Type.INT_TYPE)){
            GestionnaireErreur.getInstance().addSemanticError(ifThenElseExpr.condExpr,"l'expression de la condition dans If est de type."+conditionType.getId()+" Or, elle doit avoir un type \u001B[31m entier");
        }
        Type thenType=ifThenElseExpr.thenExpr.accept(this);
        if(ifThenElseExpr.elseExpr!=null){
            Type elseType=ifThenElseExpr.elseExpr.accept(this);
            //Vérifier si thenExpr et ElseExpr ont le même type
            if(!(thenType.equals(elseType))){
                GestionnaireErreur.getInstance().addSemanticError(ifThenElseExpr.condExpr,"les expressions then et else renvoient respectivement des types " + thenType.getId()+" "+ elseType.getId()+ ".Or, elles doivent avoir le même type");
            }
            tdsController.up();
            return thenType;
        }
        else{
            //Vérifier si le type de l'expression
            if(!thenType.equals(Type.VOID_TYPE)){
                GestionnaireErreur.getInstance().addSemanticError(ifThenElseExpr.condExpr,"l'expression then renvoie un type"+thenType.getId() + " Or, elle doit avoir un type \u001B[31m void");
            }
            tdsController.up();
            return Type.VOID_TYPE;
        }
    }

    @Override
    public Type visit(While whileExpr) {
        return null;
    }

    @Override
    public Type visit(Addition node) {
        Type type1 = node.leftValue.accept(this);
        Type type2 = node.rightValue.accept(this);

        if (!type1.equals(Type.INT_TYPE) || !type2.equals(Type.INT_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Impossible d'additionner des types %s et %s", type1.getId(), type2.getId()));
        }
        // "+, -, *, /: The operands must be of type int and the result type is int"
        return Type.INT_TYPE;
    }

    @Override
    public Type visit(Affect node) {
        //l'affectation envoie un type void
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(And node) {
        return null;
    }

    @Override
    public Type visit(Division node) {
        return null;
    }

    @Override
    public Type visit(Equals node) {
        return null;
    }

    @Override
    public Type visit(Inferior node) {
        return null;
    }

    @Override
    public Type visit(InferiorOrEquals node) {
        return null;
    }

    @Override
    public Type visit(Multiplication node) {
        return null;
    }

    @Override
    public Type visit(Negation node) {
        return null;
    }

    @Override
    public Type visit(NotEquals node) {
        return null;
    }

    @Override
    public Type visit(Or node) {
        return null;
    }

    @Override
    public Type visit(Soustraction node) {
        return null;
    }

    @Override
    public Type visit(Superior node) {
        return null;
    }

    @Override
    public Type visit(SuperiorOrEquals node) {
        return null;
    }

    @Override
    public Type visit(ArrayAccess node) {
        return null;
    }

    @Override
    public Type visit(ArrayAssign node) {
        return null;
    }

    @Override
    public Type visit(FieldCreate node) {
        return null;
    }

    @Override
    public Type visit(FieldDeclaration node) {
        return null;
    }

    @Override
    public Type visit(FieldDecList node) {
        return null;
    }

    @Override
    public Type visit(FunctionCall node) {
        return null;
    }

    @Override
    public Type visit(DeclarationList node) {
        node.list.forEach(declaration -> declaration.accept(this));
        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(ParameterList node) {
        return null;
    }

    @Override
    public Type visit(RecordAccess node) {
        return null;
    }

    @Override
    public Type visit(RecordCreate node) {
        return null;
    }

    @Override
    public Type visit(Id node) {
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
