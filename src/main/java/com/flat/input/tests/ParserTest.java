package com.flat.input.tests;

import com.flat.FLATLexer;
import com.flat.FLATParser;
import com.flat.algorithms.*;
import com.flat.algorithms.models.NDWffTree;
import com.flat.algorithms.models.TruthTree;
import com.flat.algorithms.predicate.*;
import com.flat.algorithms.propositional.PDFTruthTablePrinter;
import com.flat.algorithms.propositional.PropositionalNaturalDeductionValidator;
import com.flat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.flat.algorithms.propositional.TexTablePrinter;
import com.flat.input.FLATErrorListener;
import com.flat.input.FLATErrorStrategy;
import com.flat.input.FLATParserAdapter;
import com.flat.input.FLATParserListener;
import com.flat.models.treenode.WffTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

/**
 * Basic parser tester. Has a main() so can be run from the command line, with
 * one optional command line parameter. If provided, this is a filename to use
 * for input. Otherwise, input is taken from standard input. More importantly,
 * the parseFromFile and parseFromStdin methods are public static methods and
 * can be called from automated tests. They return the
 * FLATParserListener object that was used in parsing, giving
 * access to both the final syntax tree and the final symbol table.
 *
 * @author Steve Tate (srtate@uncg.edu)
 * @modified Joshua Crotts
 * @date 3/30/2021
 */
public class ParserTest {

