package com.flat.algorithms.propositional;

import com.flat.algorithms.PdfPrinter;
import com.flat.models.treenode.WffTree;
import com.flat.tools.FLATUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * 
 */
public final class PdfTruthTablePrinter extends PdfPrinter {

    /**
     * Template location to read from.
     */
    private static final String TEX_TABLE_TEMPLATE = "src/main/resources/tex_truth_table_template.tex";

    public PdfTruthTablePrinter(WffTree _tree, String _outputFile) {
        super(_tree, _outputFile);
    }

    /**
     * Outputs the truth table for a propositional logic formula in LaTeX to a pdf.
     * We first create a TruthTableGenerator algorithm, then traverse its contents
     * for the truth values. These [nodes] are inserted in post-order, so atomic/
     * non-complex sentences are inserted first. The ordering isn't guaranteed to
     * put the plain atoms first, but that's a feature for another time.
     */
    public void outputToFile() {
        // First make sure that we actually can generate this tree.
        TruthTableGenerator ttg = new TruthTableGenerator(this.getWffTree());
        if (!ttg.getTruthTable()) {
            System.err.println("Could not create truth table.");
            return;
        }
        LinkedHashSet<WffTree> postorderTraversal = new LinkedHashSet<>(ttg.getWffTree().postorderTraversal());

        try {
            this.setBufferedReader(new BufferedReader(new FileReader(TEX_TABLE_TEMPLATE)));
            StringBuilder httpTex = new StringBuilder();

            // First copy the template over.
            int ch = this.getBufferedReader().read();
            while (ch != -1) {
                httpTex.append((char) ch);
                ch = this.getBufferedReader().read();
            }
            this.getBufferedReader().close();

            // Append the table code to this request.
            httpTex.append(this.getTexTable(postorderTraversal));
            httpTex.append("\n\\end{tabular}\n\n\\end{document}\n");

            // Build the URL and HTTP request.
            String texURL = "https://latex.ytotech.com/builds/sync";
            PdfPrinter.downloadFile(texURL, this.getOutputFile(), httpTex.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds the truth table. Initializes the string builder with the column
     * sizes, headers, then populates the rows with the boolean values. We substitute
     * true for T and false for F.
     *
     * @param _set - set of WffTree nodes that are collected in postorder prior to
     *             this method.
     * @return String representation of the TeX file.
     */
    private String getTexTable(LinkedHashSet<WffTree> _set) {
        StringBuilder sb = new StringBuilder();
        ArrayList<WffTree> list = new ArrayList<>(_set);
        // The last element is just ROOT so we can remove it.
        list.remove(list.size() - 1);
        int rows = list.get(0).getTruthValues().size();

        // Print the preamble stuff.
        sb.append("\\begin{tabular}{");
        sb.append(FLATUtils.repeatString(list.size() - 1, "c|"));
        sb.append("c}\n");

        // First print the headers.
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append("$" + list.get(i).getTexCommand() + "$");
            sb.append(" & ");
        }

        // Output the hline separator.
        sb.append("$").append(list.get(list.size() - 1).getTexCommand()).append("$");
        sb.append("\\\\\n\\hline\n");

        // Now print the truth values.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows - 1; j++) {
                sb.append(list.get(j).getTruthValues().get(i));
                sb.append(" & ");
            }

            sb.append(list.get(rows - 1).getTruthValues().get(i));
            sb.append(" \\\\ ");
            if (i != rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
