/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import newton.resultApi.CBSEResult;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meghdut Mandal
 */
public class DCBSEResultLoader extends CBSEResultLoader {

    /**
     *
     * @param App
     */
    public DCBSEResultLoader(final App App) {
        super(App);
    }

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public List<CBSEResult> doInBackground() throws IOException {
        try {
            isRunning = true;
            CBSEResult result;

            File list = new File("test\\");
            File[] listFiles = list.listFiles();
            for (int i = 0; i < listFiles.length && isRunning; i++) {
                File listFile = listFiles[i];
                result = app.getCBSEFetcher().getResult(listFile);
                if (result != null) {
                    this.publish(result);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            ErrorDialog.showError(ex.getMessage(), ex);
            return currentBatch.getResults();
        }
        return currentBatch.getResults();
    }

}