    /**
     * Public static method to run the parser on an input file.
     *
     * @param fileName the name of the file to use for input
     */
    public static FLATParserListener parseFromFile(String fileName) {
        try {
            return parseStream(CharStreams.fromFileName(fileName));
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                System.err.println("Could not open file " + fileName);
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Command line interface -- one argument is filename, and if omitted then input
     * is taken from standard input.
     *
     * @param argv command line arguments
     */
    public static void main(String[] argv) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<WffTree> resultList = FLATParserAdapter.getAbstractSyntaxTree(reader.readLine());
        if (resultList == null) {
            return;
        }

        // If we only have one WffTree, we can do the simple operations.
        if (resultList.size() == 1) {
            WffTree result = resultList.get(0);
            result.printSyntaxTree();

            // Print the parse tree in LaTeX.
            TexPrinter texParseTreePrinter = new TexParseTreePrinter(result, "latex_parse_tree.tex");
            texParseTreePrinter.outputToFile();

            // Prints the parse tree to a PDF.
            PDFPrinter pdfParseTreePrinter = new PDFParseTreePrinter(result, "pdf_parse_tree.pdf");
            pdfParseTreePrinter.outputToFile();

            BaseTruthTreeGenerator truthTreeGenerator;
            if (result.isPredicateWff()) {
                System.out.println("Bound variables: " + new BoundVariableDetector(result).getBoundVariables());
                System.out.println("Free variables: " + new FreeVariableDetector(result).getFreeVariables());
                System.out.println("Open sentence: " + new OpenSentenceDeterminer(result).isOpenSentence());
                System.out.println("Closed sentence: " + new ClosedSentenceDeterminer(result).isClosedSentence());
                System.out.println("Ground sentence: " + new GroundSentenceDeterminer(result).isGroundSentence());
                truthTreeGenerator = new PredicateTruthTreeGenerator(result);
            } else {
                // Print the truth table in LaTeX.
                TexPrinter texTruthTablePrinter = new TexTablePrinter(result, "latex_truth_table.tex");
                texTruthTablePrinter.outputToFile();
                truthTreeGenerator = new PropositionalTruthTreeGenerator(result);

                // Print the truth table to a PDF.
                PDFPrinter pdfTruthTablePrinter = new PDFTruthTablePrinter(result, "latex_truth_table.pdf");
                pdfTruthTablePrinter.outputToFile();
            }
            // Generate the truth tree and print it to the console.
            TruthTree truthTree = truthTreeGenerator.getTruthTree();
            System.out.println("Truth Tree: \n" + truthTreeGenerator.print(truthTree));

            // Print the truth tree in LaTeX.
            TexPrinter texTruthTreePrinter = new TexTruthTreePrinter(truthTree, "latex_truth_tree.tex");
            texTruthTreePrinter.outputToFile();

            // Prints the truth tree to a PDF.
            PDFPrinter pdfTruthTreePrinter = new PDFTruthTreePrinter(truthTree, "latex_truth_tree.pdf");
            pdfTruthTreePrinter.outputToFile();

            // Display the main operator.
            System.out.println("Main operator: " + new MainOperatorDetector(result).getMainOperator());

            // Determine if it's a tautology.
            LogicalTautologyDeterminer tautologyDet = new LogicalTautologyDeterminer(result);
            System.out.println("Logical Tautology: " + tautologyDet.isTautology());

            // Determine if it's a falsehood.
            LogicalFalsehoodDeterminer falsehoodDet = new LogicalFalsehoodDeterminer(result);
            System.out.println("Logical Falsehood: " + falsehoodDet.isFalsehood());

            // Determine if it's contingent.
            LogicallyContingentDeterminer consistentDet = new LogicallyContingentDeterminer(result);
            System.out.println("Logical Contingent: " + consistentDet.isContingent());
        }
        // If there are two, we can generate logical relationships.
        else if (resultList.size() == 2) {
            // Pull the two children from their root.
            WffTree ch1 = resultList.get(0);
            WffTree ch2 = resultList.get(1);
            LogicallyEquivalentDeterminer logDet = new LogicallyEquivalentDeterminer(ch1, ch2);
            System.out.println("Logically Equivalent: " + logDet.isEquivalent());

            LogicallyConsistentDeterminer consistentDet = new LogicallyConsistentDeterminer(ch1, ch2);
            System.out.println("Logically Consistent: " + consistentDet.isConsistent());

            LogicallyContradictoryDeterminer contradictoryDet = new LogicallyContradictoryDeterminer(ch1, ch2);
            System.out.println("Logically Contradictory: " + contradictoryDet.isContradictory());

            LogicallyContraryDeterminer contraryDet = new LogicallyContraryDeterminer(ch1, ch2);
            System.out.println("Logically Contrary: " + contraryDet.isContrary());

            LogicallyImpliedDeterminer impliedDet = new LogicallyImpliedDeterminer(ch1, ch2);
            System.out.println("Logically Implied: " + impliedDet.isImplied());
        }

        // If we have at least two wffs, we can see if they form a valid or invalid argument.
        if (resultList.size() >= 2) {
            // Argument validator (truth tree test).
            ArgumentTruthTreeValidator validator = new ArgumentTruthTreeValidator(resultList);
            System.out.println("Deductively valid: " + validator.isValid());
            BaseNaturalDeductionValidator ndValidator = null;
            if (resultList.get(0).isPropositionalWff()) {
                System.out.println("PL Natural Deduction:");
                ndValidator = new PropositionalNaturalDeductionValidator(resultList);
            } else if (resultList.get(0).isPredicateWff()) {
                System.out.println("FOPL Natural Deduction:");
                ndValidator = new PredicateNaturalDeductionValidator(resultList);
            }

            // Natural deduction prover.
            ArrayList<NDWffTree> ndArgs = ndValidator.getNaturalDeductionProof();
            if (ndArgs == null) {
                System.err.println("Either the argument is invalid (check the above result) or it timed out!");
            } else {
                for (int i = 0; i < ndArgs.size(); i++) {
                    NDWffTree wff = ndArgs.get(i);
                    System.out.println((i + 1) + ": " + wff);
                }
                System.out.println("∴ " + ndArgs.get(ndArgs.size() - 1).getWffTree().getStringRep() + "  ■");
            }

            PDFPrinter ttp = new PDFTruthTreePrinter(validator.getTruthTree(), "latex-truth-tree.pdf");
            ttp.outputToFile();

            if (ndValidator.getNaturalDeductionProof() != null) {
                PDFPrinter ndp = new PDFNaturalDeductionPrinter(ndValidator.getNaturalDeductionProof(), "latex-nd.pdf");
                ndp.outputToFile();
            }
        }
    }

    /**
     * Runs the parser and syntax tree constructor for the provided input stream.
     * The returned object can be used to access the syntax tree and the symbol table
     * for either further processing or for checking results in automated tests.
     *
     * @param input an initialized CharStream
     */
    private static FLATParserListener parseStream(CharStream input) {
        // "input" is the character-by-character input - connect to lexer
        FLATLexer lexer = new FLATLexer(input);
        FLATErrorListener errorListener = new FLATErrorListener();
        FLATErrorStrategy errorStrategy = new FLATErrorStrategy();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        // Connect token stream to lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Connect parser to token stream
        FLATParser parser = new FLATParser(tokens);
        parser.removeErrorListeners();
        parser.setErrorHandler(errorStrategy);
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.program();

        // Now do the parsing, and walk the parse tree with our listeners
        ParseTreeWalker walker = new ParseTreeWalker();
        FLATParserListener compiler = new FLATParserListener(parser);
        walker.walk(compiler, tree);

        return compiler;
    }
}
