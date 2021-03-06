import java.io.File;

import com.redhat.ceylon.compiler.js.JsCompiler;
import com.redhat.ceylon.compiler.typechecker.TypeChecker;
import com.redhat.ceylon.compiler.typechecker.TypeCheckerBuilder;

/**
 * Entry point for the type checker
 * Pass the source diretory as parameter. The source directory is relative to
 * the startup directory.
 *
 * @author Gavin King <gavin@hibernate.org>
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
public class MainForJs {

    /**
     * Files that are not under a proper module structure are placed under a <nomodule> module.
     */
    public static void main(String[] args) throws Exception {
        String path;
        if ( args.length==0 ) {
            System.err.println("Usage Main <directoryName>");
            System.exit(-1);
            return;
        }
        else {
            path = args[0];
        }
        
        boolean noisy = "true".equals(System.getProperties().getProperty("verbose"));

        final TypeChecker typeChecker = new TypeCheckerBuilder()
                .verbose(noisy)
                .addSrcDirectory(new File(path))
                .getTypeChecker();
        typeChecker.process();
        new JsCompiler(typeChecker).generate();
        //getting the type checker does process all types in the source directory
    }
}
