package de.erdesignerng.visual.common;

import de.erdesignerng.io.GenericFileFilter;
import de.erdesignerng.model.ModelItem;
import de.erdesignerng.visual.ExportType;
import de.erdesignerng.visual.jgraph.JGraphEditor;
import de.erdesignerng.visual.jgraph.cells.views.TableCellView;
import de.erdesignerng.visual.jgraph.export.Exporter;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultGraphCell;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import static javax.swing.JOptionPane.showMessageDialog;

public class ExportScreenshotCommand extends UICommand {

    private final Exporter exporter;

    private final ExportType exportType;

    private final JGraphEditor editor;

    private static int fileNumber = 0;

    private final File theBaseDirectory = new File("C:\\Users\\Azzambz\\Documents");

    public ExportScreenshotCommand(JGraphEditor aEditor, Exporter aExporter, ExportType aExportType) {
        editor = aEditor;
        exporter = aExporter;
        exportType = aExportType;
    }

    @Override
    public void execute() {

        if (exportType == ExportType.ONE_PER_FILE) {
            CellView[] theViews = editor.getGraph().getGraphLayoutCache().getAllViews();
            for (CellView theView : theViews) {
                if (theView instanceof TableCellView) {
                    TableCellView theItemCellView = (TableCellView) theView;
                    DefaultGraphCell theItemCell = (DefaultGraphCell) theItemCellView.getCell();
                    ModelItem theItem = (ModelItem) theItemCell.getUserObject();

                    File theOutputFile = new File(theBaseDirectory, theItem.getName() + exporter.getFileExtension());
                    try {
                        exporter.exportToStream(theItemCellView.getRendererComponent(editor.getGraph(), false, false,
                                false), new FileOutputStream(theOutputFile));

                    } catch (Exception e) {
                        getWorldConnector().notifyAboutException(e);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Screenshot berhasil didapatkan.");

        } else {
            String filename = String.format("Screenshot%02d", fileNumber++);

            File theFile = new File(theBaseDirectory, filename + exporter.getFileExtension());
            try {
                exporter.fullExportToStream(editor.getGraph(), new FileOutputStream(theFile));
                JOptionPane.showMessageDialog(null, "Screenshot berhasil didapatkan.");
            } catch (Exception e) {
                getWorldConnector().notifyAboutException(e);
            }

        }
    }
}