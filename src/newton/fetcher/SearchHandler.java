/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import java.util.Optional;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Meghdut Mandal
 */
public class SearchHandler {

    private final BatchView batchView;
    private final TabelSearchRenderer renderer;

    /**
     *
     * @param batchView
     */
    public SearchHandler(BatchView batchView) {
        this.batchView = batchView;
        this.renderer = new TabelSearchRenderer();
        this.batchView.getSeachField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                batchView.getResultScrollView().getVerticalScrollBar().repaint();

                searchUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                batchView.getResultScrollView().getVerticalScrollBar().repaint();

                searchUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                batchView.getResultScrollView().getVerticalScrollBar().repaint();

            }

        });

    }

    /**
     *
     * @return
     */
    public TabelSearchRenderer getRenderer() {
        return renderer;
    }

    /**
     *
     */
    public void searchUpdate() {
        String pattern = batchView.getSeachField().getText().trim();
        renderer.setPattern(pattern);
        batchView.getResultViewList().stream().forEach(ResultView::repaint);

        Optional<ResultView> findFirst = batchView.getResultViewList().stream().filter((res) -> res.hasMatch(pattern)).findFirst();

        if (findFirst.isPresent()) {
            ResultView get = findFirst.get();
            batchView.getResultScrollView().getVerticalScrollBar().setValue(get.getLocation().y - get.getHeight());
            batchView.getResultScrollView().getVerticalScrollBar().repaint();
        }

    }

}
