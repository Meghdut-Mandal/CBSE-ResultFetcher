/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

/**
 *
 * @author Meghdut Mandal
 */
public interface ProcessObserver {

    /**
     *
     */
    ProcessObserver NULL_OBSERVER = new ProcessObserver() {

        @Override
        public void publishString(String text) {
        }

        @Override
        public void setProgress(double d) {
        }

        @Override
        public void finished(CBSEResult res) {
        }
    };

    /**
     *
     * @param text
     */
    public void publishString(String text);

    /**
     *
     * @param d
     */
    public void setProgress(double d);

    /**
     *
     * @param res
     */
    public void finished(CBSEResult res);
}
