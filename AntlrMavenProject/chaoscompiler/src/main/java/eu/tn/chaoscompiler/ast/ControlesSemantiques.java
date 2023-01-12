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
import eu.tn.chaoscompiler.tdstool.variable.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ControlesSemantiques implements AstVisitor<Type> {
    // Couleurs pour l'affichage
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private TDScontroller tdsController;

    public boolean checkIfTypeExist(String type, Ast node) {
        if (!tdsController.existsType(type)) {
            GestionnaireErreur.getInstance().addSemanticError(node, "Le type " + type + " n'est pas déclaré");
            return false;
        }
        return true;
    }

    /**
     * Fonctions auxiliaires pour vérfier si le break existe en dehors des boucles
     */
    public boolean checkIfBreakExistsAsId(Id id) {
        if (id.identifier.equals("break")) {
            GestionnaireErreur.getInstance().addSemanticError(id, "Interdit d'utiliser break à l'extérieur des boucles For ou While");
            return true;
        }
        return false;
    }

    public boolean checkIfBreakExistsInBinaryOp(BinaryOperator bop) {
        if (bop.leftValue instanceof Id) {
            if( checkIfBreakExistsAsId((Id) bop.leftValue)){
                return true;
            }
        } else if (bop.rightValue instanceof Id) {
            if (checkIfBreakExistsAsId((Id) bop.rightValue)){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfBreakExistsInDecList(DeclarationList decList) {
        //Vérifier si l'instruction de type Id
        for (Ast expression : decList.list) {
            if (expression instanceof Id) {
                if (((Id) expression).identifier.equals("break")) {
                    GestionnaireErreur.getInstance().addSemanticError(decList, "Interdit d'utiliser break à l'extérieur des boucles For ou While");
                    return true;
                }
            } else if (expression instanceof BinaryOperator) {
                if(checkIfBreakExistsInBinaryOp((BinaryOperator) expression)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfBreakExistsInExprSeq(Sequence sequence) {
        //Vérifier si l'instruction de type Id
        for (Ast expression : sequence.instructions) {
            if (expression instanceof Id) {
                if (((Id) expression).identifier.equals("break")) {
                    GestionnaireErreur.getInstance().addSemanticError(sequence, "Interdit d'utiliser break à l'extérieur des boucles For ou While");
                    return  true;
                }
            } else if (expression instanceof BinaryOperator) {
                if(checkIfBreakExistsInBinaryOp((BinaryOperator) expression)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkIfBreakExistsInIfThenElse(Ast field){
        //the field is eighter a condExpr, thenExpr or ElseExpr
        if(field instanceof Id){
            if(((Id) field).identifier.equals("break")){
                GestionnaireErreur.getInstance().addSemanticError(field, "Interdit d'utiliser le mot clé 'break' à l'intérieur de IfThenElse");
                return true;
            }
        }
        if(field instanceof Sequence){
            if(checkIfBreakExistsInExprSeq((Sequence) field)){
                return true;
            }
        }
        return false;
    }

    // --------------------------------------------
    // |                VISITEURS                 |
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
        //Vérifier le cas d'utilisation de break dans DecList
        if (letExpr.decList != null) {
            checkIfBreakExistsInDecList((DeclarationList) letExpr.decList);
        }
        Type typeSequence = letExpr.exprSeq.accept(this);
        //Vérifier le cas d'utilisation de break dans Sequence
        if (letExpr.exprSeq != null) {
            checkIfBreakExistsInExprSeq((Sequence) letExpr.exprSeq);
        }
        tdsController.up();
        // "letExp: If the body is empty, the type is void, otherwise, the type is that of the last body expression"
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

        boolean correct = true ;

        // verifier que la variable n'existe pas deja
        if (tdsController.existsLocalVari(node.objectId.identifier)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("La variable %s a déjà été définie", node.objectId.identifier));
            correct = false ;
        }

        tdsController.down();

        // verifier validité type retour
        Type retour;
        if (node.returnType != null) {
            String returnStr = node.returnType.identifier;

            correct = checkIfTypeExist(returnStr, node) ;

            if (correct) {
                retour = tdsController.getTypeOfId(returnStr);
            }
            else {
                retour = Type.VOID_TYPE ;
            }

        } else {
            retour = Type.VOID_TYPE;
        }

        FunctionType fType = new FunctionType(node.objectId.identifier, retour) ;

        // verifier type de retour coherent avec contenu de la fonction
        Type content = node.content.accept(this);
        if (!retour.equals(content)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format(
                    "Le type de retour %s de la fonction %s est incompatible avec le type %s de la valeur retournée",
                    retour.getId(), node.objectId.identifier, content.getId()));
            correct = false ;
        }

        // Verifier que les types des arguments existent
        for (FieldDeclaration fd:node.fields.list) {
            if (checkIfTypeExist(fd.baseType.identifier, node)) {
                tdsController.add(new Value(tdsController.getTypeOfId(fd.baseType.identifier), fd.fieldId.identifier)) ;
                fType.addIn(tdsController.getTypeOfId(fd.baseType.identifier));
            }
            else {
                correct = false ;
            }
        }

        tdsController.up();

        if (correct) {
            tdsController.add(new Value(fType, node.objectId.identifier)) ;
        }
        //vérifier si le break est utilisé dans la Function Declaration
        //Vérifier si le nom de la fonction n'est pas celui d'un mot clé
        if(node.objectId!=null){
            if(node.objectId.identifier.equals("break")){
                GestionnaireErreur.getInstance().addSemanticError(node.objectId,"Interdit d'utiliser le mot clé 'break' comme nom de déclaration d'une fonction");
            }
        }

        //Vérifier si break est dans le corps de la fonction
        if(node.content!=null){
            if(node.content instanceof Sequence){
                checkIfBreakExistsInExprSeq((Sequence) node.content);
            }
            else if(node.content instanceof DeclarationList){
                checkIfBreakExistsInExprSeq((Sequence) node.content);
            }
            else if(node.content instanceof Id){
                checkIfBreakExistsAsId((Id) node.content);
            }
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(VariableDeclaration node) {

        boolean correct = true ;

        if (tdsController.existsLocalVari(node.objectId.identifier)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("La variable %s a déjà été définie", node.objectId.identifier));
            correct = false ;
        }

        Type typeValue = node.value.accept(this);

        if (node.typeId != null) {
            Type declaredType = tdsController.getTypeOfId(node.typeId.identifier);
            checkIfTypeExist(declaredType.getId(), node);
            if (!typeValue.equals(declaredType)) {
                GestionnaireErreur.getInstance().addSemanticError(node, String.format(
                            "Une valeur de type %s ne peut pas être affectée à une variable de type %s",
                            typeValue.getId(), node.typeId.identifier));
                correct = false ;
            }
            // Si le type est explicite on s'en sert pour créer la variable
            if (correct) {
                tdsController.add(new Value(declaredType, node.objectId.identifier));
            }
        } else if (correct) {
            // Sinon on s'adapte au type implicite
            tdsController.add(new Value(typeValue, node.objectId.identifier));
        }

        //Vérifier si le nom de la varaible n'est pas break
        if(node.objectId!=null){
            if(node.objectId.identifier.equals("break")){
                GestionnaireErreur.getInstance().addSemanticError(node.objectId, "Interdit d'utiliser le mot clé 'break' comme nom de déclaration d'une variable");
            }
        }
        if(node.value!=null){
            if(node.value instanceof  Id){
                if(((Id) node.value).identifier.equals("break")){
                    GestionnaireErreur.getInstance().addSemanticError(node.objectId, "Interdit d'utiliser le mot clé 'break' comme valeur d'une variable déclarée");
                }
            }
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(ArrayTypeDeclaration node) {

        boolean correct = true ;

        // verifier que le type n'existe pas deja
        if (tdsController.existsLocalType(node.objectId.identifier)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("Le type %s a déjà été défini", node.objectId.identifier));
            correct = false ;
        }

        if (node.baseTypeId == null) {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        "Le type du tableau doit être défini");
        } else if (checkIfTypeExist(node.baseTypeId.identifier, node) && correct) {
                Type elementType = tdsController.getTypeOfId(node.baseTypeId.identifier);
                tdsController.add(new ArrayType(node.baseTypeId.identifier, elementType));
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(NoRecordTypeDeclaration node) {

        // verifier que le type n'existe pas deja
        if (tdsController.existsLocalType(node.objectId.identifier)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("Le type %s a déjà été défini", node.objectId.identifier));
            // verifier que le type de base existe
            checkIfTypeExist(node.baseTypeId.identifier, node) ;
        }
        else {
            // verifier que le type de base existe
            if (checkIfTypeExist(node.baseTypeId.identifier, node)) {
                tdsController.add(new TypeRename(node.objectId.identifier, tdsController.getTypeOfId(node.baseTypeId.identifier)));
            }
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(RecordTypeDeclaration node) {

        boolean correct = true ;

        // verifier que le type n'existe pas deja
        if (tdsController.existsLocalType(node.objectId.identifier)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("Le type %s a déjà été défini", node.objectId.identifier));
            correct = false;
        }

        RecordType rType = new RecordType(node.objectId.identifier);

        // verifier types des attributs existe
        for (FieldDeclaration fd : node.fields.list) {
            if (checkIfTypeExist(fd.baseType.identifier, node)) {
                rType.addAttribut(new Value(tdsController.getTypeOfId(fd.baseType.identifier), fd.fieldId.identifier));
            } else {
                correct = false;
            }
        }

        if (correct) {
            tdsController.add(rType);
        }

        return Type.VOID_TYPE;
    }

    @Override
    public Type visit(For forExpr) {
        boolean continuer=true;
        tdsController.down();
        // Vérifier l'expression de départ est de type entier
        Type type_start_for = forExpr.startExpr.accept(this);
        if (!type_start_for.equals(Type.INT_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(forExpr.startExpr,
                    "le départ de l'indice de  la boucle for est de type " + type_start_for.getId()
                            + ". Or, il doit avoir un type " + ANSI_RED + " entier" + ANSI_RESET);
        }
        // Vérifier que l'expression de la fin doit être entière
        Type type_end_for = forExpr.endExpr.accept(this);
        if (!type_end_for.equals(Type.INT_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(forExpr.endExpr, "la fin de la boucle for est de type "
                    + type_end_for.getId() + ". Or, il doit avoir un type " + ANSI_RED + " entier" + ANSI_RESET);
        }
        // Vérifier que le contenu de la boucle est de type void
        Type typedoexpr = forExpr.doExpr.accept(this);
        if (!typedoexpr.equals(Type.VOID_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(forExpr.doExpr,
                    "l'expression à l'intérieur de for est de type " + typedoexpr.getId()
                            + ". Or, elle doit avoir un type " + ANSI_RED + " void" + ANSI_RESET);
        }
        // Vérifier que l'indice de la boucle n'est pas assigné

        Id id = (Id) forExpr.id;
        // Récupérer le nom de l'indice de la boucle
        String id_str = id.identifier;
        Sequence sequence = (Sequence) forExpr.doExpr;
        for (Ast instruction : sequence.instructions) {
            //Vérifier s'il y a une instruction d'affectation
            if (instruction instanceof Affect) {
                if(continuer){
                    //Vérifier si la partie gauche de l'instruction est un Id
                    if (((Affect) instruction).leftValue instanceof Id) {
                        String id_left_value = ((Id) ((Affect) instruction).leftValue).identifier;
                        //Vérifier si la valeur d'id est égale à l'indice de la boucle
                        if (id_left_value.equals(id_str)) {
                            GestionnaireErreur.getInstance().addSemanticError(forExpr.doExpr,
                                    "l'indice " + ANSI_RED + " " + id_left_value + ANSI_RESET + " de For ne doit pas être assigné à l'intérieur de la boucle");
                            //Pour afficher l'erreur une seule fois lorsque l'indice de la boucle est assigné plusieurs fois dans la séquence
                            continuer=false;
                        }
                    }
                }
            }
        }
        tdsController.up();
        return typedoexpr;
    }

    @Override
    public Type visit(IfThenElse ifThenElseExpr) {
        boolean continuer=true;
        tdsController.down();
        Type conditionType = ifThenElseExpr.condExpr.accept(this);
        if(conditionType!=null){
            // Vérifier si le type de condition est entier
            if (!conditionType.equals(Type.INT_TYPE)) {
                GestionnaireErreur.getInstance().addSemanticError(ifThenElseExpr.condExpr,
                        "l'expression de la condition dans If est de type " + conditionType.getId()
                                + ". Or, elle doit avoir un type " + ANSI_RED + " entier" + ANSI_RESET);
            }
        }

        //Vérifier si le champs condition ne contient pas break
        //la condExpr est toujours non nul, sinon une exception sera levée
        if(checkIfBreakExistsInIfThenElse(ifThenElseExpr.condExpr)){
            continuer=false;
        }


        Type thenType = ifThenElseExpr.thenExpr.accept(this);

        //thenExp est toujours non nulle sinon on aura un exception levée
        if(continuer){
            if(checkIfBreakExistsInIfThenElse(ifThenElseExpr.thenExpr)){
                continuer=false;
            }
        }
        if (ifThenElseExpr.elseExpr != null) {
            Type elseType = ifThenElseExpr.elseExpr.accept(this);
            //voir si else block contient break
            if(ifThenElseExpr.elseExpr!=null && continuer){
                checkIfBreakExistsInIfThenElse(ifThenElseExpr.elseExpr);
            }
            if(thenType!=null){
                // Vérifier si thenExpr et ElseExpr ont le même type
                if (!(thenType.equals(elseType))) {
                    GestionnaireErreur.getInstance().addSemanticError(ifThenElseExpr.condExpr,
                            "les expressions then et else renvoient respectivement des types " + thenType.getId() + " "
                                    + elseType.getId() + ". Or, elles doivent avoir le même type");
                }
            }
            tdsController.up();
            return thenType;
        } else {
            // Vérifier si le type de l'expression
            if (!thenType.equals(Type.VOID_TYPE)) {
                GestionnaireErreur.getInstance().addSemanticError(ifThenElseExpr.condExpr,
                        "l'expression then renvoie un type " + thenType.getId() + " Or, elle doit avoir un type "
                                + ANSI_RED + " void" + ANSI_RESET);
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
            GestionnaireErreur.getInstance().addSemanticError(whileExpr.condExpr,
                    "l'expression de la condition dans While est de type " + whilecond.getId()
                            + ". Or, elle doit avoir un type " + ANSI_RED + " entier" + ANSI_RESET);
        }
        // vérifier que l'expression à l'intérieur de block while est de type void
        Type typedoexpr = whileExpr.doExpr.accept(this);
        if (!typedoexpr.equals(Type.VOID_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(whileExpr.condExpr,
                    "l'expression à l'intérieur de while est de type " + typedoexpr.getId()
                            + ". Or, elle doit avoir un type " + ANSI_RED + " void" + ANSI_RESET);
        }
        tdsController.up();
        // While renvoie un type void
        return typedoexpr;
    }


    @Override
    public Type visit(Affect node) {

        // verifier membre de gauche conforme
        if (node.leftValue instanceof Id leftId) {
            if (!tdsController.existsVari(leftId.identifier)) {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        String.format("La variable %s n'est pas définie", leftId.identifier));
            }
        }
        else if (!((node.leftValue instanceof ArrayAccess) || (node.leftValue instanceof RecordAccess))) {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Le membre de gauche ne correspond pas à une variable, impossible d'y affecter une valeur"));
        }

        Type type1 = node.leftValue.accept(this);
        Type type2 = node.rightValue.accept(this);
        if(type1!=null && type2!=null){
            if (!type1.equals(type2)) {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        String.format("Impossible affecter une valeur de type %s à une variable de type %s", type2.getId(), type1.getId()));
            }
        }
        // l'affectation envoie un type void
        return Type.VOID_TYPE;
    }

    public Type equalOrInequal(BinaryOperator node){
        Type type1 = node.leftValue.accept(this);
        Type type2 = node.rightValue.accept(this);

        if (!(type1.equals(type2))) {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Impossible de comparer les types %s et %s", type1.getId(), type2.getId()));
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
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Une négation s'applique uniquement sur un type int ; type actuel : %s.",
                            entree));
        }
        return Type.INT_TYPE;
    }

    public Type operation(BinaryOperator node, String operation){
        Type type1 = node.leftValue.accept(this);
        Type type2 = node.rightValue.accept(this);

        if (!(type1.equals(Type.INT_TYPE)) || !(type2.equals(Type.INT_TYPE))) {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Impossible d'effectuer %s sur les types %s et %s", operation, type1.getId(), type2.getId()));
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
        Type type1 = node.leftValue.accept(this);
        Type type2 = node.rightValue.accept(this);

        if (!(type1.equals(Type.INT_TYPE) || type1.equals(Type.STRING_TYPE))
                || !(type2.equals(Type.INT_TYPE) || type2.equals(Type.STRING_TYPE))) {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Impossible d'effectuer une comparaison sur les types %s et %s", type1.getId(), type2.getId()));
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
        return equalOrInequal(node);
    }

    @Override
    public Type visit(InferiorOrEquals node) {
        return equalOrInequal(node);
    }


    @Override
    public Type visit(ArrayAccess node) {
        // verifier que l'exp designe bien un tableau
        Type tableau = node.exp.accept(this);
        Type tabElem;
        if (!(tableau instanceof ArrayType)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("L'expression ne désigne pas un tableau" +
                    "mais est de type %s", tableau.getId()));
            tabElem = Type.VOID_TYPE;
        } else {
            tabElem = ((ArrayType) tableau).getType();
        }

        // verfier que l'index est un entier
        Type index = node.index.accept(this);
        if (!index.equals(Type.INT_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("L'index d'accès au tableau n'est pas de type int mais de type %s", index.getId()));
        }

        // Retourner le type des elem du tableau
        return tabElem;
    }

    @Override
    public Type visit(ArrayAssign node) {

        Type arrayType = Type.VOID_TYPE ;

        // verifier que le type est bien un type d'array
        if (checkIfTypeExist(((Id) node.type).identifier, node)) {
            arrayType = tdsController.findType(((Id) node.type).identifier) ;
            
            if (arrayType instanceof ArrayType at) {

                // verifier valeur d'initialisation est conforme au type de l'array
                Type contentType = node.element.accept(this) ;
                if (!(contentType.equals(at.elementsType))) {
                    GestionnaireErreur.getInstance().addSemanticError(node,
                            String.format("La valeur d'initialisation est attendue de type %s mais est de type %s",
                                    at.elementsType.getId(), contentType.getId()));
                }

            }
            else {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        String.format("Le type %s ne correspond pas à un array", arrayType.getId()));
                arrayType = Type.VOID_TYPE ;
            }
        }

        // verifier taille est entiere
        Type nbElemType = node.nombreDElements.accept(this) ;
        if (!nbElemType.equals(Type.INT_TYPE)) {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("La taille de l'array n'est pas un int mais un %s", nbElemType.getId()));
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
        // verifier que l'id est l'id d'une fonction qui existe et recuperer son type de retour
        Type func = node.id.accept(this);
        Type retour;
        checkIfTypeExist(func.getId(), node);
        if (!(func instanceof FunctionType)) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("La variable %s n'est pas une" +
                    "fonction", ((Id) node.id).identifier));
            retour = Type.VOID_TYPE;
        } else if (!tdsController.existsType(func.getId())) {
            retour = Type.VOID_TYPE;
        } else {
            retour = ((FunctionType) func).outType;
        }

        // verifier le nombre d'arguments
        int nbArgsCall = ((ParameterList) node.argList).parameters.size();
        int nbArgsExpected = ((FunctionType) func).inTypes.size();
        if (nbArgsCall != nbArgsExpected) {
            GestionnaireErreur.getInstance().addSemanticError(node, String.format("Il y a %d arguments " +
                    "alors que %d arguments sont attendus", nbArgsCall, nbArgsExpected));
        }

        // verifier le type des arguments
        for (int i = 0; (i < nbArgsCall) && (i < nbArgsExpected); i++) {
            Type argType = ((ParameterList) node.argList).parameters.get(i).accept(this);
            Type argTypeExpected = ((FunctionType) func).inTypes.get(i);
            if (!argType.equals(argTypeExpected)) {
                GestionnaireErreur.getInstance().addSemanticError(node, String.format("L'argument %d " +
                        "est de type %s alors qu'il est attendu de type %s", i, argType.getId(), argTypeExpected.getId()));
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

        Type resType = Type.VOID_TYPE ;

        // verifier que le membre de gauche est bien de type record
        Type rType = node.exp.accept(this) ;
        if (rType instanceof RecordType rt) {
            // verifier que l'attribut existe
            Value v = rt.getAttribut(((Id) node.index).identifier) ;
            if (v == null) {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        String.format("Le type record %s n'a pas d'attribut %s", rt.getId(), ((Id) node.index).identifier));
            }
            else {
                resType = v.getType() ;
            }
        }
        else {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("Le type %s n'est pas un record", rType.getId())) ;
        }

        return resType;
    }

    @Override
    public Type visit(RecordCreate node) {

        Type resType = Type.VOID_TYPE ;

        //verifier que variable existe
        if (tdsController.existsVari(((Id) node.idObject).identifier)) {

            Type varType = tdsController.getVariableOfId(((Id) node.idObject).identifier).getType() ;
            // verifier que type de la variable correspond à un record
            if (varType instanceof RecordType rt) {

                // verifier que les attributs existent
                for (Ast fc:node.args) {

                    Value v = rt.getAttribut(((Id) ((FieldCreate) fc).id).identifier) ;
                    if (v != null) {
                        // verifier que la valeur affectee est du bon type
                        Type expType = ((FieldCreate) fc).expr.accept(this) ;
                        if (!(expType.equals(v.getType()))) {
                            GestionnaireErreur.getInstance().addSemanticError(node,
                                    String.format("La valeur affectée à l'attribut %s doit être de type %s, pas de type %s",
                                            v.getId(), v.getType().getId(), expType.getId()));
                        }
                    }
                    else {
                        GestionnaireErreur.getInstance().addSemanticError(node,
                                String.format("Le type record %s n'a pas d'attribut %s",
                                        rt.getId(), ((Id) ((FieldCreate) fc).id).identifier));
                    }
                }
            }
            else {
                GestionnaireErreur.getInstance().addSemanticError(node,
                        String.format("Le type %s ne correspond pas à un record", varType.getId()));
            }

        }
        else {
            GestionnaireErreur.getInstance().addSemanticError(node,
                    String.format("La variable %s n'est pas définie", ((Id) node.idObject).identifier));
        }

        return resType;
    }

    @Override
    public Type visit(Id node) {
        //si le noeud contient le mot clé break, il retourne un type void
        if(node.identifier.equals("break")){
            return Type.VOID_TYPE;
        }
        // a faire : si il est dans la tds, type de la variable


        // sinon
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
