package com.redhat.ceylon.compiler.js;

import java.io.OutputStreamWriter;
import java.io.Writer;

import com.redhat.ceylon.compiler.typechecker.TypeChecker;
import com.redhat.ceylon.compiler.typechecker.analyzer.AnalysisError;
import com.redhat.ceylon.compiler.typechecker.context.PhasedUnit;
import com.redhat.ceylon.compiler.typechecker.tree.Message;
import com.redhat.ceylon.compiler.typechecker.tree.Node;
import com.redhat.ceylon.compiler.typechecker.tree.Visitor;

public class JsCompiler {
    
    private final TypeChecker tc;
    
    public JsCompiler(TypeChecker tc) {
        this.tc = tc;
    }

    public void generate() {
        for (PhasedUnit pu: tc.getPhasedUnits().getPhasedUnits()) {
            pu.getCompilationUnit().visit(new GenerateJsVisitor(getWriter(pu)));
            pu.getCompilationUnit().visit(new Visitor() {
                @Override
                public void visitAny(Node that) {
                    for (Message err: that.getErrors()) {
                        if (err instanceof AnalysisError) {
                            System.err.println(
                                "error encountered [" +
                                err.getMessage() + "]");
                        }
                    }
                    super.visitAny(that);
                }
            });
        }
        finish();
    }
    
    protected Writer getWriter(PhasedUnit pu) {
        return new OutputStreamWriter(System.out);
    }
    
    protected void finish() {}
}
